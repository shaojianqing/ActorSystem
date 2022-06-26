package actor.system.cmd;

import java.io.Serializable;

import actor.system.core.ActorPath;

public class StartMapCmd implements Serializable {

	private static final long serialVersionUID = 1L;

	private int start;
	
	private int limit;
	
	private ActorPath reducePath;
	
	private String mapActorId;

	public StartMapCmd(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public ActorPath getReducePath() {
		return reducePath;
	}

	public void setReducePath(ActorPath reducePath) {
		this.reducePath = reducePath;
	}

	public String getMapActorId() {
		return mapActorId;
	}

	public void setMapActorId(String mapActorId) {
		this.mapActorId = mapActorId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "StartMapCmd [start=" + start + ", limit=" + limit + ", reducePath=" + reducePath + ", mapActorId="
				+ mapActorId + "]";
	}
}
