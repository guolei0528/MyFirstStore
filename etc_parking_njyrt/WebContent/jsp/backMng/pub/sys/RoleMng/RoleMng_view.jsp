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
					<div class="col-md-3 text-right"><p> 角色名称</p></div>
					<div class="col-md-9 text-left">
						${bean.role_name}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p> 备注</p></div>
					<div class="col-md-9 text-left">
						${bean.s_comment}
					</div>
               </div>
				<div class="row">
					<div class="col-md-3 text-right"><p>权限管理</p></div>
					<div class="col-md-9 text-left">
						<div style="border: 1px solid #617775;width:400px;height:220px;overflow-y:scroll;overflow-x:auto;">
					        <ul id="treeDemo" class="ztree"></ul>
					    </div>
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
<script type="text/javascript" src="${contentPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
	$(function(){
		var zTreeObj=null;
	    //页面初始化
	    var setting = null;
	    setting={
			async: {
	            enable: true,
	            url:"loadMenuTree.shtml?query_type=view&role_id="+'${role_id}',
	            autoParam:["id=parent_id"]
	        },
	        callback: {
	        	onAsyncSuccess: zTreeOnAsyncSuccess
	        }
	    };
	    
	    $(document).ready(function(){
	        zTreeObj=$.fn.zTree.init($("#treeDemo"), setting);
	    });

	    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
	        if(treeNode){
	        	var nodes = treeNode.children;
	        	if(nodes){
	        		var length=nodes.length;
	        		for(var i=0;i<length;i++){
	        			zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
	        		}
	        	}
	        }else{
	        	var nodes = zTreeObj.getNodes();
	        	if(nodes){
	        		var length=nodes.length;
	        		for(var i=0;i<length;i++){
	        			zTreeObj.reAsyncChildNodes(nodes[i], "refresh");
	        		}
	        	}
	        }
	    };
		
	});
</script>
