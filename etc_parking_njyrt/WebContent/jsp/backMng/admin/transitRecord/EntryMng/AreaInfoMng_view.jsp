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
					<div class="col-md-3 text-right"><p>片区编号</p></div>
					<div class="col-md-9 text-left">
						${bean.area_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>片区名称</p></div>
					<div class="col-md-9 text-left">
						${bean.area_name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>停车位数量</p></div>
					<div class="col-md-9 text-left">
						${bean.space_number}
					</div>
               </div>
			   
			   <div class="row">
					<div class="col-md-3 text-right"><p>图片存储位置</p></div>
					<div class="col-md-9 text-left">
						${bean.picture_addr}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>图片存储类型</p></div>
					<div class="col-md-9 text-left">
						<c:choose>
							<c:when test="${bean.picture_type==0}">数据库存储</c:when>
							<c:when test="${bean.picture_type==1}">目录存储</c:when>
							<c:when test="${bean.picture_type==2}">数据库+目录存储</c:when>
						</c:choose>
					</div>
               </div>
			   
				
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>服务器IP</p></div>
					<div class="col-md-9 text-left">
						${bean.server_ip}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>数据库登录名</p></div>
					<div class="col-md-9 text-left">
						${bean.db_name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>数据库密码</p></div>
					<div class="col-md-9 text-left">
						${bean.db_password}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>最大迁移天数</p></div>
					<div class="col-md-9 text-left">
						${bean.day_number}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>日志存储位置</p></div>
					<div class="col-md-9 text-left">
						${bean.log_address}
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