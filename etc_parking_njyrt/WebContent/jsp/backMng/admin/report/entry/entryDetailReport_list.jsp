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
						<div class="col-sm-6">入口流量报表</div>
						<div class="col-sm-6"></div>
						<div class="col-sm-12 ">
							<table class="queryTable">
							<tr>
							<td>入口车道：${queryParam.lane_name }   日期：${queryParam.query_date }</td>
									<td>
										<button class="btn btn-primary" type="button"
											onclick="exprort()">导出</button>
										<button class="btn btn-primary" type="button"
											onclick="back()">返回</button>
									</td>
									
									<input type="hidden" id="park_id"
										name="park_id"
										value="${queryParam.query_park_id }" />
									<input type="hidden" id="area_id"
										name="query_area_id"
										value="${queryParam.query_area_id }" />
									<input type="hidden" id="lane_id"
										name="lane_id"
										value="${queryParam.query_lane_id }" />
									<input type="hidden" id="pay_method"
										name="pay_method"
										value="${queryParam.query_pay_method }" />
									<input type="hidden" id="date"
										name="date" value="${queryParam.query_date }" />
									<input type="hidden" id="query_lane_id"
										name="query_lane_id"
										value="${param.query_lane_id }" />
									<input type="hidden" id="query_statistics_date_from"
										name="query_statistics_date_from"
										value="${param.query_statistics_date_from }" />
									<input type="hidden" id="query_statistics_date_to"
										name="query_statistics_date_to"
										value="${param.query_statistics_date_to }" />
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
<!-- 																		<th>停车场</th> -->
<!-- 																		<th>区域</th> -->
									<th>日期</th>
									<th>入口车道</th>
									<th>入口时间</th>
									<th>车牌</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr align="center">
<%-- 										<td>${obj.park_name}</td> --%>
<%-- 										<td>${obj.area_name}</td> --%>
										<td>${obj.date}</td>
										<td>${obj.lanename}</td>
										<td>
										<fmt:formatDate value="${obj.time}" pattern="yyyy-MM-dd HH:mm:ss"/>
										</td>
										<td>${obj.mvlicense}</td>
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

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
// 	$(function(){
// 	tool.selOption("query_operator", '${queryParam.query_operator}');
// 	});
	
	
	function back(){
		$("#pageForm").attr("action","list.shtml");
		tool.formSubmit();
		
	}
	
	
	function search(){
			$("#pageForm").attr("action","detailList.shtml");
			tool.formSubmit();
	}
		
	function exprort()
	{
			$("#pageForm").attr("action","downLoadDetail.shtml");
			tool.formSubmit();
	}
	

	</script>
</body>
</html>