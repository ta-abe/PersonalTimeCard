package personaltimecard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author excite
 *
 */
public class PersonalTimeCard {

	private final String dbUrl = "jdbc:mysql://localhost:3306/sample";
	private final String dbUser = "root";
	private final String dbPass = "8121";

	/**
	 * 指定された年月から対応する日付、紐付けされた出退勤データの中から最後に更新されたものを取り出します
	 * @param year 選択された年
	 * @param month 選択された月
	 * @return 登録されている日付と、それに紐付けられた出退勤のデータの中から最新のものを格納たリスト
	 * @throws SQLException
	 */
	public List<TimeCard> getCurrentList(String year, String month)throws SQLException{
		List<TimeCard> array = new ArrayList<TimeCard>();
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		PreparedStatement pst3 = null;
		ResultSet  rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "SELECT * FROM DATE WHERE YEAR = ? AND MONTH = ? ORDER BY YEAR,MONTH,CAST(DAY AS SIGNED)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, year);
			pst.setString(2, month);
			rs = pst.executeQuery();
			while(rs.next()){
				String day = rs.getString("DAY");
				String dateUuid = rs.getString("UUID");
				sql = "SELECT * FROM ARRIVAL WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
				pst2 = conn.prepareStatement(sql);
				pst2.setString(1, dateUuid);
				rs2 = pst2.executeQuery();
				String arrivalHour = "--";
				String arrivalMinute = "--";
				if(true == rs2.next()){
					arrivalHour = rs2.getString("ARRIVAL_HOUR");
					arrivalMinute = rs2.getString("ARRIVAL_MINUTE");
				}
				sql = "SELECT * FROM DEPARTURE WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
				pst3 = conn.prepareStatement(sql);
				pst3.setString(1, dateUuid);
				rs3 = pst3.executeQuery();
				String departureHour = "--";
				String departureMinute = "--";
				if(true == rs3.next()){
					departureHour = rs3.getString("DEPARTURE_HOUR");
					departureMinute = rs3.getString("DEPARTURE_MINUTE");
				}
				if(arrivalHour != "--" || arrivalMinute != "--" || departureHour != "--" || departureMinute != "--"){
					TimeCard timecard = new TimeCard(dateUuid, day, arrivalHour, arrivalMinute, departureHour, departureMinute);
					array.add(timecard);
				}
			}
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(pst2 != null){
				pst2.close();
			}
			if(pst3 != null){
				pst3.close();
			}
			if(rs != null){
				rs.close();
			}
			if(rs2 != null){
				rs2.close();
			}
			if(rs3 != null){
				rs3.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return array;
	}

	/**
	 * 選択された年月日の出退勤記録の修正履歴を取り出します
	 * @param dateUuid 指定した年月日のUUID
	 * @return 修正履歴が格納されたオブジェクト
	 * @throws SQLException
	 */
	public TimeCard getModifyHistory(String dateUuid) throws SQLException {
		TimeCard timecard = null;
		Connection conn = null;
		PreparedStatement pst = null;
		PreparedStatement pst2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "SELECT * FROM ARRIVAL WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			pst = conn.prepareStatement(sql);
			pst.setString(1, dateUuid);
			rs = pst.executeQuery();
			List<String> arrivalList = new ArrayList<String>();
			List<String> arrivalRegisteredList = new ArrayList<String>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy'-'MM'-'dd' 'HH':'mm':'ss");
			while(rs.next()){
				String arrivalHour = rs.getString("ARRIVAL_HOUR");
				String arrivalMinute = rs.getString("ARRIVAL_MINUTE");
				String arrivalRegisteredDatetime = sdf.format(rs.getTimestamp("REGISTERED_DATETIME"));
				arrivalList.add(arrivalHour + ":" + arrivalMinute);
				arrivalRegisteredList.add(arrivalRegisteredDatetime);
			}
			sql = "SELECT * FROM DEPARTURE WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			pst2 = conn.prepareStatement(sql);
			pst2.setString(1, dateUuid);
			rs2 = pst2.executeQuery();
			List<String> departureList = new ArrayList<String>();
			List<String> departureRegisteredList = new ArrayList<String>();
			while(rs2.next()){
				String departureHour = rs2.getString("DEPARTURE_HOUR");
				String departureMinute = rs2.getString("DEPARTURE_MINUTE");
				String departureRegisteredDatetime = sdf.format(rs2.getTimestamp("REGISTERED_DATETIME"));
				departureList.add(departureHour + ":" + departureMinute);
				departureRegisteredList.add(departureRegisteredDatetime);
			}
			timecard = new TimeCard(arrivalList, arrivalRegisteredList, departureList, departureRegisteredList);
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(pst2 != null){
				pst2.close();
			}
			if(rs != null){
				rs.close();
			}
			if(rs2 != null){
				rs2.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return timecard;
	}
	/**
	 * データを受け取り各テーブルに新規登録する.
	 * 既に登録されているデータがあれば登録しない
	 * @param timecard 新規登録したいデータが格納されていいるオブジェクト
	 * @throws SQLException
	 */
	public int registerTime(TimeCard timecard) throws SQLException{
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String arrivalHour = timecard.getArrivalHour();
		String arrivalMinute = timecard.getArrivalMinute();
		String departureHour = timecard.getDeparturehour();
		String departureMinute = timecard.getDepartureMinute();
		Connection conn = null;
		PreparedStatement pst = null;
		int num = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
			if(departureHour == null && departureMinute == null){
				String sql = "INSERT INTO ARRIVAL(UUID, DATE_UUID, ARRIVAL_HOUR, ARRIVAL_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM ARRIVAL WHERE DATE_UUID = ?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, arrivalHour);
				pst.setString(4, arrivalMinute);
				pst.setString(5, dateUuid);
				num =pst.executeUpdate();
			}
			else if(arrivalHour == null && arrivalMinute == null)
			{
				String sql = "INSERT INTO DEPARTURE(UUID, DATE_UUID, DEPARTURE_HOUR, DEPARTURE_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM DEPARTURE WHERE DATE_UUID = ?)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, departureHour);
				pst.setString(4, departureMinute);
				pst.setString(5, dateUuid);
				num = pst.executeUpdate();
			}
			conn.commit();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return num;
	}

	/**
	 * データを受け取り新しいデータを各テーブルに新規登録する
	 * @param timecard 修正用データが格納されたオブジェクト
	 * @throws SQLException
	 */
	public void modifyTime(TimeCard timecard) throws SQLException{
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String arrivalHour = timecard.getArrivalHour();
		String arrivalMinute = timecard.getArrivalMinute();
		String departureHour = timecard.getDeparturehour();
		String departureMinute = timecard.getDepartureMinute();

		Connection conn = null;
		PreparedStatement pst = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
			if(departureHour == null && departureMinute == null){
				String sql = "INSERT INTO ARRIVAL(UUID, DATE_UUID, ARRIVAL_HOUR, ARRIVAL_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, arrivalHour);
				pst.setString(4, arrivalMinute);
				pst.executeUpdate();
			}
			else if(arrivalHour == null && arrivalMinute == null)
			{
				String sql = "INSERT INTO DEPARTURE(UUID, DATE_UUID, DEPARTURE_HOUR, DEPARTURE_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, departureHour);
				pst.setString(4, departureMinute);
				pst.executeUpdate();
			}
			conn.commit();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}

	/**
	 * 今日の日付のUUIDが存在しない場合に新しく作成する
	 * @param timecard 現在の年月日と登録するUUIDが格納されたオブジェクト
	 * @throws SQLException
	 */
	public void registerDate(TimeCard timecard) throws SQLException{
		Connection conn = null;
		PreparedStatement pst = null;
		try {
			String uuid = timecard.getUuid();
			String year = timecard.getYear();
			String month = timecard.getMonth();
			String day = timecard.getDay();
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			conn.setAutoCommit(false);
			String sql = "INSERT INTO DATE(UUID, YEAR, MONTH, DAY)SELECT ?,?,?,? WHERE NOT EXISTS(SELECT UUID FROM DATE WHERE YEAR = ? AND MONTH = ? AND DAY = ?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, uuid);
			pst.setString(2, year);
			pst.setString(3, month);
			pst.setString(4, day);
			pst.setString(5, year);
			pst.setString(6, month);
			pst.setString(7, day);
			pst.executeUpdate();
			conn.commit();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			conn.rollback();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(conn != null){
				conn.close();
			}
		}
	}

	/**
	 * 指定された日付のUUIDを取得し返す
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 取得されたUUID
	 * @throws SQLException
	 */
	public  String getTodayUuid(String year, String month, String day) throws SQLException{
		String uuid = null;
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "SELECT * FROM DATE WHERE YEAR = ? && MONTH = ? && DAY = ?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, year);
			pst.setString(2, month);
			pst.setString(3, day);
			rs = pst.executeQuery();
			rs.next();
			uuid = rs.getString("UUID");
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(pst != null){
				pst.close();
			}
			if(rs != null){
				rs.close();
			}
			if(conn != null){
				conn.close();
			}
		}
		return uuid;
	}
}
