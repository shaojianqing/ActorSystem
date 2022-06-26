package actor.system.brain;

import actor.system.cmd.StartReduceCmd;
import actor.system.constants.CommonConsts;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.ActorSystem;
import actor.system.core.message.Message;

public class Starter {
	
    public static void main( String[] args ) {
        
    	Message message = new Message();
    	StartReduceCmd startReduceCmd = new StartReduceCmd();
    	startReduceCmd.setLimit(1889);
    	message.setContent(startReduceCmd);
    	
    	ActorRef supervisorReduce = ActorRef.createActorRef(new ActorPath("30.40.236.178", CommonConsts.SUPERVISOR_REDUCE_NAME));
    	ActorSystem.send(message, null, supervisorReduce);
    }
}
