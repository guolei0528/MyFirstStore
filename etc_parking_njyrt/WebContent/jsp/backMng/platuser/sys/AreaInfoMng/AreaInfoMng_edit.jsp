<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">修改</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
			   	<div class="form-group">
					<label for="area_name" class="col-sm-3 control-label">片区编号<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="area_id" name="area_id" value="${bean.area_id}" type="text" 
							class="form-control" placeholder="请输入片区编号" reqMsg="片区编号应该是8位以内大于0的整数" 
							autocomplete="off" disabled="disabled"/>
					</div>
               </div>
				<div class="form-group">
					<label for="area_name" class="col-sm-3 control-label">片区名称<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="area_name" name="area_name" value="${bean.area_name}" type="text" 
							class="form-control" placeholder="请输入片区名称" reqMsg="请输入片区名称" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="space_number" class="col-sm-3 control-label">停车位数量<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="space_number" name="space_number" value="${bean.space_number}" type="number" 
							class="form-control" placeholder="请输入停车位数量" reqMsg="请输入停车位数量" 
							autocomplete="off"/>
					</div>
               </div>
<!-- 			   	<div class="form-group"> 
					<label for="picture_addr" class="col-sm-3 control-label">图片存储路径<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="picture_addr" name="picture_addr" value="${bean.picture_addr}" type="text" 
							class="form-control" placeholder="请输入图片存储路径" reqMsg="请输入图片存储路径" 
							autocomplete="off"/>
					</div>
               </div>
				<div class="form-group">
					<label for="picture_type" class="col-sm-3 control-label">图片存储类型<oak:required></oak:required></label>
					<div class="col-sm-9">
							<select class="form-control" id="picture_type"  name="picture_type" reqMsg="请选择图片存储类型">
								<option value="0">数据库存储</option>
								<option value="1">目录存储</option>
								<option value="2">数据库+目录存储</option>
							</select>
					</div>
               </div>
			   
			
			   
				<div class="form-group">
					<label for="server_ip" class="col-sm-3 control-label">服务器IP<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="server_ip" name="server_ip" value="${bean.server_ip}" type="text" 
							class="form-control" placeholder="请输入服务器IP" reqMsg="请输入服务器IP" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="db_name" class="col-sm-3 control-label">数据库登录名<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="db_name" name="db_name" value="${bean.db_name}" type="text" 
							class="form-control" placeholder="请输入数据库登录名" reqMsg="请输入数据库登录名" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="db_password" class="col-sm-3 control-label">数据库密码<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="db_password" name="db_password" value="${bean.db_password}" type="text" 
							class="form-control" placeholder="请输入数据库密码" reqMsg="请输入数据库密码" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="day_number" class="col-sm-3 control-label">迁移时间<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="day_number" name="day_number" value="${bean.day_number}" type="number" 
							class="form-control" placeholder="默认30天"  reqMsg="请输入最大迁移时间（天）"
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="log_address" class="col-sm-3 control-label">日志保存位置<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="log_address" name="log_address" value="${bean.log_address}" type="text" 
							class="form-control" placeholder="请输入日志保存位置" reqMsg="请输入日志保存位置" 
							autocomplete="off"/>
					</div>
               </div>
			   -->
			   
			   <div class="form-group">
					<label for="pay_free_time" class="col-sm-3 control-label">收费后免费停车（时:分:秒）</label>
					<div class="col-sm-9">
						<input id="pay_free_time" name="pay_free_time" type="text" value="${bean.pay_free_time}"
								class="form-control"
								autocomplete="off" onfocus="WdatePicker({ dateFmt: 'HH:mm:ss' })" />
					</div>
               </div>
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label">备注</label>
					<div class="col-sm-9">
						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text" 
							class="form-control" placeholder="请输入备注" 
							autocomplete="off"/>
					</div>
               </div>
			   
             </div>
          </form>
     </div>
	 
     <div class="modal-footer">
       <button id="modal_save_button" type="button" class="btn btn-primary">保存</button>
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
   </div>
</div>
<script type="text/javascript">
	$(function(){
		tool.selOption('picture_type','${bean.picture_type}');
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			var park_id=$("#park_id").val();
			var area_name=$("#area_name").val();
			var space_number=$("#space_number").val();
			var pay_free_time=$("#pay_free_time").val();
// 			var picture_type=$("#picture_type").val();
// 			var picture_addr=$("#picture_addr").val();
// 			var server_ip=$("#server_ip").val();
// 			var db_name=$("#db_name").val();
// 			var db_password=$("#db_password").val();
// 			var day_number=$("#day_number").val();
// 			var log_address=$("#log_address").val();
			var s_comment=$("#s_comment").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(space_number<0){
				tool.alert("请核实停车位数量");
				return;
			}				
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
				 
					$.ajax({
						type : 'post',
						url : '${contentPath }/backMng/platuser/sys/AreaInfoMng/update.shtml',
						data:{
							'area_id':'${bean.area_id}',
							'park_id':park_id,
							'area_name':area_name,
							'space_number':space_number,
							'pay_free_time':pay_free_time,
// 							'picture_type':picture_type,
				
// 							'picture_addr':picture_addr,
// 							'server_ip':server_ip,
// 							'db_name':db_name,
// 							'db_password':db_password,
				
// 							'day_number':day_number,
// 							'log_address':log_address,
							's_comment':s_comment
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
								$("#pageForm").attr("action",'list.shtml?park_id='+'${bean.park_id}');
								tool.formSubmit();
							}else{
								tool.alert(data.message);
							}
						},
						error : function(data, textStatus) {
							tool.alert('保存失败');
						}

					});
				}
			});
		});
	});
</script>