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
					<div class="col-md-3 text-right"><p>traffic_date</p></div>
					<div class="col-md-9 text-left">
						${bean.traffic_date}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>park_id</p></div>
					<div class="col-md-9 text-left">
						${bean.park_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>area_id</p></div>
					<div class="col-md-9 text-left">
						${bean.area_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>lane_id</p></div>
					<div class="col-md-9 text-left">
						${bean.lane_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>lane_flag</p></div>
					<div class="col-md-9 text-left">
						${bean.lane_flag}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>care_traffic</p></div>
					<div class="col-md-9 text-left">
						${bean.care_traffic}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>pay_method</p></div>
					<div class="col-md-9 text-left">
						${bean.pay_method}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>member_type</p></div>
					<div class="col-md-9 text-left">
						${bean.member_type}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>
datetime</p></div>
					<div class="col-md-9 text-left">
						${bean.datetime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>comment</p></div>
					<div class="col-md-9 text-left">
						${bean.comment}
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