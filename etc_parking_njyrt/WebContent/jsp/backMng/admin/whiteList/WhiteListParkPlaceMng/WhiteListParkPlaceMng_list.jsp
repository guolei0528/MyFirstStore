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
						<div class="col-sm-6"></div>
						<div class="col-sm-6">
							<div class="dataTables_button">
								<!-- <button type="button" class="btn btn-default"
									onclick="tool.dialog('add.shtml')">
									<i class="fa fa-plus text-green" aria-hidden="true"></i>新增
								</button> -->
								<!-- <label class="btn btn-info" onclick="tool.goUrl('correctparks.shtml?user_id=123')">纠正剩余车位</label> -->
								<c:if test="${user_number != null}">
									<label class="btn btn-info" onclick="correctparks('${user_number}','${sum_parks}','${canuse_parks}','${tolltype}')">纠正剩余车位</label>
								</c:if>
								
							</div>
						</div>
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
					<!-- /.box-header -->
					<div class="box-body">
						<table id="dataTable"
							class="table table-bordered table-striped table-condensed"
							style="font-size: 12px">
							<thead>
								<tr>
									<th>车位编号</th>
									<th>车牌</th>
									<th>名单类型</th>
									<th>有效开始时间</th>
									<th>有效结束时间</th>
									<th>拥有车位数</th>
									<th>剩余车位数</th>
									<th>在库状态</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody align="center">
								<c:forEach items="${list }" var="obj" varStatus="status">
									<tr>
										<td>${obj.user_number}</td>
										<td>${obj.mv_license}</td>
										<%-- 										<td>${obj.obu_id}</td> --%>
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
										<td><fmt:formatDate value="${obj.valid_start_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td><fmt:formatDate value="${obj.valid_end_time}"
												pattern="yyyy-MM-dd HH:mm" /></td>
										<td>${obj.spare1}</td>
										<td>${obj.spare2}</td>
										<td><c:if test="${obj.place_count == 0}">出库</c:if>
										    <c:if test="${obj.place_count != 0}">在库</c:if></td>
										<td>
											<ul class="list-inline">
												<c:if test="${obj.place_count == 0}">
													
													<li title="入库"><a href="#" onclick="tool.dialog('incost.shtml?mv_license=${obj.mv_license}&sumparks=${obj.spare1}&usernumber=${obj.user_number}&tolltype=${obj.toll_type}')"><span
															class="fa fa-check-square-o text-green"></span>入库</a></li>
												</c:if>
												<c:if test="${obj.place_count != 0}">
													<li title="清库"><a
														href="javascript:outcost('${obj.mv_license }','${obj.user_number}','${obj.spare1}','${obj.toll_type}')")"><i
															class="fa fa-trash-o text-red"></i> <span
															class="text-red"></span>清库</a></li>

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
	

		function outcost(mv_license,user_number,sumparks,tolltype) {
			tool.confirm("确定对该记录进行清库操作吗？", function(result) {
				if (result) {
					$.ajax({
						type : 'post',
						url : 'outcost.shtml',
						data : {
							"mv_license" : mv_license,
							"user_number": user_number,
							"sumparks":sumparks,
							"tolltype":tolltype
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
							tool.alert("清库失败！");
						}
					});
				}
			});
		}
		
		function correctparks(user_number,sum_parks,canuse_parks,tolltype){
			tool.confirm("确定对车位编号为: "+user_number+"  进行纠正剩余车位吗?", function(result) {
				if (result) {
					 $.ajax({
						type : 'post',
						url : 'correctparks.shtml',
						data : {
							"user_number" : user_number,
							"sum_parks":sum_parks,
							"canuse_parks":canuse_parks,
							"tolltype" : tolltype
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
							tool.alert("纠正车位失败！");
						}
					}); 
				}
			});
			
		}
		
		
		
	</script>
</body>
</html>