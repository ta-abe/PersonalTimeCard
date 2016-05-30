package personaltimecard;

import java.util.List;

public class PersonalTimeCard {

	private final String dbUrl = "jdbc:mysql://localhost:3306/sample";
	private final String dbUser = "root";
	private final String dbPass = "8121";

	public List<TimeCard> getCurrentList(String year, String month){
		return null;

	}

	public List<TimeCard> getModifyHistory(String dateUuid){
		return null;

	}

	public void register_Clock(TimeCard timecard){

	}

	public void modify_Clock(TimeCard timecard){

	}
}
