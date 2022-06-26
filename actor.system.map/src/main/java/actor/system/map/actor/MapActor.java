package actor.system.map.actor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import actor.system.cmd.FinishMapCmd;
import actor.system.cmd.StartMapCmd;
import actor.system.core.AbstractActor;
import actor.system.core.ActorRef;
import actor.system.core.ActorSystem;
import actor.system.core.OnMessage;
import actor.system.core.message.Message;

public class MapActor extends AbstractActor {
	
	private static final Logger logger = LoggerFactory.getLogger(MapActor.class);
	
	@OnMessage(StartMapCmd.class)
	public void startMap(StartMapCmd startMapCmd) {
		
		int start = startMapCmd.getStart();
		int limit = startMapCmd.getLimit();
		List<Integer> primeList = calculatePrime(start, limit);
		
		Message resultMessage = new Message();
		FinishMapCmd finishMapCmd = new FinishMapCmd(primeList);
		finishMapCmd.setMapActorId(startMapCmd.getMapActorId());
		resultMessage.setContent(finishMapCmd);
		resultMessage.setSender(getSelf());
		
		logger.info("Finish Map Calculate:{}", startMapCmd);
		
		ActorRef reduceActorRef = ActorRef.createActorRef(startMapCmd.getReducePath());
		ActorSystem.send(resultMessage, getSelf(), reduceActorRef);
		destroy();
	}
	
	List<Integer> calculatePrime(int start, int limit) {
		List<Integer> list = new ArrayList<Integer>();
		for (int i=start; i< start+limit;) {
			if (isPrime(i)) {
				list.add(i); 
			}
			
			if (i%2==0) {
				i++;
			} else {
				i+=2;
			}
		}
		return list;
	}
	
	private boolean isPrime(int num) {
		if (num==1) {
			return false;
		}
		
		if (num==2) {
			return true;
		}
		
		if (num%2==0) {
			return false;
		}
		
		for (int j = 3; j<=num/2;j+=2) {
			if (num%j==0) {
				return false;
			}
		}
		return true;
	}
}