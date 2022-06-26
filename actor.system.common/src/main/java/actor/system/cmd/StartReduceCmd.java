package actor.system.cmd;

import java.io.Serializable;

public class StartReduceCmd implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int limit;

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "StartReduceCmd [limit=" + limit + "]";
	}
}
