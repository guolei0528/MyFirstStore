<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

			<div class="box-header">
							<div class="col-sm-6">
								<div class="dataTables_info" role="status" aria-live="polite" id="title">停车场设置</div>
							</div>
							<div class="col-sm-6">
								<div class="dataTables_paginate paging_simple_numbers">
									<button id="add" type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>
									新增</button>
								</div>
							</div>
							<div class="col-sm-12 ">
								<input type="hidden" name="PARENT_ID"  autocomplete="off" placeholder="" id="PARENT_ID">
<!-- 								<button class="btn btn-primary btn-large" type="submit">确定</button> -->
							</div>
						</div>
						<!-- /.box-header -->
						<div class="box-body">
							<table id="dataTable" class="table table-bordered table-striped">
					<tr><th>停车场编号</th><th>停车场名称</th><th>地址</th><th>操作</th></tr>
					<c:forEach items="${list }" var="obj">
						<tr>
						<td>${obj.park_id }</td>
						<td>${obj.park_name }</td>
						<td>${obj.park_address }</td>
						<td>
				<ul class="list-inline">
<%-- 						<li title="查看"><a href="javascript:tool.dialog('view.shtml?ID=${obj.park_id }')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li> --%>
						<li title="修改"><a href="javascript:tool.dialog('edit.shtml?ID=${obj.park_id }')"><i class="fa fa-pencil-square-o text-green"></i><span class="text-green"></span>修改</a></li>
						<li title="删除"><a href="javascript:deleteData('${obj.park_id }')"><i class="fa fa-trash-o text-red"></i><span class="text-red"></span>删除</a></li>
				</ul>
			</td>
			</tr>
		</c:forEach>					
	</table>
	</div>
	<script type="text/javascript">
	function deleteData(dataId){
		tool.confirm('将同时删除对应的片区！确认删除吗？',function(res){
			
			if(res){
				
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
						alert("删除失败！");
					}
				});
			}
			
		});
		
	
	}
		
	</script>
	
	