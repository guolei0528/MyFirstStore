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
					<label for="NAME" class="col-sm-3 control-label"> 姓名<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="NAME" name="NAME" value="${bean.NAME}" type="text" 
							class="form-control" placeholder="请输入姓名" reqMsg="请输入姓名" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="SEX" class="col-sm-3 control-label"> 性别<oak:required></oak:required></label>
					<div class="col-sm-9">
						<label class="mt5 mr5"><input name="SEX" value="1" type="radio" 
							autocomplete="off"/>男</label>
						<label class="mt5 mr5"><input name="SEX" value="0" type="radio" 
							autocomplete="off"/>女</label>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="WORK_NO" class="col-sm-3 control-label"> 工号</label>
					<div class="col-sm-9">
						<input id="WORK_NO" name="WORK_NO" value="${bean.WORK_NO}" type="text" 
							class="form-control" placeholder="请输入工号" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="MOBILE" class="col-sm-3 control-label"> 手机号码<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="MOBILE" name="MOBILE" value="${bean.MOBILE}" type="text" 
							class="form-control" placeholder="请输入手机号码" reqMsg="请输入手机号码" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="REMARK" class="col-sm-3 control-label"> 备注</label>
					<div class="col-sm-9">
						<textarea id="REMARK" name="REMARK"class="form-control" placeholder="请输入备注">${bean.REMARK}</textarea>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="IN_USE_FLAG" class="col-sm-3 control-label"> 是否启用<oak:required></oak:required></label>
					<div class="col-sm-9">
						<label class="mt5 mr5"><input name="IN_USE_FLAG" value="1" type="radio" 
							autocomplete="off"/>启用</label>
						<label class="mt5 mr5"><input name="IN_USE_FLAG" value="0" type="radio" 
							autocomplete="off"/>未启用</label>
					 </div>
               </div>
               
               <div class="form-group">
					<label class="col-sm-3 control-label"> 关联角色</label>
					<div class="col-sm-9">
						<select id="ROLE_ID" class="form-control">
							<option value=""></option>
							<c:forEach items="${ROLE_LIST }" var="obj">
								<option value="${obj.ID }">${obj.NAME }</option>
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
		tool.selRadio('SEX','${bean.SEX}');
		tool.selRadio('IN_USE_FLAG','${bean.IN_USE_FLAG}');
		tool.selOption('ROLE_ID','${USER_ROLE.ROLE_ID}');
		
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=radioVal('DOM_NAME');
			var NAME=$("#NAME").val();
			
			var SEX=tool.radioVal('SEX');
			var WORK_NO=$("#WORK_NO").val();
			var MOBILE=$("#MOBILE").val();
			var REMARK=$("#REMARK").val();
			
			var LAST_MODIFY_TIME=$("#LAST_MODIFY_TIME").val();
			var IN_USE_FLAG=tool.radioVal('IN_USE_FLAG');
			
			var ROLE_ID=$("#ROLE_ID").val();
			if(vcheck.empty()){
				return; 
			}
			if(vcheck.radioChecked('SEX','请选择性别')==false){
				return ;
			}
			if(vcheck.radioChecked('IN_USE_FLAG','请选择是否启用')==false){
				return ;
			}
			var passFlag=true;
			//其他校验
			
			if(passFlag==false){
				return ; 
			}
							
			
		});
	});
</script>