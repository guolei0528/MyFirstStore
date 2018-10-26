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
		<form action="paymentSummary.shtml" method="post" id="pageForm"
			enctype="multipart/form-data">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">对账汇总报表</div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>日期</td>
									<td><input type="text" id="query_start_date"
										name="query_start_date"
										value="${queryParam.query_start_date}"
										onfocus="WdatePicker()"></td>
									<td>-</td>
									<td><input type="text" id="query_end_date"
										name="query_end_date" onfocus="WdatePicker()"
										value="${queryParam.query_end_date}"></td>
									<td>
										<button class="btn btn-primary" type="submit"
											>检索</button>
									</td>
								</tr>
							</table>

						</div>
					</div>
					<input type="file" id="file" name="file" style="display: none;">
					<!-- /.box-header -->
					<div class="box-body">
					<label>车辆单位:(辆)&nbsp;&nbsp;&nbsp;&nbsp;金额单位:(元)</label>
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>日期</th>
									<th>停车场</th>
									<!-- 									<th>储值卡流量</th> -->
									<!-- 									<th>储值卡收入</th> -->
									<!-- 									<th>记账卡流量</th> -->
									<!-- 									<th>记账卡收入</th> -->
									<th>ETC流量（辆）</th>
									<th>ETC收入（元）</th>
									<th>移动车流量（辆）</th>
									<th>移动支付（元）</th>
									<th>其他车流量（辆）</th>
									<th>其他支付（元）</th>
									<th>车流量总计（辆)</th>
									<th>支付总计（元）</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
										<td>${obj.exitdate}</td>
<%-- 										<td>${obj.park_id}</td> --%>
										<td>${obj.park_name}</td>
										<td>${obj.etcAmount}</td>
										<td>
										<fmt:formatNumber type="number" value="${obj.etcTotalToll/100}" pattern="0.00" maxFractionDigits="2"/>  
										</td>
										<%-- 										<td>${obj.storeValueAmount}</td> --%>
										<%-- 										<td>${obj.storeValueTotalToll}</td> --%>
										<%-- 										<td>${obj.accountAmount}</td> --%>
										<%-- 										<td>${obj.accountTotalToll}</td> --%>
										<td>${obj.mobileAmount}</td>
										<td>
										<fmt:formatNumber type="number" value="${obj.mobileTotalToll/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
										<td>${obj.otherAmount}</td>
										<td>
										<fmt:formatNumber type="number" value="${obj.otherTotalToll/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
										<td>${obj.etcAmount+obj.mobileAmount+obj.otherAmount}</td>
										<td>
										<fmt:formatNumber type="number" value="${(obj.etcTotalToll+obj.mobileTotalToll+obj.otherTotalToll)/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
									</tr>
								</c:forEach>
								<tr>
									<td>总计</td>
									<td></td>
									<td><c:set var="etcAmount" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="etcAmount"
												value="${etcAmount +obj.etcAmount }" />
										</c:forEach> 
										${etcAmount}</td>
									<td><c:set var="etcTotalToll" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="etcTotalToll"
												value="${etcTotalToll + obj.etcTotalToll }" />
										</c:forEach> 
										<fmt:formatNumber type="number" value="${etcTotalToll/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
									<td><c:set var="mobileAmount" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="mobileAmount"
												value="${mobileAmount +obj.mobileAmount }" />
										</c:forEach> ${mobileAmount}</td>
									<td><c:set var="mobileTotalToll" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="mobileTotalToll"
												value="${mobileTotalToll + obj.mobileTotalToll }" />
										</c:forEach>
										<fmt:formatNumber type="number" value="${mobileTotalToll/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
									<td><c:set var="otherAmount" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="otherAmount"
												value="${otherAmount +obj.otherAmount }" />
										</c:forEach> ${otherAmount}
										</td>
									<td><c:set var="otherTotalToll" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="otherTotalToll"
												value="${otherTotalToll + obj.otherTotalToll }" />
										</c:forEach> 
										<fmt:formatNumber type="number" value="${otherTotalToll/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
									<td><c:set var="amountSum" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="amountSum"
												value="${amountSum + obj.etcAmount+obj.mobileAmount+obj.otherAmount }" />
										</c:forEach> ${amountSum}</td>
									<td><c:set var="totalSum" value="0" /> <c:forEach
											items="${list }" var="obj">
											<c:set var="totalSum"
												value="${totalSum + obj.etcTotalToll+obj.mobileTotalToll+obj.otherTotalToll }" />
										</c:forEach> 
                                            <fmt:formatNumber type="number" value="${totalSum/100}"  pattern="0.00" maxFractionDigits="2"/> 
										</td>
									<%-- 									<td><c:set var="totalCarMember" value="0" /> <c:forEach 
											items="${list }" var="obj">
											<c:set var="totalCarCash"
												value="${totalCarMember + obj.carMember }" />
										</c:forEach> ${totalCarMember}</td>--%>
									<%-- 									<td><c:set var="totalCartStoreValue" value="0" /> <c:forEach --%>
									<%-- 											items="${list }" var="obj"> --%>
									<%-- 											<c:set var="totalCartStoreValue" --%>
									<%-- 												value="${totalCartStoreValue + obj.cartStoreValue }" /> --%>
									<%-- 										</c:forEach> ${totalCartStoreValue}</td> --%>
								</tr>
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
	
			tool.selOption("query_b_list_type", '${queryParam.query_b_list_type}');
	
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