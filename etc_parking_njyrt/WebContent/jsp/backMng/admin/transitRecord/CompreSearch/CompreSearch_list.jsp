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
</head>
<body class="hold-transition skin-blue sidebar-mini">
	<%@include file="/jsp/common/bootstrap/header.jsp"%>
	<%@include file="/jsp/common/bootstrap/leftSide.jsp"%>

	<div id="mainContentDiv" style="margin-left: 180px">

		<!-- Main content -->
		<form action="" method="post" id="pageForm">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">
							<h3>流水检索</h3>
							<h6>支持订单号和车牌两种方式检索流水，以订单号优先</h6>
						</div>
						<div class="col-sm-6"></div>
						<div class="col-sm-12">
							<table class="queryTable">
								<tr>
									<td>订单号：<input type="text" id="orderid" style="display: inline;width: 200px;">
									</td>
									<td>开始日期：<input type="text" id="begindate"
										name="begindate" value="${queryParam.begindate}"
										placeholder="" autocomplete="off"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd' })"
										style="display: inline;">
									</td>
									<td>结束日期：<input type="text" id="enddate" name="enddate"
										value="${queryParam.enddate}" placeholder=""
										autocomplete="off"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd' })"
										style="display: inline;">
									</td>
									<td>车牌：<input id="plate" style="display: inline;"
										type="text" name="plate">
									</td><!-- 
									<td>精确车牌：<select id="realPlate"
										style="position:absolute;z-index:1;display: inline;width: 11%;margin-top:-6px;"
										onmousedown="if(this.options.length>6){this.size=7}"
										onblur="this.size=0" onchange="this.size=0">
											<option value="">--请选择--</option>
									</select>
									</td> -->
									<td>
										<button class="btn btn-primary"
											type="button" onclick="queryRecords();">查询</button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-hover table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>车牌</th>
									<th>时间</th>
									<th>车道</th>
									<th>操作员</th>
									<th>金额（元）</th>
									<th>支付方式</th>
									<th>流水类型</th>
									<th>详情</th>
									<!-- 
									<th></th> -->
								</tr>
							</thead>
							<tbody>
								<%-- <c:forEach items="${list }" var="obj" varStatus="status">
									<tr>

									</tr>
								</c:forEach> --%>
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
		</form>
		<!-- /.content -->
	</div>
	<%@include file="/jsp/common/bootstrap/footer.jsp"%>

	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true"></div>

	<script type="text/javascript">
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
			var orderid = $("#orderid").val();
			if(orderid!="" && orderid.length!=21){
				alert("请输入完整21位订单号");
				return;
			}
			var plate = $("#plate").val();
			if(orderid=="" && plate.length<6){
				alert("车牌不能少于6位！");
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
				data:{"orderid":orderid,"plate":plate,"begindate":begindate,"enddate":enddate},
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
			var url = "queryDetail.shtml?license="+encodeURI(encodeURI(license))+"&time="+time+"&type="+type;
			window.open(url,"_blank","top=150px,left=230px,width=700,height=400,scrollbars=yes");
		}
	</script>
</body>
</html>