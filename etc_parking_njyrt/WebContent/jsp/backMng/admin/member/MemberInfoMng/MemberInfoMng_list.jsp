<!DOCTYPE html >
<%@page import="com.project.common.tool.AppConst"%>
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
		<form action="list.shtml" method="post" id="pageForm">
		<div class="container" style="width: 100%">
			
			<div class="box mt10">
				<div class="box-header">
					<div class="col-sm-6">
						员工基本信息管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>员工编号</td>
								<td>
									<input type="text" id="query_member_id" name="query_member_id" value="${queryParam.query_member_id}" placeholder="" autocomplete="off">
								</td>
								<td>员工姓名</td>
								<td>
									<input type="text" id="query_name" name="query_name" value="${queryParam.query_name}" placeholder="" autocomplete="off">
								</td>
								
								<td>
									<button class="btn btn-primary" type="submit">确定</button>
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
							    <th>序号</th>
								<th>员工编号</th>
								<th>员工姓名</th>
								<th>员工性别</th>
								<th>出生日期</th>
								<th>手机号码</th>
								<th>员工地址</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody align="center">
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${page.startRow + status.index+1}</td>
									<td>${obj.memberId}</td>
									<td>${obj.name}</td>
									<td>
										${obj.sex==1?'男':'女' }
									</td>
									<td>${empty obj.birthday ? '暂无' :obj.birthday}</td>
									<td>${empty obj.phone ? '暂无' :obj.phone}</td>
									<td>
										${empty obj.location ? '暂无' :obj.location}
									</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?memberId=${obj.memberId }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
											<li title="修改"><a href="javascript:tool.dialog('edit.shtml?memberId=${obj.memberId }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
											<li title="删除"><a href="javascript:deleteData('${obj.memberId }')"><i class="fa fa-trash-o text-red"></i> <span class="text-red"></span>删除</a></li>
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
		function deleteData(dataId){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'delete.shtml',
						data:{"memberId":dataId},
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