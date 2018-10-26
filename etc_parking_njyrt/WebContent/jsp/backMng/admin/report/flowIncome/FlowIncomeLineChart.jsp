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
<script src="${contentPath }/js/highcharts/highcharts.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%-- 	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%> --%>

	<!-- 	<form action="abc.shtml" method="post" id="pageForm" -->
	<!-- 			enctype="multipart/form-data"> -->
	<%-- 	<input type="hidden" value="${TOLL_REPORTS}" id="toll_reports" /> --%>
	<%-- 	 <input value="${TOLL_REPORTS[10].total_toll_receipt}" id="toll_reports"/> --%>
	<input type="hidden" name="projectName" id="projectName" value="${pageContext.request.contextPath}"/>
	<div>
		<div id="container_toll"
			style="max-width:90%;height:90%;width:90%;float:left;"></div>
		<div style="float:right;max-width:8%;height:8%;width:8%;">
			<label style='float:left;'>收入查询周期</label><br />
			<div style="clear:both"></div>
			<label style='float:left;'>小时</label> <input style='float:left;'
				type="radio" name="query_toll_cycle" value="1" checked="checked"></input>
			<label style='float:left;'>8天</label> <input style='float:left;'
				type="radio" name="query_toll_cycle" value="2"></input><br /> <label
				style='float:left;'>开始时间</label> <input style='width:80%;'
				id='query_toll_start_time' name="query_toll_start_time"
				onfocus="WdatePicker({onpicked:chageTollStartTime()})"
				value="${queryParam.query_toll_start_time}"></input><br /> <label
				style='float:left;'>结束时间</label> <input style='width:80%;'
				id='query_toll_end_time' name="query_toll_end_time"
				onfocus="WdatePicker({onpicked:chageTollEndTime()})"
				value="${queryParam.query_toll_end_time}"></input><br />
			<div class="weui-btn-area">
				<input id="search_toll" type="button" class="btn btn-primary"
					value="查询"></input>
			</div>
		</div>
		<div>
			<div id="container_flow"
				style="max-width:90%;height:90%;width:90%;float:left;"></div>
			<div style="float:right;max-width:8%;height:8%;width:8%">
				<label style='float:left;'>流量查询周期</label><br />
				<div style="clear:both"></div>
				<label style='float:left;'>小时</label> <input style='float:left;'
					type="radio" name="query_flow_cycle" value="1" checked="checked"></input>
				<label style='float:left;'>8天</label> <input style='float:left;'
					type="radio" name="query_flow_cycle" value="2"></input><br /> <label
					style='float:left;'>开始时间</label> <input style='width:80%;'
					id="query_flow_start_time" name="query_flow_start_time"
					onfocus="WdatePicker({onpicked:chageFlowStartTime()})"
					value="${queryParam.query_flow_start_time}"></input><br /> <label
					style='float:left;'>结束时间</label> <input style='width:80%;'
					id="query_flow_end_time" name="query_flow_end_time"
					onfocus="WdatePicker({onpicked:chageFlowEndTime()})"
					value="${queryParam.query_flow_end_time}"></input><br />
				<div class="weui-btn-area">
					<input id="search_flow" class="btn btn-primary" type="button"
						value="查询"></input>
				</div>
				<br />
				<div class="weui-btn-area">
					<input id="back_main" class="btn btn-primary" type="button"
						value="主页"></input>
				</div>
			</div>
		</div>
		<!-- 		<label style='float:left;'>日期</label><br /> 
						<input type="text" id="query_start_date"
										name="query_start_date" value="${queryParam.query_start_date}"
										onfocus="WdatePicker()"> -->
		<!-- 	</div> -->
		<%-- 		<%@include file="/jsp/common/bootstrap/footer.jsp"%> --%>

		<!-- 	</form> -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true"></div>


		<script type="text/javascript">
		
			function chageTollStartTime() {
				var query_toll_start_time = $("#query_toll_start_time").val();
				var cycle_time = $("input[name='query_toll_cycle']:checked").val();
		
				if (cycle_time == 1) {
					$("#query_toll_end_time").val(query_toll_start_time);
				}
				if (cycle_time == 2) {
					var startDate = new Date(query_toll_start_time.replace('-', '/'));
					var endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 7);
					$("#query_toll_end_time").val(endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + (endDate.getDate() > 10 ? endDate.getDate() : "0" + endDate.getDate()));
				}
			}
		
			function chageTollEndTime() {
				var query_toll_end_time = $("#query_toll_end_time").val();
				var cycle_time = $("input[name='query_toll_cycle']:checked").val();
		
				if (cycle_time == 1) {
					$("#query_toll_start_time").val(query_toll_end_time);
				}
				if (cycle_time == 2) {
					var endDate = new Date(query_toll_end_time.replace('-', '/'));
					var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
					$("#query_toll_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
				}
			}
		
			function chageFlowStartTime() {
				var query_flow_start_time = $("#query_flow_start_time").val();
				var cycle_time = $("input[name='query_flow_cycle']:checked").val();
		
				if (cycle_time == 1) {
					$("#query_flow_end_time").val(query_flow_start_time);
				}
				if (cycle_time == 2) {
					var startDate = new Date(query_flow_start_time.replace('-', '/'));
					var endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 7);
					$("#query_flow_end_time").val(endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + (endDate.getDate() > 10 ? endDate.getDate() : "0" + endDate.getDate()));
				}
			}
		
			function chageFlowEndTime() {
				var query_flow_end_time = $("#query_flow_end_time").val();
				var cycle_time = $("input[name='query_flow_cycle']:checked").val();
		
				if (cycle_time == 1) {
					$("#query_flow_start_time").val(query_flow_end_time);
				}
				if (cycle_time == 2) {
					var endDate = new Date(query_flow_end_time.replace('-', '/'));
					var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
					$("#query_flow_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
				}
			}
		
			$(function() {
				$("#back_main").click(function() {
					var projName =  document.getElementById("projectName").value;
					window.location.href =  projName + '/main.shtml';
				});
		
				$('input:radio[name="query_toll_cycle"]').change(function() {
					var query_toll_start_time = $("#query_toll_start_time").val();
					var query_toll_end_time = $("#query_toll_end_time").val();
					var cycle_time = $("input[name='query_toll_cycle']:checked").val();
					if (cycle_time == 1) {
						if (query_toll_end_time !== null && query_toll_end_time !== '') {
							$("#query_toll_start_time").val(query_toll_end_time);
							return;
						}
						if (query_toll_start_time !== null && query_toll_start_time !== '') {
							$("#query_toll_end_time").val(query_toll_start_time);
							return;
						}
		
						var now = new Date();
						var nowStr = startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate());
						$("#query_toll_start_time").val(nowStr);
						$("#query_toll_end_time").val(nowStr);
						return;
					}
		
					//判断结束时间不为空
					if (query_toll_end_time !== null && query_toll_end_time !== '') {
						var endDate = new Date(query_toll_end_time.replace('-', '/'));
						var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
						$("#query_toll_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
						return;
					}
		
					//判断开始时间不为空
					if (query_toll_start_time !== null && query_toll_start_time !== '') {
						var startDate = new Date(query_toll_start_time.replace('-', '/'));
						var endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 7);
						$("#query_toll_end_time").val(endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + (endDate.getDate() > 10 ? endDate.getDate() : "0" + endDate.getDate()));
						return;
					}
		
					var endDate = new Date();
					var nowStr = endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate();
					$("#query_toll_end_time").val(nowStr);
					var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
					$("#query_toll_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
				}
				);
		
		
				$('input:radio[name="query_flow_cycle"]').change(function() {
					var query_flow_start_time = $("#query_flow_start_time").val();
					var query_flow_end_time = $("#query_flow_end_time").val();
					var cycle_time = $("input[name='query_flow_cycle']:checked").val();
					if (cycle_time == 1) {
						if (query_flow_end_time !== null && query_flow_end_time !== '') {
							$("#query_flow_start_time").val(query_flow_end_time);
							return;
						}
						if (query_flow_start_time !== null && query_flow_start_time !== '') {
							$("#query_flow_end_time").val(query_flow_start_time);
							return;
						}
		
						var now = new Date();
						var nowStr = startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate());
						$("#query_flow_start_time").val(nowStr);
						$("#query_flow_end_time").val(nowStr);
						return;
					}
		
					//判断结束时间不为空
					if (query_flow_end_time !== null && query_flow_end_time !== '') {
						var endDate = new Date(query_flow_end_time.replace('-', '/'));
						var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
						$("#query_flow_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
						return;
					}
		
					//判断开始时间不为空
					if (query_flow_start_time !== null && query_flow_start_time !== '') {
						var startDate = new Date(query_flow_start_time.replace('-', '/'));
						var endDate = new Date(startDate.getFullYear(), startDate.getMonth(), startDate.getDate() + 7);
						$("#query_flow_end_time").val(endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + (endDate.getDate() > 10 ? endDate.getDate() : "0" + endDate.getDate()));
						return;
					}
		
					var endDate = new Date();
					var nowStr = endDate.getFullYear() + "-" + (endDate.getMonth() + 1) + "-" + endDate.getDate();
					$("#query_flow_end_time").val(nowStr);
					var startDate = new Date(endDate.getFullYear(), endDate.getMonth(), endDate.getDate() - 7);
					$("#query_flow_start_time").val(startDate.getFullYear() + "-" + (startDate.getMonth() + 1) + "-" + (startDate.getDate() > 10 ? startDate.getDate() : "0" + startDate.getDate()));
				}
				);
		
				$("#search_toll").click(function() {
					var query_toll_cycle = $("input[name='query_toll_cycle']:checked").val();
					var query_toll_start_time = $("#query_toll_start_time").val();
					var query_toll_end_time = $("#query_toll_end_time").val();
					// 				alert(query_toll_cycle);
					// 				tool.confirm("确实要删除该条目吗？", function(result) {
					// 				if (result) {
					$.ajax({
						type : 'post',
						url : 'search_toll_report.shtml',
						data : {
							"query_toll_cycle" : query_toll_cycle,
							"query_toll_start_time" : query_toll_start_time,
							"query_toll_end_time": query_toll_end_time
						},
						dataType : 'json',
						success : function(data) {
							var tollJson = data;
							var total_toll_receivable = new Array(tollJson.length);
							var total_toll_receipt = new Array(tollJson.length);
							var cash_toll_receivable = new Array(tollJson.length);
							var cash_toll_receipt = new Array(tollJson.length);
							var etc_toll_receivable = new Array(tollJson.length);
							var etc_toll_receipt = new Array(tollJson.length);
							var mobile_toll_receivable = new Array(tollJson.length);
							var mobile_toll_receipt = new Array(tollJson.length);
							var manual_toll_receivable = new Array(tollJson.length);
							var manual_toll_receipt = new Array(tollJson.length);
		
							for (var i = 0; i < tollJson.length; i++) {
								total_toll_receivable[i] = tollJson[i].total_toll_receivable / 100;
								total_toll_receipt[i] = tollJson[i].total_toll_receipt / 100;
								cash_toll_receivable[i] = tollJson[i].cash_toll_receivable / 100;
								cash_toll_receipt[i] = tollJson[i].cash_toll_receipt / 100;
								etc_toll_receivable[i] = tollJson[i].etc_toll_receivable / 100;
								etc_toll_receipt[i] = tollJson[i].etc_toll_receipt / 100;
								mobile_toll_receipt[i] = tollJson[i].mobile_toll_receipt / 100;
								mobile_toll_receipt[i] = tollJson[i].mobile_toll_receipt / 100;
								manual_toll_receivable[i] = tollJson[i].manual_toll_receivable / 100;
								manual_toll_receipt[i] = tollJson[i].manual_toll_receipt / 100;
							}
		
							var format = '%H:%M';
							var cycleTime = 1 * 3600 * 1000;
							if (query_toll_cycle == 2) {
								format = '%Y-%m-%d';
								cycleTime = 24 * 3600 * 1000;
		
							}
							var startDate = new Date(tollJson[0].date_time.time);
		
							var tollChart = Highcharts.chart('container_toll', {
								title : {
									text : '停车场收入情况'
								},
								xAxis : {
									type : 'datetime',
									dateTimeLabelFormats : {
										day : format
									}
								},
								yAxis : {
									title : {
										text : '收入金额(元)'
									},
								//tickInterval : 20
								},
								legend : {
									layout : 'vertical',
									align : 'right',
									verticalAlign : 'middle'
								},
								plotOptions : {
									series : {
										label : {
											connectorAllowed : false
										},
										pointStart : Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()), // 开始值
										pointInterval : cycleTime // 间隔一天
									}
								},
								series : [ {
									name : '应收总金额',
									data : total_toll_receivable
								}, {
									name : '实收总金额',
									data : total_toll_receipt
								}, {
									name : '现金应收金额',
									data : cash_toll_receivable
								}, {
									name : '现金实收金额',
									data : cash_toll_receipt
								}, {
									name : 'ETC应收金额',
									data : etc_toll_receivable
								}, {
									name : 'ETC实收金额',
									data : etc_toll_receipt
								}, {
									name : '移动应收金额',
									data : mobile_toll_receivable
								}, {
									name : '移动实收金额',
									data : mobile_toll_receipt
								},
									{
										name : '人工放行应收金额',
										data : manual_toll_receivable
									},
									{
										name : '人工放行实收金额',
										data : manual_toll_receipt
									}
								]
							});
		
						}
					});
				// 				}
				// 			});
				});
		
		//查询流量
				$("#search_flow").click(function() {
					var query_flow_cycle = $("input[name='query_flow_cycle']:checked").val();
					var query_flow_start_time = $("#query_flow_start_time").val();
					var query_flow_end_time = $("#query_flow_end_time").val();
					$.ajax({
						type : 'post',
						url : 'search_flow_report.shtml',
						data : {
							"query_flow_cycle" : query_flow_cycle,
							"query_flow_start_time" : query_flow_start_time,
							"query_flow_end_time" : query_flow_end_time
						},
						dataType : 'json',
						success : function(data) {
							var flowJsons = data;
							// 						var tollJson = JSON.parse(tollReports);
							var entryFlowJsons = flowJsons[0];
							var exitFlowJsons = flowJsons[1];
		
							var entry_total_flow = new Array(entryFlowJsons.length);
							var entry_cash_flow = new Array(entryFlowJsons.length);
							var entry_etc_flow = new Array(entryFlowJsons.length);
							var entry_mobile_flow = new Array(entryFlowJsons.length);
							var entry_manual_flow = new Array(entryFlowJsons.length);
		
		
							var exit_total_flow = new Array(entryFlowJsons.length);
							var exit_cash_flow = new Array(entryFlowJsons.length);
							var exit_etc_flow = new Array(entryFlowJsons.length);
							var exit_mobile_flow = new Array(entryFlowJsons.length);
							var exit_manual_flow = new Array(entryFlowJsons.length);
		
							for (var i = 0; i < entryFlowJsons.length; i++) {
								entry_total_flow[i] = entryFlowJsons[i].total_flow;
								entry_cash_flow[i] = entryFlowJsons[i].cash_flow;
								entry_etc_flow[i] = entryFlowJsons[i].etc_flow;
								entry_mobile_flow[i] = entryFlowJsons[i].mobile_flow;
								entry_manual_flow[i] = entryFlowJsons[i].manual_flow;
							}
		
							for (var j = 0; j < exitFlowJsons.length; j++) {
								exit_total_flow[j] = exitFlowJsons[j].total_flow;
								exit_cash_flow[j] = exitFlowJsons[j].cash_flow;
								exit_etc_flow[j] = exitFlowJsons[j].etc_flow;
								exit_mobile_flow[j] = exitFlowJsons[j].mobile_flow;
								exit_manual_flow[j] = exitFlowJsons[j].manual_flow;
							}
		
							var format = '%H:%M';
							var cycleTime = 1 * 3600 * 1000;
							if (query_flow_cycle == 2) {
								format = '%Y-%m-%d';
								cycleTime = 24 * 3600 * 1000;
							}
							var startDate = new Date(entryFlowJsons[0].date_time.time);
		
							var flowChart = Highcharts.chart('container_flow', {
								title : {
									text : '停车场流量情况'
								},
								xAxis : {
									type : 'datetime',
									dateTimeLabelFormats : {
										day : format
									}
								},
								yAxis : {
									title : {
										text : '车辆数量(量)'
									},
								//tickInterval : 20
								},
								legend : {
									layout : 'vertical',
									align : 'right',
									verticalAlign : 'middle'
								},
								plotOptions : {
									series : {
										label : {
											connectorAllowed : false
										},
										pointStart : Date.UTC(startDate.getFullYear(), startDate.getMonth(), startDate.getDate()), // 开始值
										pointInterval : cycleTime // 间隔一天
									}
								},
								series : [ {
									name : '入口总车流量',
									data : entry_total_flow
								}, {
									name : '出口总车流量',
									data : exit_total_flow
								}, {
									name : '现金入口车流量',
									data : entry_cash_flow
								}, {
									name : '现金出口车流量',
									data : exit_cash_flow
								}, {
									name : 'ETC入口车流量',
									data : entry_etc_flow
								}, {
									name : 'ETC出口车流量',
									data : exit_etc_flow
								}, {
									name : '移动入口车流量',
									data : entry_mobile_flow
								}, {
									name : '移动出口车流量',
									data : exit_mobile_flow
								}, {
									name : '人工放行入口车流量',
									data : exit_manual_flow
								}, {
									name : '人工放行出口车流量',
									data : exit_manual_flow
								}
		
								]
							});
		
						}
					});
				// 				}
				// 			});
				});
				// 			var tollReports = $('toll_reports').value;
				$("#search_toll").click();
				$("#search_flow").click();
			});
		</script>
</body>
</html>