package personaltimecard;

public class TimeCard {
	private String uuid = null;
	private String year = null;
	private String month = null;
	private String day = null;
	private String dateUuid = null;
	private String clockInHour = null;
	private String clockInMinute = null;
	private String clockOutHour = null;
	private String clockOutMinute = null;

	public TimeCard(String year, String month, String day){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public TimeCard(String uuid, String dateUuid, String clockInHour, String clockInMinute, String clockOutHour, String clockOutMinute){
		this.uuid = uuid;
		this.dateUuid = dateUuid;
		this.clockInHour = clockInHour;
		this.clockInMinute = clockInMinute;
		this.clockOutHour = clockOutHour;
		this.clockOutMinute = clockOutMinute;
	}

	public TimeCard(String day, String clockInHour, String clockInMinute, String clockOutHour, String clockOutMinute){
		this.day = day;
		this.clockInHour = clockInHour;
		this.clockInMinute = clockInMinute;
		this.clockOutHour = clockOutHour;
		this.clockOutMinute = clockOutMinute;
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

	public String getDateUuid(){
		return dateUuid;
	}

	public String getClockInHour(){
		return clockInHour;
	}

	public String getClockInMinute(){
		return clockInMinute;
	}

	public String getClockOuthour(){
		return clockOutHour;
	}

	public String getClockOutMinute(){
		return clockOutMinute;
	}
}
