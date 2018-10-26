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
		<form action="excessiveCar.shtml" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						超大金额车辆检索
					</div>
					<div class="col-sm-6">
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>车牌号</td>
								<td>
									<input type="text" id="query_mvlicense" name="query_mvlicense" value="${queryParam.query_mvlicense}" placeholder="" autocomplete="off">
								</td>
								<td>车道号</td>
								<td>
								<input type="text" id="query_lane_id" name="query_lane_id" value="${queryParam.query_lane_id }">
								</td>
								<td>进入停车场开始日期</td>
								<td>
									<input type="text" id="query_entry_time_from" name="query_entry_time_from" value="${queryParam.query_entry_time_from}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width:85%">
								</td>
								<td>进入停车场结束日期</td>
								<td>
									<input type="text" id="query_entry_time_to" name="query_entry_time_to" value="${queryParam.query_entry_time_to}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="width:85%">
								</td>
								
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>
								<tr>
								<td>超额类型</td>
								<td>
									<select id="query_type">
										<option></option>
										<option value="0">超时</option>
										<option value="1" selected="selected">超额</option>
									</select>
								</td>
								<td>金额（元）</td>
								<td><input name="query_money" type="text" value="${queryParam.query_money }"></td>
							</tr>
						</table>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
<!-- 								<th>记录编号</th> -->
<!-- 								<th>停车场编号</th> -->
<!-- 								<th>片区编号</th> -->
								<th>停车场名称</th>
								<th>片区名称</th>
								<th>入口站编号</th>
								<th>入口车道编号</th>
								<th>入口时间</th>
								<th>预计金额</th>
								<th>车牌号</th>
								<th>道口图片</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
<%-- 									<td>${obj.recordno}</td> --%>
<%-- 									<td>${obj.cardnetwork}</td> --%>
<%-- 									<td>${obj.cardid}</td> --%>
<%-- 									<td>${obj.park_id}</td> --%>
<%-- 									<td>${obj.area_id}</td> --%>
									<td>${obj.park_name}</td>
									<td>${obj.area_name}</td>
									<td>${obj.entrystation}</td>
									<td>${obj.entry_lane}</td>
									<td>${obj.entry_time}</td>
<%-- 									<td>${obj.entryoperator}</td> --%>
<%-- 									<td>${obj.entryshift}</td> --%>
									<td>${obj.income_bill }</td>
									<td>${obj.mv_license}</td>
									<td><button class="btn btn-primary" data-toggle="modal" type="button" data-target=".bs-example-modal-lg" onclick="explore(this)" dktp="/img/${fn:substringBefore(obj.imagepath,'-')}/${fn:substring(fn:substringAfter(obj.imagepath,'-'), 0, 8)}/${obj.imagepath}">查看道口图片</button></td>
									<td><a href="javascript:deleteData('${obj.mv_license}','${obj.car_color}')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>异常</a></td>
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

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
  <div class="modal-dialog modal-lg" role="document">
    <div class="modal-content" id="dktp">
      <img width="100%">
    </div>
  </div>
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
	
	
	function explore(obj){
		$("#dktp").find("img").attr("src",$(obj).attr("dktp"));
	}
		function deleteData(mv_license,car_color){
			tool.confirm("确实要删除该异常条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'delete.shtml',
						data:{"mv_license":mv_license,
						      "car_color":car_color},
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
		$(function(){
			$("#query_type").change(function(){
				if($("#query_type").val()==0){
					$("#pageForm").attr("action","timeoutCar.shtml");
					tool.formSubmit();
				}
			});
			
		});
	</script>
</body>
</html>