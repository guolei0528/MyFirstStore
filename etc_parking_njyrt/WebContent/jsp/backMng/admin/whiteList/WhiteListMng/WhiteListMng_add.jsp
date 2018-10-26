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
			<h4 class="modal-title" id="myModalLabel">新增</h4>
		</div>

		<div class="modal-body">
			<form class="form-horizontal">
				<div class="box-body">

					<div class="form-group">
						<label for="mv_license" class="col-sm-2 control-label">
							车辆牌照 <font color="#FF0000;">(必填*)</font> </label>
						<div class="col-sm-4">
							<input id="mv_license" name="mv_license" onchange="checkMvlicense();"
								value="${bean.mv_license}" type="text" class="form-control"
								placeholder="请输入车辆牌照" reqMsg="请输入车辆牌照" autocomplete="off" />
								
						</div>

						
						<label for="color" class="col-sm-2 control-label">车牌颜色</label>
						<div class="col">
							<label class="mt5 mr5"><input name="color" value="0"
								type="radio" autocomplete="off" checked="checked" />蓝</label> <label
								class="mt5 mr5"><input name="color" value="1"
								type="radio" autocomplete="off" />黄</label> <label class="mt5 mr5"><input
								name="color" value="2" type="radio" autocomplete="off" />黑</label> <label
								class="mt5 mr5"><input name="color" value="3"
								type="radio" autocomplete="off" />白</label> <label class="mt5 mr5"><input
								name="color" value="4" type="radio" autocomplete="off" />绿</label>
						</div>
					</div>

					<div class="form-group">
						<label for="vehicle_class" class="col-sm-2 control-label">
							车辆类型</label>
						<div class="col-sm-10">
							<label class="mt5 mr5"><input name="vehicle_class"
								value="1" type="radio" autocomplete="off" checked="checked" />小客车</label>
							<label class="mt5 mr5"><input name="vehicle_class"
								value="2" type="radio" autocomplete="off" />大客车</label> <label
								class="mt5 mr5"> <input name="vehicle_class" value="3"
								type="radio" autocomplete="off" />货车
							</label>
						</div>
					</div>

					<div class="form-group">
						<label for="start_time" class="col-sm-2 control-label">开始时间<oak:required></oak:required></label>
						<div class="col-sm-4">
							<input id="start_time" name="start_time" type="text"
								class="form-control" placeholder="请输入开始时间" reqMsg="请输入开始时间"
								autocomplete="off"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_time\',{d:0})}', dateFmt: 'yyyy-MM-dd HH:mm' })" />
						</div>
						<label for="end_time" class="col-sm-2 control-label">结束时间<oak:required></oak:required></label>
						<div class="col-sm-4">
							<input id="end_time" name="end_time" type="text"
								class="form-control" placeholder="请输入结束时间" reqMsg="请输入结束时间"
								autocomplete="off"
								onfocus="WdatePicker({ minDate:'#F{$dp.$D(\'start_time\',{d:0})}',dateFmt: 'yyyy-MM-dd HH:mm' })" />
						</div>
					</div>

					<div class="form-group">
						<label for="toll_type" class="col-sm-2 control-label">
							收费形式</label>
						<div class="col-sm-4" >
							<label class="mt5 mr5"><input name="toll_type" value="1" 
								type="radio" autocomplete="off" checked="checked" />免费</label> <label
								class="mt5 mr5" ><input name="toll_type" value="2"  
								type="radio" autocomplete="off"/>收费</label>
						</div>

						<label for="charge_code" class="col-sm-2 control-label" hidden="hidden">收费规则</label>
						<div class="col-sm-4" hidden="hidden">
							<select class="form-control" id="charge_code">
								<option value="15">一户多车</option>
							</select>
						</div>
						
					</div>

			
					<HR width="100%" color=#987cb9 />

					<div class="form-group">
						<label for="user_number" class="col-sm-2 control-label">
							车位编号 <font color="#FF0000;">(必填*)</font></label>
						<div class="col-sm-4">
							<input id="user_number" name="user_number" onchange="doChangeFlag();"
								value="${bean.user_number}" type="text" class="form-control"
								placeholder="请输入车位编号" reqMsg="请输入车位编号" autocomplete="off" />
							<label style="cursor:pointer;color: white;background-color: green;" onclick="checkUserNumb();">
									检测车位编号</label>	
						</div>
						

						<label for="user_name" class="col-sm-2 control-label">
							车主姓名 </label>
						<div class="col-sm-4">
							<input id="user_name" name="user_name" value="${bean.user_name}"
								type="text" class="form-control" placeholder="请输入车主姓名"
								reqMsg="请输入车主姓名" autocomplete="off" />
						</div>
						
					</div>

					<div class="form-group">
					
						<label for="spare1" class="col-sm-2 control-label">
							可拥有车位数</label>
						<div class="col-sm-4">
							<input id="spare1" name="spare1" 
								value="${bean.spare1}" type="text" class="form-control"/>
						</div>
							
							
						<label for="gender" class="col-sm-2 control-label"> 性别</label>
						<div class="col-sm-4">
							<label class="mt5 mr5"><input name="gender" value="1" id="man"
								type="radio" autocomplete="off" checked="checked" />男</label> <label
								class="mt5 mr5"><input name="gender" value="2" id="men"
								type="radio" autocomplete="off" />女</label>
						</div>
					</div>


					<div class="form-group">
					
						<label for="phone" class="col-sm-2 control-label"> 联系电话 </label>
						<div class="col-sm-4">
							<input id="phone" name="phone" value="${bean.phone}" type="text" 
								class="form-control" placeholder="请输入联系电话" reqMsg="请输入联系电话"
								autocomplete="off" />
						</div>
					
						<label for="s_comment" class="col-sm-2 control-label">
							其他信息 </label>
						<div class="col-sm-4">
							<textarea id="s_comment" name="s_comment" style="width:160px;" placeholder="请输入备注信息"></textarea>
						</div>

					</div>
					
<%--	<HR width="100%" color=#987cb9 /> --%>
				<%--区域权限                     begin --%>
				<div class="form-group" hidden="hidden;">
						<label for="area" class="col-sm-2 control-label">区域<font
							color="#FF0000;">*</font></label>
						<div class="col-sm-8">
							<c:if test="${areaid == 0}">
								<label class="mt5 mr5"><input id="allArea" name="area"
									value="" type="checkbox" autocomplete="off" checked="checked" /></label>全部
							<c:forEach items="${areaInfoList }" var="obj" varStatus="status">
									<label class="mt5 mr5"><input name="area"
										value="${obj.area_id}" type="checkbox" autocomplete="off"
										checked="checked" />${obj.area_name}</label>
								</c:forEach>
							</c:if>

							<c:if test="${areaid != 0}">
								<c:forEach items="${areaInfoList }" var="obj" varStatus="status">
									<c:if test="${obj.area_id == areaid}">
										<label class="mt5 mr5"><input name="area"
											value="${obj.area_id}" type="checkbox" autocomplete="off"
											checked="checked" />${obj.area_name}</label>
									</c:if>
								</c:forEach>
							</c:if>
						</div>
					</div> 
					<%--区域权限                     end --%>
					
				</div>
			</form>
		</div>

		<div class="modal-footer">
			<button id="modal_submit_button" type="button"
				class="btn btn-primary">提交</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
<script type="text/javascript">
	
	var checkedFlag = "0";
	
	$(function() {
		
		//默认可拥有车位数给 1
		$("#spare1").val('1');
		
		
		$("#allArea").change(function() { 
		if($("#allArea").is(':checked'))
		{
			$("[name='area']").prop("checked",true);//全选
		}
		else
		{
			$("[name='area']").removeAttr("checked");//取消全选
		}
	});
	
	$("[name='area']").change(function() { 
		var flag = true;
		$('input[name="area"]').each(function(){  
			if(!$(this).is(':checked') && $(this).val() != '')
			{
				flag = false;
			}
		});
		
		if(flag)
		{
			$("#allArea").prop("checked",true);
		}
		else
		{
			$("#allArea").removeAttr("checked");//取消全选
		}
	});
	
	
		//提交白名单
		$("#modal_submit_button").click(function() {

			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").val();
			var color = tool.radioVal('color');
			var vehicle_class = tool.radioVal('vehicle_class');
			var valid_start_time = new Date($("#start_time").val());
			var valid_end_time = new Date($("#end_time").val());
			var toll_type = tool.radioVal('toll_type');
			var charge_code = $("#charge_code").val();
			var user_number = $("#user_number").val();
			var user_name = $("#user_name").val();
			var spare1 = $("#spare1").val();
			var gender = tool.radioVal('gender');
			var phone = $("#phone").val();
			var s_comment = $("#s_comment").val();

			//当有区域时候用 
			var area_ids =new Array();  
			$('input[name="area"]:checked').each(function(){  
   			 area_ids.push($(this).val());//向数组中添加元素  
			});  
			var area_idstr=area_ids.join(',');//将数组元素连接起来以构建一个字符串   
			
			
			
			if(user_number == '' || mv_license == ''){
				tool.alert("请输入必填项!");
				return;
			}
			
			if(checkedFlag == "0"){
				tool.alert("请先检测车位编号!");
				return;
			}
			
			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'submit.shtml',
						data : {
							'mv_license' : mv_license,
							'color' : color,
							'vehicle_class' : vehicle_class,
							'toll_type' : toll_type,
							'charge_code' : charge_code,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time,
							
							'area_ids' : area_idstr,
							
							'user_number' : user_number,
							'user_name' : user_name,
							'spare1': spare1,
							'gender' : gender,
							'phone' : phone,
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
							tool.alert('保存失败!请将信息填写完整!');
						}
					});
				}
			}); 
			 
			
			
		});

		//移到右边
		$('#add').click(function() {
			//先判断是否有选中
			if (!$("#select1 option").is(":selected")) {
				alert("请选择需要移动的选项")
			}
			//获取选中的选项，删除并追加给对方
			else {
				$('#select1 option:selected').appendTo('#select2');
			}
		});

		//移到左边
		$('#remove').click(function() {
			//先判断是否有选中
			if (!$("#select2 option").is(":selected")) {
				alert("请选择需要移动的选项")
			} else {
				$('#select2 option:selected').appendTo('#select1');
			}
		});

		//全部移到右边
		$('#add_all').click(function() {
			//获取全部的选项,删除并追加给对方
			$('#select1 option').appendTo('#select2');
		});

		//全部移到左边
		$('#remove_all').click(function() {
			$('#select2 option').appendTo('#select1');
		});

		//双击选项
		$('#select1').dblclick(function() { //绑定双击事件
			//获取全部的选项,删除并追加给对方
			$("option:selected", this).appendTo('#select2'); //追加给对方
		});

		//双击选项
		$('#select2').dblclick(function() {
			$("option:selected", this).appendTo('#select1');
		});
		
		
	});
	
	function doChangeFlag(){
		checkedFlag = "0";
	}

	//根据用户工号查询用户信息 
	function checkUserNumb(){
		 checkedFlag = "1";
		if($('#user_number').val() != ''){
			$.ajax({
				type:"post",
				url:"checkusernumber.shtml",
				data:{
					"usernumber":$('#user_number').val()
					},
				dataType:"text",
				success:function(message){
					var show = message.split("-");
					if(show[0] == "true" ){
						tool.alert("友情提醒:该车位编号已经存在!");
						if(show[1] == "null"){
							show[1]="无";
						}
						if(show[2] == "null"){
							show[2]="无";
						}
						if(show[3] == "null"){
							show[3]="1";
						}
						if(show[4] == "null"){
							show[4]="无";
						}
						if(show[5] == "null"){
							show[5]="无";
						}
						
						$("#user_name").val(show[1]);
						$("#spare1").val(show[2]);
						//性别
						if(show[3]=="1"){
							$("#man").attr("checked","checked"); 
						}else{
							$("#men").attr("checked","checked"); 
							
						}

						$("#phone").val(show[4]);
						$("#s_comment").val(show[5])
						
					}else{
						tool.alert("该车位编号可用!");
						$("#user_name").val("");
						$("#spare1").val("1"); 
						$("#phone").val("");
						$("#s_comment").val("");
					}  
					
				}
			});
		}
	}
	
	
	//检查车牌合法性和在库唯一性
	function checkMvlicense(){
		var mv_license = $("#mv_license").val()
		if(isLicenseNo(mv_license)){
			$.ajax({
				type:"post",
				url:"checkmvlicense.shtml",
				data:{
					"mv_license":mv_license
					},
				dataType:"text",
				success:function(message){
					var show = message.split("-");
					if(show[0]  == "1" ){
						tool.alert("该车牌已经存在!");
						$("#modal_submit_button").attr("disabled","disabled");
						$("#mv_license").val("");
					}else{
						$("#modal_submit_button").removeAttr("disabled","disabled");
					}  
					
				}
			});

		}else{
			tool.alert("请输入正确车牌!");
			$("#modal_submit_button").attr("disabled","disabled");
			$("#mv_license").val("");
			return;
		}
	}
	
	
	//车牌验证合法想
	function isLicenseNo(str) {
		//普通汽车
		var creg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-HJ-NP-Z0-9]{4}[A-HJ-NP-Z0-9挂学警港澳]{1}$/;
		//新能源汽车
		var xreg = /^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}(([0-9]{5}[DF]$)|([DF][A-HJ-NP-Z0-9][0-9]{4}$))/;
		
		if (str.length == 7) {
			return creg.test(str);
		} else if (str.length == 8) {
			return xreg.test(str);
		} else {
			return false;
		}

		//  return /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/.test(str);
	}

	//根据用户联系方式查询用户信息 主要确保车主的可拥有车位数和联系方式一致 
	function checkPhone() {
		if ($('#phone').val() != '') {
			$.ajax({
				type : "post",
				url : "checkphone.shtml",
				data : {
					"phone" : $('#phone').val()
				},
				dataType : "text",
				success : function(message) {
					var show = message.split("-");
					if (show[0] == "true") {

						$("#spare1").val(show[1]);
						//$("#spare1").attr("readonly","readonly");

						$("#user_number").val(show[2]);
						//$("#user_number").attr("readonly","readonly");
					} else {
						$("#spare1").removeAttr("readonly", "readonly");
						$("#user_number").removeAttr("readonly", "readonly");

					}

				}
			});
		}
	}
</script>