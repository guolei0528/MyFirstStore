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
					<label for="OLD_PWD" class="col-sm-2 control-label">原密码<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="OLD_PWD" name="OLD_PWD" value="" type="password" 
							class="form-control" placeholder="请输入原密码" reqMsg="请输入原密码" 
							autocomplete="off"/>
					</div>
               </div>
               <div class="form-group">
					<label for="NEW_PWD" class="col-sm-2 control-label">新密码<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="NEW_PWD" name="NEW_PWD" value="" type="password" 
							class="form-control" placeholder="请输入新密码" reqMsg="请输入新密码" 
							autocomplete="off"/>
					</div>
               </div>
               <div class="form-group">
					<label for="RE_PWD" class="col-sm-2 control-label">确认密码<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input id="RE_PWD" name="RE_PWD" value="" type="password" 
							class="form-control" placeholder="请输入确认密码" reqMsg="请输入确认密码" 
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
		//tool.selRadio('DOM_ID','${bean.DOM_VALUE}');
		var URL='${contentPath }/backMng/userMng/modifyPwd/modifyPwd.shtml';
		var userType='${loginUser.USER_TYPE}';
		if(parseInt(userType)>0){
			URL='${contentPath }/backMng/userMng/modifyPwd/ModifyLoginUserPwd.shtml'
		}
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=radioVal('DOM_NAME');
			var OLD_PWD=$("#OLD_PWD").val();
			var NEW_PWD=$("#NEW_PWD").val();
			var RE_PWD=$("#RE_PWD").val();
			if(vcheck.empty()){
				return; 
			}
			
			if(NEW_PWD!=RE_PWD){
				tool.alert("两次密码输入不一致");
				return;
			}
					
			tool.confirm('确实要提交吗？',function(result){
				if(result){
					$.ajax({
						type : 'post',
						url : URL,
						data:{
							'OLD_PWD':OLD_PWD,
							'NEW_PWD':NEW_PWD
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.alert('修改成功');
								window.location.reload();
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