<%@ page language = "java" contentType = "text/html; charset = UTF-8"
    pageEncoding = "UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
    <title>一覧表示</title>
  </head>
  <body>
    <form method = "POST" action = "personaltimecard">
      <select name = "selectYear" required>
        <option value = "">--</option>
        <option value = "2010">2010</option>
        <option value = "2011">2011</option>
        <option value = "2012">2012</option>
        <option value = "2013">2013</option>
        <option value = "2014">2014</option>
        <option value = "2015">2015</option>
        <option value = "2016">2016</option>
        <option value = "2017">2017</option>
        <option value = "2018">2018</option>
        <option value = "2019">2019</option>
        <option value = "2020">2020</option>
        <option value = "2021">2021</option>
        <option value = "2022">2022</option>
        <option value = "2023">2023</option>
        <option value = "2024">2024</option>
        <option value = "2025">2025</option>
        <option value = "2026">2026</option>
        <option value = "2027">2027</option>
        <option value = "2028">2028</option>
        <option value = "2029">2029</option>
        <option value = "2030">2030</option>
      </select>年
      <select name = "selectMonth" required>
        <option value = "">--</option>
        <option value = "1">1</option>
        <option value = "2">2</option>
        <option value = "3">3</option>
        <option value = "4">4</option>
        <option value = "5">5</option>
        <option value = "6">6</option>
        <option value = "7">7</option>
        <option value = "8">8</option>
        <option value = "9">9</option>
        <option value = "10">10</option>
        <option value = "11">11</option>
        <option value = "12">12</option>
      </select>月
      <button type = "submit" name = "btnList" value = "btnList">一覧表示</button><BR>
    </form>
    <%
       Object year = request.getAttribute("year");
       Object month = request.getAttribute("month");
    %>
    <h2>
      <%=year%>年<%=month%>月出退勤時刻一覧
    </h2>
    <table border = "1">
      <tr>
        <th>日付</th>
        <th>出勤時間</th>
        <th>退勤時間</th>
        <th></th>
      </tr>
      <%
         Object size = request.getAttribute("size");
         int j = 0;
         if(size != null){
            String s ;
            s = size.toString();
            j = Integer.parseInt(s);
         }
         for(int i = 0; i < j; i++){
      %>
      <tr>
        <form method = "post" action = "personaltimecard">
          <th width = "50"><label name = "lblDay"><%=request.getAttribute("lblDay" + i)%></label></th>
          <th width = "100"><label name = "lblArrivalHour"><%=request.getAttribute("lblArrivalHour" + i)%>:<%=request.getAttribute("lblArrivalMinute" + i)%></label></th>
          <th width = "100"><label name = "lblDeparture"><%=request.getAttribute("lblDepartureHour" + i)%>:<%=request.getAttribute("lblDepartureMinute" + i)%></label></th>
          <th width = "80">
            <button type = "submit" name  = "btnModify" value = "btnModify">修正</button>
            <input type = "hidden" name = "hidUuid" value = <%=request.getAttribute("hidUuid" + i) %>>
            <input type = "hidden" name = "hidYear" value = <%=year%>>
            <input type = "hidden" name = "hidMonth" value = <%=month%>>
            <input type = "hidden" name = "hidDay" value = <%=request.getAttribute("lblDay" + i)%>>
          </th>
        </form>
      </tr>
      <%} %>
    </table>
    <BR>
    <form method = "POST" action = "personaltimecard">
      <button type = "submit" name = "btnBack" value = "btnBack001" style = "position : relative ; left : 230px ; width : 100px">戻る</button>
    </form>
  </body>
</html>