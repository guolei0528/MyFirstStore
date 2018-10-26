<!DOCTYPE html>
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<c:set var="contentPath" value="<%=request.getContextPath()%>"></c:set>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
	<title><%=PropertiesUtil.get("PROJECT_NAME") %></title>
	<link  href="${contentPath }/css/login.css" rel="stylesheet" type="text/css" />
	<style type="text/css">
		body{height:100%; overflow:hidden; background-color: #d7e8f8;margin: 0px;padding: 0px}
		#contentbottom{
		 width:100%; 
		 height:12px;
		 position:absolute;
		 text-align:center;
		 bottom:10px;
		}
	</style>
</head>
<body>
	<form action="checkLogin.shtml" method="post" id="pageForm">
		<div class="l-box-container">
<!-- 		<div style="color:#00FF00"> -->
<!--         <h1 style="text-align:center">This is a header</h1> -->
<!--         </div> -->
			<div class="loginbox">
				<div class="l-box-login-inputbox">
					<label>用户名：</label><input type="text" id="user" name="loginName" class="input_text" autocomplete="off"/>
				</div>
				<div class="l-box-login-inputbox">
					<label>密<span style="width:14px;text-align:left;display:inline-block;">&nbsp;</span>码：</label><input type="password" name="password" class="input_text" autocomplete="off"/><br />
				</div>
				
				<div class="l-box-login-btnbox">
					<input name="提交" type="submit" class="btn_all btn_login"
						onClick="locking()" value="登录" />&nbsp; 
					<input name="重置"
						type="reset" class="btn_all btn_reset" value="重置" />
				</div>
				<font color="red">${ERROR_MSG }</font>
			</div>
			<div id="contentbottom"></div>
		</div>
	</form>
</body>
</html>