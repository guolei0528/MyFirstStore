var tool=(function(){
	var DEFAULT_WIDTH=700;
	var DEFAULT_HEIGHT=450;
	
	return {
		optValueSel:function(id){
			$('#'+id).val($('#'+id).attr('optValue'));
		},
		optValue:function(){
			$('[optValue]').each(function(){
				$(this).val($(this).attr('optValue'));
			});
			
		},	
		/**
		 * 弹出模态对话框
		 * @param url
		 * @returns
		 */
		dialog:function(url,width,height){
			var w=DEFAULT_WIDTH;
			var h=DEFAULT_HEIGHT;
			if(!tool.isEmpty(width)){
				w=width;
			}
			if(!tool.isEmpty(height)){
				h=height;
			}
			$("#myModal").html("");
			$('#myModal').modal({
			  keyboard: false,
			  backdrop:'static'
			});
			$("#myModal").load(url);
		},
		
		closeDialog:function(){
			$("#myModal").hide();
		},
		/**
		 * 获取选中的记录值
		 * @param radioName
		 * @returns
		 */
		getSelectedValue:function(radioName){
			if($.trim(radioName).length==0){
				radioName='listRadio';
			}
			
			var radio=$("input[type='radio'][name='"+radioName+"']:checked");
			if(radio&&radio.length>0){
				return radio[0].value;
			}else{
				return '';
			}
		},
		/**
		 * 获取选中的记录值同级的隐藏项目值
		 * @param Name
		 * @returns
		 */
		getSelectedhiddenValue:function(name){
			radioName='listRadio';
			
			var event=$("input[type='radio'][name='"+radioName+"']:checked")[0].parentElement.children;
			var inputname = event[name];
			if(inputname){
				return inputname.value;
			}else{
				return '';
			}
		},
		
		/**
		 * 判断字符串是否为空
		 * @param str
		 * @returns
		 */
		isEmpty:function(str){
			if(typeof(str)=='undefined'||$.trim(str).length==0){
				return true;
			}else{
				return false;
			}
		},
		/**
		 * 判断字符串是否为空
		 * @param str
		 * @returns
		 */
		radioEmpty:function(domName){
			var flag=true;
			$("input[type='radio'][name='"+domName+"']").each(function(){
				if($(this)[0].checked){
					flag=false;
				}
			});
			return flag;
		},
		/**
		 * 提交表单信息
		 * @param formId
		 * @returns
		 */
		formSubmit:function(formId){
			if(tool.isEmpty(formId)){
				$("#pageForm").submit();
			}else{
				$("#"+formId).submit();
			}
		},
		/**
		 * 重新加载当前页面
		 * @returns
		 */
		reload:function(){
			document.location.reload();
		},
		/**
		 * 关闭窗口
		 * @returns
		 */
		close:function(){
			window.close();
		},
		/**
		 * 关闭DIV
		 * @returns
		 */
		hide:function(divId){
			$("#"+divId).hide();
		},
		/**
		 * 显示DIV
		 * @returns
		 */
		show:function(divId){
			$("#"+divId).hide();
		},
		/**
		 * 刷新SELECT下拉框的信息
		 */
		refreshSelectOpt:function(alias,targetId,paramValue,selectedValue){
			var c={};
			if(!tool.isEmpty(paramValue)){
				c=paramValue;
			}
			
			$.ajax({
				async:false,
				type : 'post',
				url : '../../component/RefreshOption/list.action?alias='+alias,
				data:c,
				dataType : 'json',
				success : function(data, textStatus) {
					document.getElementById(targetId).options.length=0;
					document.getElementById(targetId).options.add(new Option('',''));
					
					if(data){
						var length=data.length;
						for(var i=0;i<length;i++){
							document.getElementById(targetId).options.add(new Option(data[i].text,data[i].value));
						}
					}
					if(!tool.isEmpty(selectedValue)){
						document.getElementById(targetId).value=selectedValue;
					}
				},
				error : function(XMLHttpRequest, textStatus, errorThrown) {
					alert('数据获取失败');
				}
			});
		},
		radioVal:function(radioName){
			var val='';
			$("input[name='"+radioName+"'][type='radio']:checked").each(function(){
				val=$(this).val();
			});
			return val;
		},
		selRadio:function(radioName,value){
			$("input[name='"+radioName+"'][type='radio']").each(function(){
				if($(this).val()==value){
					$(this)[0].checked=true;
				}else{
					$(this)[0].checked=false;
				}
			});
		},
		/**
		 * 选择OPTION
		 */
		selOption:function(radioName,value){
			$("#"+radioName).val(value);
		},
		/**
		 * 跳转至相应的URL地址
		 */
		goUrl:function(url){
			if(tool.isEmpty(url)==false){
				if(url.indexOf('#')<0){
					document.location=url+'#mp.weixin.qq.com';
				}else{
					document.location=url;
				}
			}else{
				alert('链接地址为空，访问失败');
			}
			
		},
		/**
		 * 拼选中的CHECKBOX值
		 */
		joinCheckbox:function(DOM_NAME,SPLITER){
			if(tool.isEmpty(SPLITER)){
				SPLITER=",";
			}
			var RESULT='';
			$("input[name='"+DOM_NAME+"'][type='checkbox']").each(function(){
				if($(this)[0].checked==true){
					RESULT=RESULT+$(this).val()+SPLITER;
				}
			});
			if(RESULT.length>0){
				return RESULT.substring(0,RESULT.length-1);
			}else{
				return RESULT;
			}
		}
	};
})();