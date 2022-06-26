package actor.system.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.google.gson.Gson;

public class ProtocolUtil {
	
	private static final Gson gson = new Gson();
	
	public static <T> T transToObject(String json, Class<T> clazz) throws IOException {
		return gson.fromJson(json, clazz);
	}
	
	public static String transToString(Object object) throws IOException {
		return gson.toJson(object);
	}
	
	public static Object transToObject(byte[] data) throws IOException {
		ByteArrayInputStream is = new ByteArrayInputStream(data);   
        HessianInput hessianInput = new HessianInput(is); 
        return hessianInput.readObject();
	}
	
	public static byte[] transToByte(Object object) throws IOException {
		ByteArrayOutputStream os = new ByteArrayOutputStream();  
        HessianOutput ho = new HessianOutput(os);  
        ho.writeObject(object);  
        return os.toByteArray();  
	}
}