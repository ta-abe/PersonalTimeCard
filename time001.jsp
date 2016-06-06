<%@ page import = "java.util.Calendar"%>
<%@ page language = "java" contentType = "text/html; charset = UTF-8" pageEncoding = "UTF-8"%>
<%
  Calendar now = Calendar.getInstance();
  int year = now.get(now.YEAR);
  int month = now.get(now.MONTH);
  int day  = now.get(now.DATE);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv = "Content-Type" content = "text/html; charset = UTF-8">
    <title>初期画面</title>
  </head>
  <body>
    <%
      Object hiduuid = request.getAttribute("hidUuid");
      Object num = request.getAttribute("num");
      if(null == hiduuid && null == num){
    %>
    <form method = "GET" action = "personaltimecard">
       <meta http-equiv = "refresh" content = "0;URL=http://localhost:8080/PersonalTimeCard/personaltimecard?year=<%=year%>&month=<%=month + 1%>&day=<%=day%>">
    </form>
    <%}else if(null == hiduuid && null != num){%>
    <form method = "GET" action = "personaltimecard">
       <meta http-equiv = "refresh" content = "0;URL=http://localhost:8080/PersonalTimeCard/personaltimecard?year=<%=year%>&month=<%=month + 1%>&day=<%=day%>&mode=<%=num%>">
    </form>
    <%}else{%>
    <h1>
      <SCRIPT type="text/javascript"><!--
        function myFunc(){
          myDate=new Date();
          myMsg = myDate.getFullYear() + "年";
          myMsg += ( myDate.getMonth() + 1 ) + "月";
          myMsg += myDate.getDate() + "日<BR>&emsp;&emsp;";
          myMsg += ("0"+myDate.getHours()).slice(-2) + ":";
          myMsg += ("0"+myDate.getMinutes()).slice(-2) + "";
          document.getElementById("myIDdate").innerHTML = "&nbsp;" + myMsg;
        }
        // -->
      </SCRIPT>
      <DIV id="myIDdate">&nbsp;</DIV>
            <SCRIPT type="text/javascript">
      <!--
        setInterval( "myFunc()", 1 );
        //
      -->
      </SCRIPT>
    </h1>
    <%
      Object mode = request.getAttribute("mode");
      if(null != mode){
    %>
    <h2>&nbsp;!<%=mode%></h2>
    <%}%>
    <form method = "POST" action = "personaltimecard">
      <input type = "hidden" name = "hidUuid" value = "<%=request.getAttribute("hidUuid")%>">&nbsp;&nbsp;
      <button type = "submit" name = "btnArrival" value = "btnArrival" style = "width : 81px; height : 50px"><h2>出勤</h2></button>
      <button type = "submit" name = "btnDeparture" value = "btnDeparture" style = "width : 81px; height : 50px"><h2>退勤</h2></button><BR><BR>
    </form>
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
      <button type = "submit" name = "btnList" value = "btnList">一覧表示</button>
    </form>
    <%} %>
  </body>
</html>