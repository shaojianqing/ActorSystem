package actor.system.core;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import actor.system.core.constant.ActorConstant;
import actor.system.util.LocalIpUtil;

public class ActorPath implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String host;
	
	private int port = ActorConstant.ACTOR_SERVER_PORT;
	
	private String path;
	
	public ActorPath(String localPath) {
		this.host = LocalIpUtil.getLocalIp();
		this.path = localPath;
	}
	
	public ActorPath(String serverIp, String path) {
		this.host = serverIp;
		this.path = path;
	}
	
	public ActorPath(String serverIp, int port, String path) {
		this.host = serverIp;
		this.port = port;
		this.path = path;
	}
	
	public boolean isLocal() {
		return StringUtils.equals(LocalIpUtil.getLocalIp(), host);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActorPath other = (ActorPath) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ActorPath [host=" + host + ", port=" + port + ", path=" + path + "]";
	}
}
