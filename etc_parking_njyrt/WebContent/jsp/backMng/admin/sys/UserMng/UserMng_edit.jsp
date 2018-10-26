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
			<input type="hidden" name="userId" value="${bean.userId }">   
             <div class="box-body">
				<div class="form-group">  
					<label for="name" class="col-sm-3 control-label"> 姓名<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="name" name="name" value="${bean.name}" type="text" 
							class="form-control" placeholder="请输入姓名" reqMsg="请输入姓名" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="sex" class="col-sm-3 control-label"> 性别<oak:required></oak:required></label>
					<div class="col-sm-9">
						<label class="mt5 mr5"><input name="sex" value="1" type="radio" 
							autocomplete="off"/>男</label>
						<label class="mt5 mr5"><input name="sex" value="0" type="radio" 
							autocomplete="off"/>女</label>
					 </div>
               </div>
               
               <div class="form-group">
					<label for="login_name" class="col-sm-3 control-label"> 工号</label>
					<div class="col-sm-9">
						<input id="login_name" name="login_name" value="${bean.loginName}" type="text" 
							class="form-control" placeholder="请输工号" 
							autocomplete="off"/>
					 </div>
               </div> 
			   
               	<div class="form-group">
					<label for="user_shift" class="col-sm-3 control-label">班次<oak:required></oak:required></label>
					<div class="col-sm-9">
							<select class="form-control" id="user_shift" reqMsg="请输入班次" >
								<option value="0">无班次</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
							</select>
					 </div>
               </div>
			   
				<!--<div class="form-group">
					<label for="user_operator" class="col-sm-3 control-label"> 工号</label>
					<div class="col-sm-9">
						<input id="user_operator" name="user_operator" value="${bean.userOperator}" type="text" 
							class="form-control" placeholder="请输入工号" 
							autocomplete="off"/>
					 </div>
               </div>-->
			   
				<div class="form-group">
					<label for="phone" class="col-sm-3 control-label"> 手机号码<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="phone" name="phone" value="${bean.phone}" type="text" 
							class="form-control" placeholder="请输入手机号码" reqMsg="请输入手机号码" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label"> 备注</label>
					<div class="col-sm-9">
						<textarea id="s_comment" name="s_comment"class="form-control" placeholder="请输入备注">${bean.sComment}</textarea>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="in_use_flag" class="col-sm-3 control-label"> 是否启用<oak:required></oak:required></label>
					<div class="col-sm-9">
						<label class="mt5 mr5"><input name="in_use_flag" value="1" type="radio" 
							autocomplete="off"/>启用</label>
						<label class="mt5 mr5"><input name="in_use_flag" value="0" type="radio" 
							autocomplete="off"/>未启用</label>
					 </div>
               </div>
               
               <div class="form-group">
					<label class="col-sm-3 control-label"> 关联角色</label>
					<div class="col-sm-9">
						<select id="role_id" class="form-control">
							<option value=""></option>
							<c:forEach items="${ROLE_LIST }" var="obj"> 
								<option value="${obj.role_id }">${obj.role_name }</option>
							</c:forEach>
						</select>
					 </div>
               </div>
               
               <div class="form-group" hidden="hidden">
					<label class="col-sm-3 control-label"> 校区</label>
					<div class="col-sm-9">
						<select id="area_id" class="form-control">
							<option value="0">所有</option>
							<c:forEach items="${AREA_LIST}" var="obj"> 
								<option value="${obj.area_id }"  <c:if test="${obj.area_id ==bean.areaId }">selected="selected" </c:if>   >${obj.area_name }</option>
							</c:forEach>
							
						</select>
						
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
	    tool.selOption("user_shift",'${bean.userShift}');
		tool.selRadio('sex','${bean.sex}');
		tool.selRadio('in_use_flag','${bean.inUseFlag}');
		tool.selOption('role_id','${bean.roleId}');
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=radioVal('DOM_name');
			var name=$("#name").val();
			
			var sex=tool.radioVal('sex');
			
			var login_name = $("#login_name").val();
// 			var user_operator=$("#user_operator").val();
			var phone=$("#phone").val();
			var user_shift=$("#user_shift").val();
			
			var s_comment=$("#s_comment").val();
			
			var LAST_MODIFY_TIME=$("#LAST_MODIFY_TIME").val();
			var in_use_flag=tool.radioVal('in_use_flag');
			
			var role_id=$("#role_id").val();
			
			var area_id =$("#area_id").val();
			
// 			if(vcheck.empty()){
// 				return; 
// 			}
			if(vcheck.radioChecked('sex','请选择性别')==false){
				return ;
			}
			if(vcheck.radioChecked('in_use_flag','请选择是否启用')==false){
				return ;
			}
			var passFlag=true;
			//其他校验
			
			if(passFlag==false){
				return ; 
			}
							
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data:{
							'userId':'${bean.userId}',
							'name':name,
				
							'sex':sex,
							'loginName':login_name,
// 							'userOperator':user_operator,
							'phone':phone,
							'userShift':user_shift,
							'sComment':s_comment,
				
							'inUseFlag':in_use_flag,
							
							'roleId':role_id,
							'areaId':area_id
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
								tool.formSubmit();
							}else{
								alert(data.message);
							}
						},
						error : function(data, textStatus) {
							alert('保存失败');
						}
	
					});
				 }
			});
		});
	});
</script>