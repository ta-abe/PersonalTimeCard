package personaltimecard;

public class TimeCard {
	private String uuid = null;
	private String year = null;
	private String month = null;
	private String day = null;
	private String hour = null;
	private String minute = null;
	private String date_uuid = null;
	private String clock_in_hour = null;
	private String clock_in_minute = null;
	private String clock_out_hour = null;
	private String clock_out_minute = null;

	public TimeCard(String year, String month, String day){

	}

	public TimeCard(String uuid, String dateUuid, String clockInHour, String clockInMinute, String clockOutHour, String clockOutMinute){

	}

	public TimeCard(String day, String clock_in_hour, String clock_in_minute, String clock_out_hour, String clock_out_minute){

	}

	public String getUuid(){
		return uuid;
	}

	public String getYear(){
		return year;
	}
	public String getMonth(){
		return month;
	}

	public String getDay(){
		return day;
	}

	public String getHour(){
		return hour;
	}

	public String getMinute(){
		return minute;
	}

	public String getDate_Uuid(){
		return date_uuid;
	}

	public String getClock_In_Hour(){
		return clock_in_hour;
	}

	public String getClock_In_Minute(){
		return clock_in_minute;
	}

	public String getClock_Out_hour(){
		return clock_out_hour;
	}

	public String getClock_Out_Minute(){
		return clock_out_minute;
	}
}
