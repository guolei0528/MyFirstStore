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
		<form action="comfirmList.shtml" method="post" id="pageForm">
			<div class="container" style="width: 100%">

				<div class="box mt10">
					<div class="box-header">
						<div class="col-sm-6">白名单审核</div>
						<!-- 						<div class="col-sm-6"> 
							<div class="dataTables_button">
								<button type="button" class="btn btn-default"
									onclick="tool.dialog('comfirmAdd.shtml')">
									<i class="fa fa-plus text-green" aria-hidden="true"></i>新增
								</button>
							</div>
						</div>-->
						<div class="col-sm-12 ">
							<table class="queryTable">
								<tr>
									<td>车辆牌照</td>
									<td><input type="text" id="query_mv_license"
										name="query_mv_license" value="${queryParam.query_mv_license}"
										placeholder="" autocomplete="off"></td> 
									<!-- 									<td>OBU编号</td> 
									<td><input type="text" id="query_color"
										name="query_obu_id" value="${queryParam.query_obu_id}"
										placeholder="" autocomplete="off"></td> -->
<!-- 									<td>车牌颜色</td>
									<td><input type="text" id="query_status"
										name="query_color" value="${queryParam.query_color}"
										placeholder="" autocomplete="off"></td>
									<td>有效时间</td> 
									<td><input type="text" id="query_cancel_time_from"
										name="query_cancel_time_from"
										value="${queryParam.query_cancel_time_from}"
										onfocus="WdatePicker()"></td>
									<td>-</td>
									<td><input type="text" id="query_cancel_time_to"
										name="query_cancel_time_to"
										value="${queryParam.query_cancel_time_to}"
										onfocus="WdatePicker()"></td>
									<td>车主姓名</td>
									<td><input type="text" id="query_mv_license"
										name="query_mv_license" value="${queryParam.query_mv_license}"
										placeholder="" autocomplete="off"></td>
									<td>公司</td>
									<td><input type="text" id="query_mv_license"
										name="query_mv_license" value="${queryParam.query_mv_license}"
										placeholder="" autocomplete="off"></td>-->
									<td>车位编号</td> 
									<td><input type="text" id="query_user_number"
										name="query_user_number" value="${queryParam.query_user_number}"
										placeholder="" autocomplete="off"></td>
									<td>车主姓名</td> 
									<td><input type="text" id="query_user_name"
										name="query_user_name" value="${queryParam.query_user_name}"
										placeholder="" autocomplete="off"></td>
									<td>联系方式</td> 
									<td><input type="text" id="query_user_name"
										name="query_phone" value="${queryParam.query_phone}"
										placeholder="" autocomplete="off"></td>
									<td>
										<button class="btn btn-primary" type="submit">确定</button>
									</td>
									<a style="float:right">
										<button class="btn btn-success green" type="button" onClick="allow(1)">通过</button>
										<!-- 										<button class="btn btn-danger red" type="submit">驳回</button> -->
									</a>
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
									<th><input type="checkbox" name="alls"
										onClick="selectAll()" title="全选/取消全选"></th>
									<th>车辆牌照</th>
									<th>车牌颜色</th>
									<!-- 									<th>OBU编号</th> -->
									<th>车辆类型</th>
									<th>开始时间</th>
									<th>截止时间</th>
							
									<th>车位编号</th>
									<th>车主姓名</th>
									<th>联系方式</th>

									<th>审核状态</th>
									<th>提交用户</th>
<!-- 									<th>审核用户</th> -->
									<th>录入时间</th>
								<%-- 	<c:if test="${areaid == '0'}"><th>所在校区</th></c:if> --%>
									<th>操作</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
										<%-- <td><input type="checkbox" class="whiteList_index"
											name="index_id" title="选择/取消"
											value="${obj.mv_license},${obj.color},<fmt:formatDate value="${obj.create_time}"
												type="both" />,${obj.status},${obj.lane_info}"></td> --%>
										<td><input type="checkbox" class="whiteList_index"
											name="index_id" title="选择/取消"
											value="${obj.mv_license},${obj.color},<fmt:formatDate value="${obj.create_time}"
												type="both" />,${obj.status},${obj.user_number},${obj.toll_type},${obj.spare1}"></td>			
										<td>${obj.mv_license}</td>
										<td><c:choose>
												<c:when test="${obj.color==0 }">蓝牌</c:when>
												<c:when test="${obj.color==1 }">黄牌</c:when>
												<c:when test="${obj.color==2 }">黑牌</c:when>
												<c:when test="${obj.color==3 }">白牌</c:when>
												<c:when test="${obj.color==4 }">绿牌</c:when>
												<c:otherwise>颜色未知</c:otherwise>
											</c:choose></td>
										<%-- 										<td>${obj.obu_id}</td> --%>
										<td><c:choose>
												<c:when test="${obj.vehicle_class == 1}">
													小客车
												</c:when>
												<c:when test="${obj.vehicle_class == 2}">
													大客车
												</c:when>
												<c:when test="${obj.vehicle_class == 3}">
													货车
												</c:when>
											</c:choose></td>
										<td><fmt:formatDate value="${obj.valid_start_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td><fmt:formatDate value="${obj.valid_end_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
									
										<td>${obj.user_number}</td>
										<td>${obj.user_name}</td>
										<td>${obj.phone}</td>
<%-- 										<td>${obj.dept_id}</td> --%>
								
		
										<td><c:choose>
												<c:when test="${obj.status == 1}">
													待审核
												</c:when>
												<c:when test="${obj.status == 3}">
													已生效
												</c:when>
												<c:when test="${obj.status == 4}">
													审核未通过
												</c:when>
												<c:when test="${obj.status == 5}">
													作废待审核
												</c:when>
												<c:when test="${obj.status == 6}">
													已作废
												</c:when>
												<c:when test="${obj.status == 7}">
													作废未通过
												</c:when>
											</c:choose></td>
										<td>
										<c:choose>
												<c:when test="${obj.status == 1}">
													${obj.initiate_name}
												</c:when>
												<c:when test="${obj.status == 3}">
													${obj.initiate_name}
												</c:when>
												<c:when test="${obj.status == 4}">
													${obj.initiate_name}
												</c:when>
												<c:when test="${obj.status == 5}">
													${obj.invalid_initiate_name}
												</c:when>
												<c:when test="${obj.status == 6}">
													${obj.invalid_initiate_name}
												</c:when>
												<c:when test="${obj.status == 7}">
													${obj.invalid_initiate_name}
												</c:when>
										</c:choose>
										
										</td>
<!-- 										<td> 
										<c:choose>
										<c:when test="${obj.status == 1}">
													${obj.comfirm_name}
												</c:when>
												<c:when test="${obj.status == 3}">
													${obj.comfirm_name}
												</c:when>
												<c:when test="${obj.status == 4}">
													${obj.comfirm_name}
												</c:when>
												<c:when test="${obj.status == 5}">
													${obj.invalid_comfirm_name}
												</c:when>
												<c:when test="${obj.status == 6}">
													${obj.invalid_comfirm_name}
												</c:when>
												<c:when test="${obj.status == 7}">
													${obj.invalid_comfirm_name}
												</c:when>
												</c:choose>
										</td> -->
										<td><fmt:formatDate value="${obj.create_time}"
												type="both" /></td>

									<%-- 	<c:if test="${areaid == '0'}">
											<td><c:forEach items="${areaInfoList }" var="areainfo"
													varStatus="status">
													<c:set var="area_id_str" value="|${areainfo.area_id}-"></c:set>
													<c:if test="${fn:contains(obj.lane_info,area_id_str)}">
														<c:set var="area_id_checked" value="${areainfo.area_id}"></c:set>
														${areainfo.area_name}
													</c:if>
												</c:forEach></td>
										</c:if>
										<c:if test="${areaid != '0'}">
											<c:set var="area_id_checked" value="${areaid}"></c:set>
										</c:if> --%>
										<td>
									<%-- 		<ul class="list-inline">
												<c:if test="${obj.status == 1}">
													<li title="审核通过"><a
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','${obj.obu_id }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',3,'${area_id_checked}')"><span
															class="fa fa-check-square-o text-green"></span>审核通过</a></li>
													<li title="审核驳回"><a 
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','${obj.obu_id }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',4,'${area_id_checked}')"><span
															class="fa fa-minus-square-o text-red"></span>驳回</a></li>
												</c:if>
												<c:if test="${obj.status == 5}">
													<li title="审核通过"><a
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','${obj.obu_id }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',6,'${area_id_checked}')"><span
															class="fa fa-check-square-o text-green"></span>审核通过</a></li>
													<li title="审核驳回"><a 
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','${obj.obu_id }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',7,'${area_id_checked}')"><span
															class="fa fa-minus-square-o text-red"></span>驳回</a></li>
												</c:if>
											</ul> --%>
											<ul class="list-inline">
												<c:if test="${obj.status == 1}">
													<li title="审核通过"><a
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',3,'${obj.user_number}','${obj.toll_type}','${obj.spare1}')"><span
															class="fa fa-check-square-o text-green"></span>审核通过</a></li>
													<li title="审核驳回"><a 
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',4,'${obj.user_number}','${obj.toll_type}','${obj.spare1}')"><span
															class="fa fa-minus-square-o text-red"></span>驳回</a></li>
												</c:if>
												<c:if test="${obj.status == 5}">
													<li title="审核通过"><a
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',6,'${obj.user_number}','${obj.toll_type}','${obj.spare1}')"><span
															class="fa fa-check-square-o text-green"></span>审核通过</a></li>
													<li title="审核驳回"><a 
														href="javascript:comfirmData('${obj.mv_license }','${obj.color }','${obj.vehicle_class }','<fmt:formatDate value="${obj.create_time}"
												type="both" />',7,'${obj.user_number}','${obj.toll_type}','${obj.spare1}')"><span
															class="fa fa-minus-square-o text-red"></span>驳回</a></li>
												</c:if>
											</ul>
										</td>
										
										
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
	
		/* 是否全选标记 */
		var checkedAll = false;
	
		//全局数组,
		var checkedData = new Array();
	
		/* 全选/取消全选
		* formName 所在form的name值
		* checkboxName checkbox的name值
		* 注意：所有checkbox的name值都必须一样，这样才能达到全选的效果
		*/
		function selectAll() {
			// 			var table = $("#dataTable");
			var form = document.all.item('pageForm');
			var elements = form.elements['index_id'];
			for (var i = 0; i < elements.length; i++) {
				var e = elements[i];
				if (checkedAll) {
					e.checked = false;
					form.alls.checked = false;
				} else {
					e.checked = true;
					form.alls.checked = true;
				}
			}
			if (checkedAll) {
				checkedAll = false;
			} else {
				checkedAll = true;
			}
			
			changeCheckbox();
		}
	
	function changeCheckbox()
	{
	var chks = $(".whiteList_index");
				$(".whiteList_index").each(function() {
					if (this.checked) {
						for (var i = 0; i < checkedData.length; i++) {
							if (checkedData[i] == this.value) {
								return;
							}
						}
						checkedData.push(this.value);
					} else {
						for (var i = 0; i < checkedData.length; i++) {
							if (checkedData[i] == this.value) {
								checkedData.remove(this.value);
							}
						}
					}
				});
	}
	
	
		Array.prototype.remove = function(val) {
			var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
		};
		/**
			$('.whiteList_index').click(function() {
				alert(12345);
		
				// 			var uid = $(this).attr('uid');
				SetArticleId(this, '1');
			})
		
		
			function SetArticleId(o, i) {
				if (o.checked) {
					AddCookie(i)
				} else {
		// 				RemoveCookie(i)
				}
			}
		
			function AddCookie(i) {
				d = GetCookie("ArticleId");
				if (d == "")
					d = "|";
				if (d.indexOf("|" + i + "|") == -1) {
					d += i + "|";
					SetCookie("ArticleId", d);
				}
			}
			
			//塞入cookie数据
			 function SetCookie(name, value) {
		     	 document.cookie = name + "=" + escape(value); 
		         }
		
		//获取cookie参数
			function GetCookie(name) {
				if (document.cookie.length > 0) {
					c_start = document.cookie.indexOf(name + "=");
					if (c_start != -1) {
						c_start = c_start + name.length + 1;
						c_end = document.cookie.indexOf(";", c_start);
						if (c_end == -1)
							c_end = document.cookie.length;
						return unescape(document.cookie.substring(c_start, c_end));
					}
				}
				return "";
			}
			 */
			 
		/**批量通过
		**/
		function allow(type)
		{
		if(checkedData == null || checkedData.length == 0)
		{
			alert("请选择白名单！");
			return;
		}
		
		tool.confirm("确实要审核通过白名单吗？("+checkedData.length+"辆)", function(result) {
				if (result) {
					//增加一个数组元素，避免获取时转换出错。
					checkedData.push("");
					$.ajax({
						type : 'post',
						url : 'comfirmVerifyList.shtml',
						data : {
							"allowData" : checkedData,
							"type" : type
						},
						dataType : 'json',
						traditional: true,
						success : function(msg) {
							if (msg.success) {
								alert("审核通过成功！");
								tool.formSubmit();
							} else {
								tool.alert(msg.message);
							}
						},
						error : function(msg) {
							tool.alert("审核失败！");
						}
					});
				}
			});
		}
	
		$(function() {
			$(".whiteList_index").change(function() {
					changeCheckbox();
			});
	
	
	
			$("#file").change(function() {
				$
					.ajaxFileUpload({
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
	
	
		/*
		*提交白名单，状态改为已审核
		*/
/* 		function comfirmData(mv_license, color, vehicle_class, obu_id, time, type,checkedareaid) {
			var create_time = new Date(time);
			var alertMsg = "";
			if(type == 3)
			{
			    alertMsg = "确实要审核通过该白名单吗?";
			}
			if(type == 6)
			{
				alertMsg = "确实要作废通过该白名单吗?";
			}
			if(type == 4)
			{
			    alertMsg = "确实要驳回该白名单吗?";
			}
			if(type == 7)
			{
			    alertMsg = "确实要驳回作废该白名单吗?";
			    type = 3;
			}
			tool.confirm(alertMsg, function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'comfirmVerify.shtml',
						data : {
							"mv_license" : mv_license,
							"color" : color,
							"vehicle_class" : vehicle_class,
							"obu_id" : obu_id,
							"create_time" : create_time,
							"type" : type,
							"lane_info":"|"+checkedareaid+"-"
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
							tool.alert("审核失败！");
						}
					});
				}
			});
		} */
		/*
		*提交白名单，状态改为已审核
		*/
		function comfirmData(mv_license, color, vehicle_class,time, type,user_number,toll_type,spare1) {
			var create_time = new Date(time);
			var alertMsg = "";
			if(type == 3)
			{
			    alertMsg = "确实要审核通过该白名单吗?";
			}
			if(type == 6)
			{
				alertMsg = "确实要作废通过该白名单吗?";
			}
			if(type == 4)
			{
			    alertMsg = "确实要驳回该白名单吗?";
			}
			if(type == 7)
			{
			    alertMsg = "确实要驳回作废该白名单吗?";
			    type = 3;
			}
			tool.confirm(alertMsg, function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'comfirmVerify.shtml',
						data : {
							"mv_license" : mv_license,
							"color" : color,
							"vehicle_class" : vehicle_class,
							"create_time" : create_time,
							"type" : type,
							"user_number" :user_number,
							"toll_type" :toll_type,
							"spare1" :spare1
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
							tool.alert("审核失败！");
						}
					});
				}
			});
		}
		
		
		
	
		function deleteData(mv_license, color, vehicle_class, obu_id, create_time) {
			tool.confirm("确实要作废该白名单吗？", function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'comfirmDelete.shtml',
						data : {
							"mv_license" : mv_license,
							"color" : color,
							"vehicle_class" : vehicle_class,
							"obu_id" : obu_id,
							"create_time" : create_time
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