<!DOCTYPE html >
<%@page import="com.project.common.tool.AppConst"%>
<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="/jsp/common/bootstrap/taglib.jsp"%>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><%=PropertiesUtil.get("PROJECT_NAME") %></title>
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
					<div class="col-sm-6">
						优惠券批次
					</div>
					<div class="col-sm-6">
						<div class="dataTables_button">
							<button type="button" class="btn btn-default" onclick="tool.dialog('add.shtml')"><i class="fa fa-plus text-green" aria-hidden="true"></i>新增</button>
						</div>
					</div>
					<div class="col-sm-12 ">
						<table class="queryTable">
							<tr>
								<td>发行商</td>
								<td>
									<select name="query_issuer_code" id="query_issuer_code" style="width:100%">
										<option value="01">南京大学</option>
									</select>
								</td>
								<td>发行日期</td>
								<td>
									<input id="start_time" name="query_issue_date" type="text"
								class="form-control"
								autocomplete="off" value="${queryParam.query_issue_date}"
								onfocus="WdatePicker({ dateFmt: 'yyyy-MM' })" />
								</td>
								<td>发行批次</td>
								<td>
									<input type="text" id="query_batch_id"
										name="query_batch_id"
										value="${queryParam.query_batch_id}"
										autocomplete="off">
								</td>
								<td>有效时间</td>
								<td><input type="text" id="query_start_time"
										name="query_start_time"
										value="${queryParam.query_start_time}" 
										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' })"></td>
									<td>-</td>
									<td><input type="text" id="query_end_time"
										name="query_end_time"
										value="${queryParam.query_end_time}"
 										onfocus="WdatePicker({ dateFmt: 'yyyy-MM-dd HH:00:00' })"></td>
<!-- 								<td>车辆颜色</td> 
								<td>
									<input type="text" id="query_car_color" name="query_car_color" value="${queryParam.query_car_color}" placeholder="" autocomplete="off">
								</td>
								<td>车辆类型</td>
								<td>
									<input type="text" id="query_type" name="query_type" value="${queryParam.query_type}" placeholder="" autocomplete="off">
								</td>  -->
								<td></td><td></td><td></td><td></td>
								<td>
									<button class="btn btn-primary" type="submit">查询</button>
								</td>
							</tr>
							<tr>
								<td>类型</td>
								<td>
									<select name="query_coupon_type" id="query_coupon_type">
										<option value="">全部</option>
									    <option value="J">金额</option>
									    <option value="S">时长</option>
									</select>
								</td>
								<td>状态</td>
								<td>
									<select name="query_status" id="query_status">
										<option value="">全部</option>
									    <option value="0">生效</option>
									    <option value="1">已导出</option>
									    <option value="2">失效</option>
									</select>
								</td>
								<td><label for="file" class="btn btn-info"  onclick="exportCoupon()">导出</label></td> 
							</tr>
						</table>
					</div>
				</div>
				<!-- /.box-header -->
				<div class="box-body">
					<table id="dataTable" class="table table-bordered table-striped table-condensed" style="font-size: 12px">
						<thead>
							<tr>
								<th><input type="checkbox" name="alls"
										onClick="selectAll()" title="全选/取消全选"></th>
								<th>发行商</th>
								<th>发行日期</th>
								<th>发行批次</th>
							    <th>发行张数</th>
								<th>类型</th>
								<th>优惠内容</th>
								<th>有效开始时间</th>
								<th>有效结束时间</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody align="center">
<!-- 						<tr> -->
									<!-- <td><input type="checkbox" class="whiteList_index"
											name="index_id" title="选择/取消"
											></td>
									<td>南京大学</td>
									<td>201712</td>
									<td>770</td>
									<td>
										999
									</td>
									<td>
										金额
									</td>
									<td>
										优惠5元
									</td>
									<td>
										2018-03-30 00:00:00
									</td>
									<td>
										2019-03-30 00:00:00
									</td>
									<td>
										生效
									</td>
									<td>
										<ul class="list-inline">
											<li title="详细"><a href="javascript:tool.dialog('view.shtml?mvLicense=${obj.mvLicense }&carColor=${obj.carColor } ')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>详情</a></li>
										</ul>
									</td> -->
							<c:forEach items="${list }" var="obj" varStatus="status">
								<tr align="center">
								    <td>
									<c:if test="${obj.status == 0}">
									<input type="checkbox" class="couponList_index" value="${obj.issue_id }"
											name="index_id" title="选择/取消">
									</c:if>
									<c:if test="${obj.status != 0}">
									<input type="checkbox" class="couponList_index" value="${obj.issue_id }"
											name="index_id" title="选择/取消"  disabled="disabled">
									</c:if>
									</td>
									<td>${obj.issuer_name}</td>
									<td>20${obj.issue_date}</td>
									<td>${obj.batch_id}</td>
									<td>
										${obj.issue_count }
									</td>
									<td>
										<c:if test="${obj.coupon_type == 'J'}">金额</c:if>
										<c:if test="${obj.coupon_type == 'S'}">时长</c:if>
									</td>
									<td>
										<c:if test="${obj.coupon_type == 'J'}">优惠<fmt:formatNumber type="number" maxFractionDigits="2" value="${obj.coupon_toll/100 }" />元</c:if>
										<c:if test="${obj.coupon_type == 'S'}">优惠时长<fmt:formatNumber type="number" maxFractionDigits="2" value="${obj.coupon_toll/60 }" />小时</c:if>
									</td>
									<td><fmt:formatDate value="${obj.start_time}"
												pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td>
										<fmt:formatDate value="${obj.end_time}"
												pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>
										<c:if test="${obj.status == 0}">生效</c:if>
										<c:if test="${obj.status == 1}">已导出</c:if>
										<c:if test="${obj.status == 2}">已作废</c:if>
									</td>
									<td>
										<ul class="list-inline">
											<li title="详情"><a href="javascript:tool.dialog('view.shtml?issue_id=${obj.issue_id} ')"><i class="fa fa-file-text-o text-blue"></i><span class="text-blue"></span>详情</a></li>
										</ul>
									</td>
								</tr>
							</c:forEach>
							<input id="checkedData" type="hidden" value="" name="checkedData"></input>
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
	
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		
	</div>
	
	<script type="text/javascript">
	
		
	$(function(){
	tool.selOption("query_coupon_type",'${queryParam.query_coupon_type}');
	tool.selOption("query_status",'${queryParam.query_status}');
	
	
	$(".couponList_index").change(function() {
					changeCheckbox();
			});
	});
	
	function exportCoupon()
	{
		var checkedDataStr = "";
		for (var i = 0; i < checkedData.length; i++) {
			checkedDataStr = checkedDataStr+checkedData[i]+',';
		}
		$("#pageForm").attr("action","downLoad.shtml");
		$("#checkedData").val(checkedDataStr);
		tool.formSubmit();
		//迭代判断循环
		var timer1 = setInterval(refeshPage,1000);//每隔一秒判断下cookie是否已经写回，如果写回，则进行页面刷新
		
		function refeshPage(){
 		if(getCookie("updateStatus")=="success"){
  			clearInterval(10000);//每隔一秒的判断操作停止
 			delCookie("updateStatus");//删除cookie
 			$("#pageForm").attr("action","list.shtml");
  			$("#pageForm").submit();//页面刷新
 		}
	} 
}


//删除cookies
function delCookie(name)
{
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cval=getCookie(name);
    if(cval!=null)
        document.cookie= name + "="+cval+";expires="+exp.toGMTString();
}


function getCookie(name)   
{   
    //document.cookie.setPath("/");  
    var arr, reg = new RegExp("(^| )"+name+"=([^;]*)(;|$)");  
    if(arr=document.cookie.match(reg))  
    {  
          return unescape(arr[2]);   
    }  
    else   
    {  
        return null;   
    }  
}  

//设置cookie值  
function setCookie(name,value)   
{   
    //document.cookie.setPath("/");  
    var hour = 8;   
    var exp = new Date();   
    exp.setTime(exp.getTime() + hour*60*60*1000);   
    document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString()+";path=/";   
}  
	
	
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
// 			alert(elements.length);
			for (var i = 0; i < elements.length; i++) {
				var e = elements[i];
				if (checkedAll) {
					e.checked = false;
					form.alls.checked = false;
				} else {
				if(!e.disabled)
				{
					e.checked = true;
					form.alls.checked = true;
				}
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
	var chks = $(".couponList_index");
				$(".couponList_index").each(function() {
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
	</script>
</body>
</html>