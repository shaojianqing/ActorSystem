package actor.system.core.rpc;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.core.ActorSystem;
import actor.system.util.ProtocolUtil;
import actor.system.util.ServerUtil;
import actor.system.util.ServerUtil.RequestProcessor;
import actor.system.util.ThreadUtil;

public class RpcServer {
	
	private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);
	
	public static void startServer(final int port) {
		
		ThreadUtil.run(new Runnable(){

			public void run() {
				ServerUtil.accept(port, new RequestProcessor(){

					public byte[] onService(byte[] request) {
						try {
							RpcRequest rpcRequest = (RpcRequest)ProtocolUtil.transToObject(request);
							boolean result = ActorSystem.send(rpcRequest.getMessage(), 
									rpcRequest.getSender(), rpcRequest.getReceiver());
							
							RpcResult rpcResult = new RpcResult(result, "发送消息成功!");
							return ProtocolUtil.transToByte(rpcResult);
						} catch (Exception e) {
							logger.error("Accept Message Raise Exception!", e);
							String errorMsg = String.format("发送消息出现异常:%s", e.getMessage());
							RpcResult rpcResult = new RpcResult(false, errorMsg);
							try {
								return ProtocolUtil.transToByte(rpcResult);
							} catch (IOException e1) {
								logger.error("Protocol Serialize Exception!", e1);
							}
						}
						return null;
					}
				});
			}
		});
	}
}
