package subgit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDate {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getCurrentDate() {
		return format.format(new Date());
	}
}
