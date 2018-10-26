<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
     <title></title>
     <input id="contentPath" value="<%=request.getContextPath() %>" type="hidden">
</head>
<body>
</body>
<script type="text/javascript" language="javascript">
    var contentPath=document.getElementById('contentPath').value;
	window.top.location=contentPath+'/login.shtml';
</script>
</html>