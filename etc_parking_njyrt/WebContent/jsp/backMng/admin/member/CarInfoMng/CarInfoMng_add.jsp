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
						<label for="mvLicense" class="col-sm-3 control-label">
							车辆牌照
						</label>
						<div class="col-sm-9">
							<input id="mvLicense" name="mvLicense" value="${bean.mvLicense}"
								type="text" class="form-control" placeholder="请输入车辆牌照"
								reqMsg="请输入车辆牌照" autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="obuId" class="col-sm-3 control-label"> obu编号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="obuId" name="obuId" value="${bean.obuId}" type="text"
								class="form-control" placeholder="请输入obu编号" reqMsg="请输入obu编号"
								autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="memberId" class="col-sm-3 control-label"> 员工编号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="memberId" name="memberId" value="${bean.memberId}"
								type="text" class="form-control" placeholder="请输入员工编号"
								reqMsg="请输入员工编号" autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="carColor" class="col-sm-3 control-label">
							车牌颜色</label>
						<div class="col-sm-9">
							 <select id="carColor" name="carColor">
							<option value="0">蓝色牌照</option>
							<option value="1">黄色牌照</option>
							<option value="2">白色牌照</option>
							<option value="3">黑色牌照</option>
						</select>
						</div>

					</div>


					<div class="form-group">
						<label for="license" class="col-sm-3 control-label"> 行驶证<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="license" name="license" value="${bean.license}"
								type="text" class="form-control" placeholder="请输入行驶证"
								reqMsg="请输入行驶证" autocomplete="off" />
						</div>
					</div>

					<div class="form-group">
						<label for="type" class="col-sm-3 control-label"> 车辆类型</label>
						<div class="col-sm-9">
							<label class="mt5 mr5"><input name="type" value="1"
								type="radio" autocomplete="off"  checked="checked" disabled="disabled"/>小车</label> <label class="mt5 mr5"><input
								name="type" value="3" type="radio" autocomplete="off" disabled="disabled"/>大车</label>
						</div>
					</div>

					<div class="form-group">
						<label for="sComment" class="col-sm-3 control-label"> 备注</label>
						<div class="col-sm-9">
							<input id="sComment" name="sComment" value="${bean.sComment}"
								type="text" class="form-control" placeholder="请输入备注"
								reqMsg="请输入备注" autocomplete="off" />
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
	$(function() {

		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mvLicense = $("#mvLicense").val();
			var obuId = $("#obuId").val();
			var memberId = $("#memberId").val();
			var carColor = $("#carColor").val();
			var license = $("#license").val();
			var type = tool.radioVal('type');
			var sComment = $("#sComment").val();
// 			if(vcheck.empty()){
// 				return; 
// 			}
			if(vcheck.radioChecked('type','请选择车辆类型')==false){
				return ;
			}

			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data : {
							'mvLicense' : mvLicense,
							'obuId' : obuId,
							'memberId' : memberId,
							'carColor' : carColor,
							'license' : license,
							'type' : type,
							'sComment' : sComment
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