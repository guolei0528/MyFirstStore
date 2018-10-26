<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><%=PropertiesUtil.get("PROJECT_NAME") %></title>
	<%@include file="/jsp/common/bootstrap/commonJsCss.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>
	
	<div id="mainContentDiv" style="margin-left: 180px">

		<!-- Main content -->
		<form action="emptyPositionList.shtml" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						空闲车位检索管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td nowrap="nowrap">片区编号</td>
								<td>
									<input type="text" id="QUERY_AREA_ID" name="QUERY_AREA_ID" value="${queryParam.QUERY_AREA_ID}" placeholder="" autocomplete="off">
								</td>
								<td nowrap="nowrap">片区名称</td>
								<td>
									<input type="text" id="QUERY_AREA_NAME" name="QUERY_AREA_NAME" value="${queryParam.QUERY_AREA_NAME}" placeholder="" autocomplete="off">
								</td>
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>
						</table>
					
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped">
		<tr><th>片区编号</th><th>片区名称</th><th>空闲车位数量</th><th>服务器IP</th><th>操作</th></tr>
		<c:forEach items="${list }" var="obj">
			<tr>
			<td>${obj.area_id }</td>
			<td>${obj.area_name }</td>
			<td>${obj.space_number }</td>
			<td>${obj.server_ip }</td>
			<td><ul class="list-inline">
						<li title="查看"><a href="javascript:tool.dialog('areaView.shtml?ID=${obj.area_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
				</ul>
			</td>
			</tr>
		</c:forEach>					
	</table>
	<input type="hidden" id="PARK_ID" value="${queryParam.park_id }">
				</div>
				</div>
				<%@include file="/jsp/common/bootstrap/divPage.jsp"%>
				</div></form>
				</div>
				
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	
	<script type="text/javascript">
	
	
	function explore(obj){
		
		$("#dktp").find("img").attr("src",$(obj).attr("dktp"));
	}
		function deleteData(dataId){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'delete.shtml',
						data:{"ID":dataId},
						dataType:'json',
						success:function(msg){
							if(msg.success){
								tool.formSubmit();
							}else{
								tool.alert(msg.message);
							}
						},
						error:function(msg){
							tool.alert("删除失败！");
						}
					});
				}
			});			
		}
	</script>
</body>
</html>