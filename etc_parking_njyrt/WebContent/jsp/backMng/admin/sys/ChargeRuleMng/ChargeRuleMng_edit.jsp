<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


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
					<label for="charge_id" class="col-sm-3 control-label">计费规则编号<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="charge_id" name="charge_id" value="${bean.charge_id}" type="number" 
							class="form-control" placeholder="请输入规则编号" reqMsg="请输入计费规则编号" 
							autocomplete="off" disabled="disabled"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="charge_type" class="col-sm-3 control-label">计费类型<oak:required></oak:required></label>
					<div class="col-sm-9">
							<select id="charge_type" name="charge_type" class="form-control" reqMsg="请选择计费类型"> 
								<option></option>
								<option value="0">按时间计费</option>
								<option value="1"> 按次计费</option>
								<option value="2">重大节日/活动</option>
							</select>
					 </div>
               </div>
				<div class="form-group">
					<label for="car_type" class="col-sm-3 control-label">车辆类型<oak:required></oak:required></label>
					<div class="col-sm-9">
							<select id="car_type" name="car_type" class="form-control" reqMsg="请选择车辆类型"> 
<!-- 								<option></option> -->
								<option value="0">小车</option>
<!-- 								<option value="1">大车</option> -->
							</select>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="valid_begin_time" class="col-sm-3 control-label">生效时间<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="valid_begin_time" name="valid_begin_time" value="${bean.valid_begin_time}" type="text" 
							class="form-control" placeholder="请选择生效时间" reqMsg="请选择生效时间" 
							autocomplete="off" onfocus="WdatePicker()"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="valid_end_time" class="col-sm-3 control-label">失效时间<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="valid_end_time" name="valid_end_time" value="${bean.valid_end_time}" type="text" 
							class="form-control" placeholder="请输入失效时间" reqMsg="请输入失效时间" 
							autocomplete="off" onfocus="WdatePicker()"/>
					 </div>
               </div>
				<div class="form-group">
					<label for="begin_time" class="col-sm-3 control-label">每天生效开始时间<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="begin_time" name="begin_time" value="${bean.begin_time}" type="text" 
							class="form-control" placeholder="请输入失效时间" reqMsg="请输入每日生效时间" 
							autocomplete="off" onfocus="WdatePicker({dateFmt:'HH:mm'})" />
					 </div>
               </div>
				<div class="form-group">
					<label for="end_time" class="col-sm-3 control-label">每天失效结束时间<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="end_time" name="end_time" value="${bean.end_time}" type="text" 
							class="form-control" placeholder="请输入失效时间" reqMsg="请输入每日失效时间" 
							autocomplete="off" onfocus="WdatePicker({dateFmt:'HH:mm'})" />
					 </div>
               </div>
				
				<div class="form-group">
					<label for="money" class="col-sm-3 control-label">收费金额（元）<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="money" name="money" value="${bean.money}" type="number" 
							class="form-control" placeholder="请输入收费金额" reqMsg="请输入收费金额" 
							autocomplete="off"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="period" class="col-sm-3 control-label">停车时长（分）<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="period" name="period" value="${bean.period}" type="number" 
							class="form-control" placeholder="每过多少分钟累加收费" 
							autocomplete="off" reqMsg="请填写停车时长"/>
					 </div>
               </div>
				<div class="form-group">
					<label for="free_time" class="col-sm-3 control-label">免费时长（分）<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="free_time" name="free_time" value="${bean.free_time}" type="number" 
							class="form-control" placeholder="请输入免费时长" 
							autocomplete="off" reqMsg="请填写免费时长"/>
					 </div>
               </div>
			   
				<div class="form-group">
					<label for="money_limit" class="col-sm-3 control-label">上限金额（元）<oak:required></oak:required></label>
					<div class="col-sm-9">
						<input id="money_limit" name="money_limit" value="${bean.money_limit}" type="number" 
							class="form-control" placeholder="清输入上限金额"  
							autocomplete="off"/>
					 </div>
               </div>
			   
			   
			   
				<div class="form-group">
					<label for="s_comment" class="col-sm-3 control-label">备注</label>
					<div class="col-sm-9">
						<input id="s_comment" name="s_comment" value="${bean.s_comment}" type="text" 
							class="form-control" placeholder="请输入备注" 
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
		tool.selOption("charge_type",'${bean.charge_type}');
		tool.selOption("car_type",'${bean.car_type}');
// 		if($("#charge_type").val()==1){
// 			$("#period").val('');
// 			$("#period").attr("disabled","disabled");
// 			$("#money_limit").val('');
// 			$("#money_limit").attr("disabled","disabled");
// 		}
// 		$("#charge_type").change(function(){
// 			var charge_type=$("#charge_type").val()
// 			if(charge_type==1){
// 				$("#period").val('');
// 				$("#period").attr("disabled","disabled");
// 				$("#money_limit").val('');
// 				$("#money_limit").attr("disabled","disabled");
				
// 			}else{
// 				$("#period").removeAttr("disabled");
// 				$("#money_limit").removeAttr("disabled");
// 			}
			
// 		});
		$("#modal_save_button").click(function(){
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			
			var charge_type=$("#charge_type").val();
			var begin_time=$("#begin_time").val();
			var end_time=$("#end_time").val();
			var valid_begin_time=$("#valid_begin_time").val();
			var valid_end_time=$("#valid_end_time").val();
			
			var money=$("#money").val();
			
			var period=$("#period").val();
			var money_limit=$("#money_limit").val();
			
			var car_type=$("#car_type").val();
			var begin_time=$("#begin_time").val();
			var end_time=$("#end_time").val();
			var free_time=$("#free_time").val();
			var s_comment=$("#s_comment").val();
			
			if(vcheck.empty()){
				return; 
			}
			if(money<0){
				tool.alert("收费金额请填写大于等于0的金额");
				return;
			}
			if(money_limit){
				if(money_limit<0){
					tool.alert("上限金额请填写大于等于0的金额");
					return;
				}
			}
			if(charge_type!=1){
				if(!period){
					tool.alert("请输入停车时长");
					return ;	
				}
				
			}
			if(period){
				if(period<=0){
					tool.alert("停车时长应大于0");
				}
			}
			if(Date.parse(valid_begin_time)-Date.parse(valid_end_time)>0){
				tool.alert("开始生效日期不应该超过失效日期");
				return;
			};
			if(free_time){
				if(free_time<0){
					tool.alert("请填写正确的免费时长");
					return;
				}
			}else{
				free_time=0;
			}		
			tool.confirm('确实要提交吗？',function(result){
				 if(result){
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data:{
							'charge_id':'${bean.charge_id}',
							'charge_type':charge_type,
							'begin_time':begin_time,
							'end_time':end_time,
							'money':money,
							'period':period,
							'money_limit':money_limit,
							'car_type':car_type,
							'begin_time':begin_time,
							'end_time':end_time,
							'free_time':free_time,
							's_comment':s_comment,
							'valid_begin_time':valid_begin_time,
							'valid_end_time':valid_end_time
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