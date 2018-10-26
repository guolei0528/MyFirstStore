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
						本地黑名单管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>作废时间</td>
								<td>
									<input type="text" id="query_cancel_time_from" name="query_cancel_time_from" value="${queryParam.query_cancel_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
							<input type="text" id="query_cancel_time_to" name="query_cancel_time_to" value="${queryParam.query_cancel_time_to}" onfocus="WdatePicker()">
								</td>
								<td>录入时间</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_from" value="${queryParam.query_in_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_to" value="${queryParam.query_in_time_to}" onfocus="WdatePicker()">
								</td>
								<td>黑名单类型</td>
								<td>
									<select name="query_b_list_type" id="query_b_list_type">
										<option></option>
										<option value="1">欠费</option>
										<option value="2">逃票</option>
									</select>
								</td>
								
								<td>
									<button class="btn btn-primary" type="button" onclick="search()">查询</button>
								</td>
								<td><button class="btn btn-success" onclick="daochu()">导出</button></td>
								<td>
								<label for="file" class="btn btn-info">导入</label>
								
								</td>
								<td>
								<label class="btn btn-warning" onclick="tool.goUrl('downloadModule.shtml')">模板</label>
								
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
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
								<th>车牌号</th>
								<th>车牌颜色</th>
								<th>录入时间</th>
								<th>废弃时间</th>
								<th>黑名单类型</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${obj.mv_license}</td>
									<td>
										<c:choose>
											<c:when test="${obj.plate_color==0 }">蓝牌</c:when>
											<c:when test="${obj.plate_color==1 }">黄牌</c:when>
											<c:when test="${obj.plate_color==2 }">黑牌</c:when>
											<c:when test="${obj.plate_color==3 }">白牌</c:when>
											<c:otherwise>颜色未知</c:otherwise>
										</c:choose>
									</td>
									<td><fmt:formatDate value="${obj.in_time}" type="both"/></td>
									<td><fmt:formatDate value="${obj.cancel_time }" type="both"/></td>
									<td>
									<c:choose>
										<c:when test="${obj.b_list_type==1}">欠费</c:when>
										<c:when test="${obj.b_list_type==2}">逃票</c:when>
									</c:choose>
									</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?mv_license=${obj.mv_license }&plate_color=${obj.plate_color }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
											<li title="修改"><a href="javascript:tool.dialog('edit.shtml?mv_license=${obj.mv_license }&plate_color=${obj.plate_color }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
											<li title="删除"><a href="javascript:deleteData('${obj.mv_license }','${obj.plate_color }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li>
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
							tool.confirm("导入成功，是否刷新页面",function(res){
								if(res){
									location.reload();
								}
								
							});
						}else{
							$("#file").attr("value",'');
							tool.alert("黑名单类型可能存在非法参数，请核实");
						}
					}
				});
			}
		);
		
	});
		function deleteData(dataId,plate_color){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'delete.shtml',
						data:{"mv_license":dataId,'plate_color':plate_color},
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
		function test1(){
			var start = new Date();
			$.ajax({
				url:'${contentPath}/valid/test.shtml',
				dataType:'json',
				type:'post',
				success:function(data){
					console.log(data);
					console.log(new Date()-start);
				}
				
			});
			
		}
		
		function test2(){
		var start = new Date();
			$.ajax({
				url:'${contentPath}/valid/test2.shtml',
				dataType:'json',
				type:'post',
				success:function(data){
					console.log(data);
					console.log(new Date()-start);
				}
				
			});
			
		}

		function test3(){
			$.ajax({
				url:'${contentPath}/valid/test3.shtml',
				dataType:'json',
				type:'post',
				success:function(data){
					console.log(data);
				}
				
			});
			
		}
		
		function test4(){
			$.ajax({
				url:'${contentPath}/valid/test4.shtml',
				dataType:'json',
				type:'post',
				success:function(data){
					console.log(data);
				}
				
			});
			
		}
		
		function search(){
			$("#pageForm").attr("action","list.shtml");
			tool.formSubmit();
		}
	</script>
</body>
</html>