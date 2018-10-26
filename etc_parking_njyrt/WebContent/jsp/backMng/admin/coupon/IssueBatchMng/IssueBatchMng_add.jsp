<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">新增</h4>
		</div>

		<div class="modal-body">
			<form class="form-horizontal">
				<div class="box-body">

					<div class="form-group">
						<label for="issuer_code" class="col-sm-2 control-label">
							发行商
						</label>
						<div class="col-sm-4">
							<select name="issuer_code" id="issuer_code" style="width:100%">
								<option value="01">南京大学</option>
						</select>
						</div>
						<label for="issue_count" class="col-sm-2 control-label">
							发行张数
						</label>
						<div class="col-sm-4">
							<input id="issue_count" name="issue_count"
								type="text" class="form-control" autocomplete="off" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="use_restrict" class="col-sm-2 control-label">
							使用限制
						</label>
						<div class="col-sm-4">
							<label class="mt5 mr5"><input name="use_restrict" value="0"
								type="radio" autocomplete="off" checked="checked"/>各地通用</label> <label class="mt5 mr5"><input
								name="use_restrict" value="1" type="radio" autocomplete="off"/>地方使用</label>
						</div>
					</div>
					
					<div class="form-group">
						<label for="coupon_type" class="col-sm-2 control-label">
							类型
						</label>
						<div class="col-sm-4">
							<label class="mt5 mr5"><input name="coupon_type" value="J" onclick="changeType(this.value)"
								type="radio" autocomplete="off"  checked="checked"/>金额</label> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<label class="mt5 mr5"><input
								name="coupon_type" value="S" type="radio" autocomplete="off" onclick="changeType(this.value)"/>时长</label>&nbsp&nbsp&nbsp&nbsp
						</div>
						<label for="coupon_toll" class="col-sm-2 control-label" id="coupon_toll_label">
							优惠内容（元）
						</label>
						<div class="col-sm-4">
							<input id="coupon_toll" name="coupon_toll" type="text"
								class="form-control"/>
						</div>
					</div>
					
					<div class="form-group">
						<label for="start_time" class="col-sm-2 control-label">
							开始时间
						</label>
						<div class="col-sm-4">
							<input type="text" id="start_time_t" class="form-control"
										name="start_time_t" value="${bean.start_time}"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' })">
						</div>
						<label for="end_time" class="col-sm-2 control-label">
							结束时间
						</label>
						<div class="col-sm-4">
							<input type="text" id="end_time" class="form-control"
										name="end_time" value="${bean.end_time}"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' })">
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

	// 变更优惠券类型
	function changeType(type)
	{
// alert(type);
		if('J' == type)
		{
		$("#coupon_toll_label").html("优惠内容(元)");  
		}
		if('S' == type)
		{
		$("#coupon_toll_label").html("优惠内容(小时)");  
		}
	}
	

	$(function() {

		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var issuer_code = $("#issuer_code").val();
			var issue_count = $("#issue_count").val();
			var use_restrict = $("input[name='use_restrict']:checked").val();
			var coupon_type = $("input[name='coupon_type']:checked").val();
			var coupon_toll = $("#coupon_toll").val();
			var start_time = $("#start_time_t").val();
			var end_time = $("#end_time").val();
			
			if(issue_count >1000)
			{
			alert("最多1个批次保存1000张！");
			return;
			
			}
			
			
			// 根据类型更改coupon_toll值
			if(coupon_type != null && coupon_type == 'J')
			{
				coupon_toll = coupon_toll*100
			}
			
			if(coupon_type != null && coupon_type == 'S')
			{
				coupon_toll = coupon_toll*60
			}
			
			
// 			alert(coupon_type);
// 			alert(coupon_toll);
			
			if(vcheck.empty()){
				return; 
			}
			
			if(vcheck.radioChecked('use_restrict','请选择使用限制')==false){
				return ;
			}
			
			if(vcheck.radioChecked('coupon_type','请选择优惠类型')==false){
				return ;
			}

			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data : {
							'issuer_code' : issuer_code,
							'issue_count' : issue_count,
							'use_restrict' : use_restrict,
							'coupon_type' : coupon_type,
							'coupon_toll' : coupon_toll,
							'start_time_str' : start_time,
							'end_time_str' : end_time
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