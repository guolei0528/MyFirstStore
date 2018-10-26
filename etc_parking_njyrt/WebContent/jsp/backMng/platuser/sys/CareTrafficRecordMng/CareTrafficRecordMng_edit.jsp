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
			<input type="hidden" name="ID" value="${bean.ID }">
             <div class="box-body">
				<div class="form-group">
					<label for="traffic_date" class="col-sm-2 control-label">traffic_date<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="traffic_date" name="traffic_date" value="${bean.traffic_date}" type="text" 
							class="form-control" placeholder="请输入traffic_date" reqMsg="请输入traffic_date" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="park_id" class="col-sm-2 control-label">park_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="park_id" name="park_id" value="${bean.park_id}" type="text" 
							class="form-control" placeholder="请输入park_id" reqMsg="请输入park_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="area_id" class="col-sm-2 control-label">area_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="area_id" name="area_id" value="${bean.area_id}" type="text" 
							class="form-control" placeholder="请输入area_id" reqMsg="请输入area_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="lane_id" class="col-sm-2 control-label">lane_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="lane_id" name="lane_id" value="${bean.lane_id}" type="text" 
							class="form-control" placeholder="请输入lane_id" reqMsg="请输入lane_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="lane_flag" class="col-sm-2 control-label">lane_flag<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="lane_flag" name="lane_flag" value="${bean.lane_flag}" type="text" 
							class="form-control" placeholder="请输入lane_flag" reqMsg="请输入lane_flag" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="care_traffic" class="col-sm-2 control-label">care_traffic<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="care_traffic" name="care_traffic" value="${bean.care_traffic}" type="text" 
							class="form-control" placeholder="请输入care_traffic" reqMsg="请输入care_traffic" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="pay_method" class="col-sm-2 control-label">pay_method<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="pay_method" name="pay_method" value="${bean.pay_method}" type="text" 
							class="form-control" placeholder="请输入pay_method" reqMsg="请输入pay_method" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="member_type" class="col-sm-2 control-label">member_type<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="member_type" name="member_type" value="${bean.member_type}" type="text" 
							class="form-control" placeholder="请输入member_type" reqMsg="请输入member_type" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="datetime" class="col-sm-2 control-label">
datetime<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="datetime" name="datetime" value="${bean.datetime}" type="text" 
							class="form-control" placeholder="请输入datetime" reqMsg="请输入datetime" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="comment" class="col-sm-2 control-label">comment<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="comment" name="comment" value="${bean.comment}" type="text" 
							class="form-control" placeholder="请输入comment" reqMsg="请输入comment" 
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
		//tool.selRadio('DOM_ID','${DOM_VALUE}');
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=radioVal('DOM_NAME');
			var traffic_date=$("#traffic_date").val();
			
			var park_id=$("#park_id").val();
			var area_id=$("#area_id").val();
			var lane_id=$("#lane_id").val();
			var lane_flag=$("#lane_flag").val();
			
			var care_traffic=$("#care_traffic").val();
			var pay_method=$("#pay_method").val();
			var member_type=$("#member_type").val();
			var datetime=$("#datetime").val();
			
			var comment=$("#comment").val();
			
			if(vcheck.empty()){
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
							'ID':'${bean.ID}',
							'traffic_date':traffic_date,
				
							'park_id':park_id,
							'area_id':area_id,
							'lane_id':lane_id,
							'lane_flag':lane_flag,
				
							'care_traffic':care_traffic,
							'pay_method':pay_method,
							'member_type':member_type,
							'datetime':datetime,
				
							'comment':comment
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
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