<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	list-style-type: none;
	outline: none;
}

a, img {
	border: 0;
}

body {
	font: 12px/normal "microsoft yahei";
}

.selectbox {
	width: 500px;
	height: 220px;
	margin: 0px auto;
}

.selectbox div {
	float: left;
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 160px;
	height: 200px;
	border: 1px #A0A0A4 solid;
	padding: 4px;
	font-size: 14px;
	font-family: "microsoft yahei";
}

.btn-bar {
	
}

.btn-bar p {
	margin-top: 16px;
}

.btn-bar p .btn {
	width: 50px;
	height: 30px;
	cursor: pointer;
	font-family: simsun;
	font-size: 14px;
}
</style>

<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">入库操作</h4>
		</div>

		<div class="modal-body" >
			<form class="form-horizontal">
				<div class="box-body">
					
					<label id="sumparks" hidden="hidden">${sumparks}</label>
					<label id="usernumber" hidden="hidden">${usernumber}</label>
					<label id="tolltype" hidden="hidden">${tolltype}</label>
					
					
					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">
							入场车牌 </label>
						<div class="col-sm-4">
							<label id="mv_license">${mv_license}</label>
						</div>
					</div>
			

					<div class="form-group">
						<label for="entry_lane" class="col-sm-2 control-label">入场车道</label>
						<div class="col-sm-4">
							<select class="form-control" id="entry_lane">
								<c:forEach items="${inLaneInfoList}" var="obj">
									<option value="${obj.lane_id}">${obj.lane_name}</option>
								</c:forEach>
<!-- 								<option value="16">内部收费</option> -->
							</select>
							
						</div>
					</div>
					
					<div class="form-group">
						<label for="entry_time" class="col-sm-2 control-label">入场时间</label>
						<div class="col-sm-4">
							<input id="entry_time" name="mv_license"
									onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm:ss' })" 
								value="${nowtime}" type="text" class="form-control"
								autocomplete="off" />
						</div>
					</div>
				</div>
				
			</form>
		</div>

		<div class="modal-footer">
			<!-- 			<button id="modal_save_button" type="button" -->
			<!-- 				class="btn btn-primary">提交</button> -->
			<button id="modal_submit_button" type="button"
				class="btn btn-primary">确认</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		</div>
	</div>
</div>
<script type="text/javascript">

	$(function() {

		//提交入库操作
		$("#modal_submit_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").html();
			var entry_lane = $("#entry_lane").val();
			var entry_time = $("#entry_time").val();
			var sumparks = $("#sumparks").html();
			var usernumber = $("#usernumber").html();
			var tolltype = $("#tolltype").html();
			
			tool.confirm('确定入库吗？', function(result) {
				if (result) {
				 	$.ajax({
						type : 'post',
						url : 'sureincost.shtml',
						data : {
							'mv_license' : mv_license,
							'entry_lane' : entry_lane,
							'entry_time' : entry_time,
							'sumparks' : sumparks,
							'usernumber' : usernumber,
							'tolltype' : tolltype
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
							tool.alert('保存失败!');
						}
					}); 
				}
			});
		});

	});
</script>