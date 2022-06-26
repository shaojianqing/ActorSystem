package actor.system.core;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.core.message.Message;
import actor.system.core.task.ActorTask;
import actor.system.core.task.ActorTaskExecutor;

public abstract class AbstractActor implements Actor {
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractActor.class);
	
	private Map<Class<?>, Method> methodMap = new ConcurrentHashMap<Class<?>, Method>();
	
	private Queue<Message> messageQueue = new ConcurrentLinkedDeque<Message>();
	
	private ActorRef self;
	
	private ActorRef sender;
	
	public AbstractActor() {
		
		Method[] methods = this.getClass().getMethods();
		for (Method method:methods) {
			if (method.isAnnotationPresent(OnMessage.class)); {
				OnMessage onMessage = method.getAnnotation(OnMessage.class);
				if (onMessage!=null && onMessage.value()!=null) {
					Class<?> clazz = onMessage.value();
					methodMap.put(clazz, method);
				}
			}
		}
	}

	public void onReceive(Object content) {
		try {
			Method method = methodMap.get(content.getClass());
			if (method==null) {
				logger.error(String.format("Method does not exist! Content:%s", content));
			}
			method.invoke(this, content);
		} catch(Exception e) {
			logger.error("Receive Message Raise Exception!", e);
		}
	}

	public void willReceive(Message message) {
		if (message!=null) {
			messageQueue.offer(message);
			tryDispatch();
		}
	}

	private void tryDispatch() {
		try {
			Message message = messageQueue.poll();
			ActorTask actorTask = new ActorTask(this, message);
			ActorTaskExecutor.startTask(actorTask);
		} catch (Exception e) {
			logger.error("Execute ActorTask Raise Exception!", e);
		}
	}

	public ActorRef getSelf() {
		return self;
	}

	public void setSelf(ActorRef self) {
		this.self = self;
	}

	public ActorRef getSender() {
		return sender;
	}

	public void setSender(ActorRef sender) {
		this.sender = sender;
	}

	public void destroy() {
		ActorSystem.unregister(self);
	}
}
