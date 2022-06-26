package actor.system.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static SimpleDateFormat fullFormat = new SimpleDateFormat("yyyyMMddHHmmss"); 
	
	public static String formatFullDate(Date date) {
		return fullFormat.format(date);
	}
}
