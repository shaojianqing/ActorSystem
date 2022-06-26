package actor.system.util;

import actor.system.exception.ValidateException;

public class AssertUtil {
	
	public static void isNotNull(Object source, String message) throws Exception {
		if (source==null) {
			throw new ValidateException(message);
		}
	}
}
