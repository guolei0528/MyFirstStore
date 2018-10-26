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
	<input type="hidden" id="currentPage" name="page.currentPage" value="${page.currentPage}">
	<input type="hidden" id="pageCount" name="page.pageCount" value="${page.pageCount}">
	<input type="hidden" id="pageSize" name="page.pageSize" value="${page.pageSize }">
	<div class="box-footer">
		<div class="col-sm-5">
			<div class="dataTables_info" role="status" aria-live="polite">
				${page.pageCount==0?0:page.currentPage }/${page.pageCount}页 &nbsp;&nbsp;&nbsp;共${page.recordCount }条记录</div>
		</div>
		<div class="col-sm-7">
			<div class="dataTables_paginate paging_simple_numbers"
				id="example2_paginate">
				<ul class="pagination">
					<li class="paginate_button previous" id="example2_previous">
						<a href="javascript:divPage(1);" aria-controls="example2" data-dt-idx="0" tabindex="0">首页</a>
					</li>
					<li class="paginate_button">
						<a href="javascript:divPage(2);" aria-controls="example2" data-dt-idx="1" tabindex="0">上一页</a>
					</li>
					<li class="paginate_button ">
						<a href="javascript:divPage(3);" aria-controls="example2" data-dt-idx="2" tabindex="0">下一页</a>
					</li>
					<li class="paginate_button next" id="example2_next">
						<a href="javascript:divPage(4);" aria-controls="example2" data-dt-idx="7" tabindex="0">尾页</a>
					</li>
				</ul>
			</div>
		</div>
	</div>