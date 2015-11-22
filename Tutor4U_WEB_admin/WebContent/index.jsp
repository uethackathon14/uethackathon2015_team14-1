<%@page import="bean.UserData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.ReadJSON"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script>
function myFunction() {
	
    location.reload();
}
</script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
<%
	if (session.getAttribute("username") == null) {
		response.sendRedirect("login.jsp");
	}
%>
<title>Untitled Document</title>
</head>

<body>

	<div class="center_content">
		<div class="center_title_bar">Danh sách gia sư</div>
		<%
			ReadJSON rs=new ReadJSON();
			ArrayList<UserData> list=rs.getData();
			for(int i=0;i<list.size();i++)
			{
		%>
		<div class="prod_box">
			<div class="top_prod_box"></div>
			<div class="center_prod_box">
				<div class="product_title">
					<a href="details.jsp"><%=list.get(i).getUsername()%></a>
				</div>
				<div class="product_img">
					<a href="#"><img src="<%=list.get(i).getAvatar()%>" alt=""
						border="0"></a>
				</div>
				<div class="prod_price">
					<span class="reduce">Trình độ: <%=list.get(i).getLevel()%></span> <br>
				</div>
				<div class="prod_price">
					<span class="reduce">Môn học: <%=list.get(i).getSubJect()%></span>
					<br>
				</div>
				<div class="prod_price">
					<span class="reduce">Số điện thoại: <%=list.get(i).getPhone()%></span>
					<br>
				</div>
				<div class="prod_price">
					<span class="reduce">Email: <%=list.get(i).getEmail()%></span> <br>
				</div>
			</div>
			<div class="bottom_prod_box"></div>
			<div class="prod_details_tab">
				<a href="#" class="accept">Delete</a> <a
					href="http://tutor4u-anhdat.rhcloud.com/TutorWS/data/json/accept/<%=list.get(i).getUsername()%>" onclick="myFunction()"
					class="prod_details">Accept </a>
			</div>
		</div>
		<%
			}
		%>
	
</body>
</html>
