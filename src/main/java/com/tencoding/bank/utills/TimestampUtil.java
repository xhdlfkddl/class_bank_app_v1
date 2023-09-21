package com.tencoding.bank.utills;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class TimestampUtil {
	
	public static String timestampToString(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(timestamp);
	}
	
}
