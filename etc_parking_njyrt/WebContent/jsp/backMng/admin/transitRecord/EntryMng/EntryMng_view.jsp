<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog" >
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">查看</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>卡网络编号</p></div>
					<div class="col-md-9 text-left">
						${bean.cardnetwork}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>卡编号</p></div>
					<div class="col-md-9 text-left">
						${bean.cardid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>停车场编号</p></div>
					<div class="col-md-9 text-left">
						${bean.parkid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>区域编号</p></div>
					<div class="col-md-9 text-left">
						${bean.areaid}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>入口站编号</p></div>
					<div class="col-md-9 text-left">
						${bean.entrystation}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>道口编号</p></div>
					<div class="col-md-9 text-left">
						${bean.entrylane}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>进站时间</p></div>
					<div class="col-md-9 text-left">
						${bean.entrytime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>入口操作员编号</p></div>
					<div class="col-md-9 text-left">
						${bean.entryoperator}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>班次</p></div>
					<div class="col-md-9 text-left">
						${bean.entryshift}
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>车牌号</p></div>
					<div class="col-md-9 text-left">
						${bean.mvlicense}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>ETC编号</p></div>
					<div class="col-md-9 text-left">
						${bean.etcobuid}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>车型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.vehicleclass==1 || bean.vehicleclass==2}">小车</c:when>
							<c:when test="${bean.vehicleclass==3 || bean.vehicleclass==4}">大车</c:when>
						</c:choose>
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>统计日期</p></div>
					<div class="col-md-9 text-left">
					${bean.entrydate }
					</div>
               </div>
			   	<c:if test="${bean.imagepath != ''}">
				     <img width="100%" src="/img/${fn:substringBefore(bean.imagepath,'-')}/${fn:substring(fn:substringAfter(bean.imagepath,'-'), 0, 8)}/${bean.imagepath}">
                 </c:if>
                 <c:if test="${bean.imagepath == ''}">
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