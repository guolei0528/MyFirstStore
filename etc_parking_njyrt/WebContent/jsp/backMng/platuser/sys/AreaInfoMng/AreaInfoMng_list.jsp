<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	</head>
				<div class="box-header">
					<div class="col-sm-6">
						片区管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('${contentPath }/backMng/platuser/sys/AreaInfoMng/add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td nowrap="nowrap">片区编号</td>
								<td>
									<input type="text" id="QUERY_AREA_ID" name="QUERY_AREA_ID" value="${queryParam.QUERY_AREA_ID}" placeholder="" autocomplete="off">
								</td>
								<td nowrap="nowrap">片区名称</td>
								<td>
									<input type="text" id="QUERY_AREA_NAME" name="QUERY_AREA_NAME" value="${queryParam.QUERY_AREA_NAME}" placeholder="" autocomplete="off">
								</td>
<!-- 								<td nowrap="nowrap">服务器IP</td> 
								<td>
								<input type="text" id="QUERY_SERVER_IP" name="QUERY_SERVER_IP" value="${queryParam.QUERY_SERVER_IP}" placeholder="" autocomplete="off">
								</td>-->
								<td>
									<button class="btn btn-primary" type="button" onclick="query()">查询</button>
								</td>
							</tr>
<!-- 							<tr> 
								<td nowrap="nowrap">数据库登录名</td>
								<td>
								<input type="text" id="QUERY_DB_NAME" name="QUERY_DB_NAME" value="${queryParam.QUERY_DB_NAME}" placeholder="" autocomplete="off">
								</td>
							</tr>-->
						</table>
					
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped">
		<tr><th>片区编号</th><th>片区名称</th><th>停车位数量</th>
		<th>收费后免费停车（时:分:秒）</th>
<!-- 		<th>服务器IP</th><th>数据库用户名</th><th>密码</th><th>图片存储路径</th><th>图片存储方式</th><th>操作</th> -->
<th>操作</th>
		</tr>
		<c:forEach items="${list }" var="obj">
			<tr>
			<td>${obj.area_id }</td>
			<td>${obj.area_name }</td>
			<td>${obj.space_number }</td>
			<td>${obj.pay_free_time }
			</td>
<%-- 			<td>${obj.server_ip }</td> 
			<td>${obj.db_name }</td>
			<td>${obj.db_password }</td>
			<td>${obj.picture_addr }</td>
			<td><c:choose >
				<c:when test="${obj.picture_type==0 }">数据库存储</c:when>
				<c:when test="${obj.picture_type==1 }">目录存储</c:when>
				<c:when test="${obj.picture_type==2 }">数据库+目录存储</c:when>
			</c:choose></td>--%>
			<td><ul class="list-inline">
						<li title="查看"><a href="javascript:tool.dialog('${contentPath }/backMng/platuser/sys/AreaInfoMng/view.shtml?ID=${obj.area_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li>
						<li title="修改"><a href="javascript:tool.dialog('${contentPath }/backMng/platuser/sys/AreaInfoMng/edit.shtml?ID=${obj.area_id }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
						<li title="删除"><a href="javascript:deleteData('${obj.area_id }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li>
				</ul>
			</td>
			</tr>
		</c:forEach>					
	</table>
	<input type="hidden" id="PARK_ID" value="${queryParam.park_id }">
				</div>
			
			
		<!-- /.content -->
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	
	<script type="text/javascript">
// 		console.log($("#box"));
		function query(){
			var  QUERY_AREA_ID=$("#QUERY_AREA_ID").val();
			var  QUERY_AREA_NAME=$("#QUERY_AREA_NAME").val();
			var  QUERY_SERVER_IP=$("#QUERY_SERVER_IP").val();
			var  QUERY_DB_NAME=$("#QUERY_DB_NAME").val();
// 			$("#box").html('');
			var url='${contentPath}/backMng/platuser/sys/AreaInfoMng/list.shtml?';
			url+='QUERY_AREA_ID='+QUERY_AREA_ID+'&QUERY_AREA_NAME='+QUERY_AREA_NAME;
			url+='&QUERY_SERVER_IP='+QUERY_SERVER_IP+'&QUERY_DB_NAME'+QUERY_DB_NAME;
			$("#box").load(url);
			
		}
		function deleteData(dataId){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'${contentPath }/backMng/platuser/sys/AreaInfoMng/delete.shtml',
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
