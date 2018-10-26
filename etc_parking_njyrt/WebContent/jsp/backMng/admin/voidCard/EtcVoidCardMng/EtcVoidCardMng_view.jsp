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
					<div class="col-md-3 text-right"><p>卡编号</p></div>
					<div class="col-md-9 text-left">
						${bean.card_id}
					</div>
               </div>
			   
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>卡网络编号</p></div>
					<div class="col-md-9 text-left">
						${bean.card_network}
					</div>
               </div>
               <div class="box-body">
							<div class="row">
					<div class="col-md-3 text-right"><p>卡类型</p></div>
					<div class="col-md-9 text-left">
						${bean.card_type}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>卡状态</p></div>
					<div class="col-md-9 text-left">
						${bean.card_status}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>状态类型</p></div>
					<div class="col-md-9 text-left">
						${bean.status_type}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>发行商ID</p></div>
					<div class="col-md-9 text-left">
						${bean.issuer_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>电子设备ID</p></div>
					<div class="col-md-9 text-left">
						${bean.electronic_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>车牌号</p></div>
					<div class="col-md-9 text-left">
						${bean.mv_license}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>车牌号颜色</p></div>
					<div class="col-md-9 text-left">
						${bean.mv_license_colour}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>录入时间</p></div>
					<div class="col-md-9 text-left">
						${bean.intime}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>作废时间</p></div>
					<div class="col-md-9 text-left">
						${bean.cancel_time}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>发行方黑名单版本号</p></div>
					<div class="col-md-9 text-left">
						${bean.issuer_version}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>黑名单类型</p></div>
					<div class="col-md-9 text-left">
						${bean.ban_type}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>发行方生成状态名单时间</p></div>
					<div class="col-md-9 text-left">
						${bean.issuer_effective_time}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>有效标识</p></div>
					<div class="col-md-9 text-left">
						${bean.valid_flag}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>生效时间</p></div>
					<div class="col-md-9 text-left">
						${bean.valid_time}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>是否需要锁卡</p></div>
					<div class="col-md-9 text-left">
						${bean.lock_card}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>联系方式</p></div>
					<div class="col-md-9 text-left">
						${bean.contact_info}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>备注</p></div>
					<div class="col-md-9 text-left">
						${bean.s_comment}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>版本</p></div>
					<div class="col-md-9 text-left">
						${bean.version}
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