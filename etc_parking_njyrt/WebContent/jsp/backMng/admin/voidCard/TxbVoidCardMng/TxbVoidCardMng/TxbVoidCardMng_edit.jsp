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
					<label for="mv_license" class="col-sm-2 control-label">车牌号<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="mv_license" name="mv_license" value="${bean.mv_license}" type="text" 
							class="form-control" placeholder="请输入车牌号" reqMsg="请输入车牌号" 
							autocomplete="off" disabled="disabled"/>
					</div>
               </div>
			     <div class="form-group">
					<label for="mv_license" class="col-sm-2 control-label">车牌颜色<oak:required></oak:required></label>
					<div class="col-sm-10">
						<select class="form-control"  id="plate_color" reqMsg="请选择车牌颜色">
							<option></option>
								<option value="0">蓝牌</option>
								<option value="1">黄牌</option>
								<option value="2">黑牌</option>
								<option value="3">白牌</option>
						</select>
					</div>
               </div>
				<div class="form-group">
					<label for="cancel_time" class="col-sm-2 control-label">作废时间<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cancel_time" name="cancel_time" value="<fmt:formatDate value="${bean.cancel_time}" type="both"/>" type="text" 
							class="form-control" placeholder="请选择作废时间" reqMsg="请选择作废时间" 
							autocomplete="off" onfocus="WdatePicker()"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="b_list_type" class="col-sm-2 control-label">黑名单类型<oak:required></oak:required></label>
					<div class="col-sm-10">
						
							<select class="form-control"  id="b_list_type" >
								<option value="0"></option>
								<option value="1">欠费</option>
								<option value="2">逃票</option>
							</select>
					</div>
               </div>
			   
			   
				<div class="form-group">
					<label for="park_id" class="col-sm-2 control-label">停车场<oak:required></oak:required></label>
					<div class="col-sm-10">
						<select id="park_id" class="form-control">
						<option></option>
						<c:forEach items="${parks }" var="park">
						<option value="${park.park_id }">${park.park_name }</option>
						</c:forEach>
						</select>	
					</div>
               </div>
			   
			
			   
<!-- 				<div class="form-group"> -->
<!-- 					<label for="s_comment" class="col-sm-2 control-label">备注</label> -->
<!-- 					<div class="col-sm-10"> -->
<%-- 						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text"  --%>
<!-- 							class="form-control" placeholder="请输入备注"   -->
<!-- 							autocomplete="off"/> -->
<!-- 					</div> -->
<!--                </div> -->
			   
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
		//tool.selRadio('DOM_ID','${DOM_VALUE}');
// 			$("#park_id").change(function(){
// 			$.ajax({
// 				url:'findAreaList.shtml',
// 				type:'post',
// 				dataType:'json',
// 				data:{'park_id':$("#park_id").val()},
// 				success:function(data){
// 					if(data){
// 						$("#area_id")[0].options.length=1;
// 						$(data).each(function(){
// 							$("#area_id").append('<option value="'+this.area_id+'">'+this.area_name+'</option>');
// 						});
// 					}
// 				}
				
// 			});
			
// 		});
		tool.selOption("b_list_type",'${bean.b_list_type}');
		tool.selOption("park_id",'${bean.park_id}');
		tool.selOption("plate_color",'${bean.plate_color}');
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=radioVal('DOM_NAME');
			var mv_license=$("#mv_license").val();
			
			var in_time=$("#in_time").val();
			var cancel_time=$("#cancel_time").val();
			var b_list_type=$("#b_list_type").val();
			var plate_color=$("#plate_color").val();
// 			var user_id=$("#user_id").val();
			var park_id=$("#park_id").val();
// 			var area_id=$("#area_id").val();
			var s_comment=$("#s_comment").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(Date.parse(cancel_time)-new Date().getTime()<0){
				tool.alert("作废时间不能早于当前时间");
				return;
			}
/*			if(vcheck.radioChecked('DOM_NAME','ERROR_MSG')){
				return ;
			}
			var passFlag=true;
			//其他校验
			
			if(passFlag==false){
				return ; 
			}
			*/
							
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
				 
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data:{
							'mv_license':'${bean.mv_license}',
							'in_time':in_time,
							'cancel_time':cancel_time,
							'b_list_type':b_list_type,
							'plate_color':plate_color,
// 							'user_id':user_id,
							'park_id':park_id,
// 							'area_id':area_id,
							's_comment':s_comment
				
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
								$("#pageForm").attr("action","list.shtml");
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