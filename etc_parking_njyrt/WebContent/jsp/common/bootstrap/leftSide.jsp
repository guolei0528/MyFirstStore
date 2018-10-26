<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Left side column. contains the logo and sidebar -->
<aside class="leftside">
	<section>
		<ul>
			<c:forEach items="${sessionScope.USER_SESSION_TREEMENU }" var="obj" varStatus="status">
				<li class="menu-title"><a class="title"><span class="fa fa-th" style="margin-right: 5px"></span>${obj.node.name }</a>
					<ul class="menu-body" style="display: none;">
						<c:forEach items="${obj.childNodeList }" var="child">
							<li class="menu"><a href="${contentPath }/${child.urlAction }"><span class="fa fa-circle-o" style="margin-right: 5px"></span>${child.name } </a></li>
						</c:forEach>
					</ul>
				</li>
			</c:forEach>
		</ul>
	</section>
</aside>  
<script type="text/javascript">
$('.menu a').click(function(){
	$('.menu a').removeClass('active');
	$(this).addClass('active');
});
var loc=document.location.href;

$(".menu a").each(function(){
	  var LI_URL=$(this).attr("href");
	  if(tool.isEmpty(LI_URL)==false&&loc.indexOf(LI_URL)>0){
  		$(this).addClass("active");
  		$(this).parent().parent().slideDown(500);
  	  }
})
// $('.leftside .menu-title .title').click(function(){
// 		var checkedMenu = $(this);
// 		var menuBody = $(this).next();
// 		//可见
// 		if(menuBody.is(':visible')){
// 			menuBody.slideUp(500);
// 		}else{
// // 			menuBody.slideDown(500);
// 		}
// 	});
</script>

