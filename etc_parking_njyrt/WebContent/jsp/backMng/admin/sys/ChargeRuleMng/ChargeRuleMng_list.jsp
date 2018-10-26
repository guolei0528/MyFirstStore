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
		<form action="list.shtml" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						计费规则管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>计费编号</td>
								<td>
									<input type="text" id="query_charge_id" name="query_charge_id" value="${queryParam.query_charge_id}" placeholder="" autocomplete="off">
								</td>
								<td>起效时间</td>
								<td>
									<input type="text" id="query_begin_time" name="query_begin_time" value="${queryParam.query_begin_time}" onfocus="WdatePicker()">
								</td>
								<td>失效时间</td>
								<td>
									<input type="text" id="query_end_time" name="query_end_time" value="${queryParam.query_end_time}" onfocus="WdatePicker()">
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
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
								<th>计费编号</th>
								<th>计费类型</th>
								<th>生效时间</th>
								<th>失效时间</th>
								<th>收费金额（周期/元）</th>
								<th>停车时长（周期/分）</th>
								<th>免费时长（分钟）</th>
								<th>每天生效时间段</th>
								<th>金额上限（元）</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${obj.charge_id}</td>
									<td><c:choose>
										<c:when test="${obj.charge_type==0}">按时间计费</c:when>
										<c:when test="${obj.charge_type==1}">按次计费</c:when>
										<c:when test="${obj.charge_type==2}">重大节假日/活动</c:when>
									</c:choose></td>
									<td>${obj.valid_begin_time}</td>
									<td>${obj.valid_end_time}</td>
									<td>${obj.money}</td>
									<td>${obj.period}</td>
									<td>${obj.free_time }</td>
									<td style="text-align: center;">${obj.begin_time } — ${obj.end_time }</td>
									<td>${obj.money_limit}</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?ID=${obj.charge_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
											<li title="修改"><a href="javascript:tool.dialog('edit.shtml?ID=${obj.charge_id }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
											<li title="删除"><a href="javascript:deleteData('${obj.charge_id }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li>
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
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="overflow: auto;">
		
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