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
					<label for="recordno" class="col-sm-2 control-label">recordno<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="recordno" name="recordno" value="${bean.recordno}" type="text" 
							class="form-control" placeholder="请输入recordno" reqMsg="请输入recordno" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="cardnetwork" class="col-sm-2 control-label"> cardnetwork<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cardnetwork" name="cardnetwork" value="${bean.cardnetwork}" type="text" 
							class="form-control" placeholder="请输入cardnetwork" reqMsg="请输入cardnetwork" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="cardid" class="col-sm-2 control-label"> cardid<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="cardid" name="cardid" value="${bean.cardid}" type="text" 
							class="form-control" placeholder="请输入cardid" reqMsg="请输入cardid" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="parkid" class="col-sm-2 control-label"> parkid<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="parkid" name="parkid" value="${bean.parkid}" type="text" 
							class="form-control" placeholder="请输入parkid" reqMsg="请输入parkid" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="areaid" class="col-sm-2 control-label"> areaid<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="areaid" name="areaid" value="${bean.areaid}" type="text" 
							class="form-control" placeholder="请输入areaid" reqMsg="请输入areaid" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="entrystation" class="col-sm-2 control-label"> entrystation<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="entrystation" name="entrystation" value="${bean.entrystation}" type="text" 
							class="form-control" placeholder="请输入entrystation" reqMsg="请输入entrystation" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="entrylane" class="col-sm-2 control-label"> entrylane<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="entrylane" name="entrylane" value="${bean.entrylane}" type="text" 
							class="form-control" placeholder="请输入entrylane" reqMsg="请输入entrylane" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="entrytime" class="col-sm-2 control-label"> entrytime<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="entrytime" name="entrytime" value="${bean.entrytime}" type="text" 
							class="form-control" placeholder="请输入entrytime" reqMsg="请输入entrytime" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="entryoperator" class="col-sm-2 control-label"> entryoperator<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="entryoperator" name="entryoperator" value="${bean.entryoperator}" type="text" 
							class="form-control" placeholder="请输入entryoperator" reqMsg="请输入entryoperator" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="entryshift" class="col-sm-2 control-label"> entryshift<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="entryshift" name="entryshift" value="${bean.entryshift}" type="text" 
							class="form-control" placeholder="请输入entryshift" reqMsg="请输入entryshift" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="autovlicense" class="col-sm-2 control-label"> autovlicense<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="autovlicense" name="autovlicense" value="${bean.autovlicense}" type="text" 
							class="form-control" placeholder="请输入autovlicense" reqMsg="请输入autovlicense" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="mvlicense" class="col-sm-2 control-label"> mvlicense<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="mvlicense" name="mvlicense" value="${bean.mvlicense}" type="text" 
							class="form-control" placeholder="请输入mvlicense" reqMsg="请输入mvlicense" 
							autocomplete="off"/>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="imagepath" class="col-sm-2 control-label">imagepath<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="imagepath" name="imagepath" value="${bean.imagepath}" type="text" 
							class="form-control" placeholder="请输入imagepath" reqMsg="请输入imagepath" 
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
			var recordno=$("#recordno").val();
			
			var cardnetwork=$("#cardnetwork").val();
			var cardid=$("#cardid").val();
			var parkid=$("#parkid").val();
			var areaid=$("#areaid").val();
			
			var entrystation=$("#entrystation").val();
			var entrylane=$("#entrylane").val();
			var entrytime=$("#entrytime").val();
			var entryoperator=$("#entryoperator").val();
			
			var entryshift=$("#entryshift").val();
			var autovlicense=$("#autovlicense").val();
			var mvlicense=$("#mvlicense").val();
			var imagepath=$("#imagepath").val();
			
			
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
							'recordno':recordno,
				
							'cardnetwork':cardnetwork,
							'cardid':cardid,
							'parkid':parkid,
							'areaid':areaid,
				
							'entrystation':entrystation,
							'entrylane':entrylane,
							'entrytime':entrytime,
							'entryoperator':entryoperator,
				
							'entryshift':entryshift,
							'autovlicense':autovlicense,
							'mvlicense':mvlicense,
							'imagepath':imagepath
				
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