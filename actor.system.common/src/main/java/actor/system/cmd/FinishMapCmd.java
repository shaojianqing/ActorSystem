package actor.system.cmd;

import java.io.Serializable;
import java.util.List;

public class FinishMapCmd implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Integer> primeList;
	
	private String mapActorId;
	
	public FinishMapCmd(List<Integer> primeList) {
		super();
		this.primeList = primeList;
	}

	public List<Integer> getPrimeList() {
		return primeList;
	}

	public void setPrimeList(List<Integer> primeList) {
		this.primeList = primeList;
	}

	public String getMapActorId() {
		return mapActorId;
	}

	public void setMapActorId(String mapActorId) {
		this.mapActorId = mapActorId;
	}
}
