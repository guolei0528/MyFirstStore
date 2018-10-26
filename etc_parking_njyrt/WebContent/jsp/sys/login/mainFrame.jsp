<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <c:set var="contentPath" value="<%=request.getContextPath() %>" ></c:set>
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	<title>净水后台管理</title>
	<link href="css/ligerui-all.css" rel="stylesheet" type="text/css" />
	<link href="css/pageTop.css" rel="stylesheet" type="text/css" />
	<script src="${contentPath }/js/jquery/jquery-1.11.2.min.js" type="text/javascript"></script>  
	<script src="${contentPath }/js/ligerUI/ligerui.min.js" type="text/javascript"></script>
	<link href="${contentPath }/js/ztree/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	 <script language="javascript" src="${contentPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#layout1").ligerLayout({
				leftWidth : 200,
				bottomHeight:20
			});
			
			var navtab = null;
	        $(function ()
	        {
	        	var height = $(".l-layout-center").height();
	        	
	            $("#rightTab").ligerTab({ height: height });
	            navtab = $("#rightTab").ligerGetTabManager();
	        });
			
			var zTreeObj=null;
		    //页面初始化
		    var setting = null;
		    setting={
		            async: {
		                enable: true,
		                url:"loadAdminTree.shtml",
		                autoParam:["id=parentId","nodeType=parentType"]
		            },
		            callback: {
		                onClick: zTreeOnClick
		            }
		        };

		    function zTreeOnClick(event, treeId, treeNode) {
		    	if(treeNode.isParent==false){
		    		navtab.addTabItem({tabid:treeNode.id ,url: treeNode.urlAction,text:treeNode.name });
		    	}
		    };
		    
		    $(document).ready(function(){
		        zTreeObj=$.fn.zTree.init($("#treeDemo"), setting);
		    });
		    
		    $("#resetPassword").click(function(){
		    	navtab.addTabItem({tabid:'-1' ,url: '${contentPath }/pub/ModifyPwd/list.shtml',text:'修改密码' });
		    });
		});
	</script>
<style type="text/css">
body {
	padding: 5px;
	margin: 0;
	padding-bottom: 5px;
}

#layout1 {
	width: 100%;
	margin: 0;
	padding: 0;
}

.l-page-top {
	height: 80px;
	background: #f8f8f8;
	margin-bottom: 3px;
}

h4 {
	margin: 20px;
}
</style>
</head>
<body>
	<div class="l-frame-top">
		<div class="l-frame-top-logo"></div>
	    <div class="headright">
		    欢迎您，<span style="color: #CCFF00">${sessionScope.loginUser.NAME }</span> 
		    <span style="margin-left:5px">[ <a href="logout.shtml" class="linktext yellowtext" >退出 </a>]</span><br />
		    <a id="resetPassword" href="#" class="linktext righttopicon password_icon">修改密码</a>
	    </div>
	</div>
 	<div id="layout1">
       <div position="left">
       		<ul id="treeDemo" class="ztree"></ul>
       </div>
       <div position="center" id="rightTab"></div>
       <div position="bottom"><p align="center">Copyright&copy;<fmt:formatDate value="<%=new Date() %>" pattern="yyyy"/> </p></div>
   	</div>
	
</body>
</html>