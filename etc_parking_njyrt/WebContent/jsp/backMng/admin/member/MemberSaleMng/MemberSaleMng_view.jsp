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
					<div class="col-md-3 text-right"><p> 办理类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose> 
							<c:when test="${bean.memberType==04}">月票</c:when> 
						    <c:when test="${bean.memberType==06}">次票</c:when> 
						    <c:when test="${bean.memberType==05}">年票</c:when> 
						 </c:choose> 
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 卡编号</p></div>
					<div class="col-md-9 text-left">
						${bean.cardId}
					</div>
               </div>
               
               <div class="row">
					<div class="col-md-3 text-right"><p> 发卡时间</p></div>
					<div class="col-md-9 text-left">
						${bean.issueTime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 开始时间</p></div>
					<div class="col-md-9 text-left">
						${bean.beginTime}
					</div>
               </div>
               
               			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 结束时间</p></div>
					<div class="col-md-9 text-left">
						${bean.endTime}
					</div>
               </div>
               
				<div class="row">
					<div class="col-md-3 text-right"><p> 可使用总次数</p></div>
					<div class="col-md-9 text-left">
						${bean.count}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 缴费金额(元 )</p></div>
					<div class="col-md-9 text-left">
						${bean.money}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 累计停车时长</p></div>
					<div class="col-md-9 text-left">
						${bean.totalMinute}
					</div>
               </div>
               
               	<div class="row">
					<div class="col-md-3 text-right"><p> 累计停车次数</p></div>
					<div class="col-md-9 text-left">
						${bean.totalCount}
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