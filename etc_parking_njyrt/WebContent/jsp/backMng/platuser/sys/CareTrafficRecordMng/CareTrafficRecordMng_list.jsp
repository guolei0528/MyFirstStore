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
						流量统计
					</div>
					<div class="col-sm-6">
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								
								<td>区域编号</td>
								<td>
								<input id="query_area_id" name="query_area_id" value="${queryParam.query_area_id }">
								</td>
								<td>车道编号</td>
								<td>
									<input id="query_lane_id" name="query_lane_id" value="${queryParam.query_lane_id }">
								</td>
								<td>统计日期</td>
								<td>
									<input id="query_date" name="query_date" value="${queryParam.query_date }" onfocus="WdatePicker()">
								</td>
								<td>用户类型</td>
								<td>
								
									<select name="query_member_type" id="query_member_type" class="form-control">
									<option></option>
										<option value="00">非会员用户（普通用户）</option>
										<option value="01">VIP用户（不收费）</option>
										<option value="02">内部车辆</option>
										<option value="03">公务车辆</option>
										<option value="04">月票用户</option>
										<option value="05">年票用户</option>
										<option value="06">次票用户</option>
										<option value="07">无牌车辆</option>
										<option value="08">累计时长用户（总停车400分钟免费）</option>
										<option value="09">某时间段内免费[6:00---24:00]</option>
										<option value="10">规定时间内某个车道免费</option>
										<option value="111">用户类型不确定</option>
									</select>
								</td>
								
								
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							
							</tr>
							<tr>
								
								<td>支付方式</td>
								<td>
									<select name="query_pay_method" id="query_pay_method">
										<option></option>
									</select>
								</td>
								<td>车道类型</td>
								<td>
									<select id="query_lane_flag" name="query_lane_flag">
										<option></option>
										<option value="1">入口</option>
										<option value="2">出口</option>
									</select>
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
								<th>统计流量日期</th>
								<th>停车场编号</th>
								<th>片区编号</th>
								<th>车道编号</th>
								<th>车道类型</th>
								<th>车流数量</th>
								<th>支付类型</th>
								<th>用户类型</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${obj.datetime}</td>
<%-- 									<td>${obj.traffic_date}</td> --%>
									<td>${obj.park_id}</td>
									<td>${obj.area_id}</td>
									<td>${obj.lane_id}</td>
									<td>
									<c:choose>
										<c:when test="${obj.lane_flag==1}">入口</c:when>
										<c:when test="${obj.lane_flag==2}">出口</c:when>
									</c:choose>
									</td>
									<td>${obj.care_traffic}</td>
									<td>
										<c:if test="${obj.lane_flag==2}">
											${obj.pay_method}
										</c:if>
									</td>
									<td>
										<c:choose>
											<c:when test="${obj.member_type eq '00'}">非会员用户（普通用户）</c:when>
										<c:when test="${obj.member_type eq '01'}">VIP用户不收费</c:when>
										<c:when test="${obj.member_type eq '02'}">内部车辆</c:when>
										<c:when test="${obj.member_type eq '03'}">公务车辆</c:when>
										<c:when test="${obj.member_type eq '04'}">月票用户</c:when>
										<c:when test="${obj.member_type eq '05'}">年票用户</c:when>
										<c:when test="${obj.member_type eq '06'}">次票用户</c:when>
										<c:when test="${obj.member_type eq '07'}">无牌车辆</c:when>
										<c:when test="${obj.member_type eq '08'}">累计时长用户（总停车400分钟免费）</c:when>
										<c:when test="${obj.member_type eq '09'}">某时间段内免费[6:00---24:00]</c:when>
										<c:when test="${obj.member_type eq '10'}">规定时间内某个车道免费</c:when>
										<c:when test="${obj.member_type eq '111'}">用户类型不确定</c:when>
										</c:choose>
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
	tool.selOption("query_lane_flag",'${queryParam.query_lane_flag }');
	tool.selOption("query_pay_method",'${queryParam.query_pay_method }');
	tool.selOption("query_member_type",'${queryParam.query_member_type }');
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