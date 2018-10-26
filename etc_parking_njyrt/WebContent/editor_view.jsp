<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
<head>
    <title>内容查看</title>
    <c:set var="contentPath" value="<%=request.getContextPath()%>"></c:set>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <script type="text/javascript" src="${contentPath }/js/jquery/jquery-1.11.2.min.js"></script>

    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<div id="dataDiv"></div>

<script type="text/javascript">
   	$.ajax({
		type:'post',
		url:'${contentPath}/backMng/richtext/RichTextMng/fetchData.shtml',
		data:{"ID":'${param.ID}'},
		dataType:'json',
		success:function(msg){
			document.getElementById('dataDiv').innerHTML=msg.CONTENT;
		},
		error:function(XMLHttpRequest, textStatus, errorThrown) {
			 
		}
	});
</script>
</body>
</html>