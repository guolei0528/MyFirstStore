<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">修改</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
			   
				<div class="form-group">
					<label for="lane_flag" class="col-sm-2 control-label">道口编号<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input class="form-control" value="${bean.lane_id}" disabled="disabled" >
					</div>
               </div>
				<div class="form-group">
					<label for="lane_name" class="col-sm-2 control-label">道口名称<oak:required></oak:required></label>
					<div class="col-sm-10">
						<input class="form-control" value="${bean.lane_name}"  id="lane_name" reqMsg="请输入道口名称">
					</div>
               </div>
				<div class="form-group">
					<label for="lane_flag" class="col-sm-2 control-label">道口类型<oak:required></oak:required></label>
					<div class="col-sm-10">
						<label class="mt5 mr5"><input name="lane_flag" value="1" type="radio" 
							autocomplete="off"/>入口</label>
						<label class="mt5 mr5"><input name="lane_flag" value="2" type="radio" 
							autocomplete="off"/>出口</label>
					</div>
               </div>
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text" 
							class="form-control"  
							autocomplete="off"/>
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
<script type="text/javascript">
	$(function(){
		tool.selRadio('lane_flag','${bean.lane_flag}');
		console.log('${bean.lane_flag}');
		$("#modal_save_button").click(function(){
			//RADIO赋值
			var park_id=$("#PARK_ID").val();
			var area_id=$("#AREA_ID").val();
			var lane_flag=tool.radioVal("lane_flag")
			var s_comment=$("#s_comment").val();
			var lane_name=$("#lane_name").val();
			if(vcheck.empty()){
				return; 
			}
			if(!lane_flag){
				tool.alert("请选择道口类型");
				return;
			}
/*			if(vcheck.radioChecked('DOM_NAME','ERROR_MSG')){
				return ;
			}
			var passFlag=true;
			//其他校验
			
			if(passFlag==false){
				return ; 
			}
			*/
							
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
				 
					$.ajax({
						type : 'post',
						url : '${contentPath }/backMng/platuser/sys/LaneInfoMng/update.shtml',
						data:{
							'park_id':park_id,
							'area_id':area_id,
							'lane_id':'${bean.lane_id}',
							'lane_flag':lane_flag,
							's_comment':s_comment,
							'lane_name':lane_name
				
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
								$("#pageForm").attr("action",'list.shtml?area_id='+$("#AREA_ID").val());
								tool.formSubmit();
							}else{
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