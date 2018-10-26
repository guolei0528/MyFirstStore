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
		<form action="list.shtml" method="post" id="pageForm"
			enctype="multipart/form-data">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">出口/集中缴费报表</div>
						<div class="col-sm-6"></div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>收费点</td>
									<td>
									<td><select id="query_lane_id" name="query_lane_id"
										style="WIDTH: 100%">
											<option value="">所有</option>
											<c:forEach items="${QUERY_LANE_LIST }" var="obj">
												<option value="${obj.lane_id }">${obj.lane_name }</option>
											</c:forEach>
									</select></td>
									<td>收费员</td>
									<td><select id="query_operator" name="query_operator"
										style="WIDTH: 100%">
											<option value="">所有</option>
											<c:forEach items="${QUERY_USER_LIST }" var="obj">
												<option value="${obj.login_name }">${obj.name }</option>
											</c:forEach>
									</select></td>
									<td>开始日期</td>
									<td><input type="text" id="query_statistics_date_from"
										name="query_statistics_date_from"
										value="${queryParam.query_statistics_date_from }"
										placeholder="" autocomplete="off"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd' })"
										style="width:90%"></td>
									<td>结束日期</td>
									<td><input type="text" id="query_statistics_date_to"
										name="query_statistics_date_to"
										value="${queryParam.query_statistics_date_to }" placeholder=""
										autocomplete="off"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd' })"
										style="width:90%"></td>

									<td>
										<button class="btn btn-primary" type="button"
											onclick="search()">查询</button>
										<button class="btn btn-primary" type="button"
											onclick="exprort()">导出</button>
										<button class="btn btn-primary" type="button"
											onclick="print()">打印</button>
									</td>
								</tr>
							</table>
						</div>
					</div>

					<input type="file" id="file" name="file" style="display: none;">
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<!-- 									<th>停车场</th> -->
									<!-- 									<th>区域</th> -->
									<th>收费点</th>
									<th>收费员</th>
									<th>日期</th>
									<th>总流量 (辆)</th>
									<th>总金额 (元)</th>
									<th>现金流量 (辆)</th>
									<th>现金金额 (元)</th>
									<th>ETC流量(辆)</th>
									<th>ETC金额 (元)</th>
									<th>微信流量(辆)</th>
									<th>微信金额 (元)</th>
									<th>支付宝流量(辆)</th>
									<th>支付宝金额 (元)</th>
									<th>放行流量</th>
									<th>0元流量</th>
									<th>预付费流量</th>
								</tr>
							</thead>
							<tbody>
							<input type="hidden" id="query_park_id"
										name="query_park_id" />
									<input type="hidden" id="query_area_id"
										name="query_area_id"  />
									<input type="hidden" id="query_lane_id"
										name="query_lane_id"  />
									<input type="hidden" id="lane_name"
										name="lane_name" />
									<input type="hidden" id="query_pay_method"
										name="query_pay_method" />
									<input type="hidden" id="query_operator"
										name="query_operator" />
									<input type="hidden" id="operator_name"
										name="operator_name" />
									<input type="hidden" id="query_date"
										name="query_date" />
									<input type="hidden" id="query_pdis_count"
										name="query_pdis_count" />
										
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr align="center">
										<%-- 										<td>${obj.park_name}</td> --%>
										<%-- 										<td>${obj.area_name}</td> --%>
										<td>${obj.lane_name}</td>
										<td>${obj.operator_name}</td>
										<td>
											${fn:substring(obj.statistics_date,0,4)}-${fn:substring(obj.statistics_date,4,6)}-${fn:substring(obj.statistics_date,6,8)}
										</td>
										<td><a 
										href="javascript:detailList(${obj.flow_cash_total+obj.flow_etc_total+obj.flow_wx_total+obj.flow_zfb_total+obj.flow_cash_manual+obj.flow_cash_free+obj.flow_cash_prepay},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','','')"
											>${obj.flow_cash_total+obj.flow_etc_total+obj.flow_wx_total+obj.flow_zfb_total+obj.flow_cash_manual+obj.flow_cash_free+obj.flow_cash_prepay}</a>
										</td>
										<td><fmt:formatNumber type="number"
												value="${(obj.toll_cash_total+obj.toll_etc_total+obj.toll_wx_total+obj.toll_zfb_total)/100}"
												pattern="#0.00" /></td>
										<td><a class="detailList" href="javascript:detailList(${obj.flow_cash_total},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','0','1')"><c:if
													test="${empty obj.flow_cash_total}">0</c:if> <c:if
													test="${not empty obj.flow_cash_total}">${obj.flow_cash_total}</c:if></a>
										</td>
										<td><c:if test="${empty obj.toll_cash_total}">0.00</c:if>
											<c:if test="${not empty obj.toll_cash_total}">
												<fmt:formatNumber type="number"
													value="${obj.toll_cash_total/100}" pattern="#0.00" />
											</c:if></td>
										<td><a href="javascript:detailList(${obj.flow_etc_total},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','2','')"><c:if
													test="${empty obj.flow_etc_total}">0</c:if> <c:if
													test="${not empty obj.flow_etc_total}">${obj.flow_etc_total}</c:if></a>
										</td>
										<td><c:if test="${empty obj.toll_etc_total}">0.00</c:if>
											<c:if test="${not empty obj.toll_etc_total}">
												<fmt:formatNumber type="number"
													value="${obj.toll_etc_total/100}" pattern="#0.00" />
											</c:if></td>
										<td><a href="javascript:detailList(${obj.flow_wx_total},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','3','')"><c:if
													test="${empty obj.flow_wx_total}">0</c:if> <c:if
													test="${not empty obj.flow_wx_total}">${obj.flow_wx_total}</c:if></a>
										</td>
										<td><c:if test="${empty obj.toll_wx_total}">0.00</c:if> <c:if
												test="${not empty obj.toll_wx_total}">
												<fmt:formatNumber type="number"
													value="${obj.toll_wx_total/100}" pattern="#0.00" />
											</c:if></td>
										<td><a href="javascript:detailList(${obj.flow_zfb_total},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','4','')"><c:if
													test="${empty obj.flow_zfb_total}">0</c:if> <c:if
													test="${not empty obj.flow_zfb_total}">${obj.flow_zfb_total}</c:if></a>
										</td>
										<td><c:if test="${empty obj.toll_zfb_total}">0.00</c:if>
											<c:if test="${not empty obj.toll_zfb_total}">
												<fmt:formatNumber type="number"
													value="${obj.toll_zfb_total/100}" pattern="#0.00" />
											</c:if></td>
										<td><a href="javascript:detailList(${obj.flow_cash_manual},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','9','')"><c:if
													test="${empty obj.flow_cash_manual}">0</c:if> <c:if
													test="${not empty obj.flow_cash_manual}">${obj.flow_cash_manual}</c:if></a>
										</td>
										<td><a href="javascript:detailList(${obj.flow_cash_free},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','0','0')"><c:if
													test="${empty obj.flow_cash_free}">0</c:if> <c:if
													test="${not empty obj.flow_cash_free}">${obj.flow_cash_free}</c:if></a>
										</td>
										<td><a href="javascript:detailList(${obj.flow_cash_prepay},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','11','')"><c:if
													test="${empty obj.flow_cash_prepay}">0</c:if> <c:if
													test="${not empty obj.flow_cash_prepay}">${obj.flow_cash_prepay}</c:if></a>
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
						<table id="printTable" style="display: none;"
							class="table table-bordered table-condensed">
							<thead style="font-size: small;">
								<tr><th colspan="11"><h3>出口/集中缴费报表</h3></th></tr>
								<tr>
									<th>收费点</th>
									<th>收费员</th>
									<th>日期</th>
									<th>总流量 (辆)</th>
									<th>总金额 (元)</th>
									<th>现金流量 (辆)</th>
									<th>现金金额 (元)</th>
									<th>ETC流量(辆)</th>
									<th>ETC金额 (元)</th>
									<th>移动流量(辆)</th>
									<th>移动金额 (元)</th>
								</tr>
							</thead>
							<tbody>
							
							</tbody>
						</table>
					</div>
					<!-- /.box-body -->
					<div class="box-footer">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status" aria-live="polite">
				${page.pageCount==0?0:page.currentPage }/${page.pageCount}页 &nbsp;&nbsp;&nbsp;共${page.recordCount }条记录</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers"
				id="example2_paginate">
				<ul class="pagination">
					<li class="paginate_button previous" id="example2_previous">
						<a href="javascript:divPage(1);" aria-controls="example2" data-dt-idx="0" tabindex="0">首页</a>
					</li>
					<li class="paginate_button">
						<a href="javascript:divPage(2);" aria-controls="example2" data-dt-idx="1" tabindex="0">上一页</a>
					</li>
					<li class="paginate_button ">
						<a href="javascript:divPage(3);" aria-controls="example2" data-dt-idx="2" tabindex="0">下一页</a>
					</li>
					<li class="paginate_button next" id="example2_next">
						<a href="javascript:divPage(4);" aria-controls="example2" data-dt-idx="7" tabindex="0">尾页</a>
					</li>
				</ul>
			</div>
		</div>
	</div>
		<input type="hidden" id="currentPage" name="page.currentPage" value="${page.currentPage}">
	<input type="hidden" id="pageCount" name="page.pageCount" value="${page.pageCount}">
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize }">
				</div>
				<!-- /.box -->

			</div>
		</form>
		<!-- /.content -->
	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>
	<script src="${contentPath }/js/jquery/jquery.jqprint-0.3.js"></script>
	<script src="${contentPath }/js/jquery/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript">
	$(function(){
	tool.selOption("query_lane_id", '${queryParam.query_lane_id}');
	tool.selOption("query_operator", '${queryParam.query_operator}');
	
	});
	
	
	function exprort(){
		 bootbox.dialog({  
		 		size: "small",
                message: "是否导出明细数据?",  
                buttons: {  
                    Cancel: {  
                        label: "否",  
                        className: "btn-default",  
                        callback: function () {  
                        	$("#pageForm").attr("action","downLoad.shtml");
							tool.formSubmit();
                        }  
                    }  
                    , OK: {  
                        label: "是",  
                        className: "btn-primary",  
                        callback: function () {  
//                             $("#pageForm").attr("action","downLoad.shtml");
// 							tool.formSubmit();
							$("#pageForm").attr("action","downLoadAll.shtml");
							tool.formSubmit();
                        }  
                    }  
                }  
            });  
		
// 		$("#pageForm").attr("action","downLoad.shtml");
// 		tool.formSubmit();
	}
	
	
	
	function detailList(flow,park_id,area_id,lane_id,lane_name,operator,operator_name,statistics_date,pay_method,pdiscounttoll)
	{
		if(flow == 0)
		{	
			tool.alert("无车辆记录！");
			return;
		}
		$("#query_park_id").val(park_id);
		$("#query_area_id").val(area_id);
		$("#query_lane_id").val(lane_id);
		$("#lane_name").val(lane_name);
		$("#query_operator").val(operator);
		$("#operator_name").val(operator_name);
		$("#query_date").val(statistics_date);
		$("#query_pay_method").val(pay_method);
		$("#query_pdis_count").val(pdiscounttoll);
		$("#currentPage").val("");
		$("#pageCount").val("");
		$("#pageSize").val("");
		$("#pageForm").attr("action","detailList.shtml");
		tool.formSubmit();
	}
	
	
	function search(){
			$("#pageForm").attr("action","list.shtml");
			tool.formSubmit();
		}
		
	function divPage(type){
	        var to_page;
	        if(type==1){
	        	to_page = 1;
	        }else if(type==2){
	        	to_page = parseInt(document.getElementById('currentPage').value) - 1; 
	        }else if(type==3){
	        	to_page = parseInt(document.getElementById('currentPage').value) + 1;
	        }else if(type==4){
	        	to_page = parseInt(document.getElementById('pageCount').value);
	        }else if(type==5){
	        	if(document.getElementById('selPage').value.length==0){
	        		return ;
	        	}
	        	to_page = parseInt(document.getElementById('selPage').value);
	        }
	        if(to_page < 1){
	        	to_page = 1;
	        }
	        if(to_page > parseInt(document.getElementById('pageCount').value)){
	        	to_page = parseInt(document.getElementById('pageCount').value);
	        }
	        $("#pageForm").attr("action","list.shtml");
	        document.getElementById('currentPage').value = to_page;
	        document.getElementById('pageForm').submit();
	    }
	    //只允许输入的值是否为0--9，左向方向键及删除键
		function checkCharIsNumber(){
			if(event.keyCode==13){//回车键
				divPage(5);
				return false;
			}else if((event.keyCode<=57&&event.keyCode>=48)||(event.keyCode<=105&&event.keyCode>=96)||
				(event.keyCode==8||event.keyCode==37||event.keyCode==39)){
				return true;
			}else{
				return false;
			}
		}
		function print() {
			var begin = $("#query_statistics_date_from").val();
			var end = $("#query_statistics_date_to").val();
			if(begin=='' || end=='' || (begin!=end)){
				tool.alert("只能打印一天的内容！");
				return;
			}
			bootbox.dialog({
				size : "small",
				message : "是否打印?",
				buttons : {
					Cancel : {
						label : "否",
						className : "btn-default",
						callback : function() {}
					},
					OK : {
						label : "是",
						className : "btn-primary",
						callback : function() {
							var lane = $("#query_lane_id").val();
							var opt = $("#query_operator").val();
							$.ajax({
								url:"print.shtml",
								type:"post",
								dataType:"json",
								data:{"query_statistics_date_from":begin,"query_statistics_date_to":end,"query_lane_id":lane,"query_operator":opt},
								success:function(list){
									console.info(list.length);
									if(list.length>0){
										var str = "";
										for(var i=0;i<list.length;i++){
											var obj = list[i];
											str += "<tr align='center'><td>"+obj.lane_name+"</td><td>"+obj.operator_name+"</td><td>"+obj.statistics_date+"</td><td>"
												+(obj.flow_cash_total+obj.flow_etc_total+obj.flow_wx_total+obj.flow_zfb_total+obj.flow_cash_manual+obj.flow_cash_free+obj.flow_cash_prepay)
												+"</td><td>"+(obj.toll_cash_total+obj.toll_etc_total+obj.toll_wx_total+obj.toll_zfb_total)*0.01+"</td><td>"
												+obj.flow_cash_total+"</td><td>"+obj.toll_cash_total*0.01+"</td><td>"+obj.flow_etc_total+"</td><td>"+obj.toll_etc_total*0.01
												+"</td><td>"+(obj.flow_wx_total+obj.flow_zfb_total)+"</td><td>"+(obj.toll_wx_total+obj.toll_zfb_total)*0.01+"</td></tr>";
											
										}
										$("#printTable tbody").html(str);
									}else{
									
									}
									$("#printTable").show();
									$("#printTable").jqprint();
									$("#printTable tbody").html("");
									$("#printTable").hide();
								},
								error:function(){
									
								}
							});
						}
					}
				}
			});
		}
	</script>
</body>
</html>