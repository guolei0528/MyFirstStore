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
					<div class="col-md-3 text-right"><p>车牌号</p></div>
					<div class="col-md-9 text-left">
						${bean.mv_license}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>录入时间</p></div>
					<div class="col-md-9 text-left">
						<fmt:formatDate value="${bean.in_time}" type="both"/>
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>作废时间</p></div>
					<div class="col-md-9 text-left">
						<fmt:formatDate value="${bean.cancel_time}" type="both"/>
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>黑名单类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
								<c:when test="${bean.b_list_type==1}">欠费</c:when>
								<c:when test="${bean.b_list_type==2}">逃票</c:when>
						</c:choose>
					</div>
               </div>
			   
			   
					<div class="row">
					<div class="col-md-3 text-right"><p>停车场</p></div>
					<div class="col-md-9 text-left">
						${bean.park_name}
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>备注</p></div>
					<div class="col-md-9 text-left">
						${bean.s_comment}
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