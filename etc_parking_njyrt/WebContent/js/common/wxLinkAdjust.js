/**
 * 
 */
$(document).ready(function(){
	var ANCHORE_DOMAIN="mp.weixin.qq.com";
	$("a").each(function(){
		var href=$(this).attr("href");
		if(tool.isEmpty(href)==false){
			if(href.indexOf('tel:')==0){
				return ;
			}
			if(href.indexOf('#')<0){
				$(this).attr("href",href+"#"+ANCHORE_DOMAIN);
			}
		}
	});
})