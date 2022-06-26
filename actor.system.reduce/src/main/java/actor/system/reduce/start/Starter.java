package actor.system.reduce.start;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.constants.CommonConsts;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.rpc.RpcServer;
import actor.system.reduce.actor.SupervisorReduceActor;

public class Starter {
	
	private static final Logger logger = LoggerFactory.getLogger(Starter.class);
	
	private static final int REDUCE_SERVER_PORT = 8888;
	
    public static void main( String[] args ) {
    	
    	SupervisorReduceActor supervisorReduceActor = new SupervisorReduceActor();
    	ActorRef supervisor = ActorRef.createActorRef(new ActorPath(CommonConsts.SUPERVISOR_REDUCE_NAME), supervisorReduceActor);
    	
    	RpcServer.startServer(REDUCE_SERVER_PORT);
    	
    	logger.info("Actor:{} Prepare^+^", supervisor);
    	logger.info("Reduce Server Start^+^");
    }
}
