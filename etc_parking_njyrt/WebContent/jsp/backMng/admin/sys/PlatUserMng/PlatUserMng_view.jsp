<%@page import="com.project.common.tool.AppConst"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">查看</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 姓名</p></div>
					<div class="col-md-9 text-left">
						${bean.NAME}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 性别</p></div>
					<div class="col-md-9 text-left">
						${bean.SEX==1?'男':'女' }
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 工号</p></div>
					<div class="col-md-9 text-left">
						${bean.WORK_NO}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 手机</p></div>
					<div class="col-md-9 text-left">
						${bean.MOBILE}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 备注</p></div>
					<div class="col-md-9 text-left">
						${bean.REMARK}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 创建时间</p></div>
					<div class="col-md-9 text-left">
						${bean.CREATE_TIME}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 最后修改时间</p></div>
					<div class="col-md-9 text-left">
						${bean.LAST_MODIFY_TIME}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 是否启用</p></div>
					<div class="col-md-9 text-left">
						${bean.IN_USE_FLAG==1?'启用':'禁用' }
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-3 text-right"><p> 关联角色</p></div>
					<div class="col-md-9 text-left">
						${USER_ROLE.ROLE_NAME }
					</div>
               </div>
			   
             </div>
          </form>
     </div>
	 
     <div class="modal-footer">
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
   </div>
</div>