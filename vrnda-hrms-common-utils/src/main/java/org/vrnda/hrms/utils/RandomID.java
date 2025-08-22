package org.vrnda.hrms.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RandomID {
	
//	public static Long getRandomID(){
//		String ALPHA_NUMERIC_STRING = "0123456789";
//		String randomId = getRandomNo();
//	    int count = 20-randomId.length();
//	    StringBuilder builder = new StringBuilder();
//	    while (count-- != 0) 
//	    {
//		    int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
//		    builder.append(ALPHA_NUMERIC_STRING.charAt(character));
//	    }
//	    randomId = randomId+builder.toString();
//	    Long num = Long.parseLong(randomId);
//	    return Long.parseLong(randomId);
//	}
	
	public static Long getRandomID(){
		Calendar now = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String year = df.format(Calendar.getInstance().getTime());
	    int month = now.get(Calendar.MONTH) + 1; // Note: zero based!
	    int day = now.get(Calendar.DAY_OF_MONTH);
	    int hour = now.get(Calendar.HOUR_OF_DAY);
	    int minute = now.get(Calendar.MINUTE);
	    int second = now.get(Calendar.SECOND);
	    int millis = now.get(Calendar.MILLISECOND);
	    String randomId = Integer.parseInt(year) + Integer.toString(month) + Integer.toString(day)
	        + Integer.toString(hour) + Integer.toString(minute) + Integer.toString(second)
	        + Integer.toString(millis);
	    return Long.parseLong(randomId);
	}

}
