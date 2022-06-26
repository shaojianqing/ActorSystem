package actor.system.core.task;

import actor.system.core.Actor;
import actor.system.core.message.Message;

public class ActorTask implements Runnable {

	private Actor actor;
	
	private Message message;
	
	public ActorTask(Actor actor, Message message) {
		this.actor = actor;
		this.message = message;
	}

	public void run() {
		actor.setSender(message.getSender());
		actor.onReceive(message.getContent());
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ActorTask [actor=" + actor + ", message=" + message + "]";
	}
}
