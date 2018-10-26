<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/jsp/common/bootstrap/taglib.jsp"%>


<div class="modal-dialog">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title" id="myModalLabel">现金解缴</h4>
		</div>

		<div class="modal-body">
			<form class="form-horizontal">
				<div class="box-body">

					<div class="form-group">
						<label for="date" class="col-sm-3 control-label">收费日期<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="date" name="date" value="${bean.date}" type="text"
								class="form-control" placeholder="请选择日期" reqMsg="请输入日期"
								autocomplete="off" onfocus="WdatePicker()"
								onchange="getUserData()" />
						</div>
					</div>
					<div class="form-group">
						<label for="operator_id" class="col-sm-3 control-label">收费员工号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<select class="form-control" id="operator_id" reqMsg="请输入收费员工号"
								onChange="getUserShiftData()">
								<option>——————请选择———————</option>
							</select>
						</div>
					</div>
					<div class="form-group">
						<label for="exit_shift" class="col-sm-3 control-label">收费员班次<oak:required></oak:required></label>
						<div class="col-sm-9">
							<select class="form-control" id="exit_shift" reqMsg="请输入收费员班次"
								oninput="getLaneData()">
								<option>——————请选择———————</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="lane_id" class="col-sm-3 control-label">出口车道编号</label>
						<div class="col-sm-9">
							<select class="form-control" id="lane_id" oninput="getData()">
								<option>——————请选择———————</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="invoice_count" class="col-sm-3 control-label">发票数量<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="invoice_count" name="invoice_count"
								value="${bean.invoice_count}" type="number" class="form-control"
								placeholder="请输入发票数量" reqMsg="请输入发票数量" autocomplete="off" />
						</div>
					</div>
					<div class="form-group">
						<label for="user_id" class="col-sm-3 control-label">财务编号<oak:required></oak:required></label>
						<div class="col-sm-9">
							<input id="login_name" name="login_name"
								value="${loginUser.loginName}" type="text" class="form-control"
								placeholder="财务工号" reqMsg="请输入user_id" autocomplete="off"
								disabled="disabled" />
						</div>
					</div>
					<!-- 				<div class="form-group"> -->
					<!-- 					<label for="user_name" class="col-sm-3 control-label">财务姓名<oak:required></oak:required></label> -->
					<!-- 					<div class="col-sm-9"> -->
					<%-- 						<input id="user_name" name="user_name" value="${cw.name}" type="text"  --%>
					<!-- 							class="form-control" disabled="disabled"/> -->
					<!-- 					 </div> -->
					<!--                </div> -->

                    <div class="form-group" style="display:none">
						<label for="park_id" class="col-sm-3 control-label">停车场编号</label>
						<div class="col-sm-9">
							<input id="park_id" name="park_id"
								type="text" class="form-control"
								autocomplete="off" disabled="disabled" />
						</div>
					</div>

					<div class="form-group" style="display:none">
						<label for="area_id" class="col-sm-3 control-label">片区编号</label>
						<div class="col-sm-9">
							<input id="area_id" name="area_id"
								type="text" class="form-control"
								autocomplete="off" disabled="disabled" />
						</div>
					</div>

                    <div class="form-group">
						<label for="park_name" class="col-sm-3 control-label">停车场名称</label>
						<div class="col-sm-9">
							<input id="park_name" name="park_name"
								type="text" class="form-control"
								autocomplete="off" disabled="disabled" />
						</div>
					</div>

					<div class="form-group">
						<label for="area_name" class="col-sm-3 control-label">片区名称</label>
						<div class="col-sm-9">
							<input id="area_name" name="area_name"
								type="text" class="form-control"
								autocomplete="off" disabled="disabled" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="car_flow_statistic_cash"
							class="col-sm-3 control-label">支付现金的统计车流量(辆)</label>
						<div class="col-sm-9">
							<input id="car_flow_statistic_cash"
								name="car_flow_statistic_cash" type="text" class="form-control"
								placeholder="" autocomplete="off" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="car_flow_real_cash" class="col-sm-3 control-label">支付现金的实际车流量(辆)</label>
						<div class="col-sm-9">
							<input id="car_flow_real_cash" name="car_flow_real_cash"
								type="text" class="form-control" placeholder=""
								autocomplete="off" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="statistic_cash" class="col-sm-3 control-label">统计现金金额(元)</label>
						<div class="col-sm-9">
							<input id="statistic_cash" name="statistic_cash" type="text"
								class="form-control" placeholder="" autocomplete="off"
								disabled="disabled" />
						</div>
					</div>

					<div class="form-group">
						<label for="real_cash" class="col-sm-3 control-label">实际现金金额(元)</label>
						<div class="col-sm-9">
							<input id="real_cash" name="real_cash" type="text"
								class="form-control" placeholder="" autocomplete="off"
								disabled="disabled" />
						</div>
					</div>
					
					<div class="form-group">
						<label for="car_flow_statistic_total"
							class="col-sm-3 control-label">车流量统计总数(辆)</label>
						<div class="col-sm-9">
							<input id="car_flow_statistic_total"
								name="car_flow_statistic_total" type="text" class="form-control"
								placeholder="" autocomplete="off" disabled="disabled" />
						</div>
					</div>
					<div class="form-group">
						<label for="car_flow_real_total" class="col-sm-3 control-label">车流量实际总数(辆)</label>
						<div class="col-sm-9">
							<input id="car_flow_real_total" name="car_flow_real_total"
								type="text" class="form-control" placeholder=""
								autocomplete="off" disabled="disabled" />
						</div>
					</div>

					<div class="form-group">
						<label for="real_bill" class="col-sm-3 control-label">应收金额(元)</label>
						<div class="col-sm-9">
							<input id="real_bill" name="real_bill" value="${bean.real_bill}"
								type="text" class="form-control" autocomplete="off"
								disabled="disabled" />
						</div>
					</div>

					<div class="form-group">
						<label for="pay_bill" class="col-sm-3 control-label">实收金额(元)</label>
						<div class="col-sm-9">
							<input id="pay_bill" name="pay_bill" value="${bean.pay_bill}"
								type="text" class="form-control" autocomplete="off"
								disabled="disabled" />
						</div>
					</div>

					<div class="form-group">
						<label for="remit_bill" class="col-sm-3 control-label">解缴金额(元)</label>
						<div class="col-sm-9">
							<input id="remit_bill" name="remit_bill" type="text"
								class="form-control" placeholder="" autocomplete="off" onkeyup="getMoreCash()"/>
						</div>
					</div>

					<div class="form-group">
						<label for="more_cash" class="col-sm-3 control-label">长款(元)</label>
						<div class="col-sm-9">
							<input id="more_cash" name="more_cash" type="text"
								class="form-control" placeholder="" autocomplete="off" disabled="disabled"/>
						</div>
					</div>

					<div class="form-group">
						<label for="s_comment" class="col-sm-3 control-label">备注</label>
						<div class="col-sm-9">
							<input id="s_comment" name="s_comment" value="${bean.s_comment}"
								type="text" class="form-control" placeholder=""
								autocomplete="off" />
						</div>
					</div>

				</div>
			</form>
		</div>

		<div class="modal-footer">
			<button id="modal_save_button" type="button" class="btn btn-primary">解缴</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	var operatorIdObj = document.getElementById("operator_id");
	var exitShiftObj = document.getElementById("exit_shift");
	var laneIdObj = document.getElementById("lane_id");
	var arrData = [];
	function getUserData() {
// 	    alert(12345);
		operatorIdObj.length = 0;
		exitShiftObj.length = 0;
		laneIdObj.length = 0;
		operatorIdObj.add(new Option("——————请选择———————"));
		exitShiftObj.add(new Option("——————请选择———————"));
		laneIdObj.add(new Option("——————请选择———————"));
		var date = $("#date").val();
		if (!date) {
			return;
		}
		$.ajax(
			{
				url : 'findUser.shtml',
				data : {
					'date' : date
				},
				dataType : 'json',
				type : 'post',
				// 				async:false,
				success : function(data) {
					if (data) {
						// 						alert(data.length);
						// 						for (var i = 0; i < data.length; i++) {
						// 							operatorIdObj.add(new Option(data[i].exitoperator, data[i].exitoperator));
						// 						}
						if (!data) {
							return;
						}
						arrData = data;
						var res = [ data[0] ];
						operatorIdObj.add(new Option(arrData[0].exitoperator, arrData[0].exitoperator));
						for (var i = 1; i < data.length; i++) {
							var repeat = false;
							for (var j = 0; j < res.length; j++) {
								if (data[i].exitoperator == res[j].exitoperator) {
									repeat = true;
									break;
								}
							}
							if (!repeat) {
								res.push(data[i]);
								operatorIdObj.add(new Option(arrData[i].exitoperator, arrData[i].exitoperator));
// 								var s = $("#s_comment").val();
// 								alert(s);
// 								s = '123';
							}
						}
					}
				}
			});
	}

	/**
	 * 改变收费员班次事件
	*/
	function getUserShiftData() {
		exitShiftObj.length = 0;
		laneIdObj.length = 0;
		exitShiftObj.add(new Option("——————请选择———————"));
		laneIdObj.add(new Option("——————请选择———————"));
		var operator_id = $("#operator_id").val();
		if (!arrData) {
			return;
		}
		var res = [];
		for (var i = 0; i < arrData.length; i++) {
			if (operator_id != arrData[i].exitoperator) {
				continue;
			}
			var repeat = false;
			if (res.length == 0) {
				res.push(arrData[i]);
				exitShiftObj.add(new Option(arrData[i].exitshift, arrData[i].exitshift));
				continue;
			}
			for (var j = 0; j < res.length; j++) {
				if (arrData[i].exitshift == res[j].exitshift) {
					repeat = true;
					break;
				}
			}
			if (!repeat) {
				res.push(arrData[i]);
				exitShiftObj.add(new Option(arrData[i].exitshift, arrData[i].exitshift));
			}
		}
	}


	/**
	 * 改变收费员班次事件
	*/
	function getLaneData() {
		// 		exitShiftObj.length = 0;
		laneIdObj.length = 0;
		laneIdObj.add(new Option("——————请选择———————"));
		var operator_id = $("#operator_id").val();
		var exit_shift = $("#exit_shift").val();

		if (!arrData) {
			return;
		}
		var res = [];
		for (var i = 0; i < arrData.length; i++) {
			if (operator_id != arrData[i].exitoperator
				|| exit_shift != arrData[i].exitshift) {
				// 		        alert(operator_id);
				// 		        alert(arrData[i].exitoperator);
				// 		        alert(exit_shift);
				// 		        alert(arrData[i].exitshift);
				continue;
			}
			var repeat = false;
			if (res.length == 0) {
				res.push(arrData[i]);
				laneIdObj.add(new Option(arrData[i].exitlane, arrData[i].exitlane));
				continue;
			}
			for (var j = 0; j < res.length; j++) {
				if (arrData[i].exitlane == res[j].exitlane) {
					repeat = true;
					break;
				}
			}
			if (!repeat) {
				res.push(arrData[i]);
				laneIdObj.add(new Option(arrData[i].exitlane, arrData[i].exitlane));
				
			}
		}
	}

	//提交收费员信息数据
	function getData() {
		// 		alert(1234);
		var date = $("#date").val();
		var operator_id = $("#operator_id").val();
		var exit_shift = $("#exit_shift").val();
		var lane_id = $("#lane_id").val();

		if (!(operator_id && date && exit_shift && lane_id)) {
			tool.alert("请输入正确的车道收费员信息");
			return;
		}
		$.ajax(
			{
				url : 'findAmountAndBill.shtml',
				data : {
					'user_operator' : operator_id,
					'date' : date,
					"exit_shift" : exit_shift,
					"lane_id" : lane_id
				},
				dataType : 'json',
				type : 'post',
				// 				async:false,
				success : function(data) {
					if (data) {
					    $("#car_flow_statistic_total").val(data.car_flow_statistic_total);
						$("#car_flow_real_total").val(data.car_flow_real_total);
						
						$("#car_flow_statistic_cash").val(data.car_flow_statistic_cash);
						$("#car_flow_real_cash").val(data.car_flow_real_cash);
						
						$("#statistic_cash").val(data.statistic_cash/100);
						$("#real_cash").val(data.real_cash/100);
					
						$("#real_bill").val(data.real_bill/100);
						$("#pay_bill").val(data.pay_bill/100);
						
						$("#area_name").val(data.area_name);
						$("#park_name").val(data.park_name);
						
						$("#park_id").val(data.park_id);
						$("#area_id").val(data.area_id);
					} else {
					    $("#car_flow_statistic_total").val('');
						$("#car_flow_real_total").val('');
						
						$("#car_flow_statistic_cash").val('');
						$("#car_flow_real_cash").val('');
						
						$("#statistic_cash").val('');
						$("#real_cash").val('');
					
						$("#real_bill").val('');
						$("#pay_bill").val('');
						$("#park_id").val('');
						$("#area_id").val('');
						$("#area_name").val('');
						$("#park_name").val('');
					}
				}
			});
	}


	function getData1() {
		var operator_id = $("#operator_id").val();
		var date = $("#date").val();
		var exit_shift = $("#exit_shift").val();
		var lane_id = $("#lane_id").val();
		if (!(operator_id && date && exit_shift && lane_id)) {
			return;
		}

		$.ajax(
			{
				url : 'findUserAndAmount.shtml',
				data : {
					'user_operator' : operator_id,
					'date' : date,
					"exit_shift" : exit_shift,
					"lane_id" : lane_id
				},
				dataType : 'json',
				type : 'post',
				// 				async:false,
				success : function(data) {
					if (data) {
						$("#real_bill").val(data.amount);
						$("#park_id").val(data.parkid);
						$("#area_id").val(data.areaid);
						$("#lane_id").val(data.entrylane);
					} else {
						$("#real_bill").val('');
						$("#park_id").val('');
						$("#area_id").val('');
						$("#lane_id").val('');
					}



				}
			});

	}
	
	/**
	*生成长款
	*/
	function getMoreCash()
	{
		var remit_bill = $("#remit_bill").val();
		var real_bill = $("#real_bill").val();
		var remit = 0.00;
		if(!real_bill)
		{
		    tool.alert("请正确填写车道信息");
		    return;
		}
		if((remit_bill-real_bill) >= 0)
		{
		     remit = remit_bill-real_bill;
		}
		$("#more_cash").val(parseFloat((remit).toFixed(2)));
	}
	
	$(function() {

		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			// 			var begin_time=$("#begin_time").val();
			var date = $("#date").val();
			var login_name = $("#login_name").val();
			var operator_id = $("#operator_id").val();
			var park_id = $("#park_id").val();

			var area_id = $("#area_id").val();
			var lane_id = $("#lane_id").val();
			var exit_shift = $("#exit_shift").val();

			var car_flow_statistic_total = $("#car_flow_statistic_total").val();
			var car_flow_real_total = $("#car_flow_real_total").val();
						
			var car_flow_statistic_cash = $("#car_flow_statistic_cash").val();
			var car_flow_real_cash = $("#car_flow_real_cash").val();
						
			var statistic_cash = $("#statistic_cash").val();
			var real_cash = $("#real_cash").val();
					
			var real_bill = $("#real_bill").val();
			var pay_bill = $("#pay_bill").val();
			var remit_bill = $("#remit_bill").val();
						
			var area_name = $("#area_name").val();
			var park_name = $("#park_name").val();
			var invoice_count = $("#invoice_count").val();
			var more_cash = $("#more_cash").val();
			var s_comment = $("#s_comment").val();


			if (vcheck.empty()) {
				return;
			}
			if (pay_bill < real_bill ) {
				tool.alert("金额少于应收金额，无法提交");
				return;
			}
			if (pay_bill > real_bill) {
				long_bill = pay_bill - real_bill;
			}
			if (!vcheck.isNumber(invoice_count)) {
				tool.alert("请输入正确的发票数量");
				return;
			}
			if(car_flow_statistic_total != car_flow_real_total)
			{
			     tool.alert("统计总车流量与实际总车流量不相等，不能解缴");
			     return;
			}
			if(car_flow_statistic_cash != car_flow_real_cash)
			{
			     tool.alert("统计现金车流量与实际现金车流量不相等，不能解缴");
			     return;
			}
			if(statistic_cash != real_cash)
			{
			     tool.alert("统计现金金额与实际现金金额不相等，不能解缴");
			     return;
			}
			if(real_bill != pay_bill)
			{
			     tool.alert("统计总金额与实际总金额不相等，不能解缴");
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

			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data : {
							// 							'begin_time':begin_time,
							'date' : date,
							'login_name' : login_name,
							'operator_id' : operator_id,
							'park_id' : park_id,
							'area_id' : area_id,
							'lane_id' : lane_id,
							'exit_shift' : exit_shift,
							'car_flow_statistic_total':car_flow_statistic_total,
							'car_flow_real_total':car_flow_real_total,
							'car_flow_statistic_cash':car_flow_statistic_cash,
							'car_flow_real_cash':car_flow_real_cash,
							'statistic_cash':statistic_cash,
							'real_cash':real_cash,
							'real_bill':real_bill,
							'pay_bill':pay_bill,
							'remit_bill':remit_bill,
							'long_bill':more_cash,
							'invoice_count' : invoice_count,
							's_comment' : s_comment
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