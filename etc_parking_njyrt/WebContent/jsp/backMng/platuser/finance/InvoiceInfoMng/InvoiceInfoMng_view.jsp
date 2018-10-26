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
					<div class="col-md-3 text-right"><p>发票代码</p></div>
					<div class="col-md-9 text-left">
						${bean.code}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>起始发票号码</p></div>
					<div class="col-md-9 text-left">
						${bean.code}${bean.begin_number}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>最后一张发票号码</p></div>
					<div class="col-md-9 text-left">
						${bean.code}${bean.end_number}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>领入/领出</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.flag==0}">领入发票</c:when>
							<c:when test="${bean.flag==1}">领出发票</c:when>
						</c:choose>
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>发票数量</p></div>
					<div class="col-md-9 text-left">
						${bean.count}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>
						<c:choose>
							<c:when test="${bean.flag==0}">财务编号</c:when>
							<c:when test="${bean.flag==1}">收费员编号</c:when>
						</c:choose>		
					</p></div>
					<div class="col-md-9 text-left">
						${bean.user_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>
						<c:choose>
							<c:when test="${bean.flag==0}">领入时间</c:when>
							<c:when test="${bean.flag==1}">领出时间</c:when>
						</c:choose>	
				</p></div>
					<div class="col-md-9 text-left">
						${bean.begin_time}
					</div>
               </div>
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>cancel_record</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.cancel_record} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>cancel_time</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.cancel_time} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>cancel_user_id</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.cancel_user_id} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
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