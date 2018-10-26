$(function(){
	function fix(){		
		var window_height = $(window).height();
		var leftside_height=$('.leftside').outerHeight();
		
		if(window_height>leftside_height){
			$('.leftside').css('min-height',window_height);
		}else{
			$("#mainContentDiv").css('min-height', window_height - $('.main-footer').outerHeight()-$('.header').outerHeight());
		}
		
		
		var window_width = $(window).width();
		$("#mainContentDiv").css('width',window_width-$(".leftside").outerWidth());
	}
	fix();
	
	$(window).resize(fix);
	$('.leftside .menu-title .title').click(function(){
		var checkedMenu = $(this);
		var menuBody = $(this).next();
		//可见
		if(menuBody.is(':visible')){
			menuBody.slideUp(500);
		}else{
			menuBody.slideDown(500);
		}
	});
	$('.menu a').click(function(){
		$('.menu a').removeClass('active');
		$(this).addClass('active');
	});
	var loc=document.location.href;

	$(".menu a").each(function(){
		  var LI_URL=$(this).attr("href");
		  if(tool.isEmpty(LI_URL)==false&&loc.indexOf(LI_URL)>0){
	  		$(this).addClass("active");
	  	  }
	})
});
