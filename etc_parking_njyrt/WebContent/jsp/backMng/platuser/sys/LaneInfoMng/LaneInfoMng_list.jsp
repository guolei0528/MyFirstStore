<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

				<div class="box-header">
					<div class="col-sm-6">
						道口管理
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('${contentPath }/backMng/platuser/sys/LaneInfoMng/add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
					<table class="queryTable">
							<tr>
								<td nowrap="nowrap">道口编号</td>
								<td>
									<input type="text" id="QUERY_LANE_ID" name="QUERY_LANE_ID" value="${queryParam.QUERY_LANE_ID}" placeholder="" autocomplete="off">
								</td>
								<td nowrap="nowrap">道口类型</td>
								<td>
								<select id="QUERY_LANE_FLAG" name="QUERY_LANE_FLAG" >
									<option></option>
									<option value="1">入口</option>
									<option value="2">出口</option>
									<option value="3">集中收费</option> 
								</select>
								</td>
								<td>
									<button class="btn btn-primary" type="button" onclick="query()">查询</button>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped">
		<tr><th>道口编号</th><th>道口名称</th><th>所属片区</th><th>道口类型</th><th>操作</th></tr>
		<c:forEach items="${list }" var="obj">
			<tr>
			<td>${obj.lane_id }</td>
			<td>${obj.lane_name }</td>
			<td>${obj.area_name }</td>
			<td>
				<c:choose>
					<c:when test="${obj.lane_flag==1 }">入口</c:when>
					<c:when test="${obj.lane_flag==2 }">出口</c:when>
					<c:when test="${obj.lane_flag==3 }">集中收费</c:when> 
				</c:choose>
			</td>
			<td><ul class="list-inline">
<%-- 					<li title="查看"><a href="javascript:tool.dialog('${contentPath }/backMng/platuser/sys/LaneInfoMng/view.shtml?ID=${obj.lane_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li> --%>
						<li title="修改"><a href="javascript:tool.dialog('${contentPath }/backMng/platuser/sys/LaneInfoMng/edit.shtml?ID=${obj.lane_id }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
						<li title="删除"><a href="javascript:deleteData('${obj.lane_id }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li>
				</ul>
			</td>
			</tr>
		</c:forEach>					
	</table>
	<input type="hidden" id="PARK_ID" value="${queryParam.park_id }">
	<input type="hidden" id="AREA_ID" value="${queryParam.area_id }">
				</div>
				<!-- /.box-body -->
			<!-- /.box -->
			
			
		<!-- /.content -->
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	
	<script type="text/javascript">
		function deleteData(dataId){
			tool.confirm("确实要删除该条目吗？",function(result){
				if(result){
					$.ajax({
						type:'post',
						url:'${contentPath }/backMng/platuser/sys/LaneInfoMng/delete.shtml',
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
		function query(){
			var QUERY_LANE_ID=$("#QUERY_LANE_ID").val();
			var QUERY_LANE_FLAG=$("#QUERY_LANE_FLAG").val();
			var url='${contentPath}/backMng/platuser/sys/LaneInfoMng/list.shtml?';
			url+='QUERY_LANE_ID='+QUERY_LANE_ID+'&QUERY_LANE_FLAG='+QUERY_LANE_FLAG;
			$("#box").load(url);
		}
	</script>
