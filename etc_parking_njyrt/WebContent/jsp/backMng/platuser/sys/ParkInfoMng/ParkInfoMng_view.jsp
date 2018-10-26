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
					<div class="col-md-3 text-right"><p>park_id</p></div>
					<div class="col-md-9 text-left">
						${bean.park_id}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>park_name</p></div>
					<div class="col-md-9 text-left">
						${bean.park_name}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>park_address</p></div>
					<div class="col-md-9 text-left">
						${bean.park_address}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>spare1</p></div>
					<div class="col-md-9 text-left">
						${bean.spare1}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>spare2</p></div>
					<div class="col-md-9 text-left">
						${bean.spare2}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>spare3</p></div>
					<div class="col-md-9 text-left">
						${bean.spare3}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>spare4</p></div>
					<div class="col-md-9 text-left">
						${bean.spare4}
					</div>
               </div>
			   
				<div class="row">
					<div class="col-md-3 text-right"><p>s_comment</p></div>
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