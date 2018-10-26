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
					<div class="col-md-3 text-right"><p>录入时间</p></div>
					<div class="col-md-9 text-left">
						${bean.begin_time}
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>财务编号</p></div>
					<div class="col-md-9 text-left">
						${bean.login_name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>收费员编号</p></div>
					<div class="col-md-9 text-left">
						${bean.operator_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>停车场编号</p></div>
					<div class="col-md-9 text-left">
						${bean.park_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>片区编号</p></div>
					<div class="col-md-9 text-left">
						${bean.area_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>道口编号</p></div>
					<div class="col-md-9 text-left">
						${bean.lane_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>班次</p></div>
					<div class="col-md-9 text-left">
						${bean.exit_shift}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>应收款(元)</p></div>
					<div class="col-md-9 text-left">
						${bean.real_bill}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>实收款(元)</p></div>
					<div class="col-md-9 text-left">
						${bean.pay_bill}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>长款(元)</p></div>
					<div class="col-md-9 text-left">
						${bean.long_bill}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>发票数量</p></div>
					<div class="col-md-9 text-left">
						${bean.invoice_count}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>车辆总流量(辆)</p></div>
					<div class="col-md-9 text-left">
						${bean.car_flow_statistic_total}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>实际车流量(辆)</p></div>
					<div class="col-md-9 text-left">
						${bean.car_flow_real_total}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>支付现金的统计车流量(辆)</p></div>
					<div class="col-md-9 text-left">
						${bean.car_flow_statistic_cash}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>支付现金的实际车流量(辆)</p></div>
					<div class="col-md-9 text-left">
						${bean.car_flow_real_cash}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>统计现金支付金额(元)</p></div>
					<div class="col-md-9 text-left">
						${bean.statistic_cash}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>实际现金支付金额(元)</p></div>
					<div class="col-md-9 text-left">
						${bean.real_cash}
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