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
						${bean.name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 性别</p></div>
					<div class="col-md-9 text-left">
						${bean.sex==1?'男':'女' }
					</div>
               </div>
               
              <div class="row">
					<div class="col-md-3 text-right"><p> 工号</p></div>
					<div class="col-md-9 text-left">
						${bean.loginName}
					</div>
               </div> 
              <div class="row">
					<div class="col-md-3 text-right"><p> 班次</p></div>
					<div class="col-md-9 text-left">
						${bean.userShift==0 ? '无班次' : bean.userShift }  
					</div>
               </div> 
				<!--<div class="row">
					<div class="col-md-3 text-right"><p> 工号</p></div>
					<div class="col-md-9 text-left">
						${bean.userOperator}
					</div>
               </div>
			   -->
				<div class="row">
					<div class="col-md-3 text-right"><p> 手机</p></div>
					<div class="col-md-9 text-left">
						${bean.phone}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 备注</p></div>
					<div class="col-md-9 text-left">
						${bean.sComment}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 创建时间</p></div>
					<div class="col-md-9 text-left">
						${bean.createTime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 最后修改时间</p></div>
					<div class="col-md-9 text-left">
						${bean.lastModifyTime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 是否启用</p></div>
					<div class="col-md-9 text-left"> 
						${bean.inUseFlag==1?'启用':'禁用' }
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-3 text-right"><p> 关联角色</p></div>
					<div class="col-md-9 text-left">
						${bean.roleName }
					</div>
               </div>
               
               <div class="row" hidden="hidden">
					<div class="col-md-3 text-right"><p> 校区</p></div>
					<div class="col-md-9 text-left">
							<c:if test="${bean.areaId == 0}">所有</c:if>
							 <c:forEach items="${AREA_LIST}" var="areainfo"
								varStatus="status">
								<c:if test="${areainfo.area_id == bean.areaId}">${areainfo.area_name}</c:if>
								
							</c:forEach> 
							
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