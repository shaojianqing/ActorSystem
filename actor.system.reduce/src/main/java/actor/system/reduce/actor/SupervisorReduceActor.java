package actor.system.reduce.actor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.cmd.StartReduceCmd;
import actor.system.core.AbstractActor;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.ActorSystem;
import actor.system.core.OnMessage;
import actor.system.core.message.Message;
import actor.system.util.DateUtils;

public class SupervisorReduceActor extends AbstractActor {
	
	private static final Logger logger = LoggerFactory.getLogger(SupervisorReduceActor.class);
	
	@OnMessage(StartReduceCmd.class)
	public void startReduceCmd(StartReduceCmd startReduceCmd) {
		logger.info("Received Message:{}", startReduceCmd);
		
		ReduceActor reduceActor = new ReduceActor();
		String reduceName = buildReduceName(startReduceCmd);
		ActorRef reduceActorRef = ActorRef.createActorRef(new ActorPath(reduceName), reduceActor);
		reduceActor.setSelf(reduceActorRef);
		
		Message message = new Message();
		message.setSender(getSelf());
		message.setContent(startReduceCmd);
		ActorSystem.send(message, getSelf(), reduceActorRef);
	}

	private String buildReduceName(StartReduceCmd startReduceCmd) {
		return String.format("reduce_%d_%s", startReduceCmd.getLimit(), DateUtils.formatFullDate(new Date()));
	}
}
