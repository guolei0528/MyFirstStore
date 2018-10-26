<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	 <c:set var="contentPath" value="<%=request.getContextPath() %>" ></c:set>
	 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
	 <link href="${contentPath }/js/ztree/zTreeStyle.css" rel="stylesheet" type="text/css"/>
	 <script language="javascript" src="${contentPath }/js/jquery/jquery-1.4.4.min.js"></script>
	 <script language="javascript" src="${contentPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
</head>
<body>
	<div style="border: 1px solid #617775;width:100%;height:450px;overflow-y:scroll;overflow-x:auto;">
        <ul id="treeDemo" class="ztree"></ul>
    </div>
</body>
<script type="text/javascript" language="javascript">
$(function(){
    
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
    		parent.right.location=treeNode.urlAction;
    	}
    };
    
    $(document).ready(function(){
        zTreeObj=$.fn.zTree.init($("#treeDemo"), setting);
    });
});

</script>
</html>