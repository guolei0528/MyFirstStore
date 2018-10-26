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
						<label for="mv_license" class="col-sm-3 control-label">
							车辆牌照<oak:required></oak:required>
						</label>
						<div class="col-sm-9">
							<input id="mv_license" name="mv_license"
								value="${bean.mv_license}" type="text" class="form-control"
								placeholder="请输入车辆牌照" reqMsg="请输入车辆牌照" autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="color" class="col-sm-3 control-label"> 车牌颜色<oak:required></oak:required></label>
						<div class="col-sm-9">
							<label class="mt5 mr5"><input name="color" value="0"
								type="radio" autocomplete="off" checked="checked" />蓝牌</label> <label
								class="mt5 mr5"><input name="color" value="1"
								type="radio" autocomplete="off" />黄牌</label> <label class="mt5 mr5"><input
								name="color" value="2" type="radio" autocomplete="off" />黑牌</label> <label
								class="mt5 mr5"><input name="color" value="3"
								type="radio" autocomplete="off" />白牌</label>
						</div>
					</div>

					<div class="form-group">
						<label for="obu_id" class="col-sm-3 control-label"> OBU编号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="obu_id" name="obu_id" value="${bean.obu_id}"
								type="text" class="form-control" placeholder="请输入OBU编号"
								reqMsg="请输入OBU编号" autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="type" class="col-sm-3 control-label"> 车辆类型</label>
						<div class="col-sm-9">
							<label class="mt5 mr5"><input name="type" value="1"
								type="radio" autocomplete="off" checked="checked" />小客车</label> <label
								class="mt5 mr5"><input name="type" value="2"
								type="radio" autocomplete="off" />大客车</label>
								<label
								class="mt5 mr5">
								<input name="type" value="3"
								type="radio" autocomplete="off" />货车</label>
						</div>
					</div>

					<div class="form-group">
						<label for="start_time" class="col-sm-3 control-label">开始时间<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="start_time" name="start_time" type="text"
								class="form-control" placeholder="请输入开始时间" reqMsg="请输入开始时间"
								autocomplete="off" onfocus="WdatePicker()" />
						</div>
					</div>

					<div class="form-group">
						<label for="end_time" class="col-sm-3 control-label">结束时间<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="end_time" name="end_time" type="text"
								class="form-control" placeholder="请输入结束时间" reqMsg="请输入结束时间"
								autocomplete="off" onfocus="WdatePicker()" />
						</div>

					</div>
				</div>
			</form>
		</div>

		<div class="modal-footer">
<!-- 			<button id="modal_submit_button" type="button" -->
<!-- 				class="btn btn-primary">提交</button> -->
			<button id="modal_save_button" type="button" class="btn btn-primary">启用</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	$(function() {
	 //提价白名单
		$("#modal_submit_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").val();
			var color = tool.radioVal('color');
			var obu_id = $("#obu_id").val();
			var vehicle_class = tool.radioVal('type');
			var valid_start_time = new Date($("#start_time").val());
			var valid_end_time = new Date($("#end_time").val());
			tool.confirm('确实要启用吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'submit.shtml',
						data : {
							'mv_license' : mv_license,
							'color' : color,
							'obu_id' : obu_id,
							'vehicle_class' : vehicle_class,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time
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
	
		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").val();
			var color = tool.radioVal('color');
			var obu_id = $("#obu_id").val();
			var vehicle_class = tool.radioVal('type');
			var valid_start_time = new Date($("#start_time").val());
			var valid_end_time = new Date($("#end_time").val());
			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'comfirmSave.shtml',
						data : {
							'mv_license' : mv_license,
							'color' : color,
							'obu_id' : obu_id,
							'vehicle_class' : vehicle_class,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time
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