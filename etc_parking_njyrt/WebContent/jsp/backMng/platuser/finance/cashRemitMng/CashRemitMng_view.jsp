<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog" style="width:700px">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">查看</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
<!-- 							<div class="row"> -->
<!-- 					<div class="col-md-3 text-right"><p>下班时间</p></div> -->
<!-- 					<div class="col-md-9 text-left"> -->
<%-- 						${bean.create_time} --%>
<!-- 					</div> -->
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">下班时间</label>
						<div class="col-sm-4">
							<label class="mt5 mr5"><fmt:formatDate value="${bean.create_time}" pattern="yyyy-MM-dd HH:mm:ss" /></label>
						</div>
						<label for="color" class="col-sm-2 control-label">工班日期</label>
						<div class="col">
							<label class="mt5 mr5">${bean.date }</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">停车场</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">${bean.park_id }</label>
						</div>
						<label for="color" class="col-sm-2 control-label">区域</label>
						<div class="col">
							<label class="mt5 mr5">${bean.area_id }</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">收费点</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">
							<c:if test="${bean.remit_type == 0}">
											车道
							</c:if>
							<c:if test="${bean.remit_type == 1}">
											终端
							</c:if>
							</label>
						</div>
						<label for="color" class="col-sm-2 control-label">收费终端</label>
						<div class="col">
							<label class="mt5 mr5">${bean.lane_id }</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">收费员编号</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">${bean.user_id}</label>
						</div>
						<label for="color" class="col-sm-2 control-label">收费员</label>
						<div class="col">
							<label class="mt5 mr5">${bean.user_name }</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">强制通过</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">
								<c:if test="${bean.check_status == 0 || bean.check_status == 1}">
										否
								</c:if>
								<c:if test="${bean.check_status == 2}">
										是
								</c:if>
							</label>
						</div>
						<label for="color" class="col-sm-2 control-label">
						审核状态
						</label>
						<div class="col">
							<label class="mt5 mr5">
								<c:if test="${bean.check_status == 0}">
											未通过审核
								</c:if>
								<c:if test="${bean.check_status != 0 && bean.remit_status == 0 }">
											待解缴
								</c:if>
								<c:if test="${bean.check_status != 0 && bean.remit_status == 1 } ">
											已解缴
								</c:if>
							</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">总流量(辆)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">免费流量(辆)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
						<label for="color" class="col-sm-2 control-label">免费金额(元)</label>
						<div class="col">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">优惠流量(辆)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
						<label for="color" class="col-sm-2 control-label">优惠金额(元)</label>
						<div class="col">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">作废流量(辆)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
						<label for="color" class="col-sm-2 control-label">作废流量(元)</label>
						<div class="col">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">实收现金(元)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
						<label for="color" class="col-sm-2 control-label">应收现金(元)</label>
						<div class="col">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">解缴金额(元)</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-4">
							<label class="mt5 mr5">****</label>
						</div>
					</div>
					
					 
             </div>
          </form>
     </div>
	 
     <div class="modal-footer">
       <button id="modal_save_button" type="button" class="btn btn-primary">保存</button>
       <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
     </div>
   </div>
</div>