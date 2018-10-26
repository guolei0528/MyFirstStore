<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">新增</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
			   	<div class="form-group">
					<label for="memberId" class="col-sm-3 control-label"> 员工编号<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="memberId" name="memberId" value="${bean.memberId}" type="text" 
							class="form-control" placeholder="请输入员工编号" reqMsg="请输入员工编号" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label"> 员工姓名<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="name" name="name" value="${bean.name}" type="text" 
							class="form-control" placeholder="请输入员工姓名" reqMsg="请输入员工姓名" 
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
					<label for="birthday" class="col-sm-3 control-label"> 出生日期</label>
					<div class="col-sm-9">
						<input id="birthday" name="birthday" value="${bean.birthday}" type="date" 
							class="form-control" placeholder="请输入出生日期" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="phone" class="col-sm-3 control-label"> 手机号码</label>
					<div class="col-sm-9">
						<input id="phone" name="phone" value="${bean.phone}" type="text" 
							class="form-control" placeholder="请输入手机号码" reqMsg="请输入手机号码" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="location" class="col-sm-3 control-label"> 员工地址</label>
					<div class="col-sm-9">
						<input id="location" name="location" value="${bean.location}" type="text" 
							class="form-control" placeholder="请输入员工地址" reqMsg="请输入员工地址" 
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
			var memberId = $("#memberId").val();
			var name=$("#name").val();
			var sex=tool.radioVal('sex');
			var birthday=$("#birthday").val();
			var phone=$("#phone").val();
			var location=$("#location").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(vcheck.radioChecked('sex','请选择性别')==false){
				return ;
			}
			
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data:{
							'memberId':memberId,
							'name':name,
							'sex':sex,
							'birthday':birthday,
							'phone':phone,
							'location':location
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