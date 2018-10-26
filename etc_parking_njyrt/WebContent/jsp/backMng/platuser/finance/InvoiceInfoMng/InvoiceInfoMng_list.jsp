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
		<form action="list.shtml?query_flag=${queryParam.query_flag }" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						发票查询
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
					<c:choose>
							<c:when test="${queryParam.query_flag==0 }">
							<button type="button" class="btn btn-default" onclick="tool.dialog('addIn.shtml')"><span class="glyphicon glyphicon-save"></span>发票领入</button>
							</c:when>
						<c:when test="${queryParam.query_flag==1 }">
						<button type="button" class="btn btn-default" onclick="tool.dialog('addOut.shtml')"><span class="glyphicon glyphicon-log-out"></span>发票领出</button>
						</c:when>
					</c:choose>
							
							</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>发票号码</td>
								<td>
									<input type="text" id="query_code" name="query_code" value="${queryParam.query_code}" placeholder="" autocomplete="off">
								</td>
								
								<td>
									<button class="btn btn-primary" type="submit">确定</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
<!-- 								<th>sn</th> -->
								<th>起始发票号码</th>
								<th>最后一张发票号码</th>
								<th>发票数量</th>
								<th>
								<c:choose>
									<c:when test="${queryParam.query_flag==0 }">财务编号</c:when>
									<c:when test="${queryParam.query_flag==1 }">收费员编号</c:when>
								</c:choose>
								</th>
								<th><c:choose>
									<c:when test="${queryParam.query_flag==0 }">领入时间</c:when>
									<c:when test="${queryParam.query_flag==1 }">领出时间</c:when>
								</c:choose></th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
<%-- 									<td>${obj.sn}</td> --%>
									<td>${obj.code}${obj.begin_number}</td>
									<td>${obj.code}${obj.end_number}</td>
									<td>${obj.count}</td>
									<td>${obj.user_id}</td>
									<td>${obj.begin_time}</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?ID=${obj.sn }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
<%-- 											<li title="修改"><a href="javascript:tool.dialog('edit.shtml?ID=${obj.sn }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li> --%>
<%-- 											<li title="删除"><a href="javascript:deleteData('${obj.sn }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li> --%>
										</ul>
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<!-- 
						<tfoot>
							<tr>
								<th></th>
							</tr>
						</tfoot>
						 -->
					</table>
				</div>
				<!-- /.box-body -->
				<%@include file="/jsp/common/bootstrap/divPage.jsp"%>
			</div>
			<!-- /.box -->
			
			
		</div>
		</form>
		<!-- /.content -->
	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	
	<script type="text/javascript">
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