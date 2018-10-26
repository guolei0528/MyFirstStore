<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
   <div class="modal-content">
     <div class="modal-header">
       <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only" >Close</span></button>
       <h4 class="modal-title" id="myModalLabel">发票领入</h4>
     </div>
	 
     <div class="modal-body">
		<form class="form-horizontal">
             <div class="box-body">
			   
				<div class="form-group">
					<label for="code" class="col-sm-3 control-label"  >发票头<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="code" name="code" value="${bean.code}" type="text" 
							class="form-control" placeholder="票头，数字+字母，如1000A" reqMsg="请输入发票头" 
							autocomplete="off" onkeyup="change()"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="begin_number" class="col-sm-3 control-label"   >起始流水号<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="begin_number" name="begin_number" value="${bean.begin_number}" type="number" 
							class="form-control" placeholder="数字流水号，如：123450000" reqMsg="请输入起始流水号" 
							autocomplete="off" onkeyup="change()"/>
					 </div>
               </div>
			   
			
			
			   
				<div class="form-group">
					<label for="count" class="col-sm-3 control-label"  >发票数量<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="count" name="count" value="${bean.count}" type="number" 
							class="form-control" placeholder="请输入发票数量" reqMsg="请输入发票数量" 
							autocomplete="off" onkeyup="change()"/>
					 </div>
               </div>
			   
			   	<div class="form-group">
					<label for="end_number" class="col-sm-3 control-label">最后一张发票号码<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="end_number" name="end_number" value="${bean.end_number}" type="text" 
							class="form-control" placeholder="" reqMsg="请确认发票数或者起始流水号" 
							autocomplete="off" disabled="disabled"/>
					 </div>
               </div>
			   
			   
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label">备注<oak:required></oak:required></label>
					<div class="col-sm-9">
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
	function change(){
		var code=$("#code").val();
		var begin_number=$("#begin_number").val();
		var count=$("#count").val();
		if(code&&count&&begin_number){
			var end_number=Math.floor(begin_number)+Math.floor(count);
			$("#end_number").val(code+""+end_number);
		}
	}
	$(function(){
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			
			var code=$("#code").val();
			var begin_number=$("#begin_number").val();
			var end_number=$("#end_number").val().replace(code,"");
			var count=$("#count").val();
			var begin_time=$("#begin_time").val();
			
			var s_comment=$("#s_comment").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(count<=0){
				tool.alert("请核实发票数量");
				return;
			}
			if(begin_number<=0){
				tool.alert("请核实发票起始流水号");
				return;
			}
			if(begin_number.length>10){
				tool.alert("发票流水号最多10位");
				return;
			}
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data:{
							'code':code,
							'begin_number':begin_number,
							'end_number':end_number,
							'flag':0,
							'count':count,
							'user_id':'${loginUser.userOperator}',
							's_comment':s_comment
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if(data.success){
								tool.closeDialog();
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