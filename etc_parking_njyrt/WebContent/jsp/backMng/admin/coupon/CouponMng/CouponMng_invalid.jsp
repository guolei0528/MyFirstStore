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
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>

	<div id="mainContentDiv" style="margin-left: 180px">

		<!-- Main content -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">优惠券失效</h4>
				</div>

				<div class="modal-body">
					<form class="form-horizontal">
					<h4 class="modal-title" id="myModalLabel">优惠券失效</h4>
						<div class="row">
					<div class="col-md-2 text-right"><p> 发行商</p></div>
					<div class="col-md-4 text-left">
						12345
					</div>
					<div class="col-md-2 text-right"><p> 发行日期</p></div>
					<div class="col-md-4 text-left">
						20123
					</div>
              		</div>
					
					
					<div class="box-body">
							
					</div>
					</form>
				</div>

				<div class="modal-footer">
					<button id="modal_save_button" type="button"
						class="btn btn-primary">保存</button>
<!-- 					<button type="button" class="btn btn-default" data-dismiss="modal">清空</button> -->
				</div>
	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
		$(function() {
			//tool.selRadio('DOM_ID','${bean.DOM_VALUE}');
			var URL = '${contentPath }/personalCenter/user/ModifyPassword/update.shtml';
	
			$("#modal_save_button").click(function() {
				//RADIO赋值
				//var x=radioVal('DOM_NAME');
				var OLD_PWD = $("#OLD_PWD").val();
				var NEW_PWD = $("#NEW_PWD").val();
				var RE_PWD = $("#RE_PWD").val();
				if (vcheck.empty()) {
					return;
				}
	
				if (NEW_PWD != RE_PWD) {
					tool.alert("两次密码输入不一致");
					return;
				}
	
				tool.confirm('确实要提交吗？', function(result) {
					if (result) {
						$.ajax({
							type : 'post',
							url : URL,
							data : {
								'old_pwd' : OLD_PWD,
								'new_pwd' : NEW_PWD
							},
							dataType : 'json',
							success : function(data, textStatus) {
								if (data.success) {
								    tool.alert('修改成功');
// 									window.location.reload();
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
</body>
</html>