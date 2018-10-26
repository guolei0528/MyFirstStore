<!DOCTYPE html >
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=PropertiesUtil.get("PROJECT_NAME")%></title>
<%@include file="/jsp/common/bootstrap/commonJsCss.jsp"%>
</head>
<body>
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>
	<div id="mainContentDiv" style="margin-left: 230px">
		
	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	<script type="text/javascript">
$(function(){
	$('.menu a').click(function(){
		$('.menu a').removeClass('active');
		$(this).addClass('active');
	});
	var loc=document.location.href;

	$(".menu a").each(function(){
		  var LI_URL=$(this).attr("href");
		  if(tool.isEmpty(LI_URL)==false&&loc.indexOf(LI_URL)>0){
	  		$(this).addClass("active");
	  	  }
	})
	
});
</script>
</body>
</html>