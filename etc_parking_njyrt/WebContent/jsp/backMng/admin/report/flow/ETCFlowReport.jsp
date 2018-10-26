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
	<script type="text/javascript"
	src="${contentPath }/js/common/ajaxfileupload.js"></script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>
	
	<div id="mainContentDiv" style="margin-left: 180px">

		<!-- Main content -->
		<form action="list.shtml" method="post" id="pageForm" enctype="multipart/form-data">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						ETC流量报表
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>日期</td>
								<td>
									<input type="text" id="query_cancel_time_from" name="query_cancel_time_from" value="${queryParam.query_cancel_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
							<input type="text" id="query_cancel_time_to" name="query_cancel_time_to" value="${queryParam.query_cancel_time_to}">
								</td>
								<td>停车场编号</td>
								<td>
									<input type="text" id="query_park_id" name="query_in_time_to" value="${queryParam.query_in_time_to}">
								</td>
								<td>区域编号</td>
								<td>
									<input type="text" id="query_area_id" name="query_in_time_to" value="${queryParam.query_in_time_to}">
								</td>
								<td>车道</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_from" value="${queryParam.query_in_time_from}">
								</td>
								<td>车道类型</td>
								<td>
									<select name="query_b_list_type" id="query_b_list_type">
										<option></option>
										<option value="1">入口</option>
										<option value="2">出口</option>
									</select>
								</td>
								<td>
									<button class="btn btn-primary" type="button">检索</button>
								</td>
							</tr>
							<tr>
							<td>车牌号</td>
								<td>
									<input type="text" id="query_mv_license" name="query_mv_license" value="${queryParam.query_mv_license}" placeholder="" autocomplete="off">
								</td>
								
							</tr>
						</table>
					</div>
				</div>
				<input type="file" id="file"  name="file" style="display: none;">
				<!-- /.box-header -->
				<div class="box-body" style="overflow:hidden;">
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
								<th>日期</th>
								<th>停车场</th>
								<th>区域</th>
								<th>车道</th>
								<th>车道类型</th>
								<th>本省储值</th>
								<th>外省储值</th>
								<th>本省记账</th>
								<th>外省记账</th>
								<th>总计</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
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
	
	$(function(){
	
	tool.selOption("query_b_list_type",'${queryParam.query_b_list_type}');
		
		$("#file").change(function(){
				$
				.ajaxFileUpload({
					url : 'upload.shtml',
					secureuri : false,
					fileElementId : 'file',
					dataType : 'json',
					success : function(data, status) {
						if(data.success){
							$("#file").attr("value",'');
							location.reload();
						}else{
							tool.alert("黑名单类型可能存在非法参数，请核实");
						}
					}
				});
			}
		);
		
	});
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
								$("#pageForm").attr("action","list.shtml");
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
		function daoru(){
			$("#file").click();
		}
		function daochu(){
			$("#pageForm").attr("action","downLoad.shtml");
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
		function search(){
			$("#pageForm").attr("action","list.shtml");
			tool.formSubmit();
		}
	</script>
</body>
</html>