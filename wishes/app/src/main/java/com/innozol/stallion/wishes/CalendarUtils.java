package com.innozol.stallion.wishes;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * A Utility Class for Calendar.
 * 
 * @author Ashwin
 * 
 */

public class CalendarUtils {
	
	private static ArrayList<Integer> stDates = new ArrayList<Integer>();
	private static ArrayList<Integer> ndDates = new ArrayList<Integer>();
	private static ArrayList<Integer> rdDates = new ArrayList<Integer>();
	
	static{
		stDates.add(1);stDates.add(21);stDates.add(31);
		
		ndDates.add(2);ndDates.add(22);
		
		rdDates.add(3);rdDates.add(23);
	}
	
	/**
	 * Gives the Day of Week
	 * @param day day of month
	 * @return String representation of a week day
	 */
	private static String getDay(int day){
		String sDay = "";
		switch (day) {
		case 1:
			sDay = "Sunday";
			break;
		case 2:
			sDay = "Monday";
			break;
		case 3:
			sDay = "Tuesday";
			break;
		case 4:
			sDay = "Wednesday";
			break;
		case 5:
			sDay = "Thursday";
			break;
		case 6:
			sDay = "Friday";
			break;
		case 7:
			sDay = "Saturday";
			break;
		}
		return sDay;
	}
	
	private static String getMonth(int month){
		String sMonth = "";
		switch (month) {
		case 0:
			sMonth = "January";
			break;
		case 1:
			sMonth = "February";
			break;
		case 2:
			sMonth = "March";
			break;
		case 3:
			sMonth = "April";
			break;
		case 4:
			sMonth = "May";
			break;
		case 5:
			sMonth = "June";
			break;
		case 6:
			sMonth = "July";
			break;
		case 7:
			sMonth = "August";
			break;
		case 8:
			sMonth = "September";
			break;
		case 9:
			sMonth = "October";
			break;
		case 10:
			sMonth = "November";
			break;
		case 11:
			sMonth = "December";
			break;
		}
		return sMonth;
	}
	
	/**
	 * 
	 * @param calendar
	 * @return date with prefixes
	 */
	public static String styleDate(Calendar calendar){
		String style = "";
		int date = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH);
		if(stDates.contains(date)){
			style = calendar.get(Calendar.DAY_OF_MONTH)+"<sup>st</sup> "+getMonth(month);
		}
		else if(ndDates.contains(date)){
			style = calendar.get(Calendar.DAY_OF_MONTH)+"<sup>nd</sup> "+getMonth(month);
		}
		else if(rdDates.contains(date)){
			style = calendar.get(Calendar.DAY_OF_MONTH)+"<sup>rd</sup> "+getMonth(month);
		}
		else{
			style = calendar.get(Calendar.DAY_OF_MONTH)+"<sup>th</sup> "+getMonth(month);
		}
		return style;
	}
	
	/**
	 * Checks the calendar is today.
	 * @param calendar Calendar object to check.
	 * @return true if calendar is today else false.
	 */
	public static boolean isToday(Calendar calendar){
		return calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) 
				&& calendar.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @return Current calendar year
	 */
	public static int getCurrentYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * @return Current calendar month
	 */
	public static int getCurrentMonth(){
		return Calendar.getInstance().get(Calendar.MONTH);
	}
	
	/**
	 * 
	 * @return Day of current calendar month
	 */
	public static int getCurrentDay(){
		return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @return Hour of the Day in 24 hours format
	 */
	public static int getCurrentHours(){
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 
	 * @return Minute within current hour
	 */
	public static int getCurrentMinutes(){
		return Calendar.getInstance().get(Calendar.MINUTE);
	}
	
	/**
	 * @return Second within minute
	 */
	public static int getCurrentSeconds(){
		return Calendar.getInstance().get(Calendar.SECOND);
	}
	
	/**
	 * Checks the calendar is tomorrow.
	 * @param calendar Calendar object to check.
	 * @return true if calendar is tomorrow else false.
	 */
	public static boolean isTomorrow(Calendar calendar){
		return calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) 
				&& calendar.get(Calendar.DAY_OF_MONTH) == (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1);
	}
	
	/**
	 * Checks for the given calendar is in coming 7 days
	 * @param calendar
	 * @return true if given calendar is in coming week
	 */
	public static boolean isComingWeek(Calendar calendar){
		boolean flag = false;
		Calendar calendar2 = Calendar.getInstance(); 
		calendar2.add(Calendar.DATE, 7);
		if(calendar.before(calendar2)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * Get the weekday of the calendar
	 * @param calendar
	 * @return Weekday
	 */
	public static String getWeekDay(Calendar calendar){
		return getDay(calendar.get(Calendar.DAY_OF_WEEK));
	}
	/**
	 * Check the given date is February 29th.
	 * @param calendar Calendar object to check
	 * @return true if date is February 29th, else false.
	 */
	public static boolean isInsaneDate(Calendar calendar){
		return calendar.get(Calendar.DAY_OF_MONTH) == 29 && calendar.get(Calendar.MONTH) == 1;
	}
	
	/**
	 * Get the coming leap year next to <code>year</code>
	 * @param year year
	 * @return a leap year
	 */
	public static int getNextLeap(int year) {
		int nYear = year;
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
				.getInstance();
		if (calendar.isLeapYear(year)) {
			nYear = year;
		} else {
			while (!calendar.isLeapYear(year++)) {
				nYear = year;
			}
		}
		return nYear;
	}
	
	/**
	 * @param time selected in TimePicker
	 * @return Converted to calendar
	 */
	
	public static Calendar timeToCalendar(String time){
		Calendar calTime = Calendar.getInstance();
		calTime.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time.split(":")[0]));
		calTime.set(Calendar.MINUTE, Integer.parseInt(time.split(":")[1]));
		return calTime;
	}
	
	/**
	 * Formats a birthday date to a String    
	 * @param bDay date in <i>"mm/dd/yyyy"</i> format
	 * @return formated date string </br><b>Format : </b> "yyyy-MM-dd HH:mm:ss" 
	 */
	public static String formatDate(String bDay){
		Calendar calDob = Calendar.getInstance();
		String formated = "";
		if(bDay != null){
			String[] mmddyyyy = bDay.split("/");
			if(mmddyyyy.length == 3){
				calDob.set(calDob.get(Calendar.YEAR), Integer.parseInt(mmddyyyy[0])-1, Integer.parseInt(mmddyyyy[1]), 23, 59, 59);
			}else if(mmddyyyy.length == 2){
				calDob.set(calDob.get(Calendar.YEAR), Integer.parseInt(mmddyyyy[0])-1, Integer.parseInt(mmddyyyy[1]), 23, 59, 59);
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			dateFormat.setCalendar(calDob);
			formated = dateFormat.format(calDob.getTime());
		}
		return formated;
	}
	
	
	/**
	 * Formats a birthday date to a String    
	 * @param bDay date in <i>"yyyy-mm-dd"</i> format
	 * @return formated date string </br><b>Format : </b> "yyyy-MM-dd HH:mm:ss" 
	 */
	public static String formatContactDate(String bDay){
		Calendar calDob = Calendar.getInstance();
		String formated = "";
		if(bDay != null){
			String[] mmddyyyy = bDay.split("-");
			if(mmddyyyy.length == 3){
				calDob.set(calDob.get(Calendar.YEAR), Integer.parseInt(mmddyyyy[1])-1, Integer.parseInt(mmddyyyy[2]), 23, 59, 59);
			}else if(mmddyyyy.length == 2){
				calDob.set(calDob.get(Calendar.YEAR), Integer.parseInt(mmddyyyy[1])-1, Integer.parseInt(mmddyyyy[2]), 23, 59, 59);
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
			dateFormat.setCalendar(calDob);
			formated = dateFormat.format(calDob.getTime());
		}
		return formated;
	}
	
	/**
	 * Convert given calendar to formated string 
	 * @param calendar to convert
	 * @return formated date in string
	 */
	@SuppressLint("SimpleDateFormat")
	public static String calendarToString(Calendar calendar){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setCalendar(calendar);
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	 * Date from database as string can convert to calendar
	 * @param date A formated (yyyy-MM-dd HH:mm:ss) date from database 
	 * @return A converted calendar object
	 */
	public static Calendar stringToCalendar(String date){
		Calendar calendar = Calendar.getInstance();
		String[] dateTime = date.split(" ");
		String[] s_date = dateTime[0].split("-");
		String[] s_time = dateTime[1].split(":");
		calendar.set(toInt(s_date[0]), (toInt(s_date[1])-1), toInt(s_date[2]), toInt(s_time[0]), toInt(s_time[1]), toInt(s_time[2]));
		return calendar;
	}
	
	private static int toInt(String string){
		return Integer.parseInt(string);
	}
}
