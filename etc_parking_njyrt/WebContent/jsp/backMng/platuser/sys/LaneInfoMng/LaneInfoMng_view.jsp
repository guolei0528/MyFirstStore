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
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>道口编号</p></div>
					<div class="col-md-9 text-left">
						${bean.lane_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>道口类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.lane_flag==0}">入口</c:when>
							<c:when test="${bean.lane_flag==1}">出口</c:when>
						</c:choose>
					</div>
               </div>
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>spare1</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.spare1} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>spare2</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.spare2} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>spare3</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.spare3} --%>
<!-- 					</div> -->
<!--                </div> -->
			   
<!-- 				<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>spare4</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.spare4} --%>
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
          <div class="modal-footer">
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
     </div>
	 
     
   </div>
</div>