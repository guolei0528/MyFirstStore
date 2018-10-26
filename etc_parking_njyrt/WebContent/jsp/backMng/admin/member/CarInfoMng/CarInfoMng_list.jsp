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
						车辆信息管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>车辆牌照</td>
								<td>
									<input type="text" id="query_mv_license" name="query_mv_license" value="${queryParam.query_mv_license}" placeholder="" autocomplete="off">
								</td>
								<td>obu编号</td>
								<td>
									<input type="text" id="query_obu_id" name="query_obu_id" value="${queryParam.query_obu_id}" placeholder="" autocomplete="off">
								</td>
								<td>员工编号</td>
								<td>
									<input type="text" id="query_member_id" name="query_member_id" value="${queryParam.query_member_id}" placeholder="" autocomplete="off">
								</td>
<!-- 								<td>车辆颜色</td> 
								<td>
									<input type="text" id="query_car_color" name="query_car_color" value="${queryParam.query_car_color}" placeholder="" autocomplete="off">
								</td>
								<td>车辆类型</td>
								<td>
									<input type="text" id="query_type" name="query_type" value="${queryParam.query_type}" placeholder="" autocomplete="off">
								</td>-->
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
								<th>车辆牌照</th>
								<th>obu编号</th>
								<th>员工编号</th>
								<th>员工姓名</th>
								<th>颜色</th>
								<th>行驶证</th>
								<th>车辆类型</th>
								<th>备注</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody align="center">
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr>
									<td>${page.startRow + status.index+1}</td>
									<td>${obj.mvLicense}</td>
									<td>${empty obj.obuId  ? 0 : obj.obuId}</td>
									<td>
										${obj.memberId }
									</td>
									<td>
										${obj.memberName }
									</td>
									<td>
										${obj.carColor==0?'蓝牌':'黄牌' }
									</td>
									<td>
										${empty obj.license ? '暂无':obj.license }
									</td>
									<td>
										${obj.type==1 || obj.type==2?'小车':'大车' }
									</td>
									<td>
										${empty obj.sComment  ? '无':obj.sComment }
									</td>
									<td>
										<ul class="list-inline">
											<li title="查看"><a href="javascript:tool.dialog('view.shtml?mvLicense=${obj.mvLicense }&carColor=${obj.carColor } ')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
											<li title="修改"><a href="javascript:tool.dialog('edit.shtml?mvLicense=${obj.mvLicense }&carColor=${obj.carColor } ')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
											<li title="删除"><a href="javascript:deleteData('${obj.mvLicense }','${obj.carColor} ')"><i class="fa fa-trash-o text-red"></i> <span class="text-red"></span>删除</a></li>
										</ul>
									</td>
								</tr>
							</c:forEach>
						</tbody>
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
		function deleteData(mvLicense,carColor){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'delete.shtml',
						data:{"mvLicense":mvLicense,
						      "carColor":carColor},
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