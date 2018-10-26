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
					<label for="begin_time" class="col-sm-2 control-label">begin_time<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="begin_time" name="begin_time" value="${bean.begin_time}" type="text" 
							class="form-control" placeholder="请输入begin_time" reqMsg="请输入begin_time" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="date" class="col-sm-2 control-label">date<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="date" name="date" value="${bean.date}" type="text" 
							class="form-control" placeholder="请输入date" reqMsg="请输入date" 
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
					<label for="operator_id" class="col-sm-2 control-label">operator_id<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="operator_id" name="operator_id" value="${bean.operator_id}" type="text" 
							class="form-control" placeholder="请输入operator_id" reqMsg="请输入operator_id" 
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
					<label for="exit_shift" class="col-sm-2 control-label">exit_shift<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="exit_shift" name="exit_shift" value="${bean.exit_shift}" type="text" 
							class="form-control" placeholder="请输入exit_shift" reqMsg="请输入exit_shift" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="real_bill" class="col-sm-2 control-label">real_bill<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="real_bill" name="real_bill" value="${bean.real_bill}" type="text" 
							class="form-control" placeholder="请输入real_bill" reqMsg="请输入real_bill" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="pay_bill" class="col-sm-2 control-label">pay_bill<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="pay_bill" name="pay_bill" value="${bean.pay_bill}" type="text" 
							class="form-control" placeholder="请输入pay_bill" reqMsg="请输入pay_bill" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="long_bill" class="col-sm-2 control-label">
long_bill<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="long_bill" name="long_bill" value="${bean.long_bill}" type="text" 
							class="form-control" placeholder="请输入long_bill" reqMsg="请输入long_bill" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="invoice_count" class="col-sm-2 control-label">invoice_count<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="invoice_count" name="invoice_count" value="${bean.invoice_count}" type="text" 
							class="form-control" placeholder="请输入invoice_count" reqMsg="请输入invoice_count" 
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
			   
				<div class="form-group">
					<label for="spare4" class="col-sm-2 control-label">spare4<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="spare4" name="spare4" value="${bean.spare4}" type="text" 
							class="form-control" placeholder="请输入spare4" reqMsg="请输入spare4" 
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
			var begin_time=$("#begin_time").val();
			
			var date=$("#date").val();
			var user_id=$("#user_id").val();
			var operator_id=$("#operator_id").val();
			var park_id=$("#park_id").val();
			
			var area_id=$("#area_id").val();
			var lane_id=$("#lane_id").val();
			var exit_shift=$("#exit_shift").val();
			var real_bill=$("#real_bill").val();
			
			var pay_bill=$("#pay_bill").val();
			var long_bill=$("#long_bill").val();
			var invoice_count=$("#invoice_count").val();
			var spare1=$("#spare1").val();
			
			var spare2=$("#spare2").val();
			var spare3=$("#spare3").val();
			var spare4=$("#spare4").val();
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
							'begin_time':begin_time,
				
							'date':date,
							'user_id':user_id,
							'operator_id':operator_id,
							'park_id':park_id,
				
							'area_id':area_id,
							'lane_id':lane_id,
							'exit_shift':exit_shift,
							'real_bill':real_bill,
				
							'pay_bill':pay_bill,
							'long_bill':long_bill,
							'invoice_count':invoice_count,
							'spare1':spare1,
				
							'spare2':spare2,
							'spare3':spare3,
							'spare4':spare4,
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