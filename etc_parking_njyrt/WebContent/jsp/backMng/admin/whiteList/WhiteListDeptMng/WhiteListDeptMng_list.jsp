<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=PropertiesUtil.get("PROJECT_NAME")%></title>
<%@include file="/jsp/common/bootstrap/commonJsCss.jsp"%>
<link href="${contentPath }/js/ztree/zTreeStyle.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript"
	src="${contentPath }/js/ztree/jquery.ztree.all-3.5.min.js"></script>

<style type="text/css">
ul.ztree {
	height: 630px;
	margin-top: 0px;
}
</style>


</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>
	<div class="content-wrapper">

		<form action="list.shtml" method="post" id="pageForm">
			<section class="content">
				<div class="row">
					<div class="col-xs-2 ">
						<div class="box">
							<ul id="treeDemo" class="ztree"></ul>
						</div>
					</div>
					<div class="col-xs-10">
						<div class="box" id="box">
							<div class="box-header">
								<div class="col-sm-6">
									<div class="dataTables_info" role="status" aria-live="polite"
										id="title">白名单部门管理</div>
								</div>
								<div class="col-sm-6">
									<div class="dataTables_paginate paging_simple_numbers">
										<button id="add" type="button" class="btn btn-default"
											onclick="tool.dialog('add.shtml')">
											<i class="fa fa-plus text-green" aria-hidden="true"></i> 新增
										</button>
									</div>
								</div>
								
								<div class="col-sm-12 ">
									<input type="hidden" name="PARENT_ID" autocomplete="off"
										placeholder="" id="PARENT_ID">
									<!-- 								<button class="btn btn-primary btn-large" type="submit">查询</button> -->
								</div>
							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<table id="dataTable" class="table table-bordered table-striped">
									<tr>
										<th>白名单部门</th>
										<th>上级部门</th>
										<th>创建时间</th>
										<th>操作</th>
									</tr>
									<c:forEach items="${list }" var="obj">
										<tr>
											<td>${obj.park_id }</td>
											<td>${obj.park_name }</td>
											<td>${obj.park_address }</td>
											<td>
												<ul class="list-inline">
													<li title="查看"><a
														href="javascript:tool.dialog('view.shtml?ID=${obj.park_id }')"><i
															class="fa fa-file-text-o text-blue"></i><span
															class="text-blue"></span>查看</a></li>
													<li title="修改"><a
														href="javascript:tool.dialog('edit.shtml?ID=${obj.park_id }')"><i
															class="fa fa-pencil-square-o text-green"></i><span
															class="text-green"></span>修改</a></li>
													<%-- 						<li title="删除"><a href="javascript:deleteData('${obj.park_id }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li> --%>
												</ul>
											</td>
										</tr>
									</c:forEach>
									
<!-- 									<tbody align="center"> -->
									<tr align="center"> 
											<td>江苏省人民医院</td>
											<td>无</td>
											<td>2017-12-01</td>
											<td>
											<ul class="list-inline">
													<li title="修改"><a
														href="javascript:tool.dialog('edit.shtml?ID=${obj.park_id }')"><i
															class="fa fa-pencil-square-o text-green"></i><span
															class="text-green"></span>修改</a></li>
															</ul>
											
											
											</td>
									</tr>
<!-- 									</tbody> -->
									
									
									
								</table>
							</div>

						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->

			</section>

		</form>

		<!-- /.content -->

	</div>

	<%@include file="/jsp/common/bootstrap/footer.jsp"%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<input id="treeNodeID" type="hidden">
	<script type="text/javascript">
		var zTreeObj = null;
		$(function() {
			//页面初始化
			var setting = null;
	
			setting = {
				async : {
					enable : true,
					url : "loadTree.shtml",
					autoParam : [ "id=PARENT_ID", "level=LEVEL" ]
				},
				callback : {
					onClick : zTreeOnClick,
					onAsyncSuccess : zTreeOnAsyncSuccess
				}
			};
	
			$(document).ready(function() {
				zTreeObj = $.fn.zTree.init($("#treeDemo"), setting);
	
			});
	
		});
		function loadData(PARENT_ID, nodeType) {
			/* if (nodeType == 0) {
				$("#box").load("parkList.shtml");
			} else if (nodeType == 1) {
				$("#box").load("${contentPath}/backMng/platuser/sys/AreaInfoMng/list.shtml?park_id=" + PARENT_ID);
			} else if (nodeType == 2) {
				$("#box").load("${contentPath}/backMng/platuser/sys/LaneInfoMng/list.shtml?area_id=" + PARENT_ID);
			} else {
	
			} */
	
		}
		function zTreeOnClick(event, treeId, treeNode) {
			loadData(treeNode.id, treeNode.nodeType);
		}
		;
	
	
		function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
			if (treeNode) {
				var nodes = treeNode.children;
				if (nodes) {
	
					for (i = 0; i < nodes.length; i++) {
						zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
					}
					if ('${area_id}') {
						$("#pageForm").attr("action", 'list.shtml');
						var zTree = $.fn.zTree.getZTreeObj("treeDemo"); //获取ztree对象  
						var node = zTree.getNodeByParam('id', '${area_id}');
						if (node != null && node.nodeType == 2) {
							zTree.selectNode(node);
							zTree.setting.callback.onClick(null, zTree.setting.treeId, node);
						}
	
					} else if ('${park_id}') {
						$("#pageForm").attr("action", 'list.shtml');
						var zTree = $.fn.zTree.getZTreeObj("treeDemo"); //获取ztree对象  
						var node = zTree.getNodeByParam('id', '${park_id}');
						if (node != null && node.nodeType == 1) {
							zTree.selectNode(node);
							zTree.setting.callback.onClick(null, zTree.setting.treeId, node);
						}
	
					}
	
				}
			} else {
				var nodes = zTreeObj.getNodes();
				for (i = 0; i < nodes.length; i++) {
					zTreeObj.reAsyncChildNodes(nodes[i], "refresh","false");
				}
				if (!'${park_id}') {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo"); //获取ztree对象  
					var node = zTree.getNodeByParam('id', 0);
					zTree.selectNode(node);
					zTree.setting.callback.onClick(null, zTree.setting.treeId, node);
				} else {
					var zTree = $.fn.zTree.getZTreeObj("treeDemo"); //获取ztree对象  
					var node = zTree.getNodeByParam('id', '${park_id}');
					zTree.selectNode(node);
					zTree.setting.callback.onClick(null, zTree.setting.treeId, node);
				}
	
			}
	
		// 	var nodes=zTreeObj.getNodes();
		// 	if(nodes){
		// 	zTreeObj.expandNode(nodes[0], true, false, true); 
		// 	}
		}
	</script>
</body>
</html>