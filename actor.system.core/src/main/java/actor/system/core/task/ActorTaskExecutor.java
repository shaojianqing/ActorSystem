package actor.system.core.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActorTaskExecutor {
	
	private static ExecutorService threadPoolExecutor = Executors.newCachedThreadPool();

	public static void startTask(ActorTask actorTask) {
		threadPoolExecutor.execute(actorTask);
	}
}