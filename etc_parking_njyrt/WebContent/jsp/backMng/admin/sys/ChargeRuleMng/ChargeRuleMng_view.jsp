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
					<div class="col-md-3 text-right"><p>计费编号</p></div>
					<div class="col-md-9 text-left">
						${bean.charge_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>计费类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.charge_type==0}">按时间计费</c:when>
						<c:when test="${bean.charge_type==1}">按次计费</c:when>
						<c:when test="${bean.charge_type==2}">重大节假日/活动</c:when>
						
						</c:choose>
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>车辆类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.car_type==0}">小车</c:when>
						<c:when test="${bean.car_type==1}">大车</c:when>
						
						</c:choose>
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>生效时间</p></div>
					<div class="col-md-9 text-left">
						${bean.valid_begin_time}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>失效时间</p></div>
					<div class="col-md-9 text-left">
						${bean.valid_end_time}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>每天生效时间段</p></div>
					<div class="col-md-9 text-left">
						${bean.begin_time } - ${bean.end_time }
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>收费金额</p></div>
					<div class="col-md-9 text-left">
						${bean.money}分
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>停车时长</p></div>
					<div class="col-md-9 text-left">
						${bean.period}分钟
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>免费时长</p></div>
					<div class="col-md-9 text-left">
						${bean.free_time }
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>上限收费金额</p></div>
					<div class="col-md-9 text-left">
						${bean.money_limit}分
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>备注</p></div>
					<div class="col-md-9 text-left">
						${bean.s_comment}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>最后修改时间</p></div>
					<div class="col-md-9 text-left">
						${bean.last_modify_time}
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