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
					<div class="col-md-3 text-right"><p> 车辆牌照</p></div>
					<div class="col-md-9 text-left">
						${bean.mvLicense}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> obu编号</p></div>
					<div class="col-md-9 text-left">
						${bean.obuId == ''? 0 :bean.obuId}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 员工编号</p></div>
					<div class="col-md-9 text-left">
						${bean.memberId }
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 车牌颜色</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.carColor==0}">蓝色车牌</c:when>
						    <c:when test="${bean.carColor==1}">黄色车牌</c:when>
						    <c:when test="${bean.carColor==2}">白色车牌</c:when>
						    <c:when test="${bean.carColor==3}">黑色车牌</c:when>
						</c:choose>
					</div>
               </div>
               
				<div class="row">
					<div class="col-md-3 text-right"><p> 行驶证</p></div>
					<div class="col-md-9 text-left">
						${empty bean.license ? '暂无':bean.license}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 车辆类型</p></div>
					<div class="col-md-9 text-left">
						${bean.type==1 || bean.type == 2?'小车':'大车'}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 备注</p></div>
					<div class="col-md-9 text-left">
						${empty bean.sComment ? '无':bean.sComment}
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