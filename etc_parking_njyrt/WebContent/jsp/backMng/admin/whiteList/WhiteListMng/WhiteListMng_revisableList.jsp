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
		<form action="revisableList.shtml" method="post" id="pageForm">
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
<!-- 									<td>审核状态</td> 
									<td>
										<select name="query_status"
										id="query_status">
											<option>全部</option>
											<option value="1">待审核</option>
											<option value="3">已审批</option>
											<option value="4">待作废</option>
											<option value="5">已作废</option>
									</select></td>
									
									<td>有效时间</td>
									<td><input type="text" id="query_time"
										name="query_time"
										value="${queryParam.query_time}"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm' })"></td> -->
 									<!--<td><input type="text" id="query_cancel_time_from"
										name="query_cancel_time_from"
										value="${queryParam.query_cancel_time_from}"
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm' })"></td>
									<td>-</td>
									<td><input type="text" id="query_cancel_time_to"
										name="query_cancel_time_to"
										value="${queryParam.query_cancel_time_to}"
 										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:mm' })"></td> -->
<!-- 										<td>车主姓名</td> 
									<td><input type="text" id="query_user_name"
										name="query_user_name" value="${queryParam.query_user_name}"
										placeholder="" autocomplete="off"></td>
									<td>院系</td>
									<td><input type="text" id="query_dept_info"
										name="query_dept_info" value="${queryParam.query_dept_info}"
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
									<td></td><td></td><td></td><td></td><td></td><td></td>
									<td>
										<button class="btn btn-primary" type="submit">确定</button>
									</td>
								</tr>
							</table>
						</div>
					</div>
					<input type="file" id="file"  name="file" style="display: none;">
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>车辆牌照</th>
									<th>车牌颜色</th>

									<th>车辆类型</th>
									<th>收费形式</th>

									<th>开始时间</th>
									<th>截止时间</th>

									<th>车位编号</th>
									<th>车主姓名</th>
									<th>联系方式</th>

									<th>审核状态</th>
									<th>提交用户</th>
<!-- 									<th>审核用户</th> -->
									<th>录入时间</th>
									<%-- <c:if test="${areaid == '0'}"><th>所在校区</th></c:if> --%>
									<th>操作</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
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
										<td><c:choose>
												<c:when test="${obj.toll_type == 1}">
													免费
												</c:when>
												<c:when test="${obj.toll_type == 2}">
													收费
												</c:when>
												<c:when test="${obj.toll_type == 3}">
													计票
												</c:when>
												<c:otherwise>
												          其他
												</c:otherwise>
											</c:choose></td>
										<%-- <td><c:choose>
												<c:when test="${obj.charge_code == 11}">
													时段计费规则A
												</c:when>
												<c:when test="${obj.charge_code == 21}">
													时长计费规则A
												</c:when>
												<c:when test="${obj.charge_code == 31}">
													计次计费规则A
												</c:when>
												<c:otherwise>
												          其他
												</c:otherwise>
											</c:choose></td> --%>
										<td><fmt:formatDate value="${obj.valid_start_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td><fmt:formatDate value="${obj.valid_end_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td>${obj.user_number}</td>
										<td>${obj.user_name}</td>
										<td>${obj.phone}</td>
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
										<td>${obj.initiate_name}</td>
<%-- 										<td>${obj.comfirm_name}</td> --%>
										<td><fmt:formatDate value="${obj.create_time}"
												type="both" /></td>
												
<%-- 										<c:if test="${areaid == '0'}">
											<td><c:forEach items="${areaInfoList }" var="areainfo"
													varStatus="status">
													<c:set var="area_id_str" value="|${areainfo.area_id}-"></c:set>
													<c:if test="${fn:contains(obj.lane_info,area_id_str)}">
														<c:set var="area_id_checked" value="${areainfo.area_id}"></c:set>
													${areainfo.area_name}
												</c:if>
												</c:forEach></td>
										</c:if>		
										
										<td style="display: none">
											<c:forEach
												items="${areaInfoList }" var="areainfo" varStatus="status">
												<c:set var="area_id_str" value="|${areainfo.area_id}-"></c:set>
												<c:if test="${fn:contains(obj.lane_info,area_id_str)}">
													<c:set var="area_id_checkedforedit" value="${areainfo.area_id}"></c:set>		
												</c:if>
											</c:forEach>
										</td> --%>
												
										<td>
											<ul class="list-inline">
											<li title="修改"><%-- <a
														href="javascript:tool.dialog('revisableEdit.shtml?mv_license=${obj.mv_license }&color=${obj.color }&create_time=<fmt:formatDate value="${obj.create_time}"
												type="date" pattern='yyyy-MM-dd,HH:mm:ss'/>&laneinfo=${area_id_checkedforedit}')"><i
															class="fa fa-pencil-square-o text-green"></i><span
															class="text-green"></span>修改</a> --%>
												<a href="javascript:tool.dialog('revisableEdit.shtml?mv_license=${obj.mv_license }&color=${obj.color }&create_time=<fmt:formatDate value="${obj.create_time}"
												type="date" pattern='yyyy-MM-dd,HH:mm:ss'/>')"><i
															class="fa fa-pencil-square-o text-green"></i><span
															class="text-green"></span>修改</a>
												</li>
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
					<!-- /.box-body 
					<div style="float:right">
					    <select name="carColor">
							<option value="0">蓝色牌照</option>
							<option value="1">黄色牌照</option>
							<option value="2">白色牌照</option>
							<option value="3">黑色牌照</option>
						</select>
					</div>  -->
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
		
	</script>
</body>
</html>