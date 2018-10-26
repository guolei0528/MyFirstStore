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
		<form action="detailList.shtml" method="post" id="pageForm"
			enctype="multipart/form-data">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">出口/集中缴费报表:</div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
								<td>收费点：${queryParam.lane_name }   收费员：${queryParam.operator_name}   日期：${queryParam.query_date }</td>
									<td  style="text-align:right;float:right">
										<button  class="btn btn-primary" type="button"
											onclick="back()">返回</button>
										&nbsp;
										<button class="btn btn-primary" type="button"
											onclick="exprort()">导出</button>
									</td>
									<input type="hidden" id="query_park_id"
										name="query_park_id"
										value="${queryParam.query_park_id }" />
									<input type="hidden" id="query_area_id"
										name="query_area_id"
										value="${queryParam.query_area_id }" />
									<input type="hidden" id="query_lane_id"
										name="query_lane_id"
										value="${queryParam.query_lane_id }" />	
									<input type="hidden" id="lane_name"
										name="lane_name"
										value="${queryParam.lane_name }" />
									<input type="hidden" id="query_pay_method"
										name="query_pay_method"
										value="${queryParam.query_pay_method }" />
									<input type="hidden" id="query_operator"
										name="query_operator"
										value="${queryParam.query_operator }" />
									<input type="hidden" id="operator_name"
										name="operator_name"
										value="${queryParam.operator_name }" />
									<input type="hidden" id="query_date"
										name="query_date"
										value="${queryParam.query_date }" />
									<input type="hidden" id="query_statistics_date_from"
										name="query_statistics_date_from"
										value="${queryParam.query_statistics_date_from }" />
									<input type="hidden" id="query_statistics_date_to"
										name="query_statistics_date_to"
										value="${queryParam.query_statistics_date_to }" />
									<input type="hidden" id="query_pdis_count"
										name="query_pdis_count"
										value="${queryParam.query_pdis_count }" />
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
									<th>车牌</th>
									<th>入口时间</th>
									<th>收费时间</th>
									<th>收费方式</th>
									<th>应收金额(元)</th>
									<th>实收金额 (元)</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr align="center">
										<%-- 										<td>${obj.park_name}</td> --%>
										<%-- 										<td>${obj.area_name}</td> --%>
										<td>${obj.lanename}</td>
										<td>${obj.operatorname}</td>
										<td>
											${fn:substring(obj.date,0,4)}-${fn:substring(obj.date,4,6)}-${fn:substring(obj.date,6,8)}
										</td>
										<td>${obj.mvlicense}</td>
										<td>
										<fmt:formatDate value="${obj.entrytime}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>
										<fmt:formatDate value="${obj.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
										
										</td>
										<td>
										<c:choose>
												<c:when test="${obj.paymethod == 0 && obj.pdiscounttoll != 0}">  
													现金支付
												</c:when>
												<c:when test="${obj.paymethod == 0 && obj.pdiscounttoll == 0}">  
													0元支付
												</c:when>
												<c:when test="${obj.paymethod == 2}">  
													ETC支付
												</c:when>
												<c:when test="${obj.paymethod == 3}">  
													微信支付
												</c:when>
												<c:when test="${obj.paymethod == 4}">  
													支付宝支付
												</c:when>
												<c:when test="${obj.paymethod == 5}">  
													其他移动支付方式
												</c:when>
												<c:when test="${obj.paymethod == 9}">  
													人工放行
												</c:when>
												<c:when test="${obj.paymethod == 10}">  
													人工放行
												</c:when>
												<c:when test="${obj.paymethod == 11}">  
													预支付
												</c:when>
												<c:otherwise> 
													其他支付方式
												</c:otherwise>
											</c:choose>
										
										</td>
										<td>
										<fmt:formatNumber type="number"
													value="${obj.pdiscounttoll/100}" pattern="#0.00" />
										</td>
										<td>
										<fmt:formatNumber type="number"
													value="${obj.totaltoll/100}" pattern="#0.00" />
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

	<script type="text/javascript">
	$(function(){
	tool.selOption("query_lane_id", '${queryParam.query_lane_id}');
	tool.selOption("query_operator", '${queryParam.query_operator}');
	});
	
	
	function back(){
		$("#pageCount").val("");
		$("#pageSize").val("");
		$("#pageForm").attr("action","list.shtml");
		tool.formSubmit();
		
	}
	
	function exprort()
	{
			$("#pageForm").attr("action","downLoadDetail.shtml");
			tool.formSubmit();
	}
	
	
	function search(){
			$("#pageForm").attr("action","totalList.shtml");
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
	        $("#pageForm").attr("action","detailList.shtml");
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
	

	</script>
</body>
</html>