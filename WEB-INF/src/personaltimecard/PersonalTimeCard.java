package personaltimecard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		String sql = "SELECT * FROM DATE WHERE YEAR = ? AND MONTH = ? ORDER BY YEAR,MONTH,CAST(day as signed)";
		PreparedStatement pst = conn.prepareStatement(sql);
		pst.setString(1, year);
		pst.setString(2, month);
		ResultSet rs = pst.executeQuery();
		while(rs.next()){
			String day = rs.getString("DAY");
			String dateUuid = rs.getString("UUID");
			sql = "SELECT * FROM CLOCK_IN WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			PreparedStatement pst2 = conn.prepareStatement(sql);
			pst2.setString(1, dateUuid);
			ResultSet rs2 = pst2.executeQuery();
			rs2.next();
			String clockInHour = rs2.getString("CLOCK_IN_HOUR");
			String clockInMinute = rs2.getString("CLOCK_IN_MINUTE");

			sql = "SELECT * FROM CLOCK_OUT WHERE DATE_UUID = ? ORDER BY REGISTERED_DATETIME DESC";
			PreparedStatement pst3 = conn.prepareStatement(sql);
			pst3.setString(1, dateUuid);
			ResultSet rs3 = pst3.executeQuery();
			rs3.next();
			String clockOutHour = rs3.getString("CLOCK_OUT_HOUR");
			String clockOutMinute = rs3.getString("CLOCK_OUT_MINUTE");

			TimeCard timecard = new TimeCard(day, clockInHour, clockInMinute, clockOutHour, clockOutMinute);
			array.add(timecard);
		}
		return array;
	}

	public List<TimeCard> getModifyHistory(String dateUuid){
		return null;
	}

	public void registerClock(TimeCard timecard){
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String clockInHour = timecard.getClockInHour();
		String clockInMinute = timecard.getClockInMinute();
		String clockOutHour = timecard.getClockOuthour();
		String clockOutMinute = timecard.getClockOutMinute();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if(clockOutHour == null && clockOutMinute == null){
				String sql = "INSERT INTO CLOCK_IN(UUID, DATE_UUID, CLOCK_IN_HOUR, CLOCK_IN_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM CLOCK_IN WHERE DATE_UUID = ?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, clockInHour);
				pst.setString(4, clockInMinute);
				pst.setString(5, dateUuid);
				pst.executeUpdate();
			}
			else if(clockInHour == null && clockInMinute == null)
			{
				String sql = "INSERT INTO CLOCK_OUT(UUID, DATE_UUID, CLOCK_OUT_HOUR, CLOCK_OUT_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME) WHERE NOT EXISTS(SELECT UUID FROM CLOCK_OUT WHERE DATE_UUID = ?)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, clockOutHour);
				pst.setString(4, clockOutMinute);
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

	public void modifyClock(TimeCard timecard){
		String uuid = timecard.getUuid();
		String dateUuid = timecard.getDateUuid();
		String clockInHour = timecard.getClockInHour();
		String clockInMinute = timecard.getClockInMinute();
		String clockOutHour = timecard.getClockOuthour();
		String clockOutMinute = timecard.getClockOutMinute();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = null;
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
			if(clockOutHour == null && clockOutMinute == null){
				String sql = "INSERT INTO CLOCK_IN(UUID, DATE_UUID, CLOCK_IN_HOUR, CLOCK_IN_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, clockInHour);
				pst.setString(4, clockInMinute);
				pst.executeUpdate();
			}
			else if(clockInHour == null && clockInMinute == null)
			{
				String sql = "INSERT INTO CLOCK_OUT(UUID, DATE_UUID, CLOCK_OUT_HOUR, CLOCK_OUT_MINUTE, REGISTERED_DATETIME)SELECT ?,?,?,?,CAST(NOW() AS DATETIME)";
				PreparedStatement pst = conn.prepareStatement(sql);
				pst.setString(1, uuid);
				pst.setString(2, dateUuid);
				pst.setString(3, clockOutHour);
				pst.setString(4, clockOutMinute);
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
