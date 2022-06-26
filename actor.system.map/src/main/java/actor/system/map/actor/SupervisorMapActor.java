package actor.system.map.actor;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.cmd.StartMapCmd;
import actor.system.core.AbstractActor;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.ActorSystem;
import actor.system.core.OnMessage;
import actor.system.core.message.Message;
import actor.system.util.DateUtils;

public class SupervisorMapActor extends AbstractActor {

	private static final Logger logger = LoggerFactory.getLogger(SupervisorMapActor.class);
	
	@OnMessage(StartMapCmd.class)
	public void startReduceCmd(StartMapCmd startMapCmd) {
		logger.info("Received Message:{}", startMapCmd);
		
		MapActor mapActor = new MapActor();
		String mapName = buildMapName(startMapCmd);
		ActorRef mapRef = ActorRef.createActorRef(new ActorPath(mapName), mapActor);
		
		Message message = new Message();
		message.setSender(getSelf());
		message.setContent(startMapCmd);
		ActorSystem.send(message, getSelf(), mapRef);
	}

	private String buildMapName(StartMapCmd startMapCmd) {
		return String.format("map_%d_%d_%s", startMapCmd.getStart(), startMapCmd.getLimit(), DateUtils.formatFullDate(new Date()));
	}
}
