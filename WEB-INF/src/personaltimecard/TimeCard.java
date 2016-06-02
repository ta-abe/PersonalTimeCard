package personaltimecard;

import java.util.List;


/**
 *
 * @author excite
 *
 */
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

	/**
	 * パラメータを受け取りUUIDを追加して格納する
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 */
	public TimeCard(String year, String month, String day){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.year = year;
		this.month = month;
		this.day = day;
	}

	/**
	 * 新規登録用のデータを受け取り格納する
	 * @param dateUuid DateテーブルのUUID
	 * @param arrivalHour 出勤時
	 * @param arrivalMinute 出勤分
	 * @param departureHour 退勤時
	 * @param departureMinute 退勤分
	 */
	public TimeCard(String dateUuid, String arrivalHour, String arrivalMinute, String departureHour, String departureMinute){
		this.uuid = java.util.UUID.randomUUID().toString();
		this.dateUuid = dateUuid;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
	}

	/**
	 * 修正用のパラメータを受け取り格納する
	 * @param dateUuid DATEテーブルのUUID
	 * @param day 日
	 * @param arrivalHour 出勤時
	 * @param arrivalMinute 出勤分
	 * @param departureHour 退勤時
	 * @param departureMinute 退勤分
	 */
	public TimeCard(String dateUuid , String day, String arrivalHour, String arrivalMinute, String departureHour, String departureMinute){
		this.dateUuid = dateUuid;
		this.day = day;
		this.arrivalHour = arrivalHour;
		this.arrivalMinute = arrivalMinute;
		this.departureHour = departureHour;
		this.departureMinute = departureMinute;
	}

	/**
	 * 修正履歴に表示するためのListを受け取り格納する
	 * @param arrivalList ある日付に対して紐付けられた出勤時刻すべてのレコードが格納されたリスト
	 * @param arrivalRegisteredDatetime arrivalListの要素すべてに対応する登録日時が格納されたリスト
	 * @param departureList ある日付に対して紐付けられた退勤時刻すべてのレコードが格納されたリスト
	 * @param departureRegisteredDatetime departureListの要素すべてに対応する登録日時が格納されたリスト
	 */
	public TimeCard(List<String> arrivalList, List<String> arrivalRegisteredDatetime, List<String> departureList, List<String> departureRegisteredDatetime){
		this.arrivalList = arrivalList;
		this.arrivalRegisteredDatetime = arrivalRegisteredDatetime;
		this.departureList = departureList;
		this.departureRegisteredDatetime = departureRegisteredDatetime;
	}

	/**
	 * オブジェクトからUUIDを返す
	 * @return uuid
	 */
	public String getUuid(){
		return uuid;
	}

	/**
	 * オブジェクトから年を返す
	 * @return 年
	 */
	public String getYear(){
		return year;
	}

	/**
	 * オブジェクトから月を返す
	 * @return 月
	 */
	public String getMonth(){
		return month;
	}

	/**
	 * オブジェクトから日を返す
	 * @return 日
	 */
	public String getDay(){
		return day;
	}

	/**
	 * オブジェクトからDATEテーブルのUUIDを返す
	 * @return DATEテーブルのUUID
	 */
	public String getDateUuid(){
		return dateUuid;
	}

	/**
	 * オブジェクトから出勤時刻(時)を返す
	 * @return 出勤時刻(時)
	 */
	public String getArrivalHour(){
		return arrivalHour;
	}

	/**
	 * オブジェクトから出勤時刻(分)を返す
	 * @return 出勤時刻(分)
	 */
	public String getArrivalMinute(){
		return arrivalMinute;
	}

	/**
	 * オブジェクトから退勤時刻(時)を返す
	 * @return 退勤時刻(時)
	 */
	public String getDeparturehour(){
		return departureHour;
	}

	/**
	 * オブジェクトから退勤時刻(分)を返す
	 * @return 退勤時刻(分)
	 */
	public String getDepartureMinute(){
		return departureMinute;
	}

	/**
	 * オブジェクトから出勤時刻が格納されたリストを返す
	 * @return 出勤時刻が格納されたリスト
	 */
	public List<String> getArrivalList(){
		return arrivalList;
	}

	/**
	 * オブジェクトから退勤時刻が格納されたリストを返す
	 * @return 退勤時刻が格納されたリスト
	 */
	public List<String> getDepartureList(){
		return departureList;
	}

	/**
	 * オブジェクトから出勤時刻に対応した登録日時が格納されたリストを返す
	 * @return ARRIVALテーブルの登録日時が格納されたリスト
	 */
	public List<String> getArrivalRegisteredDatetime(){
		return arrivalRegisteredDatetime;
	}

	/**
	 * オブジェクトから退勤時刻に対応した登録日時が格納されたリストを返す
	 * @return DEPARTUREテーブルの登録日時が格納されたリスト
	 */
	public List<String> getDepartureRegisteredDatetime(){
		return departureRegisteredDatetime;
	}
}
