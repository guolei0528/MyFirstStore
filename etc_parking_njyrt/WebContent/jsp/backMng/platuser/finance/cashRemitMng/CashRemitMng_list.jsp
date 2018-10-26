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
				<!-- 	<div class="col-sm-6">
					<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>解缴</button>
						</div> 
					</div>-->
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>收费员</td>
								<td>
									<input type="text" id="query_user_id" value="${queryParam.query_user_id}" name="query_user_id" placeholder="" autocomplete="off">
								</td>
								<td>
								下班时间
								</td>
								<td>
								<input type="text" name="income_time_from" value="${queryParam.income_time_from }" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
								<input type="text" name="income_time_to" value="${queryParam.income_time_to }" onfocus="WdatePicker()">
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
								<th>工班日期</th>
								<th>下班时间</th>
<!-- 								<th>date</th> -->
<!-- 								<th>user_id</th> -->
								<th>收费员</th>
								<th>停车场</th>
								<th>区域</th>
								<th>收费点</th>
								<th>收费终端</th>
<!-- 								<th>收费员班次</th> -->
<!-- 								<th>实际现金收入</th> -->
<!-- 								<th>缴付金额</th> -->
<!-- 								<th>发票数量</th> -->
								<th>解缴状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody align="center">
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${obj.date }</td>
									<td><fmt:formatDate value="${obj.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>${obj.user_name }</td>
									<td>${obj.park_name }</td>
									<td>${obj.area_name}</td>
									<td>
									<c:if test="${obj.remit_type == 1}">
											车道
										</c:if>
										<c:if test="${obj.remit_type == 2}">
											终端
										</c:if>
									</td>
									<td>${obj.lane_name}</td>
<%-- 									<td>${obj.exit_shift}</td> --%>
<%-- 									<td>${obj.real_bill}</td> --%>
<%-- 									<td>${obj.pay_bill}</td> --%>
<%-- 									<td>${obj.invoice_count}</td> --%>
									<td>
										<c:if test="${obj.check_status == 0}">
											未通过审核
										</c:if>
										<c:if test="${obj.check_status != 0 && obj.remit_status == 0 }">
											待解缴
										</c:if>
										<c:if test="${obj.check_status != 0 && obj.remit_status == 1 } ">
											已解缴
										</c:if>
									</td>
									<td>
										<ul class="list-inline">
										<c:if test="${obj.check_status == 0}">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?create_time=${obj.create_time.time }&user_id=${obj.user_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
											<li title="重新生成"><a href="javascript:tool.dialog('view.shtml?ID=${obj.user_id }&date=${obj.date}')"><i class="fa fa-refresh text-blue"></i><span class="text-blue"></span>重新生成</a></li>
										</c:if>
										<c:if test="${obj.check_status != 0 && obj.remit_status == 0 }">
											<li title="待解缴"><a href="javascript:tool.dialog('view.shtml?ID=${obj.user_id }&date=${obj.date}')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>待解缴</a></li>
										</c:if>
										<c:if test="${obj.check_status != 0 && obj.remit_status == 1 } ">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?ID=${obj.user_id }&date=${obj.date}')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
										</c:if>
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