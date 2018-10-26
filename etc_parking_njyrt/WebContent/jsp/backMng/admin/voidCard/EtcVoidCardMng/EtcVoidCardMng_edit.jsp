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
					<label for="card_type" class="col-sm-2 control-label">card_type<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="card_type" name="card_type" value="${bean.card_type}" type="text" 
							class="form-control" placeholder="请输入card_type" reqMsg="请输入card_type" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="card_network" class="col-sm-2 control-label">card_network<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="card_network" name="card_network" value="${bean.card_network}" type="text" 
							class="form-control" placeholder="请输入card_network" reqMsg="请输入card_network" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="card_id" class="col-sm-2 control-label">card_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="card_id" name="card_id" value="${bean.card_id}" type="text" 
							class="form-control" placeholder="请输入card_id" reqMsg="请输入card_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="card_status" class="col-sm-2 control-label">card_status<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="card_status" name="card_status" value="${bean.card_status}" type="text" 
							class="form-control" placeholder="请输入card_status" reqMsg="请输入card_status" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="status_type" class="col-sm-2 control-label">status_type<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="status_type" name="status_type" value="${bean.status_type}" type="text" 
							class="form-control" placeholder="请输入status_type" reqMsg="请输入status_type" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="issuer_id" class="col-sm-2 control-label">issuer_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="issuer_id" name="issuer_id" value="${bean.issuer_id}" type="text" 
							class="form-control" placeholder="请输入issuer_id" reqMsg="请输入issuer_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="electronic_id" class="col-sm-2 control-label">electronic_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="electronic_id" name="electronic_id" value="${bean.electronic_id}" type="text" 
							class="form-control" placeholder="请输入electronic_id" reqMsg="请输入electronic_id" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="mv_license" class="col-sm-2 control-label">
mv_license<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="mv_license" name="mv_license" value="${bean.mv_license}" type="text" 
							class="form-control" placeholder="请输入mv_license" reqMsg="请输入mv_license" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="mv_license_colour" class="col-sm-2 control-label">mv_license_colour<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="mv_license_colour" name="mv_license_colour" value="${bean.mv_license_colour}" type="text" 
							class="form-control" placeholder="请输入mv_license_colour" reqMsg="请输入mv_license_colour" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="intime" class="col-sm-2 control-label">intime<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="intime" name="intime" value="${bean.intime}" type="text" 
							class="form-control" placeholder="请输入intime" reqMsg="请输入intime" 
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
					<label for="issuer_version" class="col-sm-2 control-label">issuer_version<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="issuer_version" name="issuer_version" value="${bean.issuer_version}" type="text" 
							class="form-control" placeholder="请输入issuer_version" reqMsg="请输入issuer_version" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="ban_type" class="col-sm-2 control-label">ban_type<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="ban_type" name="ban_type" value="${bean.ban_type}" type="text" 
							class="form-control" placeholder="请输入ban_type" reqMsg="请输入ban_type" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="issuer_effective_time" class="col-sm-2 control-label">
issuer_effective_time<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="issuer_effective_time" name="issuer_effective_time" value="${bean.issuer_effective_time}" type="text" 
							class="form-control" placeholder="请输入issuer_effective_time" reqMsg="请输入issuer_effective_time" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="valid_flag" class="col-sm-2 control-label">valid_flag<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="valid_flag" name="valid_flag" value="${bean.valid_flag}" type="text" 
							class="form-control" placeholder="请输入valid_flag" reqMsg="请输入valid_flag" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="valid_time" class="col-sm-2 control-label">valid_time<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="valid_time" name="valid_time" value="${bean.valid_time}" type="text" 
							class="form-control" placeholder="请输入valid_time" reqMsg="请输入valid_time" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="lock_card" class="col-sm-2 control-label">lock_card<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="lock_card" name="lock_card" value="${bean.lock_card}" type="text" 
							class="form-control" placeholder="请输入lock_card" reqMsg="请输入lock_card" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="contact_info" class="col-sm-2 control-label">contact_info<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="contact_info" name="contact_info" value="${bean.contact_info}" type="text" 
							class="form-control" placeholder="请输入contact_info" reqMsg="请输入contact_info" 
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
			   
				<div class="form-group">
					<label for="version" class="col-sm-2 control-label">version
<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="version" name="version" value="${bean.version}" type="text" 
							class="form-control" placeholder="请输入version" reqMsg="请输入version" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="spare1" class="col-sm-2 control-label">spare1<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="spare1" name="spare1" value="${bean.spare1}" type="text" 
							class="form-control" placeholder="请输入spare1" reqMsg="请输入spare1" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="spare2" class="col-sm-2 control-label">spare2<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="spare2" name="spare2" value="${bean.spare2}" type="text" 
							class="form-control" placeholder="请输入spare2" reqMsg="请输入spare2" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="spare3" class="col-sm-2 control-label">spare3<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="spare3" name="spare3" value="${bean.spare3}" type="text" 
							class="form-control" placeholder="请输入spare3" reqMsg="请输入spare3" 
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
			var card_type=$("#card_type").val();
			
			var card_network=$("#card_network").val();
			var card_id=$("#card_id").val();
			var card_status=$("#card_status").val();
			var status_type=$("#status_type").val();
			
			var issuer_id=$("#issuer_id").val();
			var electronic_id=$("#electronic_id").val();
			var mv_license=$("#mv_license").val();
			var mv_license_colour=$("#mv_license_colour").val();
			
			var intime=$("#intime").val();
			var cancel_time=$("#cancel_time").val();
			var issuer_version=$("#issuer_version").val();
			var ban_type=$("#ban_type").val();
			
			var issuer_effective_time=$("#issuer_effective_time").val();
			var valid_flag=$("#valid_flag").val();
			var valid_time=$("#valid_time").val();
			var lock_card=$("#lock_card").val();
			
			var contact_info=$("#contact_info").val();
			var s_comment=$("#s_comment").val();
			var version=$("#version").val();
			var spare1=$("#spare1").val();
			
			var spare2=$("#spare2").val();
			var spare3=$("#spare3").val();
			
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
							'card_type':card_type,
				
							'card_network':card_network,
							'card_id':card_id,
							'card_status':card_status,
							'status_type':status_type,
				
							'issuer_id':issuer_id,
							'electronic_id':electronic_id,
							'mv_license':mv_license,
							'mv_license_colour':mv_license_colour,
				
							'intime':intime,
							'cancel_time':cancel_time,
							'issuer_version':issuer_version,
							'ban_type':ban_type,
				
							'issuer_effective_time':issuer_effective_time,
							'valid_flag':valid_flag,
							'valid_time':valid_time,
							'lock_card':lock_card,
				
							'contact_info':contact_info,
							's_comment':s_comment,
							'version':version,
							'spare1':spare1,
				
							'spare2':spare2,
							'spare3':spare3
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