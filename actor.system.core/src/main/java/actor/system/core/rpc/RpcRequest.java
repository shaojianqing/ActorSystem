package actor.system.core.rpc;

import java.io.Serializable;

import actor.system.core.ActorRef;
import actor.system.core.message.Message;

public class RpcRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Message message;
	
	private ActorRef sender;
	
	private ActorRef receiver;
	
	public RpcRequest(Message message, ActorRef sender, ActorRef receiver) {
		this.message = message;
		this.sender = sender;
		this.receiver = receiver;
	}

	public static RpcRequest build(ActorRef sender, ActorRef receiver, Message message) {
		return new RpcRequest(message, sender, receiver);
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public ActorRef getSender() {
		return sender;
	}

	public void setSender(ActorRef sender) {
		this.sender = sender;
	}

	public ActorRef getReceiver() {
		return receiver;
	}

	public void setReceiver(ActorRef receiver) {
		this.receiver = receiver;
	}
}
