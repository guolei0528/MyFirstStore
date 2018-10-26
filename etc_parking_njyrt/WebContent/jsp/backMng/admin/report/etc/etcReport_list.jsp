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
						<div class="col-sm-6">ETC报表</div>
					</div>
					<input type="file" id="file" name="file" style="display: none;">
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th rowspan="2">停车场</th>
									<th rowspan="2">区域</th>
									<th rowspan="2">收费点</th>
									<th rowspan="2">日期</th>
									<th rowspan="2">收费员</th>
									<th rowspan="2">工班</th>
									<th rowspan="2">总金额（单位：元）</th>
									<th colspan="2">全额收款</th>
									<th colspan="2">优惠收款</th>
									<th rowspan="2">o元收款笔数</th>
								</tr>
								<tr>
									<th>笔数</th>
									<th>金额</th>
									<th>笔数</th>
									<th>金额</th>
								</tr>
							</thead>
							<tbody >
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr align="center">
										<td>${obj.park_name}</td>
										<td>${obj.area_name}</td>
										<td>${obj.lane_name}</td>
										<td>
										${fn:substring(obj.statistics_date,0,4)}-${fn:substring(obj.statistics_date,4,6)}-${fn:substring(obj.statistics_date,6,8)}
										</td>
										<td>${obj.operator_name}</td>
										<td>${obj.shift}</td>
										<td>
										<fmt:formatNumber type="number" value="${obj.toll_etc_total/100}" pattern="#0.00"/>
										</td>
										<td>
										<c:if test="${empty obj.flow_etc_full}">0</c:if>
										<c:if test="${not empty obj.flow_etc_full}">${obj.flow_etc_full}</c:if>
										</td>
										<td>
										<c:if test="${empty obj.toll_etc_full}">0.00</c:if>
										<c:if test="${not empty obj.toll_etc_full}"><fmt:formatNumber type="number" value="${obj.toll_etc_full/100}" pattern="#0.00"/></c:if>
										
										</td>
										<td>
										<c:if test="${empty obj.flow_etc_coupon}">0</c:if>
										<c:if test="${not empty obj.flow_etc_coupon}">${obj.flow_etc_coupon}</c:if>
										</td>
										<td>
										<c:if test="${empty obj.toll_etc_coupon_ea}">0.00</c:if>
										<c:if test="${not empty obj.toll_etc_coupon_ea}"><fmt:formatNumber type="number" value="${obj.toll_etc_coupon_ea/100}" pattern="#0.00"/></c:if>
										</td>
										<td>
										<c:if test="${empty obj.flow_etc_free}">0</c:if>
										<c:if test="${not empty obj.flow_etc_free}">${obj.flow_etc_free}</c:if>
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

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
	</script>
</body>
</html>