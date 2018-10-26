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
		<form action="sreachList.shtml" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						当前出口流量检索
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
								<td>出口车道号</td>
								<td>
								<input type="text" name="query_lane_id" value="${queryParam.query_lane_id }">
								</td>
								
								<td>进入停车场开始日期</td>
								<td>
									<input type="text" id="query_entry_time_from" name="query_entry_time_from" value="${queryParam.query_entry_time_from}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })">
								</td>
								<td>进入停车场结束日期</td>
								<td>
									<input type="text" id="query_entry_time_to" name="query_entry_time_to" value="${queryParam.query_entry_time_to}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })">
								</td>
								
								
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>
							<tr>
								<td>离开停车场开始日期</td>
								<td>
									<input type="text" id="query_exit_time_from" name="query_exit_time_from" value="${queryParam.query_exit_time_from}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })">
								</td>
								<td>离开停车场结束日期</td>
								<td>
									<input type="text" id="query_exit_time_to" name="query_exit_time_to" value="${queryParam.query_exit_time_to}" placeholder="" autocomplete="off" onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })">
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
								<th>记录编号</th>
<!-- 								<th>停车场编号</th> -->
<!-- 								<th>片区编号</th> -->
								<th>停车场名称</th>
								<th>片区名称</th>
<!-- 								<th>出口站编号</th> -->
                                <th>入口车道编号</th>
                                <th>入口时间</th>
								<th>出口车道编号</th>
								<th>出口时间</th>
<!-- 								<th>entryoperator</th> -->
<!-- 								<th>entryshift</th> -->
								<th>车牌号</th>
								<th>应收金额</th>
								<th>实收金额</th>
								<th>道口图片</th>
							</tr>	
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${obj.recordno}</td>
<%-- 									<td>${obj.parkid}</td> --%>
<%-- 									<td>${obj.areaid}</td> --%>
									<td>${obj.park_name}</td>
									<td>${obj.area_name}</td>
									<td>${obj.entrylane}</td>
<%-- 									<td>${obj.entrystation}</td> --%>
									<td>${obj.entrytime}</td>
									<td>${obj.exitlane}</td>
									<td>${obj.exittime}</td>
<%-- 									<td>${obj.entryoperator}</td> --%>
<%-- 									<td>${obj.entryshift}</td> --%>
									<td>${obj.mvlicense}</td>
									<td>${obj.pdiscounttoll}</td>
									<td>${obj.totaltoll}</td>
									<td>
									<ul class="list-inline">
										<li title="查看"><a href="javascript:tool.dialog('view.shtml?exittime=${fn:replace(obj.exittime," ", ",")}&exitlane=${obj.exitlane }&mvlicense=${obj.mvlicense }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
<%-- 										<li title="查看图片"><a class="btn btn-primary" data-toggle="modal" type="button" data-target=".bs-example-modal-lg" onclick="explore(this)" dktp="/img/${obj.exitlane}/${obj.exitdate}/${obj.imagepath}"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看图片</a></li> --%>
									</ul>

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
			<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		  <div class="modal-dialog modal-lg" role="document">
		    <div class="modal-content" id="dktp">
		      <img width="100%">
		    </div>
		  </div>
		</div>
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