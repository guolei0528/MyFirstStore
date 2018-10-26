<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
     <title></title>
     <c:set var="contentPath" value="<%=request.getContextPath()%>"></c:set>
     <script language="javascript" src="${contentPath }/js/jquery/jquery-1.11.2.min.js"></script>
	<script src="${contentPath }/js/ligerUI/ligerui.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="${contentPath }/js/ligerUI/js/core/base.js"></script>
	<script type="text/javascript" src="${contentPath }/js/ligerUI/js/plugins/ligerDrag.js"></script>
	<script type="text/javascript" src="${contentPath }/js/ligerUI/js/plugins/ligerDialog.js"></script>
	<script type="text/javascript" src="${contentPath }/js/ligerUI/js/plugins/ligerResizable.js"></script>
</head>
<body>

</body>
<script type="text/javascript" language="javascript">
	parent.$.ligerDialog.close(); 
	parent.$(".l-dialog,.l-window-mask").css("display","none");
	if(parent.document.getElementById("pageForm")){
		parent.document.getElementById("pageForm").submit();
	}
</script>
</html>