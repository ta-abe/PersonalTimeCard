<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
  </head>
  <body>
    <form method = "POST" action = "personaltimecard">
      <h2>出勤時刻
        <select name = "selectClockInHour">
        <option value = "00">00</option>
        <option value = "01">01</option>
        <option value = "02">02</option>
        <option value = "03">03</option>
        <option value = "04">04</option>
        <option value = "05">05</option>
        <option value = "06">06</option>
        <option value = "07">07</option>
        <option value = "08">08</option>
        <option value = "09">09</option>
        <option value = "10">10</option>
        <option value = "11">11</option>
        <option value = "12">12</option>
        <option value = "13">13</option>
        <option value = "14">14</option>
        <option value = "15">15</option>
        <option value = "16">16</option>
        <option value = "17">17</option>
        <option value = "18">18</option>
        <option value = "19">19</option>
        <option value = "20">20</option>
        <option value = "21">21</option>
        <option value = "22">22</option>
        <option value = "23">23</option>
        </select>
        時
        <select name = "selectClockInMinute">
        <option value = "00">00</option>
        <option value = "01">01</option>
        <option value = "02">02</option>
        <option value = "03">03</option>
        <option value = "04">04</option>
        <option value = "05">05</option>
        <option value = "06">06</option>
        <option value = "07">07</option>
        <option value = "08">08</option>
        <option value = "09">09</option>
        <option value = "10">10</option>
        <option value = "11">11</option>
        <option value = "12">12</option>
        <option value = "13">13</option>
        <option value = "14">14</option>
        <option value = "15">15</option>
        <option value = "16">16</option>
        <option value = "17">17</option>
        <option value = "18">18</option>
        <option value = "19">19</option>
        <option value = "20">20</option>
        <option value = "21">21</option>
        <option value = "22">22</option>
        <option value = "23">23</option>
        <option value = "24">24</option>
        <option value = "25">25</option>
        <option value = "26">26</option>
        <option value = "27">27</option>
        <option value = "28">28</option>
        <option value = "29">29</option>
        <option value = "30">30</option>
        <option value = "31">31</option>
        <option value = "32">32</option>
        <option value = "33">33</option>
        <option value = "34">34</option>
        <option value = "35">35</option>
        <option value = "36">36</option>
        <option value = "37">37</option>
        <option value = "38">38</option>
        <option value = "39">39</option>
        <option value = "40">40</option>
        <option value = "41">41</option>
        <option value = "42">42</option>
        <option value = "43">43</option>
        <option value = "44">44</option>
        <option value = "45">45</option>
        <option value = "46">46</option>
        <option value = "47">47</option>
        <option value = "48">48</option>
        <option value = "49">49</option>
        <option value = "50">50</option>
        <option value = "51">51</option>
        <option value = "52">52</option>
        <option value = "53">53</option>
        <option value = "54">54</option>
        <option value = "55">55</option>
        <option value = "56">56</option>
        <option value = "57">57</option>
        <option value = "58">58</option>
        <option value = "59">59</option>
      </select>
        分
        <button type = "submit" name = "btnModifyClockIn" value = "">修正</button>
      </h2>
      <h2>退勤時刻
        <select name = "selectClockOutHour">
        <option value = "00">00</option>
        <option value = "01">01</option>
        <option value = "02">02</option>
        <option value = "03">03</option>
        <option value = "04">04</option>
        <option value = "05">05</option>
        <option value = "06">06</option>
        <option value = "07">07</option>
        <option value = "08">08</option>
        <option value = "09">09</option>
        <option value = "10">10</option>
        <option value = "11">11</option>
        <option value = "12">12</option>
        <option value = "13">13</option>
        <option value = "14">14</option>
        <option value = "15">15</option>
        <option value = "16">16</option>
        <option value = "17">17</option>
        <option value = "18">18</option>
        <option value = "19">19</option>
        <option value = "20">20</option>
        <option value = "21">21</option>
        <option value = "22">22</option>
        <option value = "23">23</option>
        </select>
        時
        <select name = "selectClockOutMinute">
        <option value = "00">00</option>
        <option value = "01">01</option>
        <option value = "02">02</option>
        <option value = "03">03</option>
        <option value = "04">04</option>
        <option value = "05">05</option>
        <option value = "06">06</option>
        <option value = "07">07</option>
        <option value = "08">08</option>
        <option value = "09">09</option>
        <option value = "10">10</option>
        <option value = "11">11</option>
        <option value = "12">12</option>
        <option value = "13">13</option>
        <option value = "14">14</option>
        <option value = "15">15</option>
        <option value = "16">16</option>
        <option value = "17">17</option>
        <option value = "18">18</option>
        <option value = "19">19</option>
        <option value = "20">20</option>
        <option value = "21">21</option>
        <option value = "22">22</option>
        <option value = "23">23</option>
        <option value = "24">24</option>
        <option value = "25">25</option>
        <option value = "26">26</option>
        <option value = "27">27</option>
        <option value = "28">28</option>
        <option value = "29">29</option>
        <option value = "30">30</option>
        <option value = "31">31</option>
        <option value = "32">32</option>
        <option value = "33">33</option>
        <option value = "34">34</option>
        <option value = "35">35</option>
        <option value = "36">36</option>
        <option value = "37">37</option>
        <option value = "38">38</option>
        <option value = "39">39</option>
        <option value = "40">40</option>
        <option value = "41">41</option>
        <option value = "42">42</option>
        <option value = "43">43</option>
        <option value = "44">44</option>
        <option value = "45">45</option>
        <option value = "46">46</option>
        <option value = "47">47</option>
        <option value = "48">48</option>
        <option value = "49">49</option>
        <option value = "50">50</option>
        <option value = "51">51</option>
        <option value = "52">52</option>
        <option value = "53">53</option>
        <option value = "54">54</option>
        <option value = "55">55</option>
        <option value = "56">56</option>
        <option value = "57">57</option>
        <option value = "58">58</option>
        <option value = "59">59</option>
        </select>
        分
        <button type = "submit" name = "btnModifyClockOut" value = "">修正</button>
      </h2><BR>
      <button type = "submit" name = "btnBack" value = "">戻る</button><BR><BR>
    </form>
    <table border = "1">
      <tr>
        <th>出勤時刻</th>
        <th>登録日時</th>
      </tr>
      <%for(int i = 0; i < 3; i++){ %>
        <tr>
          <th><label>lblClockIn<%=i + 1 %></label></th>
          <th><label>lblRegister<%=i + 1 %></label></th>
        </tr>
      <%} %>
    </table><BR><BR>
    <table border = "1">
      <tr>
        <th>退勤時刻</th>
        <th>登録日時</th>
      </tr>
      <%for(int i = 0; i < 4; i++){ %>
        <tr>
          <th><label>lblClockOut<%=i + 1 %></label></th>
          <th><label>lblRegister<%=i + 1 %></label></th>
        </tr>
      <%} %>
    </table>
  </body>
</html>