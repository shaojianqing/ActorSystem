package actor.system.util;

import java.util.UUID;

public class UUIDUitl {
	
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-","");
	}
}
