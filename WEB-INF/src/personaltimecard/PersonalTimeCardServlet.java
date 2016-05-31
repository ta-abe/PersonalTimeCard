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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		PersonalTimeCard personaltimecard = new PersonalTimeCard();
		String year = req.getParameter("year");
		String month = req.getParameter("month");
		String day = req.getParameter("day");
		TimeCard timecard = new TimeCard(year, month, day);
		personaltimecard.registerDate(timecard);
		String uuid = personaltimecard.getTodayUuid(year, month, day);
		req.setAttribute("hidUuid", uuid);
		getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");

		if("btnList".equals(req.getParameter("btnList"))){
			String year = req.getParameter("selectYear");
			String month = req.getParameter("selectMonth");
			PersonalTimeCard personaltimecard = new PersonalTimeCard();
			try {
				List<TimeCard> list = personaltimecard.getCurrentList(year, month);
				TimeCard timecard = null;
				int size = list.size();
				req.setAttribute("size", size);
				for(int i = 0;i < size;i++){
					timecard = list.get(i);
					String day = timecard.getDay();
					String clockInHour = timecard.getClockInHour();
					String clockInMinute = timecard.getClockInMinute();
					String clockOutHour = timecard.getClockOuthour();
					String clockOutMinute = timecard.getClockOutMinute();
					req.setAttribute("lblDay" + i, day);
					req.setAttribute("lblClockInHour" + i, clockInHour);
					req.setAttribute("lblClockInMinute" + i, clockInMinute);
					req.setAttribute("lblClockOutHour" + i, clockOutHour);
					req.setAttribute("lblClockOutMinute" + i, clockOutMinute);
				}
			} catch (ClassNotFoundException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

			getServletConfig().getServletContext().getRequestDispatcher("/time002.jsp").forward(req, resp);
		}
		else if("btnClockIn".equals(req.getParameter("btnClockIn")))
		{
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
			String day = String.valueOf(cal.get(Calendar.DATE));
			String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
			String minute = String.valueOf(cal.get(Calendar.MINUTE));
			String uuid = java.util.UUID.randomUUID().toString();
			PersonalTimeCard personaltimecard = new PersonalTimeCard();
			String dateUuid = personaltimecard.getTodayUuid(year, month, day);
			TimeCard timecard = new TimeCard(uuid, dateUuid, hour, minute, null, null);
			registerClock(timecard);
			getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
		}
		else if("btnClockOut".equals(req.getParameter("btnClockOut")))
		{
			Calendar cal = Calendar.getInstance();
			String year = String.valueOf(cal.get(Calendar.YEAR));
			String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
			String day = String.valueOf(cal.get(Calendar.DATE));
			String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
			String minute = String.valueOf(cal.get(Calendar.MINUTE));
			String uuid = java.util.UUID.randomUUID().toString();
			PersonalTimeCard personaltimecard = new PersonalTimeCard();
			String dateUuid = personaltimecard.getTodayUuid(year, month, day);
			TimeCard timecard = new TimeCard(uuid, dateUuid, null, null, hour, minute);
			registerClock(timecard);
			getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
		}
		else if("btnBack001".equals(req.getParameter("btnBack")))
		{
			getServletConfig().getServletContext().getRequestDispatcher("/time001.jsp").forward(req, resp);
		}
		else if("btnModify".equals(req.getParameter("btnModify")))
		{
			req.setAttribute("hidUuid", "acd9509f-a83c-418c-8405-c3219b2c7dac");//テスト用UUID
			getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
		}
		else if("btnModifyClockIn".equals(req.getParameter("btnModifyClockIn")))
		{
			String uuid = java.util.UUID.randomUUID().toString();
			String dateUuid = req.getParameter("hidUuid");
			String clockInHour = req.getParameter("selectClockInHour");
			String clockInMinute = req.getParameter("selectClockInMinute");
			TimeCard timecard = new TimeCard(uuid, dateUuid, clockInHour, clockInMinute, null, null);
			modifyClock(timecard);
			req.setAttribute("hidUuid", dateUuid);
			getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
		}
		else if("btnModifyClockOut".equals(req.getParameter("btnModifyClockOut")))
		{
			String uuid = java.util.UUID.randomUUID().toString();
			String dateUuid = req.getParameter("hidUuid");
			String clockOutHour = req.getParameter("selectClockOutHour");
			String clockOutMinute = req.getParameter("selectClockOutMinute");
			TimeCard timecard = new TimeCard(uuid, dateUuid, null, null, clockOutHour, clockOutMinute);
			modifyClock(timecard);
			req.setAttribute("hidUuid", dateUuid);
			getServletConfig().getServletContext().getRequestDispatcher("/time003.jsp").forward(req, resp);
		}
		else if("btnBack002".equals(req.getParameter("btnBack")))
		{
			getServletConfig().getServletContext().getRequestDispatcher("/time002.jsp").forward(req, resp);
		}
	}

	private void registerClock(TimeCard timecard){
		PersonalTimeCard personaltimecard = new PersonalTimeCard();
		personaltimecard.registerClock(timecard);
	}

	private void modifyClock(TimeCard timecard){
		PersonalTimeCard personaltimecard = new PersonalTimeCard();
		personaltimecard.modifyClock(timecard);
	}
}
