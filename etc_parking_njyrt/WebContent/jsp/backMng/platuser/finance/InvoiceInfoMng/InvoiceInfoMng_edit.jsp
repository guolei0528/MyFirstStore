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
					<label for="sn" class="col-sm-2 control-label">sn<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="sn" name="sn" value="${bean.sn}" type="text" 
							class="form-control" placeholder="请输入sn" reqMsg="请输入sn" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="code" class="col-sm-2 control-label">code<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="code" name="code" value="${bean.code}" type="text" 
							class="form-control" placeholder="请输入code" reqMsg="请输入code" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="begin_number" class="col-sm-2 control-label">begin_number<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="begin_number" name="begin_number" value="${bean.begin_number}" type="text" 
							class="form-control" placeholder="请输入begin_number" reqMsg="请输入begin_number" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="end_number" class="col-sm-2 control-label">end_number<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="end_number" name="end_number" value="${bean.end_number}" type="text" 
							class="form-control" placeholder="请输入end_number" reqMsg="请输入end_number" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="flag" class="col-sm-2 control-label">flag<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="flag" name="flag" value="${bean.flag}" type="text" 
							class="form-control" placeholder="请输入flag" reqMsg="请输入flag" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="count" class="col-sm-2 control-label">count<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="count" name="count" value="${bean.count}" type="text" 
							class="form-control" placeholder="请输入count" reqMsg="请输入count" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="user_id" class="col-sm-2 control-label">user_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="user_id" name="user_id" value="${bean.user_id}" type="text" 
							class="form-control" placeholder="请输入user_id" reqMsg="请输入user_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="begin_time" class="col-sm-2 control-label">begin_time<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="begin_time" name="begin_time" value="${bean.begin_time}" type="text" 
							class="form-control" placeholder="请输入begin_time" reqMsg="请输入begin_time" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="cancel_record" class="col-sm-2 control-label">cancel_record<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cancel_record" name="cancel_record" value="${bean.cancel_record}" type="text" 
							class="form-control" placeholder="请输入cancel_record" reqMsg="请输入cancel_record" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="cancel_time" class="col-sm-2 control-label">cancel_time<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cancel_time" name="cancel_time" value="${bean.cancel_time}" type="text" 
							class="form-control" placeholder="请输入cancel_time" reqMsg="请输入cancel_time" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="cancel_user_id" class="col-sm-2 control-label">cancel_user_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cancel_user_id" name="cancel_user_id" value="${bean.cancel_user_id}" type="text" 
							class="form-control" placeholder="请输入cancel_user_id" reqMsg="请输入cancel_user_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-2 control-label">s_comment<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text" 
							class="form-control" placeholder="请输入s_comment" reqMsg="请输入s_comment" 
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
			var sn=$("#sn").val();
			
			var code=$("#code").val();
			var begin_number=$("#begin_number").val();
			var end_number=$("#end_number").val();
			var flag=$("#flag").val();
			
			var count=$("#count").val();
			var user_id=$("#user_id").val();
			var begin_time=$("#begin_time").val();
			var cancel_record=$("#cancel_record").val();
			
			var cancel_time=$("#cancel_time").val();
			var cancel_user_id=$("#cancel_user_id").val();
			var s_comment=$("#s_comment").val();
			
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
							'sn':sn,
				
							'code':code,
							'begin_number':begin_number,
							'end_number':end_number,
							'flag':flag,
				
							'count':count,
							'user_id':user_id,
							'begin_time':begin_time,
							'cancel_record':cancel_record,
				
							'cancel_time':cancel_time,
							'cancel_user_id':cancel_user_id,
							's_comment':s_comment
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