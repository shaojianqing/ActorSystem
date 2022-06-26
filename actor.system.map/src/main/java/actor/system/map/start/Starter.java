package actor.system.map.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.constants.CommonConsts;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.rpc.RpcServer;
import actor.system.map.actor.SupervisorMapActor;

public class Starter {
	
	private static final Logger logger = LoggerFactory.getLogger(Starter.class);
	
	private static final int MAP_SERVER_PORT = 6666;
	
    public static void main( String[] args ) {
    	
    	SupervisorMapActor supervisorReduceActor = new SupervisorMapActor();
    	ActorRef supervisor = ActorRef.createActorRef(new ActorPath(CommonConsts.SUPERVISOR_MAP_NAME), supervisorReduceActor);
    	
    	RpcServer.startServer(MAP_SERVER_PORT);
    	
    	logger.info("Actor:{} Prepare^+^", supervisor);
    	logger.info("Map Server Start^+^");
    }
}