<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<footer class="main-footer">
	<span class="fr"><b>version</b> 2.0</span>
	<strong>Copyright © 2017 <a href="#">江苏东南智能系统科技有限公司</a></strong> All rights
</footer>
<script type="text/javascript" src="${contentPath }/js/app.js"></script>
<script type="text/javascript">
	
$(function(){
	var leftside=$(".leftside").css("height").replace("px","")-150;
	$("#mainContentDiv").css("height",leftside+"px");
});
	</script>