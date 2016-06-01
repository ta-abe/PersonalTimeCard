package personaltimecard;

import java.util.List;

public class TimeCard {
	private String uuid = null;
	private String year = null;
	private String month = null;
	private String day = null;
	private String dateUuid = null;
	private String arrivalHour = null;
	private String arrivalMinute = null;
	private String departureHour = null;
	private String departureMinute = null;
	private List<String> arrivalList = null;
	private List<String> departureList = null;
	private List<String> arrivalRegisteredDatetime = null;
	private List<String> departureRegisteredDatetime = null;


	public TimeCard(String year, String month, String day){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public TimeCard(String dateUuid, String arrivalHour, String arrivalMinute, String departureHour, String departureMinute){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.dateUuid = dateUuid;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
	}

	public TimeCard(String dateUuid , String day, String arrivalHour, String arrivalMinute, String departureHour, String departureMinute){
		this.dateUuid = dateUuid;
		this.day = day;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
	}

	public TimeCard(List<String> arrivalList, List<String> arrivalRegisteredDatetime, List<String> departureList, List<String> departureRegisteredDatetime){
		this.arrivalList = arrivalList;
		this.arrivalRegisteredDatetime = arrivalRegisteredDatetime;
		this.departureList = departureList;
		this.departureRegisteredDatetime = departureRegisteredDatetime;

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

	public String getArrivalHour(){
		return arrivalHour;
	}

	public String getArrivalMinute(){
		return arrivalMinute;
	}

	public String getDeparturehour(){
		return departureHour;
	}

	public String getDepartureMinute(){
		return departureMinute;
	}

	public List<String> getArrivalList(){
		return arrivalList;
	}

	public List<String> getDepartureList(){
		return departureList;
	}

	public List<String> getArrivalRegisteredDatetime(){
		return arrivalRegisteredDatetime;
	}

	public List<String> getDepartureRegisteredDatetime(){
		return departureRegisteredDatetime;
	}
}
