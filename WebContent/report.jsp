<%@page import="java.time.Year"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="ru.sawasemykin.web.jdbc.*"  %>

<%
	Client theClient = (Client) request.getAttribute("THE_CLIENT");
	pageContext.setAttribute("theClient", theClient);	
	pageContext.setAttribute("thePayment", theClient.getPayment());
%>

<!DOCTYPE html>
<html lang="ru">
	
	<head>		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Стоимость услуг ЖКХ</title>
		<link type="text/css" rel="stylesheet" href="css/report.css" />
	</head>
	
	<body>
	
		<header><h2>Здравстуйте, ${theClient.firstName}!</h2> </header>
		
		<div class="reporting">
			<h3>Счёт для внесения платы по видам коммунальных услуг</h3>
			<table>
				<caption></caption>
				<tr>
					<th>Услуга</th>
					<th>Ед. изм.</th>
					<th>Тариф, руб</th>
					<th>Объём оказан<wbr>ной услуги</th>
					<th>Начис<wbr>ленно за период, руб</th>
				</tr>
				<tr class="rowcolor">
					<td>Эл. энергия</td>
					<td>кВт/ч</td>
					<td class="num">${Payment.ELECTRICITY_RATE}</td>
					<td class="num">${thePayment.electrVolume}</td>
					<td class="num">${thePayment.electrValue}</td>
				</tr>
				<tr>
					<td>Хол. вода</td>
					<td>куб. м</td>
					<td class="num">${Payment.WATER_RATE}</td>
					<td class="num">${thePayment.cWVolume}</td>
					<td class="num">${thePayment.cWValue}</td>
				</tr>				  			  
				<tr class="rowcolor">
					<td>ХВС для ГВС</td>
					<td>куб. м</td>
					<td class="num">${Payment.WATER_RATE}</td>
					<td class="num">${thePayment.hWVolume}</td>
					<td class="num">${thePayment.hWValue}</td>
				</tr>
				<tr>
					<td>Водо<wbr>отведе<wbr>ние</td>
					<td>куб. м</td>
					<td class="num">${Payment.REMOVE_WATER_RATE}</td>
					<td class="num">${thePayment.remWVolume}</td>
					<td class="num">${thePayment.remWValue}</td>
				</tr>
				<tr class="rowcolor totalsum">
					<td colspan="4">Итого начислено за период:</td>
					
					
					
					<td class="num">${thePayment.totalSum}</td>
				</tr>				
			</table>
		</div>
			
		<div class="sending">
			<h3>Передать показания</h3>			
			<form action=ReadingControllerServlet method="get">	
				<input type=hidden name=command value=SEND_READING>	
				<table>
					<caption></caption>
					<tr>
						<th>Электроэнергия</th>
						<td><input type="number" name="electricity"></td>
					</tr>	
					<tr>
						<th>Хол. вода</th>
						<td><input type="number" name="coldWater"></td>
					</tr>	
					<tr>
						<th>Гор. вода</th>
						<td><input type="number" name="hotWater"></td>
					</tr>	
					<tr>						
						<td colspan="2"><input type="submit" value="Передать" class="btn"></td>
					</tr>
				</table>
			</form>
		</div>			
	
		<footer>
			<p>Автор: Александр Геннадиевич Семыкин</p>
			<p><%=Year.now()%> год</p>		
		</footer>
	
	</body>

</html>