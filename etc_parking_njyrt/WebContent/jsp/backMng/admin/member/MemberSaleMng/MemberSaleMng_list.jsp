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
						<div class="col-sm-6">员工售卖信息管理</div>
						<div class="col-sm-6">
							<div class="dataTables_button">
								<button type="button" class="btn btn-default"
									onclick="tool.dialog('add.shtml')">
									<i class="fa fa-plus text-green" aria-hidden="true"></i>新增
								</button>
							</div>
						</div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>员工编号</td>
									<td><input type="text" id="query_member_id"
										name="query_member_id" value="${queryParam.query_member_id}"
										placeholder="" autocomplete="off"></td>
									<td>办理类型</td>
									<td><select name="query_member_type"
										id="query_member_type">
											<option></option>
											<option value="04">月票</option>
											<option value="06">次票</option>
											<option value="05">年票</option>
									</select></td>
									<td>
										<button class="btn btn-primary" type="submit">确定</button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>序号</th>
									<th>员工编号</th>
									<th>员工姓名</th>
									<th>办理类型</th>
									<th>办理时间</th>
									<th>使用开始时间</th>
									<th>使用结束时间</th>
									<th>可使用总次数</th>
									<th>缴费金额</th>
									<!-- 								<th >已经停车累计时间（分）</th> -->
									<!-- 								<th>已经停车停车累计次数</th> -->
									<th>停车场编号</th>
									<th>区域编号</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
										<td>${page.startRow + status.index+1}</td>
										<td>${obj.memberId}</td>
										<td>${obj.memberName}</td>
										<td><c:choose>
												<c:when test="${obj.memberType==04}">月票</c:when>
												<c:when test="${obj.memberType==06}">次票</c:when>
												<c:when test="${obj.memberType==05}">年票</c:when>
											</c:choose></td>
										<td>${obj.issueTime }</td>
										<td>${obj.beginTime }</td>
										<td>${obj.endTime }</td>
										<td>${obj.count }</td>
										<td>${obj.money }</td>
										<%-- 									<td>${obj.totalMinute }</td> --%>
										<%-- 									<td>${obj.totalCount }</td> --%>
										<td>${obj.parkId }</td>
										<td>${obj.areaId }</td>
										<td>
											<ul class="list-inline">
												<%-- 											<li title="查看"><a href="javascript:tool.dialog('view.shtml?parkId=${obj.parkId }&areaId=${obj.areaId }&memberId=${obj.memberId }&memberType=${obj.memberType } --%>
												<%-- 											&issueTime=<fmt:formatDate value="${obj.issueTime}" pattern="yyyy-MM-ddHH:mm:ss"/> --%>
												<%-- 											&beginTime=<fmt:formatDate value="${obj.beginTime}" pattern="yyyy-MM-ddHH:mm:ss"/> --%>
												<%-- 											&endTime=<fmt:formatDate value="${obj.endTime}" pattern="yyyy-MM-ddHH:mm:ss"/> --%>
												<!-- 											')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>查看</a></li> -->


												<li title="查看"><a
													href="javascript:viewDate('${obj.memberId}','${obj.memberType}','${obj.issueTime}','${obj.beginTime}','${obj.endTime}','${obj.parkId}','${obj.areaId}')"><i
														class="fa fa-file-text-o text-blue"></i><span
														class="text-blue"></span>查看</a></li>

												<!-- 												<li title="查看"><a -->
												<%-- 													href="javascript:viewDate({memberId:'${obj.memberId}',memberType:'${obj.memberType}',issueTime:'${obj.issueTime}',beginTime:'${obj.beginTime}',endTime:'${obj.endTime}',parkId:'${obj.parkId}',areaId:'${obj.areaId}'})"><i --%>
												<!-- 														class="fa fa-file-text-o text-blue"></i><span -->
												<!-- 														class="text-blue"></span>查看</a></li> -->
												<li title="修改"><a
													href="javascript:editDate('${obj.memberId}','${obj.memberType}','${obj.issueTime}','${obj.beginTime}','${obj.endTime}','${obj.parkId}','${obj.areaId}')"><i
														class="fa fa-pencil-square-o text-green"></i><span
														class="text-green"></span>修改</a></li>
												<li title="删除"><a
													href="javascript:deleteData('${obj.memberId}','${obj.memberType}','${obj.issueTime}','${obj.beginTime}','${obj.endTime}','${obj.parkId}','${obj.areaId}')"><i
														class="fa fa-trash-o text-red"></i> <span class="text-red"></span>删除</a></li>
											</ul>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.box-body -->
					<%@include file="/jsp/common/bootstrap/divPage.jsp"%>
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
		function viewDate(memberId, memberType, issueTime, beginTime, endTime, parkId, areaId) {
			// 		function viewDate(PARAMS) {
			// 			var memberId = $("#memberId").val();
			// 			var memberType = $("#memberType").val();
			// 			var issueTime = $("#issueTime").val();
			// 			var beginTime = $("#beginTime").val();
			// 			var endTime = $("#endTime").val();
			// 			var parkId = $("#parkId").val();
			// 			var areaId = $("#areaId").val();
			// 			var temp = document.createElement("form");
			// 			temp.action = 'view.shtml';
			// 			temp.method = "post";
			// 			temp.style.display = "none";
			// 			for (var x in PARAMS) {
			// 				var opt = document.createElement("textarea");
			// 				opt.name = x;
			// 				opt.value = PARAMS[x];
			// 				// alert(opt.name)        
			// 				temp.appendChild(opt);
			// 			}
			// 			document.body.appendChild(temp);
			// 			temp.submit();
			// 			return temp;
	
			$.post("view.shtml", {
				"memberId" : memberId,
				"memberType" : memberType,
				"issueTime" : issueTime,
				"beginTime" : beginTime,
				"endTime" : endTime,
				"parkId" : parkId,
				"areaId" : areaId
			}, function(data) {
				var w = 450;
				var h = 700;
				$("#myModal").html("");
				$('#myModal').modal({
					keyboard : false,
					backdrop : 'static'
				});
				$("#myModal").html(data);
			}
			);
	
			// 			var w=450;
			// 			var h=700;
			// 			if(!tool.isEmpty(width)){
			// 				w=width;
			// 			}
			// 			if(!tool.isEmpty(height)){
			// 				h=height;
			// 			}
			// 			$("#myModal").html("");
			// 			$('#myModal').modal({
			// 			  keyboard: false,
			// 			  backdrop:'static'
			// 			});
			//             return $("#myModal").load('view.shtml');;
	
			// var temp = document.createElement("form");
			// temp.method = "get";
			// temp.style.display = "none";
			// var data = "?";
			// for (var x in PARAMS)
			// {
			// data += x + "=" + PARAMS[x] + "&";;
			// }
			// data = data.slice(0, data.length-1);
	
	
			// temp.action = 'view.shtml' + data;
			// document.body.appendChild(temp);
			// temp.submit();
			// return temp; 
	
	
		// 			$.ajax({
		// 				type:'post',
		// 				url:'view.shtml',
		// 				data:{
		// 				"memberId":memberId,
		// 				"memberType":memberType,
		// 				"issueTime":issueTime,
		// 				"beginTime":beginTime,
		// 				"endTime":endTime,
		// 				"parkId":parkId,
		// 				"areaId":areaId
		// 				},
		// 				dataType : 'json',
		// 						success : function(data, textStatus) {
		// 							if(data.success){
		// 								tool.closeDialog();
		// 								tool.formSubmit();
		// 							}else{
		// 								tool.alert(data.message);
		// 							}
		// 						},
		// 						error : function(data, textStatus) {
		// 							tool.alert('');
		// 						}
		// 			});
		}
	
	
		function editDate(memberId, memberType, issueTime, beginTime, endTime, parkId, areaId) {
			$.post("edit.shtml", {
				"memberId" : memberId,
				"memberType" : memberType,
				"issueTime" : issueTime,
				"beginTime" : beginTime,
				"endTime" : endTime,
				"parkId" : parkId,
				"areaId" : areaId
			}, function(data) {
				var w = 450;
				var h = 700;
				$("#myModal").html("");
				$('#myModal').modal({
					keyboard : false,
					backdrop : 'static'
				});
				$("#myModal").html(data);
			}
			);
		}
	
	
	
		function deleteData(memberId, memberType, issueTime, beginTime, endTime, parkId, areaId) {
			tool.confirm("确实要删除该条目吗？", function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'delete.shtml',
						data : {
							"memberId" : memberId,
							"memberType" : memberType,
							"issueTime" : issueTime,
							"beginTime" : beginTime,
							"endTime" : endTime,
							"parkId" : parkId,
							"areaId" : areaId
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
	</script>
</body>
</html>