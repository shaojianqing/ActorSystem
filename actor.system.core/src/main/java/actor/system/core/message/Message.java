package actor.system.core.message;

import java.io.Serializable;

import actor.system.core.ActorRef;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ActorRef sender;
	
	private Object content;

	public ActorRef getSender() {
		return sender;
	}

	public void setSender(ActorRef sender) {
		this.sender = sender;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "Message[sender=" + sender + ", content=" + content + "]";
	}
}
