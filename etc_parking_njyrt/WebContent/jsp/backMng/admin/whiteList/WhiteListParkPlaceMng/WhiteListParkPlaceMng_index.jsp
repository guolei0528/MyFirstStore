<!DOCTYPE html >
<%@page import="com.project.common.tool.AppConst"%>
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
		<form action="list.shtml" method="post" id="pageForm">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>车辆牌照</td>
									<td><input type="text" id="query_mv_license" 
										name="query_mv_license" value="${queryParam.query_mv_license}"
										placeholder="" autocomplete="off"></td>
									<td>车位编号</td>
									<td><input type="text" id="query_usernumber" 
										name="query_usernumber" value="${queryParam.query_usernumber}"
										placeholder="" autocomplete="off"></td>	
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td>
										<button class="btn btn-primary" type="submit">确定</button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<input type="file" id="file" name="file" style="display: none;">

				</div>
			</div>
		</form>
		<!-- /.content -->
		<div style="color: red;margin-left: 40px;">友情提醒：请先确定查询条件(至少一个)!</div>
	</div>
	
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
		function divPage(type){
	        var to_page;
	        if(type==1){
	        	to_page = 1;
	        }else if(type==2){
	        	to_page = parseInt(document.getElementById('currentPage').value) - 1; 
	        }else if(type==3){
	        	to_page = parseInt(document.getElementById('currentPage').value) + 1;
	        }else if(type==4){
	        	to_page = parseInt(document.getElementById('pageCount').value);
	        }else if(type==5){
	        	if(document.getElementById('selPage').value.length==0){
	        		return ;
	        	}
	        	to_page = parseInt(document.getElementById('selPage').value);
	        }
	        if(to_page < 1){
	        	to_page = 1;
	        }
	        if(to_page > parseInt(document.getElementById('pageCount').value)){
	        	to_page = parseInt(document.getElementById('pageCount').value);
	        }
	        document.getElementById('currentPage').value = to_page;
	        document.getElementById('pageForm').submit();
	    }
	    //只允许输入的值是否为0--9，左向方向键及删除键
		function checkCharIsNumber(){
			if(event.keyCode==13){//回车键
				divPage(5);
				return false;
			}else if((event.keyCode<=57&&event.keyCode>=48)||(event.keyCode<=105&&event.keyCode>=96)||
				(event.keyCode==8||event.keyCode==37||event.keyCode==39)){
				return true;
			}else{
				return false;
			}
		}
	
		$(function() {
			$("#file").change(function() {
				$.ajaxFileUpload({
						url : 'upload.shtml',
						secureuri : false,
						fileElementId : 'file',
						dataType : 'json',
						success : function(data, status) {
							if (data.success) {
								$("#file").attr("value", '');
								tool.confirm("导入成功，是否刷新页面", function(res) {
									if (res) {
										location.reload();
									}
	
								});
							} else {
								$("#file").attr("value", '');
								tool.alert("白名单可能存在非法参数，请核实");
							}
						}
					});
			}
			);
		});

	</script>
</body>
</html>