<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<header class="header">
	<nav class="navbar main-navbar">
		<a class="header-logo"> 
			<!-- 
				<img src="${contentPath }/img/hdImg_c43a8e9b4ed57ad4626c7fab7fa9ec7014834317215.jpg">
			 -->
			<span><%=PropertiesUtil.get("PROJECT_NAME") %> </span>
		</a>
		<div class="navbar-menu">
			<ul class="nav navbar-nav">
				<li class="">
				<a>
<!-- 				<a href="#"> -->
					<fmt:formatDate var="HEADER_CURR_DATE" value="<%=new Date() %>" pattern="yyyy-MM-dd"/>
					 	今天是${HEADER_CURR_DATE }
					 	<c:set var="HEADER_WEEK_DAY" value="<%=Calendar.getInstance().get(Calendar.DAY_OF_WEEK) %>"></c:set>
					 	<c:choose>
					 		<c:when test="${HEADER_WEEK_DAY==1 }">星期天</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==2 }">星期一</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==3 }">星期二</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==4 }">星期三</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==5 }">星期四</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==6 }">星期五</c:when>
					 		<c:when test="${HEADER_WEEK_DAY==7 }">星期六</c:when>
					 	</c:choose>
				</a></li>
				<li class=""><a><img src="${contentPath }/images/common/head.jpg">${sessionScope.loginUser.name }</a></li>
<%-- 				<li class=""><a href="#" onclick="javascript:tool.dialog('${contentPath }/backMng/userMng/modifyPwd/toModifyPwd.shtml')"><img src="${contentPath }/images/common/head.jpg">${sessionScope.loginUser.name }</a></li> --%>
				<li class=""><a href="${contentPath }/logout.shtml">退出</a></li>
			</ul>
		</div>
	</nav>
</header>