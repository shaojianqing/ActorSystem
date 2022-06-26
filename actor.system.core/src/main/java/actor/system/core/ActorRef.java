package actor.system.core;

import java.io.Serializable;

public class ActorRef implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private ActorPath actorPath;
	
	private transient Actor target;
	
	public ActorRef(ActorPath actorPath, Actor target) {
		this.actorPath = actorPath;
		this.target = target;
	}

	public boolean isLocal() {
		return actorPath.isLocal();
	}
	
	public static ActorRef createActorRef(ActorPath actorPath, Actor target) {
		ActorRef actorRef = new ActorRef(actorPath, target);
		target.setSelf(actorRef);
		ActorSystem.register(actorRef);
		return actorRef;
	}
	
	public static ActorRef createActorRef(ActorPath actorPath) {
		ActorRef actorRef = new ActorRef(actorPath, null);
		ActorSystem.register(actorRef);
		return actorRef;
	}

	public ActorPath getActorPath() {
		return actorPath;
	}

	public void setActorPath(ActorPath actorPath) {
		this.actorPath = actorPath;
	}

	public Actor getTarget() {
		return target;
	}

	public void setTarget(Actor target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return "ActorRef [actorPath=" + actorPath + ", target=" + target + "]";
	}
}
