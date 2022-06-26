package actor.system.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.core.message.Message;
import actor.system.core.rpc.RpcRequest;
import actor.system.core.rpc.RpcResult;
import actor.system.core.rpc.RpcTransport;
import actor.system.util.AssertUtil;

public class ActorSystem {
	
	private static final Logger logger = LoggerFactory.getLogger(ActorSystem.class);
	
	private static Map<ActorPath, ActorRef> actorMap = new ConcurrentHashMap<ActorPath, ActorRef>();
	
	public static void register(ActorRef actorRef) {
		actorMap.put(actorRef.getActorPath(), actorRef);
	}
	
	public static void unregister(ActorRef actorRef) {
		actorMap.remove(actorRef.getActorPath());
	}
	
	public static ActorRef getActor(ActorPath path) {
		return actorMap.get(path);
	}
	
	public static boolean send(Message message, ActorRef sender, ActorRef receiver) {
		try {
			AssertUtil.isNotNull(message, "发送消息不能为空!");
			AssertUtil.isNotNull(receiver, "消息接收者不能为空!");
			
			if (receiver.isLocal()) {
				receiver = actorMap.get(receiver.getActorPath());
				receiver.getTarget().willReceive(message);
				return true;
			} else {
				RpcRequest rpcRequest = RpcRequest.build(sender, receiver, message);
				RpcResult rpcResult = RpcTransport.request(rpcRequest);
				if (!rpcResult.isSuccess()) {
					logger.error("发送消息出现异常:path:{},error:{}", receiver.getActorPath(), rpcResult.getMessage());
				}
				return rpcResult.isSuccess();
			}
		} catch(Exception e) {
			logger.error(String.format("发送消息出现异常:%s", e.getMessage()), e);
			return false;
		}
	}
}
