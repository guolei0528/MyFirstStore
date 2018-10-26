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
					<div class="col-md-3 text-right"><p>记录编号</p></div>
					<div class="col-md-9 text-left">
						${bean.recordno}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 卡网络编号</p></div>
					<div class="col-md-9 text-left">
						${bean.cardnetwork}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 卡编号</p></div>
					<div class="col-md-9 text-left">
						${bean.cardid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 停车场编号</p></div>
					<div class="col-md-9 text-left">
						${bean.parkid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 片区编号</p></div>
					<div class="col-md-9 text-left">
						${bean.areaid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 站编号</p></div>
					<div class="col-md-9 text-left">
						${bean.entrystation}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 入口车道编号</p></div>
					<div class="col-md-9 text-left">
						${bean.entrylane}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p> 入站时间</p></div>
					<div class="col-md-9 text-left">
						${bean.entrytime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 出口车道编号</p></div>
					<div class="col-md-9 text-left">
						${bean.exitlane}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p> 出站时间</p></div>
					<div class="col-md-9 text-left">
						${bean.exittime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 出站操作员编号</p></div>
					<div class="col-md-9 text-left">
						${bean.exitoperator}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 班次</p></div>
					<div class="col-md-9 text-left">
						${bean.entryshift}
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p> 车牌号</p></div>
					<div class="col-md-9 text-left">
						${bean.mvlicense}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>会员类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.membertype eq '00'}">非会员用户（普通用户）</c:when>
							<c:when test="${bean.membertype eq '01'}">VIP用户（不收费）</c:when>
							<c:when test="${bean.membertype eq '02'}">内部车辆</c:when>
							<c:when test="${bean.membertype eq '03'}">公务车辆</c:when>
							<c:when test="${bean.membertype eq '04'}">月票用户</c:when>
							<c:when test="${bean.membertype eq '05'}">年票用户</c:when>
							<c:when test="${bean.membertype eq '06'}">次票用户</c:when>
							<c:when test="${bean.membertype eq '07'}">无牌车辆</c:when>
							<c:when test="${bean.membertype eq '08'}">累计时长用户（总停车400分钟免费）</c:when>
							<c:when test="${bean.membertype eq '09'}">某时间段内免费[6:00---24:00]</c:when>
							<c:when test="${bean.membertype eq '10'}">规定时间内某个车道免费</c:when>
							<c:otherwise>用户类型不确定</c:otherwise>						
						
						</c:choose>
						
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>应收总费额</p></div>
					<div class="col-md-9 text-left">
						${bean.totaltoll}元
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>付款方式</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.paymethod==0}">没有交费</c:when>
							<c:when test="${bean.paymethod==1}">支付宝交费</c:when>
							<c:when test="${bean.paymethod==2}">微信交费</c:when>
							<c:when test="${bean.paymethod==3}">银行卡交费</c:when>
							<c:when test="${bean.paymethod==4}">现金交付</c:when>
							<c:when test="${bean.paymethod==5}">优惠券</c:when>
						</c:choose>
						
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>发票号</p></div>
					<div class="col-md-9 text-left">
						${bean.invoiceid}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>扣款前余额</p></div>
					<div class="col-md-9 text-left">
						${bean.ebbalance}<c:if test="${empty bean.ebbalance}">0</c:if>元
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>非现金卡余额</p></div>
					<div class="col-md-9 text-left">
						${bean.eabalance}<c:if test="${empty bean.eabalance}">0</c:if>元
					</div>
               </div>
               	<div class="row">
					<div class="col-md-3 text-right"><p>非现金卡类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.ecardtype==22}">储值卡</c:when>
							<c:when test="${bean.ecardtype==23}">记账卡</c:when>
							<c:when test="${bean.ecardtype==49}">机场专用记账卡</c:when>
						</c:choose>
						
					</div>
               </div>
			      <c:if test="${bean.imagepath != ''}"> 
				     <img width="100%" src="/img/${fn:substringBefore(bean.imagepath,'-')}/${fn:substring(fn:substringAfter(bean.imagepath,'-'), 0, 8)}/${bean.imagepath}">
                  </c:if>   
                 <c:if test="${bean.imagepath == ''}">l
                     <div class="col-md-3 text-right"><p>无图片</p></div>
                 </c:if>
             </div>
          </form>
     </div>
	 
     <div class="modal-footer">
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
   </div>
</div>