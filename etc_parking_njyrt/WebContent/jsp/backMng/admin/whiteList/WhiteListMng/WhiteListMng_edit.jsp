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
						<label for="mv_license" class="col-sm-2 control-label">
							车辆牌照<font color="#FF0000;">(必填*)</font> </label>
						<div class="col-sm-4">
							<input id="mv_license" name="mv_license"    onchange="doChangeFlag1();"
								value="${bean.mv_license}" type="text" class="form-control"
								autocomplete="off" /> <input id="old_mv_license"
								name="old_mv_license" value="${bean.mv_license}" type="hidden"
								class="form-control" autocomplete="off" disabled="disabled" />
							<label style="cursor: pointer; color: white; background-color: green;"
								onclick="checkMvlicense()();"> 检测车牌号</label>	
						</div>

						<label for="color" class="col-sm-2 control-label">车牌颜色</label>
						<div class="col">
							<label class="mt5 mr5"><input name="color" value="0"
								type="radio" autocomplete="off"
								<c:if test="${bean.color == 0}">checked="checked"</c:if> />蓝</label>
							<label class="mt5 mr5"><input name="color" value="1"
								type="radio" autocomplete="off"
								<c:if test="${bean.color == 1}">checked="checked"</c:if> />黄</label>
							<label class="mt5 mr5"><input name="color" value="2"
								type="radio" autocomplete="off"
								<c:if test="${bean.color == 2}">checked="checked"</c:if> />黑</label>
							<label class="mt5 mr5"><input name="color" value="3"
								type="radio" autocomplete="off"
								<c:if test="${bean.color == 3}">checked="checked"</c:if> />白</label>
							<label class="mt5 mr5"><input name="color" value="4"
								type="radio" autocomplete="off"
								<c:if test="${bean.color == 4}">checked="checked"</c:if> />绿</label>
							<input id="old_color" type="hidden" value="${bean.color}"></>
						</div>

						<!-- 						<div class="col-sm-4"> 
							<button id="modal" type="button" class="btn btn-info">检索</button>
						</div>-->
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
						<label for="start_time" class="col-sm-2 control-label">开始时间</label>
						<div class="col-sm-4">
							<input id="start_time" name="start_time" type="text"
								value="<fmt:formatDate value="${bean.valid_start_time}"
												type="date" pattern='yyyy-MM-dd HH:mm'/>"
								class="form-control" autocomplete="off"
								onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'end_time\',{d:0})}', dateFmt: 'yyyy-MM-dd HH:mm' })" />
						</div>
						<label for="end_time" class="col-sm-2 control-label">结束时间</label>
						<div class="col-sm-4">
							<input id="end_time" name="end_time" type="text"
								value="<fmt:formatDate value="${bean.valid_end_time}"
												type="date" pattern='yyyy-MM-dd HH:mm'/>"
								class="form-control" autocomplete="off"
								onfocus="WdatePicker({minDate:'#F{$dp.$D(\'start_time\',{d:0})}', dateFmt: 'yyyy-MM-dd HH:mm' })" />
						</div>
					</div>

					<div class="form-group">
						<label for="toll_type" class="col-sm-2 control-label">
							收费形式</label>
						<div class="col-sm-4">
							<label class="mt5 mr5"><input name="toll_type" value="1" id="free"
								type="radio" autocomplete="off" checked="checked" />免费</label> <label
								class="mt5 mr5"><input name="toll_type" value="2" id="money"
								type="radio" autocomplete="off" />收费</label>
							<!-- <label
								class="mt5 mr5"><input name="toll_type" value="3"
								type="radio" autocomplete="off" />计票</label> -->
						</div>

						<label for="charge_code" class="col-sm-2 control-label"
							hidden="hidden">收费规则</label>
						<div class="col-sm-4" hidden="hidden">
							<select class="form-control" id="charge_code">
								<option value="15">一户多车</option>
								<!-- 						        <option value="16">内部收费</option> -->
								<!-- 								<option value="11">时段收费A</option> -->
								<!-- 								<option value="21">时长收费A</option> -->
								<!-- 								<option value="31">次数收费A</option> -->
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
								autocomplete="off" />
								<label style="cursor: pointer; color: white; background-color: green;"
								onclick="checkUserNumb();"> 检测车位编号</label>
						</div>

						<label for="user_name" class="col-sm-2 control-label">
							车主姓名 </label>
						<div class="col-sm-4">
							<input id="user_name" name="user_name" value="${bean.user_name}"
								type="text" class="form-control" autocomplete="off" />
						</div>

					</div>

					<div class="form-group">

						<label for="obu_id" class="col-sm-2 control-label"> 可拥有车位数
						</label>
						<div class="col-sm-4">
							<input id="spare1" name="spare1" value="${bean.spare1}"
								type="text" class="form-control" autocomplete="off" />
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
								class="form-control" autocomplete="off" />
						</div>

						<label for="s_comment" class="col-sm-2 control-label">
							其他信息 </label>
						<div class="col-sm-4">
							<textarea id="s_comment" name="s_comment" style="width:160px;" >${bean.s_comment}</textarea>
						</div>

					</div>

					<%--原来修改可以选择区域  <div class="form-group">
						<label for="area" class="col-sm-2 control-label">区域</label>
						<div class="col-sm-8">
						<c:set var="flag" value="true"></c:set>
						<c:forEach items="${areaInfoList }" var="obj" varStatus="status">
							<c:set var="area_id_str" value="|${obj.area_id}-"></c:set>   
							<c:if test="${!fn:contains(bean.lane_info,area_id_str )}">
								<c:set var="flag" value="false"></c:set>
						  	</c:if>
						</c:forEach>
						<c:if test="${flag=='false'}">
								<label class="mt5 mr5"><input id="allArea" name="area" value=""
								type="checkbox" autocomplete="off"/></label>全部
						  </c:if>
						
						<c:if test="${flag=='true'}">
								<label class="mt5 mr5"><input id="allArea" name="area" value=""
								type="checkbox" autocomplete="off" checked="checked" /></label>全部
						  </c:if>
						<c:forEach items="${areaInfoList }" var="obj" varStatus="status">
							<c:set var="area_id_str" value="|${obj.area_id}-"></c:set> 
							<c:if test="${fn:contains(bean.lane_info,area_id_str)}">
									<label class="mt5 mr5"><input name="area" value="${obj.area_id}"
								type="checkbox" autocomplete="off" checked="checked"/>${obj.area_name}</label>
						  	</c:if>
						  	<c:if test="${!fn:contains(bean.lane_info,area_id_str)}">
									<label class="mt5 mr5"><input name="area" value="${obj.area_id}"
								type="checkbox" autocomplete="off"/>${obj.area_name}</label>
						  	</c:if>
						</c:forEach>
					</div>
					</div> --%>
					
	<%-- 				<div class="form-group">
						<label for="area" class="col-sm-2 control-label">校区</label>
						<c:forEach items="${areaInfoList }" var="obj" varStatus="status">
							<c:set var="area_id_str" value="|${obj.area_id}-"></c:set> 
							<c:if test="${fn:contains(bean.lane_info,area_id_str)}">
									<c:set var="area_id_checkedforupdate" value="${obj.area_id}"></c:set>	
									<label class="mt5 mr5"> ${obj.area_name}</label>
						  	</c:if>
						</c:forEach>
					</div>  --%>
					
					
					<input id="create_time" name="create_time" type="hidden" value="<fmt:formatDate value="${bean.create_time}"
												type="date" pattern='yyyy-MM-dd HH:mm:ss'/>"/>  
<!-- <input id="create_time" name="create_time" type="hidden" value="${bean.create_time}"/> --> 
				</div>
			</form>
		</div>

		<div class="modal-footer">
			<!-- 			<button id="modal_save_button" type="button" -->
			<!-- 				class="btn btn-primary">提交</button> -->
			<%-- <button id="modal_submit_button" type="button" class="btn btn-primary" onclick="updateit('${area_id_checkedforupdate}')">修改</button> --%>
			<button id="modal_submit_button" type="button" class="btn btn-primary" onclick="updateit()">修改</button>
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
		</div>
	</div>
</div>
<script type="text/javascript">

	var checkedFlag = "0";
	var checkedFlag1 = "0";
	
	checkedFlag = "1";
	
	checkedFlag1 = "1";



	
	//修改：将现在的校区id传递的解决方案
	function updateit(){

		//RADIO赋值
		//var x=tool.radioVal('DOM_NAME');
		var mv_license = $("#mv_license").val();
		var old_mv_license = $("#old_mv_license").val();
		
		var color = $("input[name='color']:checked").val();
		var old_color = $("#old_color").val();

		var vehicle_class = tool.radioVal('vehicle_class');
		var valid_start_time = new Date($("#start_time").val());
		var valid_end_time = new Date($("#end_time").val());
		var create_time = new Date($("#create_time").val());
		var toll_type = tool.radioVal('toll_type');
		var charge_code = $("#charge_code").val();
		
		var user_number = $("#user_number").val();
		var user_name = $("#user_name").val();
		var spare1 = $("#spare1").val();
		var gender = tool.radioVal('gender');
		var phone = $("#phone").val();
		var s_comment = $("#s_comment").val();
		
		
/* 		var area_ids =new Array();  
		$('input[name="area"]:checked').each(function(){  
			 area_ids.push($(this).val());//向数组中添加元素  
		});  */
//		var area_idstr=area_ids.join(',');//将数组元素连接起来以构建一个字符串  
		
		/* var area_idstr=nowareadid;
		var lane_info = "|"+nowareadid+"-"; */
		
		
		if(user_number == '' || mv_license == ''){
			tool.alert("请输入必填项!");
			return;
		}
		
		if(checkedFlag == "0"){
			tool.alert("请检测车位编号!");
			return;
		}
		if(checkedFlag1 == "0"){
			tool.alert("请检测车牌号!");
			return;
		}

			tool.confirm('确实要修改吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data : {
							'mv_license' : mv_license,
							'old_mv_license':old_mv_license,
							
							'color' : color,
							'old_color':old_color,
							
							'vehicle_class' : vehicle_class,
							'toll_type': toll_type,
							'charge_code' : charge_code,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time,
							'create_time':create_time,
							
							'user_number': user_number,
							'user_name': user_name,
							'spare1':spare1,
							'gender': gender,
							'phone': phone,
							's_comment': s_comment
							
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
							tool.alert('修改失败');
						}
					});
				}
			});
	
	}
	
	function doChangeFlag(){
		checkedFlag = "0";
	}
	function doChangeFlag1(){
		checkedFlag1 = "0";
	}

	

	$(function() {
	
		
		tool.selRadio('vehicle_class','${bean.vehicle_class}');
		tool.selRadio('toll_type','${bean.toll_type}');
		tool.selRadio('gender','${bean.gender}');
		tool.selOption("charge_code",'${bean.charge_code}');
		tool.selRadio('spare2','${bean.spare2}');
		
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
		
		// 点击checkbox事件查看是否全选
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
		
		
		
/*用上面的 updateit(当前校区id) 替换		//提价白名单
		$("#modal_submit_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").val();
			var color = $("#color").val();
			var obu_id = $("#obu_id").val();
			var vehicle_class = tool.radioVal('vehicle_class');
			var valid_start_time = new Date($("#start_time").val());
			var valid_end_time = new Date($("#end_time").val());
			var create_time = new Date($("#create_time").val());
			var toll_type = tool.radioVal('toll_type');
			var charge_code = $("#charge_code").val();
			var user_name = $("#user_name").val();
			var gender = tool.radioVal('gender');
			var user_number = $("#user_number").val();
			var phone = $("#phone").val();
			var phone = $("#phone").val();
			var job = $("#job").val();
			var job_type = $("#job_type").val();
// 			var dept_id = $("#dept_id").val();
			var dept_info = $("#dept_info").val();
			var s_comment = $("#s_comment").val();
			var area_ids =new Array();  
			$('input[name="area"]:checked').each(function(){  
   			 area_ids.push($(this).val());//向数组中添加元素  
			}); 
			var area_idstr=area_ids.join(',');//将数组元素连接起来以构建一个字符串  
			
			var lane_info = ${area_id_checkedforupdate};
			alert(lane_info);
			
			tool.confirm('确实要修改吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'update.shtml',
						data : {
							'mv_license' : mv_license,
							'color' : color,
							'vehicle_class' : vehicle_class,
							'obu_id' : obu_id,
							'toll_type': toll_type,
							'charge_code' : charge_code,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time,
							'create_time':create_time,
							'user_name': user_name,
							'gender': gender,
							'user_number': user_number,
							'phone': phone,
							'job': job,
							'job_type': job_type,
// 							'dept_id': dept_id,
							'dept_info': dept_info,
							's_comment': s_comment,
							'area_ids' : area_idstr,
							'lane_info':lane_info
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
							tool.alert('修改失败');
						}
					});
				}
			});
		});
		 */
		
		

		$("#modal_save_button").click(function() {
			//RADIO赋值
			//var x=tool.radioVal('DOM_NAME');
			var mv_license = $("#mv_license").val();
			var color = tool.radioVal('color');
			var obu_id = $("#obu_id").val();
			var vehicle_class = tool.radioVal('type');
			var valid_start_time = new Date($("#start_time").val());
			var valid_end_time = new Date($("#end_time").val());
			var user_name = $("#user_name").val();
			var gender = $("#gender").val();
			var user_number = $("#user_number").val();
			var phone = $("#phone").val();
			var obu_id = $("#obu_id").val();
			var phone = $("#phone").val();
			var job = $("#job").val();
			var job_type = $("#job_type").val();
			var dept_id = $("#dept_id").val();
// 			alert(dept_id);
			var dept_info = $("#dept_info").val();
			var s_comment = $("#s_comment").val();
			tool.confirm('确实要提交吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'save.shtml',
						data : {
							'mv_license' : mv_license,
							'color' : color,
							'obu_id' : obu_id,
							'vehicle_class' : vehicle_class,
							'valid_start_time' : valid_start_time,
							'valid_end_time' : valid_end_time,
							'user_name': user_name,
							'gender': gender,
							'user_number': user_number,
							'phone': phone,
							'job': job,
							'job_type': job_type,
							'dept_id': dept_id,
							'dept_info': dept_info,
							's_comment': s_comment
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
	
	//根据用户工号查询用户信息 
	function checkUserNumb(){
		 checkedFlag = "1";
		if($('#user_number').val() != ''){
			$.ajax({
				type:"post",
				url:"checkusernumber.shtml",
				data:{
					"usernumber":$('#user_number').val(),
					"toll_type":tool.radioVal('toll_type')
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
						$("#s_comment").val("")
					
						//性别  默认男
						$("#man").attr("checked","checked");
						
						$("#free").attr("checked","checked");
					}  
					
				}
			});
		}
	}
	
	
	
	//检查车牌合法性和在库唯一性
	function checkMvlicense(){
		checkedFlag1="1";
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
					if(show[0] == "1" && show[1] != $("#old_mv_license").val()){
						tool.alert("该车牌已经存在!");
						$("#mv_license").val("");
						$("#modal_submit_button").attr("disabled","disabled");
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
	//车牌验证合法性
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
		
	     //return /(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/.test(str);
	  }

	
	
	//根据用户联系方式查询用户信息 主要确保车主的可拥有车位数和联系方式一致 
	function checkPhone(){
		if($('#phone').val() != ''){
			$.ajax({
				type:"post",
				url:"checkphone.shtml",
				data:{
					"phone":$('#phone').val()
					},
				dataType:"text",
				success:function(message){
					var show = message.split("-");
					if(show[0] == "true" ){

						$("#spare1").val(show[1]);
						$("#spare1").attr("readonly","readonly");
						
						$("#user_number").val(show[2]);
						$("#user_number").attr("readonly","readonly");

					}else{

						$("#spare1").removeAttr("readonly","readonly");
						$("#user_number").removeAttr("readonly","readonly");
						
						
						$("#user_number").val("");
						$("#spare1").val(0);
						$("#user_number").focus(); 
					}  
					
				}
			});
		}
	}
	
</script>