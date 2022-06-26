package actor.system.core.task;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class NamedThreadFactory implements ThreadFactory {
	
	private String name;
	
	private AtomicLong index = new AtomicLong(0);

	public NamedThreadFactory(String name) {
		super();
		this.name = name;
	}

	public Thread newThread(Runnable r) {
		String threadName = String.format("%s-%d", name, index.getAndIncrement());
		Thread thread = new Thread(threadName);
		return thread;
	}
}
