package actor.system.core.rpc;

import java.io.Serializable;

public class RpcResult implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private boolean success;
	
	private Object data;
	
	private String message;

	public RpcResult(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "RpcResult [success=" + success + ", data=" + data + ", message=" + message + "]";
	}
}
