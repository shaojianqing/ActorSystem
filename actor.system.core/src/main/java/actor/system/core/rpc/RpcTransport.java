package actor.system.core.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.util.NetworkUtil;
import actor.system.util.ProtocolUtil;

public class RpcTransport {
	
	private static final Logger logger = LoggerFactory.getLogger(RpcTransport.class);

	public static RpcResult request(RpcRequest request) {
		ActorRef receiver = request.getReceiver();
		ActorPath actorPath = receiver.getActorPath();
		try {
			byte[] content = ProtocolUtil.transToByte(request);
			byte[] result = NetworkUtil.sendRequest(actorPath.getHost(), actorPath.getPort(), content);
			return (RpcResult)ProtocolUtil.transToObject(result);
		} catch (Exception e) {
			String errorMsg = String.format("Request Raise Exception,host:%s, port:%d", 
					actorPath.getHost(), actorPath.getPort());
			logger.error(errorMsg, e);
			return new RpcResult(false, e.getMessage());
		}
	}
}
