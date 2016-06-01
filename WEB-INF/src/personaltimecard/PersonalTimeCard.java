package personaltimecard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PersonalTimeCard {

	private final String dbUrl = "jdbc:mysql://localhost:3306/sample";
	private final String dbUser = "root";
	private final String dbPass = "8121";

	public List<TimeCard> getCurrentList(String year, String month) throws ClassNotFoundException, SQLException{
		List<TimeCard> array = new ArrayList<TimeCard>();
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = null;
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
		String sql = "SELECT * FROM DATE WHERE YEAR = ? AND MONTH = ? ORDER BY YEAR,MONTH,CAST(DAY AS SIGNED)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, year);
		pst.setString(2, month);
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			String day = rs.getString("DAY");
			String dateUuid = rs.getString("UUID");
			sql = "SELECT * FROM ARRIVAL WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			PreparedStatement pst2 = conn.prepareStatement(sql);
			pst2.setString(1, dateUuid);
			ResultSet rs2 = pst2.executeQuery();
			rs2.next();
			String arrivalHour = rs2.getString("ARRIVAL_HOUR");
			String arrivalMinute = rs2.getString("ARRIVAL_MINUTE");

			sql = "SELECT * FROM DEPARTURE WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			PreparedStatement pst3 = conn.prepareStatement(sql);
			pst3.setString(1, dateUuid);
			ResultSet rs3 = pst3.executeQuery();
			rs3.next();
			String departureHour = rs3.getString("DEPARTURE_HOUR");
			String departureMinute = rs3.getString("DEPARTURE_MINUTE");

			TimeCard timecard = new TimeCard(dateUuid, day, arrivalHour, arrivalMinute, departureHour, departureMinute);
			array.add(timecard);
		}
		return array;
	}

	public TimeCard getModifyHistory(String dateUuid) {
		TimeCard timecard = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "SELECT * FROM ARRIVAL WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, dateUuid);
			ResultSet rs = pst.executeQuery();
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
			PreparedStatement pst2 = conn.prepareStatement(sql);
			pst2.setString(1, dateUuid);
			ResultSet rs2 = pst2.executeQuery();
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
		return timecard;
	}

	public void registerTime(TimeCard timecard){
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String arrivalHour = timecard.getArrivalHour();
		String arrivalMinute = timecard.getArrivalMinute();
		String departureHour = timecard.getDeparturehour();
		String departureMinute = timecard.getDepartureMinute();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if(departureHour == null && departureMinute == null){
				String sql = "INSERT INTO ARRIVAL(UUID, DATE_UUID, ARRIVAL_HOUR, ARRIVAL_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM ARRIVAL WHERE DATE_UUID = ?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, arrivalHour);
				pst.setString(4, arrivalMinute);
				pst.setString(5, dateUuid);
				pst.executeUpdate();
			}
			else if(arrivalHour == null && arrivalMinute == null)
			{
				String sql = "INSERT INTO DEPARTURE(UUID, DATE_UUID, DEPARTURE_HOUR, DEPARTURE_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM DEPARTURE WHERE DATE_UUID = ?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, departureHour);
				pst.setString(4, departureMinute);
				pst.setString(5, dateUuid);
				pst.executeUpdate();
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


	}

	public void modifyTime(TimeCard timecard){
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String arrivalHour = timecard.getArrivalHour();
		String arrivalMinute = timecard.getArrivalMinute();
		String departureHour = timecard.getDeparturehour();
		String departureMinute = timecard.getDepartureMinute();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if(departureHour == null && departureMinute == null){
				String sql = "INSERT INTO ARRIVAL(UUID, DATE_UUID, ARRIVAL_HOUR, ARRIVAL_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, arrivalHour);
				pst.setString(4, arrivalMinute);
				pst.executeUpdate();
			}
			else if(arrivalHour == null && arrivalMinute == null)
			{
				String sql = "INSERT INTO DEPARTURE(UUID, DATE_UUID, DEPARTURE_HOUR, DEPARTURE_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, departureHour);
				pst.setString(4, departureMinute);
				pst.executeUpdate();
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



	}

	public void registerDate(TimeCard timecard){
		try {
			String uuid = timecard.getUuid();
			String year = timecard.getYear();
			String month = timecard.getMonth();
			String day = timecard.getDay();
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "INSERT INTO DATE(UUID, YEAR, MONTH, DAY)SELECT ?,?,?,? WHERE NOT EXISTS(SELECT UUID FROM DATE WHERE YEAR = ? AND MONTH = ? AND DAY = ?)";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, uuid);
			pst.setString(2, year);
			pst.setString(3, month);
			pst.setString(4, day);
			pst.setString(5, year);
			pst.setString(6, month);
			pst.setString(7, day);
			pst.executeUpdate();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	public  String getTodayUuid(String year, String month, String day){
		String uuid = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			String sql = "SELECT * FROM DATE WHERE YEAR = ? && MONTH = ? && DAY = ?";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, year);
			pst.setString(2, month);
			pst.setString(3, day);
			ResultSet rs = pst.executeQuery();
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
		return uuid;
	}
}
