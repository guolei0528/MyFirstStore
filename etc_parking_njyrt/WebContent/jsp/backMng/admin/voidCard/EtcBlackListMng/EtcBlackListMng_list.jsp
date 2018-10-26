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
		<form action="list.shtml" method="post" id="pageForm">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">ETC黑名单管理</div>
						<div class="col-sm-6">
							<br> <br>
						</div>
						<div class="col-sm-12 ">
							<table class="queryTable">
								<!-- 							<tr> 
								<td>作废时间</td>
								<td>
									<input type="text" id="query_cancel_time_from" name="query_cancel_time_from" value="${queryParam.query_cancel_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
							<input type="text" id="query_cancel_time_to" name="query_cancel_time_to" value="${queryParam.query_cancel_time_to}" onfocus="WdatePicker()">
								</td>
								<td>录入时间</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_from" value="${queryParam.query_in_time_from}" onfocus="WdatePicker()">
								</td>
								<td>-</td>
								<td>
									<input type="text" id="query_in_time_from" name="query_in_time_to" value="${queryParam.query_in_time_to}" onfocus="WdatePicker()">
								</td>
								<td>黑名单类型</td>
								<td>
									<select name="query_b_list_type" id="query_b_list_type">
										<option></option>
										<option value="1">欠费</option>
										<option value="2">逃票</option>
									</select>
								</td>
								
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>-->
								<tr>
									<!-- 								<td>发行方ID</td>
								<td>
									<input type="text" id="query_IssuerID" name="query_IssuerID" value="${queryParam.query_IssuerID}" placeholder="" autocomplete="off">
								</td> -->
									<td>卡号</td>
									<td><input type="text" id="query_CardID"
										name="query_CardID" value="${queryParam.query_CardID}"
										placeholder="" autocomplete="off"></td>
									<td>车牌号</td>
									<td><input type="text" id="query_License"
										name="query_License" value="${queryParam.query_License}"
										placeholder="" autocomplete="off"></td>
									<td>OBU编号</td>
									<td><input type="text" id="query_OBUID" name="query_OBUID"
										value="${queryParam.query_OBUID}" placeholder=""
										autocomplete="off"></td>
									<td>省份</td>
									<td><select name="province" id="province">
											<option value="0">所有</option>
											<option value="32">江苏</option>
											<option value="11">北京</option>
											<option value="12">天津</option>
											<option value="13">河北</option>
											<option value="14">山西</option>
											<option value="21">辽宁</option>
											<option value="22">吉林</option>
											<option value="31">上海</option>
											<option value="33">浙江</option>
											<option value="34">安徽</option>
											<option value="35">福建</option>
											<option value="36">江西</option>
											<option value="37">山东</option>
											<option value="41">河南</option>
											<option value="42">湖北</option>
											<option value="43">湖南</option>
											<option value="44">广东</option>
											<option value="50">重庆</option>
											<option value="51">四川</option>
											<option value="52">贵州</option>
											<option value="53">云南</option>
											<option value="61">陕西</option>
											<option value="62">甘肃</option>
											<option value="63">青海</option>
											<option value="64">宁夏</option>
											<option value="15">内蒙</option>
											<option value="23">龙江</option>
											<option value="45">广西</option>
											<option value="65">新疆</option>
									</select></td>
									<td>
										<button class="btn btn-primary" type="submit">查询</button>
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
									<th>发行方ID</th>
									<th>卡网络号</th>
									<th>卡号</th>
									<th>电子标签号</th>
									<th>车牌</th>
									<th>状态类型</th>
									<th>发行方版本号</th>
									<th>发行方生成状态名单时间</th>
									<th>生效时间</th>
									<th>注释</th>
									<th>黑名单类型</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
										<td>${obj.issuerID }</td>
										<td>${obj.netNo }</td>
										<td>${obj.cardID}</td>
										<td>${empty obj.obuID ? '无':obj.obuID }</td>
										<td>${empty obj.license ? '无':obj.license }</td>
										<td>${obj.status }</td>
										<td>${obj.issuerBlackVersion }</td>
										<td><fmt:formatDate value="${obj.creationTime}"
												type="both" /></td>
										<td><fmt:formatDate value="${obj.startTime}" type="both" />
										</td>
										<td>${empty obj.sComment ? '无':obj.sComment }</td>
										<td>${obj.blackType }</td>
										<!-- 									<td> -->
										<!-- 										<UL CLASS="LIST-INLINE"> -->
										<%-- 											<LI TITLE="查看"><A HREF="JAVASCRIPT:TOOL.DIALOG('VIEW.SHTML?CARD_ID=${OBJ.CARD_ID }&CARD_NETWORK=${OBJ.CARD_NETWORK}')"><I CLASS="FA FA-FILE-TEXT-O TEXT-BLUE"></I><SPAN CLASS="TEXT-BLUE"></SPAN>查看</A></LI> --%>
										<!-- 										</UL> -->
										<!-- 									</TD> -->
									</tr>
								</c:forEach>
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
		tool.selOption("province", '${queryParam.province}');
		
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
	
		function test() {
			var start = new Date();
			$.ajax({
				url : 'testETC.shtml',
				data : {
					"card" : 'ETC_VOID_CARD:卡15017540320221896213|苏15017540320221896213'
				},
				dataType : 'json',
				type : 'post',
				success : function(data) {}
			});
	
		}
	</script>
</body>
</html>