package actor.system.reduce.actor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.cmd.FinishMapCmd;
import actor.system.cmd.StartMapCmd;
import actor.system.cmd.StartReduceCmd;
import actor.system.constants.CommonConsts;
import actor.system.core.AbstractActor;
import actor.system.core.ActorPath;
import actor.system.core.ActorRef;
import actor.system.core.ActorSystem;
import actor.system.core.OnMessage;
import actor.system.core.message.Message;
import actor.system.util.UUIDUitl;

public class ReduceActor extends AbstractActor {
	
	private static final Logger logger = LoggerFactory.getLogger(ReduceActor.class);
	
	private static final int TOTAL_TASK_COUNT = 10;
	
	private List<Integer> totalPrimeList = new ArrayList<Integer>();
	
	private Set<String> unreturnMapSet = new HashSet<String>();

	@OnMessage(StartReduceCmd.class)
	public void startReduce(StartReduceCmd reduceCmd) {
		
		int limit = reduceCmd.getLimit();
		int step = limit/TOTAL_TASK_COUNT;
		for (int i=0;i<TOTAL_TASK_COUNT;++i) {
			StartMapCmd startMapCmd = new StartMapCmd(i*step, step);
			startMapCmd.setReducePath(getSelf().getActorPath());
			String mapActorId = UUIDUitl.generateUUID();
			startMapCmd.setMapActorId(mapActorId);
			unreturnMapSet.add(mapActorId);
			Message mapMessage = new Message();
			mapMessage.setContent(startMapCmd);
			mapMessage.setSender(getSelf());
			
			ActorRef mapActorRef = ActorRef.createActorRef(new ActorPath("30.40.237.244", 6666, CommonConsts.SUPERVISOR_MAP_NAME));
			ActorSystem.send(mapMessage, getSelf(), mapActorRef);	
		}		
	}
	
	@OnMessage(FinishMapCmd.class)
	public void finishMap(FinishMapCmd finishMapCmd) {
		if (unreturnMapSet.contains(finishMapCmd.getMapActorId())) {
			totalPrimeList.addAll(finishMapCmd.getPrimeList());
			unreturnMapSet.remove(finishMapCmd.getMapActorId());
		}
		
		if (unreturnMapSet.isEmpty()) {
			totalPrimeList.sort(new Comparator<Integer>() {

				public int compare(Integer o1, Integer o2) {
					return (o1>o2?1:-1);
				}
			});
			
			for (int i=0;i<totalPrimeList.size();++i) {
				System.out.print(" " + totalPrimeList.get(i));
				if ((i+1)%20==0) {
					System.out.println();
				}
			}
			System.out.println();
			logger.info("ReduceActor output result!");
			this.destroy();
		}
	}
}
