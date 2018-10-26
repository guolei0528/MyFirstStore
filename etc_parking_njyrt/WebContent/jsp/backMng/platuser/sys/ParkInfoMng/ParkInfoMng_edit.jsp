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
					<label for="park_name" class="col-sm-3 control-label">停车场名称<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="park_name" name="park_name" value="${bean.park_name}" type="text" 
							class="form-control" placeholder="请输入停车场名称" reqMsg="请输入停车场名称" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="park_address" class="col-sm-3 control-label">停车场地址</label>
					<div class="col-sm-9">
						<input id="park_address" name="park_address" value="${bean.park_address}" type="text" 
							class="form-control" placeholder="请输入停车场地址" reqMsg="请输入停车场地址" 
							autocomplete="off"/>
					 </div>
               </div>
			   
<!-- 				<div class="form-group"> -->
<!-- 					<label for="spare1" class="col-sm-3 control-label">spare1<oak:required></oak:required></label> -->
<!-- 					<div class="col-sm-9"> -->
<%-- 						<input id="spare1" name="spare1" value="${bean.spare1}" type="text"  --%>
<!-- 							class="form-control" placeholder="请输入spare1" reqMsg="请输入spare1"  -->
<!-- 							autocomplete="off"/> -->
<!-- 					 </div> -->
<!--                </div> -->
			   
<!-- 				<div class="form-group"> -->
<!-- 					<label for="spare2" class="col-sm-3 control-label">spare2<oak:required></oak:required></label> -->
<!-- 					<div class="col-sm-9"> -->
<%-- 						<input id="spare2" name="spare2" value="${bean.spare2}" type="text"  --%>
<!-- 							class="form-control" placeholder="请输入spare2" reqMsg="请输入spare2"  -->
<!-- 							autocomplete="off"/> -->
<!-- 					 </div> -->
<!--                </div> -->
			   
<!-- 				<div class="form-group"> -->
<!-- 					<label for="spare3" class="col-sm-3 control-label">spare3<oak:required></oak:required></label> -->
<!-- 					<div class="col-sm-9"> -->
<%-- 						<input id="spare3" name="spare3" value="${bean.spare3}" type="text"  --%>
<!-- 							class="form-control" placeholder="请输入spare3" reqMsg="请输入spare3"  -->
<!-- 							autocomplete="off"/> -->
<!-- 					 </div> -->
<!--                </div> -->
			   
<!-- 				<div class="form-group"> -->
<!-- 					<label for="spare4" class="col-sm-3 control-label">spare4<oak:required></oak:required></label> -->
<!-- 					<div class="col-sm-9"> -->
<%-- 						<input id="spare4" name="spare4" value="${bean.spare4}" type="text"  --%>
<!-- 							class="form-control" placeholder="请输入spare4" reqMsg="请输入spare4"  -->
<!-- 							autocomplete="off"/> -->
<!-- 					 </div> -->
<!--                </div> -->
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label">备注</label>
					<div class="col-sm-9">
						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text" 
							class="form-control"  
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
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			
			var park_name=$("#park_name").val();
			var park_address=$("#park_address").val();
			var s_comment=$("#s_comment").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(!vcheck.isNumber(park_id) || park_id.length>8){
				tool.alert("编号应该为大于0的8位以内的数字");
				return
			}			
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data:{
							'park_id':'${bean.park_id}',
							'park_name':park_name,
							'park_address':park_address,
							's_comment':s_comment
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
								$("#pageForm").attr("action",'list.shtml?park_id=&area_id=');
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