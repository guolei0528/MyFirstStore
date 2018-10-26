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
					<div class="col-md-2 text-right"><p> 发行商</p></div>
					<div class="col-md-4 text-left">
						${bean.issuer_name}
					</div>
					<div class="col-md-2 text-right"><p> 发行日期</p></div>
					<div class="col-md-4 text-left">
						20${bean.issue_date}
					</div>
               </div>
			   
			   <div class="row">
					<div class="col-md-2 text-right"><p> 发行批次</p></div>
					<div class="col-md-4 text-left">
						${bean.batch_id}
					</div>
					<div class="col-md-2 text-right"><p> 发行券</p></div>
					<div class="col-md-4 text-left">
						${bean.coupon_code}
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-2 text-right"><p> 使用限制</p></div>
					<div class="col-md-4 text-left">
						<c:if test="${bean.use_restrict == 0}">各地通用</c:if>
						<c:if test="${bean.use_restrict == 1}">地方使用</c:if>
					</div>
					<div class="col-md-2 text-right"><p> 停车场</p></div>
					<div class="col-md-4 text-left">
						${bean.station_name}
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-2 text-right"><p> 类型</p></div>
					<div class="col-md-4 text-left">
						<c:if test="${bean.coupon_type == 'J'}">金额</c:if>
						<c:if test="${bean.coupon_type == 'S'}">时长</c:if>
					</div>
					<div class="col-md-2 text-right"><p> 优惠内容</p></div>
					<div class="col-md-4 text-left">
						<c:if test="${bean.coupon_type == 'J'}">优惠<fmt:formatNumber type="number" maxFractionDigits="2" value="${bean.coupon_toll/100 }" />元</c:if>
						<c:if test="${bean.coupon_type == 'S'}">优惠时长<fmt:formatNumber type="number" maxFractionDigits="2" value="${bean.coupon_toll/60 }" />小时</c:if>
					</div>
               </div>
               
               
               <div class="row">
               		<div class="col-md-2 text-right"><p> 验证码</p></div>
					<div class="col-md-4 text-left">
						${bean.verify_code}
					</div>
					<div class="col-md-2 text-right"><p> 状态</p></div>
					<div class="col-md-4 text-left">
						<c:if test="${bean.status == 0}">生效</c:if>
						<c:if test="${bean.status == 1}">已导出</c:if>
						<c:if test="${bean.status == 2}">已作废</c:if>
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-2 text-right"><p> 录入人员</p></div>
					<div class="col-md-4 text-left">
						${bean.user_name }
					</div>
					<div class="col-md-2 text-right"><p> 录入时间</p></div>
					<div class="col-md-4 text-left">
						<fmt:formatDate value="${bean.create_time}" pattern="yyyy-MM-dd HH:mm:ss" />
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