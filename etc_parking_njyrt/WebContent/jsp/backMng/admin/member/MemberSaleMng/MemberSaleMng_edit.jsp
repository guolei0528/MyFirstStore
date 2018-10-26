<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">修改</h4>
		</div>

		<div class="modal-body">
			<form class="form-horizontal">
				<div class="box-body">
					<div class="form-group">
						<label for="memberId" class="col-sm-3 control-label"> 员工编号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="memberId" name="memberId" value="${bean.memberId}"
								type="text" disabled="disabled" class="form-control"
								autocomplete="off" />
						</div>
					</div>

					<div class="box-body">
						<div class="form-group">
							<label for="memberType" class="col-sm-3 control-label"> 办理类型</label>
							<div class="col-sm-9">
								<select id="memberType" name="memberType" class="form-control"
									reqMsg="请选择办理类型" disabled="disabled">
									<option></option>
									<option value="04">月票</option>
									<option value="06">次票</option>
									<option value="05">年票</option>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="cardId" class="col-sm-3 control-label"> 卡编号</label>
							<div class="col-sm-9">
								<input id="cardId" name="cardId" value="${bean.cardId}"
									type="text"  class="form-control"
									autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="issueTime" class="col-sm-3 control-label"> 发卡时间</label>
							<div class="col-sm-9">
								<input id="issueTime" name="issueTime" value="${bean.issueTime}"
								type="text" disabled="disabled" class="form-control"
								disabled="disabled"  autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="beginTime" class="col-sm-3 control-label"> 开始时间</label>
							<div class="col-sm-9">
								<input id="beginTime" name="beginTime" value="${bean.beginTime}"
									type="date" class="form-control" placeholder="请输入开始时间"
									disabled="disabled" reqMsg="请输入开始时间" autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="endTime" class="col-sm-3 control-label">
								结束时间</label>
							<div class="col-sm-9">
								<input id="endTime" name="endTime" value="${bean.endTime}"
									type="date" class="form-control" placeholder="请输入结束时间"
									disabled="disabled"  reqMsg="请输入结束时间" autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="count" class="col-sm-3 control-label"> 可使用总次数</label>
							<div class="col-sm-9">
								<input id="count" name="count" value="${bean.count}" type="text" class="form-control" autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="money" class="col-sm-3 control-label"> 缴费金额(元 )</label>
							<div class="col-sm-9">
								<input id="money" name="money" value="${bean.money}" type="text"
									class="form-control" autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="totalMinute" class="col-sm-3 control-label">
								累计停车时长(分)</label>
							<div class="col-sm-9">
								<input id="totalMinute" name="totalMinute"
									value="${bean.totalMinute}" type="text" disabled="disabled"
									class="form-control" autocomplete="off" />
							</div>
						</div>

						<div class="form-group">
							<label for="totalCount" class="col-sm-3 control-label"> 累计停车次数</label>
							<div class="col-sm-9">
								<input id="totalCount" name="totalCount"
									value="${bean.totalCount}" type="text" disabled="disabled"
									class="form-control" autocomplete="off" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="parkId" class="col-sm-3 control-label">
								停车场编号</label>
							<div class="col-sm-9">
								<input id="parkId" name="parkId"
									value="${bean.parkId}" type="text" 
									disabled="disabled" class="form-control" autocomplete="off" />
							</div>
						</div>
						
						
						<div class="form-group">
							<label for="areaId" class="col-sm-3 control-label">
								区域编号</label>
							<div class="col-sm-9">
								<input id="areaId" name="areaId"
									value="${bean.areaId}" type="text" 
									disabled="disabled" class="form-control" autocomplete="off" />
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
          tool.selOption("memberType",'${bean.memberType}');
		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=radioVal('DOM_NAME');

			var memberId = $("#memberId").val();

			var memberType = $("#memberType").val();
			alert(memberType);
            var cardId = $("#cardId").val();
			// 			var sex=tool.radioVal('sex');
			var issueTime = $("#issueTime").val();
			var beginTime = $("#beginTime").val();
            var endTime = $("#endTime").val();
			var count = $("#count").val();
			var money = $("#money").val();
			var totalMinute = $("#totalMinute").val();
			var totalCount = $("#totalCount").val();
			var parkId = $("#parkId").val();
			var areaId = $("#areaId").val();
			

			if (vcheck.empty()) {
				return;
			}
			var passFlag = true;
			//其他校验

			if (passFlag == false) {
				return;
			}

			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data : {
							"memberId" : memberId,
							"memberType" : memberType,
							"cardId" :cardId,
							"issueTime" : issueTime,
							"beginTime" : beginTime,
							"endTime" : endTime,
							"count" : count,
							"money" : money,
							"totalMinute" : totalMinute,
							"totalCount" : totalCount,
							"parkId" : parkId,
							"areaId" : areaId
						},
						dataType : 'json',
						success : function(data, textStatus) {
							if (data.success) {
								tool.closeDialog();
								tool.formSubmit();
							} else {
								alert(data.message);
							}
						},
						error : function(data, textStatus) {
							alert('保存失败');
						}
					});
				}
			});
		});
	});
</script>