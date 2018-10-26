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
<script type="text/javascript"
	src="${contentPath }/js/common/ajaxfileupload.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>

	<div id="mainContentDiv" style="margin-left: 180px">

		<!-- Main content -->
		<form action="mobileWebReport.shtml" method="post" id="pageForm"
			enctype="multipart/form-data">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">手机移动支付缴费</div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
<!-- 									<td>日期</td> 
									<td><input type="text" id="query_start_date"
										name="query_start_date" value="${queryParam.query_start_date}"
										onfocus="WdatePicker()"></td>
									<td>-</td>
									<td><input type="text" id="query_end_date"
										name="query_end_date" onfocus="WdatePicker()"
										value="${queryParam.query_end_date}"></td>
									<td>-->
 									<td>车牌号</td> 
									<td>
									<input type="text" id="query_mvlicense" name=query_mvlicense value="${queryParam.query_mvlicense}" placeholder="" autocomplete="off">
									</td>
<!-- 									<td>入口车道</td> 
									<td><select id="query_terminalid" name="query_terminalid"
										style="WIDTH: 100%">
											<option value="">所有</option>
<%-- 											<c:forEach items="${QUERY_LANE_LIST }" var="obj">
												<option value="${obj.lane_id }">${obj.lane_name }</option>
											</c:forEach> --%>
											<option value="1">1</option>
											<option value="2">2</option>
											<option value="3">3</option>
									</select></td>-->
									<td>缴费时间</td>
									<td><input type="text" id="query_deal_time"
										name="query_deal_start_time" value="${queryParam.query_deal_start_time}"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" style="WIDTH: 90%"></td>
									<td>-</td>
									<td><input type="text" id="query_deal_time"
										name="query_deal_end_time" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })"
										value="${queryParam.query_deal_end_time}" style="WIDTH: 90%"></td>
									<td>
									<td>
									<td>付费类型</td>
									<td><select id="query_pay_method" name="query_pay_method"
										style="WIDTH: 100%" onChange="getDisFree(this.value)">
											<option value="">所有</option>
<!-- 											<option value="0">现金</option> -->
<!-- 											<option value="2">ETC</option> -->
											<option value="3">微信</option>
											<option value="4">支付宝</option>
<!-- 											<option value="9">人工放行</option> -->
									</select></td>
									<td>
<!-- 									<td><select id="dis_free" name="dis_free"
										style="WIDTH: 100%">
											<option value="1">所有停车明细</option>
											<option value="2">车辆非免费停车明细</option>
											<option value="3">车辆免费停车明细</option>
									</select></td> -->
									<td>订单编号</td> 
									<td>
									<input type="text" id="query_order_id" name=query_order_id value="${queryParam.query_order_id}" placeholder="" autocomplete="off">
									</td>
									<td>
										<button class="btn btn-primary" type="submit">检索</button>
									</td>
								</tr>
								<tr>
<!-- 									<td>入口时间</td> 
									<td><input type="text" id="query_entry_date"
										name="query_entry_start_date" value="${queryParam.query_entry_start_date}"
										onfocus="WdatePicker()"></td>
									<td>-</td>
									<td><input type="text" id="query_end_date"
										name="query_entry_end_date" onfocus="WdatePicker()"
										value="${queryParam.query_entry_end_date}"></td>
									<td>-->
									
								</tr>
							</table>

						</div>
					</div>
					<input type="file" id="file" name="file" style="display: none;">
					<!-- /.box-header -->
					<div class="box-body">
						<!-- 						<label>金额单位:(元)</label> -->
						<label style="font-weight:bolder">车辆:<font size="5"
							color="#00AA00">${page.recordCount }</font>辆
						</label>
						&nbsp;&nbsp;&nbsp;&nbsp; <label style="font-weight:bolder">缴费金额:<font
							size="5" color="#00AA00"><fmt:formatNumber type="number"
									value="${totalSum/100 }" pattern="0.00" maxFractionDigits="2" /></font>元
						</label>
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
<!-- 									<th>日期</th> -->
									<th>停车场</th>
									<th>区域</th>
									<th>入口车道</th>
									<!-- 									<th>卡号</th> -->
									<th>车牌</th>
									<th>入口时间</th>
									<th>缴费时间</th>
									<th>停车时长</th>
									<th>车辆类型</th>
									<th>支付方式</th>
									<th>订单号</th>
									<th>缴费金额</th>
									<!-- 									<th>总计</th> -->
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
<%-- 										<td>${obj.exitdate}</td> --%> 
										<%-- 										<td>${obj.park_id}</td> --%>
										<%-- 										<td>${obj.area_id}</td> --%>
										<td>${obj.park_name}</td>
										<td>${obj.area_name}</td>
										<td>${obj.entry_lane}</td>
										<%-- 										<td>${obj.cardid}</td> --%>
										<td>${obj.mv_license}</td>
										<td>
											<%-- 										${obj.entrytime} --%> <fmt:formatDate
												pattern="yyyy-MM-dd  HH:mm:ss" value="${obj.entry_time}" />
										</td>
										<td>
											<%-- 										${obj.terminaltime} --%> <fmt:formatDate
												pattern="yyyy-MM-dd  HH:mm:ss" value="${obj.deal_time}" />
										</td>
										<td>
											<%-- 										<fmt:formatDate var="nowStr" value="${obj.terminaltime}" pattern="yyyy/MM/dd HH:mm:ss"/> --%>
											<%-- 											<c:set var="interval" --%> <%-- 												value="${obj.terminaltime.time - obj.entrytime.time}" /> <s:property --%>
											<!-- 												value="%{getText('{0,date,yyyy-MM-dd HH:mm:ss}',{Time})}" /> -->
											<%-- 																						<fmt:parseDate value="${interval}" pattern="yyyy-MM-dd HH:mm:ss" var="createTime" /> --%>
											<%-- 																					    <fmt:formatDate var="nowStr" value="${interval}" pattern="yyyy/MM/dd HH:mm:ss"/> --%>
											<c:set var="interval"
												value="${obj.deal_time.time - obj.entry_time.time}" /> <c:if
												test="${(interval/1000/60/60/24) >= 1}">
												<fmt:formatNumber value="${(interval/1000/60/60-interval/1000/60/60%24)/24}"
													pattern="#0" />天
												</c:if> <c:if test="${(interval/1000/60/60%24) >= 1}">
												<fmt:formatNumber value="${interval/1000/60/60%24 -0.5}"
													pattern="#0" />小时
												</c:if> 
												<c:if test="${(interval/1000/60%60) >= 1}">
												<fmt:formatNumber value="${interval/1000/60%60 -0.5}" pattern="#0" />分 
												</c:if> 
												<c:if test="${(interval/1000%60) >= 1}">
												<fmt:formatNumber
												value="${interval/1000%60 }" pattern="#0" />秒
												</c:if>
										</td>
										<%-- 										<td><c:set var="interval" value="<fmt:formatDate var="nowStr" value="${obj.terminaltime}" pattern="yyyy/MM/dd HH:mm:ss"/>"/></td> --%>
										<%-- 										<td>${obj.vehicleclass}</td> --%>
										<%-- 										<td>${obj.paymethod}</td> --%>
										
										<td>
										<c:choose>
												<c:when test="${obj.white_count > 0}">  
													白名单
												</c:when>
												<c:otherwise>
													普通车辆
   												</c:otherwise>  
										</c:choose>
										</td>
										<td><c:choose>
												<c:when test="${obj.pay_method == 2}">  
													ETC支付
												</c:when>
												<c:when test="${obj.pay_method == 3}">  
													微信支付
												</c:when>
												<c:when test="${obj.pay_method == 4}">  
													支付宝支付
												</c:when>
												<c:otherwise> 
													其他支付方式
												</c:otherwise>
											</c:choose></td>
										<td>${obj.order_id}</td>
										<td>
										<fmt:formatNumber type="number"
														value="${obj.pay_bill/100}" pattern="0.00"
														maxFractionDigits="2" />
										 <!--<c:choose>
												<c:when test="${obj.paymethod == 9}">
													<fmt:formatNumber type="number"
														value="${obj.pdiscounttoll/100}" pattern="0.00"
														maxFractionDigits="2" />
												</c:when>
												<c:otherwise>
													<fmt:formatNumber type="number"
														value="${obj.totaltoll/100}" pattern="0.00"
														maxFractionDigits="2" />
												</c:otherwise>
											</c:choose> -->
											</td>
										<%-- 										<td>${obj.cartMobile}</td> --%>
										<%-- 										<td>${obj.cartCash}</td> --%>
										<%-- 										<td>${obj.cartMember}</td> --%>
										<%-- 										<td>${obj.carStoreValue+obj.carAccount --%>
										<%-- 										+obj.carMobile+obj.carCash+obj.cartStoreValue --%>
										<%-- 										+obj.cartAccount+obj.cartMobile+obj.cartCash}</td> --%>
									</tr>
								</c:forEach>
								<!-- 								<tr> 
									<td>总计</td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<%-- 									<td><c:set var="totalCarMember" value="0" /> <c:forEach 
											items="${list }" var="obj">
											<c:set var="totalCarCash"
												value="${totalCarMember + obj.carMember }" />
										</c:forEach> ${totalCarMember}</td>--%>
									<td><c:set var="totalSum" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="totalSum" value="${totalSum + obj.totaltoll }" />
										</c:forEach> <fmt:formatNumber type="number" value="${totalSum/100}"
											pattern="0.00" maxFractionDigits="2" /></td>
								</tr>-->
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

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
		$(function() {
			var query_dis_free = '${queryParam.dis_free}';
			if ('${queryParam.query_pay_method}' != null && 
			'${queryParam.query_pay_method}' == '9') {
			    $("#dis_free").empty();
			    $("#dis_free").append("<option value='1'>所有停车明细</option>");
			    $("#dis_free").append("<option value='4'>人工放行收费成功</option>");
			    $("#dis_free").append("<option value='5'>人工放行收费失败</option>");
			} else {
	 		    $("#dis_free").empty();
				$("#dis_free").append("<option value='1'>所有停车明细</option>");
			    $("#dis_free").append("<option value='2'>车辆非免费停车明细</option>");
			    $("#dis_free").append("<option value='3'>车辆免费停车明细</option>");
			}
			if (query_dis_free != null && query_dis_free.trim() != '') {
// 			alert(${queryParam.dis_free});
			tool.selOption("dis_free", '${queryParam.dis_free}');
			}
			tool.selOption("query_pay_method", '${queryParam.query_pay_method}');
			tool.selOption("query_terminalid", '${queryParam.query_terminalid}');
// 			var query_dis_free = '${queryParam.dis_free}';
			// 			tool.selOption("query_b_list_type", '${queryParam.query_b_list_type}');
	
			$("#file").change(function() {
				$
					.ajaxFileUpload({
						url : 'upload.shtml',
						secureuri : false,
						fileElementId : 'file',
						dataType : 'json',
						success : function(data, status) {
							if (data.success) {
								$("#file").attr("value", '');
								location.reload();
							} else {
								tool.alert("黑名单类型可能存在非法参数，请核实");
							}
						}
					});
			}
			);
	
		});
	
		function getDisFree(value) {
			// 		var payMethod = document.getElementById("query_pay_method"); 
			// 				alert(value);
			if (value == 9) {
			    $("#dis_free").empty();
			    $("#dis_free").append("<option value='1'>所有停车明细</option>");
			    $("#dis_free").append("<option value='4'>人工放行收费成功</option>");
			    $("#dis_free").append("<option value='5'>人工放行收费失败</option>");
			} else {
	 			$("#dis_free").empty();
				$("#dis_free").append("<option value='1'>所有停车明细</option>");
			    $("#dis_free").append("<option value='2'>车辆非免费停车明细</option>");
			    $("#dis_free").append("<option value='3'>车辆免费停车明细</option>");
			}
	
		}
	
	
		function deleteData(dataId) {
			tool.confirm("确实要删除该条目吗？", function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'delete.shtml',
						data : {
							"ID" : dataId
						},
						dataType : 'json',
						success : function(msg) {
							if (msg.success) {
								$("#pageForm").attr("action", "list.shtml");
								tool.formSubmit();
							} else {
								tool.alert(msg.message);
							}
						},
						error : function(msg) {
							tool.alert("删除失败！");
						}
					});
				}
			});
		}
		function daoru() {
			$("#file").click();
		}
		function daochu() {
			$("#pageForm").attr("action", "downLoad.shtml");
			tool.formSubmit();
		}
		// 		function test1(){
		// 			$.ajax({
		// 				url:'${contentPath}/valid/test.shtml',
		// 				dataType:'json',
		// 				type:'post',
		// 				success:function(data){
		// 					console.log(data);
		// 				}
	
		// 			});
	
		// 		}
	
		// 		function test2(){
		// 			$.ajax({
		// 				url:'${contentPath}/valid/test2.shtml',
		// 				dataType:'json',
		// 				type:'post',
		// 				success:function(data){
		// 					console.log(data);
		// 				}
	
		// 			});
	
		// 		}
		function search() {
			$("#pageForm").attr("action", "list.shtml");
			tool.formSubmit();
		}
	</script>
</body>
</html>