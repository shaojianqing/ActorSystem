package actor.system.util;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.IOUtils;

public class NetworkUtil {

	private static Map<String, Socket> connectionPool = new ConcurrentHashMap<String, Socket>();

	public static byte[] sendRequest(String serverIp, int port, byte[] data) throws Exception {
		Socket socket = new Socket(serverIp, port);
		OutputStream os = socket.getOutputStream();
		os.write(data);
		socket.shutdownOutput();

		InputStream is = socket.getInputStream();
		byte[] result = IOUtils.toByteArray(is);
		is.close();
		os.close();
		socket.close();
		return result;
	}
	
	public static byte[] sendRequest0(String serverIp, int port, byte[] data) throws Exception {

		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;

		String address = String.format("%s:%d", serverIp, port);

		try {
			if (!connectionPool.containsKey(address)) {
				Socket socketInstance = new Socket(serverIp, port);
				socketInstance.setSoTimeout(0);
				socketInstance.setKeepAlive(false);
				connectionPool.put(address, socketInstance);
			}

			socket = connectionPool.get(address);
			os = new BufferedOutputStream(socket.getOutputStream());
			os.write(data);
			os.flush();

			is = socket.getInputStream();
			byte[] result = IOUtils.toByteArray(is);
			return result;
		} catch (Exception e) {
			if (socket != null) {
				os.close();
				is.close();
				socket.close();
				connectionPool.remove(address);
			}
			e.printStackTrace();
			throw e;
		}
	}
}
