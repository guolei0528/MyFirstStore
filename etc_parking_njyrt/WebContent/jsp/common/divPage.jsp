<script language="javascript">

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
	<input type="hidden" id="currentPage" name="page.currentPage">
	<input type="hidden" id="pageCount" name="page.pageCount" >
	<input type="hidden" id="pageSize" name="page.pageSize" >
	<div class="pagination">
		<a href="javascript:divPage(1);">首页</a>
		<a href="javascript:divPage(2);">上一页</a>
		<a href="javascript:divPage(3);">下一页</a>
		<a href="javascript:divPage(4);">尾页</a>
			
			<span class="page_jump">跳转到<input
			id="selPage" value="${page.currentPage }" type="text">页<a href="javascript:divPage(5);">GO</a></span>
			<span class="total">共 <strong>${page.pageCount }</strong> 页 <strong>${page.recordCount }</strong>条记录
		</span>
	</div>