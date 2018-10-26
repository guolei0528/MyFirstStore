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
						ETC黑名单管理
					</div>
					<div class="col-sm-6">
					<br><br>
					</div>
					<div class="col-sm-12 ">
								<table class="queryTable">
							<tr>
								<td>作废时间</td>
								<td>
									<input type="text" id="query_cancel_time_from" name="query_cancel_time_from" value="${queryParam.query_cancel_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
							<input type="text" id="query_cancel_time_to" name="query_cancel_time_to" value="${queryParam.query_cancel_time_to}" onfocus="WdatePicker()">
								</td>
								<td>录入时间</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_from" value="${queryParam.query_in_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_to" value="${queryParam.query_in_time_to}" onfocus="WdatePicker()">
								</td>
								<td>黑名单类型</td>
								<td>
									<select name="query_b_list_type" id="query_b_list_type">
										<option></option>
										<option value="1">欠费</option>
										<option value="2">逃票</option>
									</select>
								</td>
								
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>
							<tr>
								<td>车牌号</td>
								<td>
									<input type="text" id="query_mv_license" name="query_mv_license" value="${queryParam.query_mv_license}" placeholder="" autocomplete="off">
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
<!-- 								<th>卡类别</th> -->
								<th>卡网络编号</th>
								<th>卡编号</th>
<!-- 								<th>卡状态</th> -->
<!-- 								<th>状态类别</th> -->
								<th>车牌号</th>
								<th>作废时间</th>
								<th>黑名单类型</th>
								<th>是否生效</th>
								<th>生效时间</th>
								<th>锁卡</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
<%-- 									<td>${obj.card_type}</td> --%>
									<td>${obj.card_network}</td>
									<td>${obj.card_id}</td>
<%-- 									<td>${obj.card_status}</td> --%>
<%-- 									<td>${obj.status_type}</td> --%>
									<td>${obj.mv_license}</td>
									<td>${obj.cancel_time}</td>
									<td>
										<c:choose>
											<c:when test="${obj.ban_type==1}">欠费</c:when>
											<c:when test="${obj.ban_type==2}">逃票</c:when>
										</c:choose>
									</td>
									<td>
										<c:choose>
											<c:when test="${obj.valid_flag==0}">有效</c:when>
											<c:when test="${obj.valid_flag==1}">无效</c:when>
										</c:choose>
									</td>
									<td>${obj.valid_time}</td>
									<td>
										<c:choose>
											<c:when test="${obj.lock_card==0}">无需锁卡</c:when>
											<c:when test="${obj.lock_card==1}">需锁卡</c:when>
										</c:choose>
									</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?card_id=${obj.card_id }&card_network=${obj.card_network}')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
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
	tool.selOption("query_b_list_type",'${queryParam.query_b_list_type}');
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
		
		function test(){
			var start = new Date();
			$.ajax({
				url:'testETC.shtml',
				data:{"card":'ETC_VOID_CARD:卡15017540320221896213|苏15017540320221896213'},
				dataType:'json',
				type:'post',
				success:function(data){
				
				}
				
			});
			
		}
	</script>
</body>
</html>