package actor.system.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;

public class ServerUtil {
	
	@SuppressWarnings("resource")
	public static void accept(int port, final RequestProcessor callBack) {
		try {
			
			ServerSocket serverSocket = new ServerSocket(port);			
			while(true) {
				final Socket socket = serverSocket.accept();
				ThreadUtil.run(new Runnable() {
					
					public void run() {
						try {
							InputStream is = socket.getInputStream();
							byte[] request = IOUtils.toByteArray(is);
							byte[] response = callBack.onService(request);
							
							OutputStream os = socket.getOutputStream();
							os.write(response);
							os.flush();
							
							os.close();
							socket.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				});
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface RequestProcessor {

		public byte[] onService(byte[] request);
	}
}