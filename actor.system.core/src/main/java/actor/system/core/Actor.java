package actor.system.core;

import actor.system.core.message.Message;

public interface Actor {
	
	public void onReceive(Object content);
	
	public void willReceive(Message message);
	
	public void setSender(ActorRef sender);
	
	public void setSelf(ActorRef sender);
	
	public void destroy();
}
