/**
 *
 */
package personaltimecard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author excite
 *
 */
public class PersonalTimeCardServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("UTF-8");
			PersonalTimeCard personaltimecard = new PersonalTimeCard();
			Object objYear = req.getParameter("year");
			Object objMonth = req.getParameter("month");
			Object objDay = req.getParameter("day");
			String year = null;
			String month = null;
			String day = null;
			if(objYear != null && objYear != "" && objMonth != null && objMonth != "" && objDay != null && objDay != ""){
				year = objYear.toString();
				month = objMonth.toString();
				day = objDay.toString();
				Calendar cal = Calendar.getInstance();
				String a = String.valueOf(cal.get(Calendar.YEAR));
				String b = String.valueOf((cal.get(Calendar.MONTH) + 1));
				String c = String.valueOf(cal.get(Calendar.DATE));
				if(year.equals(a) && month.equals(b) && day.equals(c)){
					String mode = req.getParameter("mode");
					TimeCard timecard = new TimeCard(year.toString(), month.toString(), day.toString());
					personaltimecard.registerDate(timecard);
					String uuid = personaltimecard.getTodayUuid(year.toString(), month.toString(), day.toString());
					req.setAttribute("hidUuid", uuid);
					if(null != req.getParameter("mode")){
						if(mode.equals("1")){
							req.setAttribute("mode", "登録が完了しました");
						}
						else if(mode.equals("0")){
							req.setAttribute("mode", "既に登録されています");
						}
					}
				}
			}
			getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		try {
			req.setCharacterEncoding("UTF-8");
			if("btnList".equals(req.getParameter("btnList"))){
				String year = req.getParameter("selectYear");
				String month = req.getParameter("selectMonth");
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				List<TimeCard> list = personaltimecard.getCurrentList(year, month);
				TimeCard timecard = null;
				int size = list.size();
				req.setAttribute("size", size);
				for(int i = 0; i < size; i++){
					timecard = list.get(i);
					String dateUuid = timecard.getDateUuid();
					String day = timecard.getDay();
					String arrivalHour = timecard.getArrivalHour();
					String arrivalMinute = timecard.getArrivalMinute();
					String departureHour = timecard.getDeparturehour();
					String departureMinute = timecard.getDepartureMinute();
					req.setAttribute("hidUuid" + i, dateUuid);
					req.setAttribute("lblDay" + i, day);
					req.setAttribute("lblArrivalHour" + i, arrivalHour);
					req.setAttribute("lblArrivalMinute" + i, arrivalMinute);
					req.setAttribute("lblDepartureHour" + i, departureHour);
					req.setAttribute("lblDepartureMinute" + i, departureMinute);
				}
				req.setAttribute("year", year);
				req.setAttribute("month", month);
				getServletConfig().getServletContext().getRequestDispatcher("/time002.jsp").forward(req, resp);
			}
			else if("btnArrival".equals(req.getParameter("btnArrival")))
			{
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
				String day = String.valueOf(cal.get(Calendar.DATE));
				String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				String minute = String.valueOf(cal.get(Calendar.MINUTE));
				if(Integer.parseInt(hour) < 10){
					hour = "0" + hour;
				}
				if(Integer.parseInt(minute) < 10){
					minute = "0" + minute;
				}
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				String dateUuid = personaltimecard.getTodayUuid(year, month, day);
				TimeCard timecard = new TimeCard(dateUuid, hour, minute, null, null);
				int num = registerTime(timecard);
				req.setAttribute("num", num);
				getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
			}
			else if("btnDeparture".equals(req.getParameter("btnDeparture")))
			{
				Calendar cal = Calendar.getInstance();
				String year = String.valueOf(cal.get(Calendar.YEAR));
				String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
				String day = String.valueOf(cal.get(Calendar.DATE));
				String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
				String minute = String.valueOf(cal.get(Calendar.MINUTE));
				if(Integer.parseInt(hour) < 10){
					hour = "0" + hour;
				}
				if(Integer.parseInt(minute) < 10){
					minute = "0" + minute;
				}
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				String dateUuid = personaltimecard.getTodayUuid(year, month, day);
				TimeCard timecard = new TimeCard(dateUuid, null, null, hour, minute);
				int num =  registerTime(timecard);
				req.setAttribute("num", num);
				getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
			}
			else if("btnBack001".equals(req.getParameter("btnBack")))
			{
				getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
			}
			else if("btnModify".equals(req.getParameter("btnModify")))
			{
				String uuid = req.getParameter("hidUuid");
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				TimeCard timecard = personaltimecard.getModifyHistory(uuid);
				List<String> arrivalList = timecard.getArrivalList();
				List<String> arrivalRegisteredDatetime = timecard.getArrivalRegisteredDatetime();
				List<String> departureList = timecard.getDepartureList();
				List<String> departureRegisteredDatetime = timecard.getDepartureRegisteredDatetime();
				int arrivalSize = arrivalList.size();
				for(int i = 0; i < arrivalSize; i++){
					req.setAttribute("lblArrivalHistory" + i, arrivalList.get(i));
					req.setAttribute("lblArrivalRegistered" + i, arrivalRegisteredDatetime.get(i));
				}
				int departureSize = departureList.size();
				for(int i = 0 ; i < departureSize; i ++){
					req.setAttribute("lblDepartureHistory" + i, departureList.get(i));
					req.setAttribute("lblDepartureRegistered" + i, departureRegisteredDatetime.get(i));
				}
				req.setAttribute("year", req.getParameter("hidYear"));
				req.setAttribute("month", req.getParameter("hidMonth"));
				req.setAttribute("day", req.getParameter("hidDay"));
				req.setAttribute("arrivalSize", arrivalSize);
				req.setAttribute("departureSize", departureSize);
				req.setAttribute("hidUuid", uuid);
				getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
			}
			else if("btnModifyArrival".equals(req.getParameter("btnModifyArrival")))
			{
				String uuid = req.getParameter("hidUuid");
				String arrivalHour = req.getParameter("selectArrivalHour");
				String arrivalMinute = req.getParameter("selectArrivalMinute");
				TimeCard timecard = new TimeCard(uuid, arrivalHour, arrivalMinute, null, null);
				modifyTime(timecard);
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				TimeCard timecard1 = personaltimecard.getModifyHistory(uuid);
				List<String> arrivalList = timecard1.getArrivalList();
				List<String> arrivalRegisteredDatetime = timecard1.getArrivalRegisteredDatetime();
				List<String> departureList = timecard1.getDepartureList();
				List<String> departureRegisteredDatetime = timecard1.getDepartureRegisteredDatetime();
				int arrivalSize = arrivalList.size();
				for(int i = 0; i < arrivalSize; i++){
					req.setAttribute("lblArrivalHistory" + i, arrivalList.get(i));
					req.setAttribute("lblArrivalRegistered" + i, arrivalRegisteredDatetime.get(i));
				}
				int departureSize = departureList.size();
				for(int i = 0 ; i < departureSize; i ++){
					req.setAttribute("lblDepartureHistory" + i, departureList.get(i));
					req.setAttribute("lblDepartureRegistered" + i, departureRegisteredDatetime.get(i));
				}
				req.setAttribute("arrivalSize", arrivalSize);
				req.setAttribute("departureSize", departureSize);
				req.setAttribute("hidUuid", uuid);
				req.setAttribute("year", req.getParameter("hidYear"));
				req.setAttribute("month", req.getParameter("hidMonth"));
				req.setAttribute("day", req.getParameter("hidDay"));
				getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
			}
			else if("btnModifyDeparture".equals(req.getParameter("btnModifyDeparture")))
			{
				String uuid = req.getParameter("hidUuid");
				String departureHour = req.getParameter("selectDepartureHour");
				String departureMinute = req.getParameter("selectDepartureMinute");
				TimeCard timecard = new TimeCard(uuid, null, null, departureHour, departureMinute);
				modifyTime(timecard);
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				TimeCard timecard1 = personaltimecard.getModifyHistory(uuid);
				List<String> arrivalList = timecard1.getArrivalList();
				List<String> arrivalRegisteredDatetime = timecard1.getArrivalRegisteredDatetime();
				List<String> departureList = timecard1.getDepartureList();
				List<String> departureRegisteredDatetime = timecard1.getDepartureRegisteredDatetime();
				int arrivalSize = arrivalList.size();
				for(int i = 0; i < arrivalSize; i++){
					req.setAttribute("lblArrivalHistory" + i, arrivalList.get(i));
					req.setAttribute("lblArrivalRegistered" + i, arrivalRegisteredDatetime.get(i));
				}
				int departureSize = departureList.size();
				for(int i = 0 ; i < departureSize; i++){
					req.setAttribute("lblDepartureHistory" + i, departureList.get(i));
					req.setAttribute("lblDepartureRegistered" + i, departureRegisteredDatetime.get(i));
				}
				req.setAttribute("arrivalSize", arrivalSize);
				req.setAttribute("departureSize", departureSize);
				req.setAttribute("hidUuid", uuid);
				req.setAttribute("year", req.getParameter("hidYear"));
				req.setAttribute("month", req.getParameter("hidMonth"));
				req.setAttribute("day", req.getParameter("hidDay"));
				getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
			}
			else if("btnBack002".equals(req.getParameter("btnBack")))
			{
				String year = req.getParameter("hidYear");
				String month = req.getParameter("hidMonth");
				PersonalTimeCard personaltimecard = new PersonalTimeCard();
				List<TimeCard> list = personaltimecard.getCurrentList(year, month);
				TimeCard timecard = null;
				int size = list.size();
				req.setAttribute("size", size);
				for(int i = 0; i < size; i++){
					timecard = list.get(i);
					String dateUuid = timecard.getDateUuid();
					String day = timecard.getDay();
					String arrivalHour = timecard.getArrivalHour();
					String arrivalMinute = timecard.getArrivalMinute();
					String departureHour = timecard.getDeparturehour();
					String departureMinute = timecard.getDepartureMinute();
					req.setAttribute("hidUuid" + i, dateUuid);
					req.setAttribute("lblDay" + i, day);
					req.setAttribute("lblArrivalHour" + i, arrivalHour);
					req.setAttribute("lblArrivalMinute" + i, arrivalMinute);
					req.setAttribute("lblDepartureHour" + i, departureHour);
					req.setAttribute("lblDepartureMinute" + i, departureMinute);
				}
				req.setAttribute("year", year);
				req.setAttribute("month", month);
				getServletConfig().getServletContext().getRequestDispatcher("/time002.jsp").forward(req, resp);
			}
		}
		catch (ServletException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 新規に登録したいデータを受け取り、デーブルに登録する
	 * @param timecard 新規登録用のデータが格納されたオブジェクト
	 * @throws SQLException
	 */
	private int registerTime(TimeCard timecard) throws SQLException{
		PersonalTimeCard personaltimecard = new PersonalTimeCard();
		int num =  personaltimecard.registerTime(timecard);
		return num;
	}

	/**
	 * 修正用のデータを受け取り、テーブルに登録する
	 * @param timecard 修正用のデータが格納されたオブジェクト
	 * @throws SQLException
	 */
	private void modifyTime(TimeCard timecard) throws SQLException{
		PersonalTimeCard personaltimecard = new PersonalTimeCard();
		personaltimecard.modifyTime(timecard);
	}
}
