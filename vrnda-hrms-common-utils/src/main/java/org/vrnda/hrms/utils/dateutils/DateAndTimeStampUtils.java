package org.vrnda.hrms.utils.dateutils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAndTimeStampUtils {

	public static Timestamp timeStampConversion(String parseDate) {
		Timestamp timestamp = null;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date parsedTimeStamp = dateFormat.parse(parseDate);
			timestamp = new Timestamp(parsedTimeStamp.getTime() + 1);
		} catch (ParseException E) {
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
				Date parsedTimeStamp;
				parsedTimeStamp = dateFormat.parse(parseDate);
				timestamp = new Timestamp(parsedTimeStamp.getTime() + 1);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return timestamp;
	}

	public static String myDateConversion(String date) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = formatter.parse(date);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM");
		String dated = simpleDateFormat.format(date1);
		return dated;
	}

	public static String getTodayDate() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String todaysDate = dateFormat.format(date);
		return todaysDate;
	}
}
