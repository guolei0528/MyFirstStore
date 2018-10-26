<!DOCTYPE html >
<%@page import="com.redoak.jar.util.PropertiesUtil"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>出口流水明细</title>
<%@include file="/jsp/common/bootstrap/commonJsCss.jsp"%>
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%-- <%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%> --%>

	<div id="mainContentDiv">

		<!-- Main content -->
		<div class="container" style="width: 100%">
			<div style="margin: 0 auto;">
				<h3>出口流水明细</h3>
			</div>
			<div class="box mt10">

				<div class="box-body">
					<table id="dataTable" class="table table-hover"
						style="font-size: 15px">
						<tbody>
							<tr>
								<td class="text-right">车牌</td>
								<td>${bean.mv_license }</td>
								<td colspan="2"></td>
							</tr>
							<tr>
								<td class="text-right">入口车道</td>
								<td>${bean.entry_lane }</td>
								<td class="text-right">出口车道</td>
								<td>${bean.exit_lane }</td>
							</tr>
							<tr>
								<td class="text-right">入口时间</td>
								<td>${bean.entry_time }</td>
								<td class="text-right">出口时间</td>
								<td>${bean.exit_time }</td>
							</tr>
							<tr>
								<td class="text-right">入口操作员</td>
								<td>${bean.entry_operator }</td>
								<td class="text-right">出口操作员</td>
								<td>${bean.exit_operator }</td>
							</tr>
							<tr>
								<td class="text-right">支付方式</td>
								<td>${bean.pay_method }</td>
								<td class="text-right">金额（元）</td>
								<td>${bean.totaltoll }</td>
							</tr>
							<tr>
								<td colspan="4"><img width="100%"
									src="/img/${fn:substringBefore(bean.imagepath,'-')}/${fn:substring(fn:substringAfter(bean.imagepath,'-'), 0, 8)}/${bean.imagepath}">
								</td>
							</tr>
							<!-- <tr>
								<td colspan="4"><img width="100%"
									src="/imgs/1001/20170823/1001-20170823062127.jpg"></td>
							</tr> -->
						</tbody>
						<!-- 
						<tfoot>
							<tr>
								<th></th>
							</tr>
						</tfoot>
						 -->
					</table>
				</div>

				<div class="modal fade bs-example-modal-lg" tabindex="-1"
					role="dialog" aria-labelledby="myLargeModalLabel">
					<div class="modal-dialog modal-lg" role="document">
						<div class="modal-content" id="dktp">
							<img width="100%">
						</div>
					</div>
				</div>
				<!-- /.box-body -->
				<%-- <%@include file="/jsp/common/bootstrap/divPage.jsp"%> --%>
			</div>
			<!-- /.box -->

		</div>
		<!-- /.content -->
	</div>
	<%-- <%@include file="/jsp/common/bootstrap/footer.jsp"%> --%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
	
	
		function explore(obj) {
			$("#dktp").find("img").attr("src", $(obj).attr("dktp"));
		}
		function deleteData(dataId) {
			tool.confirm("确实要删除该条目吗？", function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'delete.shtml',
						data : {
							"ID" : dataId
						},
						dataType : 'json',
						success : function(msg) {
							if (msg.success) {
								tool.formSubmit();
							} else {
								tool.alert(msg.message);
							}
						},
						error : function(msg) {
							tool.alert("删除失败！");
						}
					});
				}
			});
		}
		
		function queryPlates(){
			var plate = $("#plate").val();
			//console.info(plate.length+"--"+plate);
			if(plate.length>=3 && plate.length<7){
				$.ajax({
					url:"queryPlates.shtml",
					type:"post",
					dataType:"json",
					data:{"plate":plate},
					success:function(list){
						console.info(list.length);
						if(list && list.length>0){
							var opts = "<option value=''>--请选择--</option>";
							for(var i=0;i<list.length;i++){
								opts += "<option value='"+list[i]+"'>"+list[i]+"</option>"
							}
							$("#realPlate").html(opts);
						}
					},
					error:function(){
					
					}
				});
			}else{
				$("#realPlate").html("<option value=''>--请选择--</option>");
			}
		}
		
		function queryRecords(){
			var plate = $("#realPlate").val();
			if(plate==""){
				alert("请选择精确车牌！");
				return;
			}
			var begindate = $("#begindate").val();
			var enddate = $("#enddate").val();
			console.log(plate+","+begindate+","+enddate)
			if(begindate=="" && enddate!=""){
				alert("请选择开始日期！");
				return;
			}
			if(begindate && begindate!=""){
				if(enddate==""){
					alert("请选择结束日期！");
					return;
				}else if(begindate>enddate){
					alert("开始日期不能晚于结束日期！");
					return;
				}
			}
			$("#dataTable tbody").html("<tr align='center'><td colspan='8'>检索中···</td></tr>");
			$.ajax({
				url:"queryRecords.shtml",
				type:"post",
				dataType:"json",
				data:{"plate":plate,"begindate":begindate,"enddate":enddate},
				success:function(list){
					if(list.length>0){
						var str = "";
						for(var i=0;i<list.length;i++){
							var r = list[i];
							str += "<tr align='center'><td>"+r.license+"</td><td>"+r.time+"</td><td>"+r.lanename+"</td><td>"+r.operator+"</td><td>"
								+r.totaltoll+"</td><td>"+r.paymethod+"</td><td>"+r.typename+"</td><td>"
								+"<button class='btn btn-link btn-sm' type='button' onclick='queryDetail(\""+r.license+"\",\""+r.time+"\","+r.type+");'>详情</button></td></tr>";
						}
						$("#dataTable tbody").html(str);
					}else{
						$("#dataTable tbody").html("<tr align='center'><td colspan='8'>未检索到记录</td></tr>");
					}
				},
				error:function(){
				
				}
			});
		}
		function queryDetail(license,time,type){
			console.info(license+","+time+","+type);
			var url = "queryDetail.shtml?plate="+license+"&time="+time+"&type="+type
			window.open(url,"_blank","");
		}
	</script>
</body>
</html>