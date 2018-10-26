<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>
<link href="${contentPath }/js/ztree/zTreeStyle.css" rel="stylesheet" type="text/css">

<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">新增</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
				<div class="form-group">
					<label for="name" class="col-sm-3 control-label"> 角色名称<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="role_name" name="role_name" value="${bean.role_name}" type="text" 
							class="form-control" placeholder="请输入角色名称" reqMsg="请输入角色名称" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label"> 备注</label>
					<div class="col-sm-9">
						<input class="form-control" id="s_comment" name="s_comment" placeholder="请输入备注信息">
					 </div>
               </div>
			   
			   
			   
               	<div class="form-group">
						<label for="MENU" class="col-sm-3 control-label"> 系统菜单</label>
						<div class="col-sm-9">
							<div style="border: 1px solid #617775;width:400px;height:220px;overflow-y:scroll;overflow-x:auto;">
						        <ul id="treeDemo" class="ztree"></ul>
						    </div>
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
<script type="text/javascript" src="${contentPath }/js/ztree/jquery.ztree.all-3.5.js"></script>
<script type="text/javascript">
$(function() {
	var zTreeObj=null;
    //页面初始化
    var setting = null;
    setting={
		check : {
			enable : true,
			chkboxType : {
				"Y" : "ps",
				"N" : "ps"
			}
		},
        async: {
            enable: true,
            url:"loadMenuTree.shtml?query_type=add",
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

	$("#modal_save_button").click(function() {
		var role_name = $("#role_name").val();
		var s_comment = $("#s_comment").val();
		if (vcheck.empty()) {
			return;
		}
		
		 tool.confirm('确实要提交吗？',function(result){
			 if(result){
				 var nodes = zTreeObj.getCheckedNodes(true);
				var nodeId_str='';
				if(nodes){
					var length=nodes.length;
					for(var i=0;i<length;i++){
						nodeId_str=nodeId_str+nodes[i].id;
						if(i<length-1){
							nodeId_str=nodeId_str+',';
						}
					}
				}
				
				var WX_PAGE_IDS=tool.joinCheckbox("WX_PAGE_ID");
				
				$.ajax({
					type : 'post',
					url : 'save.shtml',
					data : {
						'role_name' : role_name,
						's_comment' : s_comment,
						'NODE_ID_STR':nodeId_str
						/**
						*'OWNER_ID':OWNER_ID,
						*'USER_TYPE':USER_TYPE
						*/
					},
					dataType : 'json',
					success : function(data, textStatus) {
						if (data.success) {
							tool.closeDialog();
							tool.formSubmit();
						} else {
							tool.alert(data.message);
						}
					},
					error : function(data, textStatus) {
						tool.alert('保存失败');
					}

				});
			 }
		 });
			
	});
});
</script>