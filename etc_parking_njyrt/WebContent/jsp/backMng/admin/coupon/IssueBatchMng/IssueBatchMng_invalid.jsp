<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><%=PropertiesUtil.get("PROJECT_NAME")%></title>
<%@include file="/jsp/common/bootstrap/commonJsCss.jsp"%>
<script type="text/javascript"
	src="${contentPath }/js/common/ajaxfileupload.js"></script>
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
	width: 1000px;
	height: 600px;
	margin: 0px auto;
}

.selectbox div {
	float: center;
}

.selectbox .select-bar {
	padding: 0 20px;
}

.selectbox .select-bar select {
	width: 280px;
	height: 500px;
	border: 1px #A0A0A4 solid;
	padding: 4px;
	font-size: 16px;
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
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>

	<div id="mainContentDiv" style="margin-left: 180px;text-align:left">

		<!-- Main content -->

		<div class="modal-body" style="text-align:left">
			<div class="box-header with-border">
				<h3 class="box-title">优惠券作废</h3>
			</div>
			<!-- /.box-header -->
			<!-- form start -->
			<div class="box box-primary"
				style="text-align:center;width:40%;margin-left:auto;margin-right:auto;">
				<form role="form" style="text-align:center">
					<div class="box-body" style="text-align:center" id="body">
						<!--                 <div class="form-group"> -->
						<!--                   <label class="col-sm-4 text-right control-label" for="query_coupon_type">券类型</label> 
                  <div class="col-sm-8 text-left"> 
						<label class="mt5 mr5"><input name="query_coupon_type" value="J"
								type="radio" autocomplete="off"  checked="checked"/>金额</label> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<label class="mt5 mr5"><input
								name="query_coupon_type" value="S" type="radio" autocomplete="off"/>时长</label>&nbsp&nbsp&nbsp&nbsp
				   </div>
				   <br/><br/>
                
                  <label class="col-sm-4 text-right" for="query_use_restrict">使用限制</label>
                  <div class="col-sm-8 text-left">
						<label class="mt5 mr5"><input name="query_use_restrict" value="0"
								type="radio" autocomplete="off" checked="checked"/>各地通用</label> <label class="mt5 mr5"><input
								name="query_use_restrict" value="1" type="radio" autocomplete="off"/>地方使用</label>
				  </div><br/><br/>
				   
				  <label class="col-sm-4 text-right" for="query_issuer_code">发行商</label>
                  <div class="col-sm-8 text-left"> 
						<select name="query_issuer_code" id="query_issuer_code" style="width:90%">
								<option value="01">南京大学</option>
						</select>
				  </div><br/><br/>
				   
				  <label class="col-sm-4 text-right" for="query_issue_date">发行年月</label>
                  <div class="col-sm-8 text-left">
						<input type="text" id="query_issue_date" class="form-control" style="width:90%"
										name="query_issue_date" 
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM' })">
				  </div><br/><br/>
				   -->
						<!--  <div class="row">
				  <label class="col-sm-2 text-right" for="query_batch_code">发行开始批次</label>
                  <div class="col-sm-3 text-left"> 
                  <input class="form-control" id="query_batch_code" name="query_batch_code" style="width:50%">
                  </div><br/><br/>
                  <div>
                  <label class="col-sm-2 text-right" for="query_batch_code">-</label>
                  <div class="col-sm-3 text-left"> 
                  <input class="form-control" id="query_batch_code" name="query_batch_code" style="width:50%"> -->
						<!-- <span>  
						<input class="form-control" id="" style="width:20%">
				  </span> -->
				  
						<!-- 						<input type="text" style="width:40%" placeholder="请输入文字"><input style="width:40%" type="text" placeholder="center"> -->


						<!-- 				   </div> -->
						<!-- 				   </div> -->
						<!-- 				   <br/><br/> -->

						<!--               </div> -->



						<div class="form-group">
							<label for="invaild_type" class="col-sm-4 control-label">
								作废类型 </label>
							<div class="col-sm-8">
								<select name="invaild_type" id="invaild_type" style="width:100%">
									<option value="0">单张作废</option>
									<option value="1">连续多张作废</option>
									<option value="2">批次作废</option>
								</select>
							</div>
						</div>
						<br/><br/>   

						<div class="form-group" style="text-align:center" id="invaild_div" name="invaild_div">
							<label for="invaild_coupon" class="col-sm-4 control-label">
								作废优惠券 </label>
							<div class="col-sm-8">
								<input class="form-control"
									name="invaild_coupon" style="width:100%">
							</div>
						</div> 
<!-- 						<br/> -->

						<div class="form-group" style="text-align:center" id="invaild_add">
							<label class="col-sm-4 control-label"></label>
							<div class="col-sm-8">
								<li title="增加"><a onclick="addCoupon()"><i
										class="fa fa-plus-square text-blue"></i><span
										class="text-blue"></span>增加</a></li>
							</div>
						</div>

					</div>
					<!-- /.box-body -->

					<div class="box-footer text-right">
						<button type="button" class="btn btn-primary" onclick="invalid()">作废</button>
					</div>

				</form>
			</div>
			<div class="col-md-1"></div>

			<!--           <div class="box box-primary col-md-6"> 
          <form>
            <div class="selectbox">
            
						<div class="select-bar">
						生效优惠券
							<select multiple="multiple" id="valid">
							</select>
						</div>

						<div class="btn-bar">
							<p>
								<span id="add"><input type="button" class="btn" value=">"
									title="移动选择项到右侧" /></span>
							</p>
							<p>
								<span id="add_all"><input type="button" class="btn"
									value=">>" title="全部移到右侧" /></span>
							</p>
							<p>
								<span id="remove"><input type="button" class="btn"
									value="<" title=" 移动选择项到左侧"/></span>
							</p>
							<p>
								<span id="remove_all"><input type="button" class="btn"
									value="<<" title=" 全部移到左侧"/></span>
							</p>
						</div>

 						<div class="select-bar"> 
							作废优惠券
							<select multiple="multiple" id="invalid">
							</select>
						</div>
					</div>
            </form>
          
          </div>-->
		</div>

		<!-- <div class="box-body">
							
				</div> -->

	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
	
		function addCoupon() {
			$("#invaild_add").remove();
			/* 	    $("#body").append("<div class='form-group' style='text-align:center'>"); 
					$("#body").append("<label for='invaild_coupon' class='col-sm-4 control-label'></label>");
				    $("#body").append("<div class='col-sm-8'><input class='form-control' name='invaild_coupon' style='width:100%'></div>");
					$("#body").append("</div>");
					$("#body").append("<div class='form-group' style='text-align:center' id='invaild_add'>");
					$("#body").append("<label class='col-sm-4 control-label'></label>");
					$("#body").append("<div class='col-sm-8'>");
					$("#body").append("<li title='增加'><a onclick ='addCoupon()'><i class='fa fa-plus-square text-blue'></i><span class='text-blue'></span>增加</a></li>");
					$("#body").append("</div>");
					$("#body").append("</div>"); */
			
			$("#body").append("<div class='form-group' style='text-align:center' name='invaild_div'><label for='invaild_coupon' class='col-sm-4 control-label'></label><div class='col-sm-8'><input class='form-control' name='invaild_coupon' style='width:100%'/></div></div>");
			$("#body").append("<div class='form-group' style='text-align:center' id='invaild_add'><label class='col-sm-4 control-label'></label><div class='col-sm-8'><li title='增加'><a onclick ='addCoupon()'><i class='fa fa-plus-square text-blue'></i><span class='text-blue'></span>增加</a><a onclick='minusCoupon()'>&nbsp;&nbsp;<i class='fa fa-minus-square text-red'></i><span class='text-blue'></span>减少</a></li></div></div>");
			/* $("#body").append("<label class='col-sm-4 control-label'></label>");
			$("#body").append("<div class='col-sm-8'>");
			$("#body").append("<li title='增加'><a onclick ='addCoupon()'><i class='fa fa-plus-square text-blue'></i><span class='text-blue'></span>增加</a></li>");
			$("#body").append("</div></div>"); */
	
	

	}
		
		
		function minusCoupon()
		{
			var invaild_coupon = $("input[name='invaild_coupon']");
			invaild_coupon[invaild_coupon.length-1].remove();
			if(invaild_coupon.length == 2)
			{
				$("#invaild_add").remove();
				$("#body").append("<div class='form-group' style='text-align:center' id='invaild_add'><label class='col-sm-4 control-label'></label><div class='col-sm-8'><li title='增加'><a onclick ='addCoupon()'><i class='fa fa-plus-square text-blue'></i><span class='text-blue'></span>增加</a></li></div></div>");
			}
		}
	
	
		// 	$(function() {  
		$("#invaild_type").change(function() {
			// 	    alert($("#invaild_type").val());
			$("div[name='invaild_div']").each(function(){
				   $(this).remove()
				});
			if ($("#invaild_type").val() == 0) {
				$("#invaild_add").remove();
			  $("#body").append("<div class='form-group' style='text-align:center' name='invaild_div'>"
			  +"<label for='invaild_coupon' class='col-sm-4 control-label'>作废优惠券 </label>"
			  +"<div class='col-sm-8'><input class='form-control' id='invaild_coupon' name='invaild_coupon' style='width:100%'/></div></div>"
			  +"<div class='form-group' style='text-align:center' id='invaild_add'><label class='col-sm-4 control-label'></label><div class='col-sm-8'><li title='增加'><a onclick ='addCoupon()'><i class='fa fa-plus-square text-blue'></i><span class='text-blue'></span>增加</a></li></div></div>");
				/* $("#invaild_div").append("<label for='invaild_coupon' class='col-sm-4 control-label'>作废优惠券 </label>");
				$("#invaild_div").append("<div class='col-sm-8'><input class='form-control' id='invaild_coupon' name='invaild_coupon' style='width:100%'></div>");*/			
				}
			if ($("#invaild_type").val() == 1) {
				$("#invaild_add").remove();
				$("#body").append("<div class='form-group' style='text-align:center' name='invaild_div'><label for='invaild_start_coupon' class='col-sm-4 control-label'>" + "起始优惠券" 
				+ "</label>"+"<div class='col-sm-8'><input class='form-control' id='invaild_start_coupon' name='invaild_start_coupon' style='width:100%'/></div>"+"<br/><br/>"
				+ "<label for='invaild_coupon_num' class='col-sm-4 control-label'>" + "作废张数" + "</label>"
				+ "<div class='col-sm-8'><input class='form-control' id='invaild_coupon_num' name='invaild_coupon_num' style='width:100%'></div>");
				
				/* $("#body").append("<div class='col-sm-8'><input class='form-control' id='invaild_start_coupon' name='invaild_start_coupon' style='width:100%'></div>");
				$("#body").append("<br/><br/>");
				$("#body").append("<label for='invaild_coupon_num' class='col-sm-4 control-label'>" + "作废张数" + "</label>");
				$("#body").append("<div class='col-sm-8'><input class='form-control' id='invaild_coupon_num' name='invaild_coupon_num' style='width:100%'></div>"); */
			}
			if ($("#invaild_type").val() == 2) {
				$("#invaild_add").remove();
				$("#body").append("<div class='form-group' style='text-align:center' name='invaild_div'>"+"<div class='form-group' style='text-align:center' name='invaild_div'><label for='invaild_batch' class='col-sm-4 control-label'>作废批次 </label>"
				+"<div class='col-sm-8'><input class='form-control' id='invaild_batch' name='invaild_batch' style='width:100%'></div></div></div>");
				/* $("#invaild_div").append("<div class='form-group' style='text-align:center' name='invaild_div'><label for='invaild_batch' class='col-sm-4 control-label'>作废批次 </label>");
				$("#invaild_div").append("<div class='col-sm-8'><input class='form-control' id='invaild_batch' name='invaild_batch' style='width:100%'></div></div>"); */
			}
		})
		// 	});
	
		// 作废事件
		function invalid() {
			var invaild_type = $("#invaild_type").val();
			var invaild_coupons = "";
			var invaild_start_coupon = "";
			var invaild_coupon_num = "";
			var invaild_batch = "";
	
			if (invaild_type == 0) {
// 				invaild_coupon = $("#invaild_coupon").val().trim();
				
				var coupon_array = new Array();
				var i = 0;
				var flag = true;
				 $("input[name='invaild_coupon']").each(function(){
				   coupon_array[i] = $(this).val();
				   if(coupon_array[i] == null || coupon_array[i].length != 14)
				   {
				      alert("请输入14位有效优惠券!");
				      flag = false;
					  return false;
				   }
				   i++;
				}); 
				
				// 判断优惠券是否为有效
				if(!flag)
				{
				    return;
				}
				
				invaild_coupons = JSON.stringify(coupon_array);
// 				alert(invaild_coupons);
				
			}
	
			if (invaild_type == 1) {
				invaild_start_coupon = $("#invaild_start_coupon").val().trim();
				invaild_coupon_num = $("#invaild_coupon_num").val().trim();
				if(invaild_start_coupon == null || invaild_start_coupon.length != 14)
				{
				    alert("请输入14位有效优惠券!");
					return;
				}
				if(invaild_coupon_num == null || invaild_coupon_num.trim() == ""
				|| isNaN(invaild_coupon_num) || invaild_coupon_num >= 1000 || invaild_coupon_num == 0)
				{
				      alert("请输入小于1000的有效数字!");
					  return;
				}
				
			}
	
			if (invaild_type == 2) {
				invaild_batch = $("#invaild_batch").val().trim();
	
				if (invaild_batch == null || invaild_batch.length != 11) {
					alert("请输入11位有效优惠券批次!");
					return;
				}
			}
	
			if (vcheck.empty()) {
				return;
			}
	
			tool.confirm('确实要作废吗？', function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'invalidCoupon.shtml',
						data : {
							'invaild_type' : invaild_type,
							'invaild_coupons' : invaild_coupons,
							'invaild_start_coupon' : invaild_start_coupon,
							'invaild_coupon_num' : invaild_coupon_num,
							'invaild_batch' : invaild_batch
						},
						dataType : 'json',
						success : function(data, textStatus) {
							// 							if(data.success){
							// 								tool.closeDialog();
							// 								tool.formSubmit();
							// 							}else{
							alert(data.message);
						// 							}
						},
						error : function(data, textStatus) {
							alert('作废失败');
						}
					});
				}
			});
	
	
	
		}
	</script>
</body>

</html>