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
						<div class="col-sm-6">入口报表</div>
						<div class="col-sm-6"></div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>入口车道</td>
									<td>
									<td><select id="query_lane_id" name="query_lane_id"
										style="WIDTH: 100%">
											<option value="">所有</option>
											<c:forEach items="${QUERY_LANE_LIST }" var="obj">
												<option value="${obj.lane_id }">${obj.lane_name }</option>
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
									<th>停车场</th>
									<th>区域</th>
									<th>车道</th>
									<th>日期</th>
									<th>总流量(辆)</th>
									<th>ETC流量(辆)</th>
									<th>车牌识别流量(辆)</th>
									<th>人工放行流量(辆)</th>
								</tr>
							</thead>
							<tbody >
							<input type="hidden" id="park_id"
										name="park_id" />
									<input type="hidden" id="area_id"
										name="area_id"  />
									<input type="hidden" id="lane_id"
										name="lane_id"  />
									<input type="hidden" id="lane_name"
										name="lane_name" />
									<input type="hidden" id="pay_method"
										name="pay_method" />
									<input type="hidden" id="operator"
										name="operator" />
									<input type="hidden" id="operator_name"
										name="operator_name" />
									<input type="hidden" id="date"
										name="date" />
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr align="center">
										<td>${obj.park_name}</td>
										<td>${obj.area_name}</td>
										<td>${obj.lane_name}</td>
										<td>
										${fn:substring(obj.statistics_date,0,4)}-${fn:substring(obj.statistics_date,4,6)}-${fn:substring(obj.statistics_date,6,8)}
										</td>
										<td>
										<a href="javascript:detailList(${obj.flow_total},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','')"><c:if test="${empty obj.flow_total}">0</c:if>
										<c:if test="${not empty obj.flow_total}">${obj.flow_total}</c:if></a>
										</td>
										<td>
										<a href="javascript:detailList(${obj.flow_etc},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','2')"><c:if test="${empty obj.flow_etc}">0</c:if>
										<c:if test="${not empty obj.flow_etc}">${obj.flow_etc}</c:if></a>
										</td>
										<td>
										<a href="javascript:detailList(${obj.flow_plate},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','0')"><c:if test="${empty obj.flow_plate}">0</c:if>
										<c:if test="${not empty obj.flow_plate}">${obj.flow_plate}</c:if></a>
										</td>
										<td>
										<a href="javascript:detailList(${obj.flow_manual},'${obj.park_id}','${obj.area_id}','${obj.lane_id}','${obj.lane_name}','${obj.operator}','${obj.operator_name}','${obj.statistics_date}','9')"><c:if test="${empty obj.flow_manual}">0</c:if>
										<c:if test="${not empty obj.flow_manual}">${obj.flow_manual}</c:if></a>
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
	
	$(function(){
	tool.selOption("query_lane_id", '${queryParam.query_lane_id}');
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
          } 
          
    function detailList(flow,park_id,area_id,lane_id,lane_name,operator,operator_name,statistics_date,pay_method)
	{
		if(flow == 0)
		{	
			tool.alert("无车辆记录！");
			return;
			
		}
		$("#park_id").val(park_id);
		$("#area_id").val(area_id);
		$("#lane_id").val(lane_id);
		$("#lane_name").val(lane_name);
		$("#operator").val(operator);
		$("#operator_name").val(operator_name);
		$("#date").val(statistics_date);
		$("#pay_method").val(pay_method);
		$("#pageForm").attr("action","detailList.shtml");
		tool.formSubmit();
	}
	
		function search(){
			$("#pageForm").attr("action","list.shtml");
			tool.formSubmit();
		}
	</script>
</body>
</html>