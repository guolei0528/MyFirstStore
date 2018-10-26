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
					<div class="col-md-3 text-right"><p> 员工编号</p></div>
					<div class="col-md-9 text-left">
						${bean.memberId}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 员工姓名</p></div>
					<div class="col-md-9 text-left">
						${bean.name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 员工性别</p></div>
					<div class="col-md-9 text-left">
						${bean.sex==1?'男':'女' }
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 出生日期</p></div>
					<div class="col-md-9 text-left">
						${empty bean.birthday ? '暂无' :bean.birthday}
					</div>
               </div>
               
				<div class="row">
					<div class="col-md-3 text-right"><p> 手机号码</p></div>
					<div class="col-md-9 text-left">
						${empty bean.phone ? '暂无' : bean.phone}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 员工地址</p></div>
					<div class="col-md-9 text-left">
						${empty bean.location ? '暂无' : bean.location}
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-3 text-right"><p> 车牌号码</p></div>
					<div class="col-md-9 text-left">
					   <c:if test="${empty carInfoBeans}">未关联</c:if>
					   <c:if test="${!empty carInfoBeans}">
					   <c:forEach items="${carInfoBeans}" var="item" >
					       <c:if test="${empty item.mvLicense}">未关联</c:if>
                           <c:if test="${!empty item.mvLicense}">${item.mvLicense}</c:if>
					   </c:forEach>
					   </c:if>
					</div>
               </div>
			   
<!-- 				<div class="row"> 
					<div class="col-md-3 text-right"><p> 最后修改时间</p></div>
					<div class="col-md-9 text-left">
						${bean.lastModifyTime}
					</div>
               </div>-->
               
             </div>
          </form>
     </div>
	 
     <div class="modal-footer">
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
   </div>
</div>