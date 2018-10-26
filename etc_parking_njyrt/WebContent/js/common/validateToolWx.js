var vcheck=(function(){
	
	return {
		/**
		 * 验证输入框非空
		 */
		inputEmpty:function(){
			var flag=false;
			$("input[type='text']").each(function(){
				var reqMsg=$(this).attr("reqMsg");
				if(tool.isEmpty(reqMsg)==false&&flag==false){
					var value=$(this).val();
					if(tool.isEmpty(value)==true){
						flag=true;
						$.alert(reqMsg);
					}
				}
			});
			return flag;
		},
		/**
		 * 验证下拉框的值
		 */
		selectEmpty:function(){
			var flag=false;
			$("select").each(function(){
				var reqMsg=$(this).attr("reqMsg");
				if(tool.isEmpty(reqMsg)==false&&flag==false){
					var value=$(this).val();
					if(tool.isEmpty(value)==true){
						flag=true;
						$.alert(reqMsg);
					}
				}
			});
			return flag;
		},
		/**
		 * 验证TEXTAREA的值
		 */
		textAreaEmpty:function(){
			var flag=false;
			$("textarea").each(function(){
				var reqMsg=$(this).attr("reqMsg");
				if(tool.isEmpty(reqMsg)==false&&flag==false){
					var value=$(this).val();
					if(tool.isEmpty(value)==true){
						flag=true;
						$.alert(reqMsg);
					}
				}
			});
			return flag;
		},
		/**
		 * 验证RADIO非空
		 */
		radioChecked:function(radioName,reqMsg){
			var checkedFlag=false;
			$("input[type='radio'][name='"+radioName+"']").each(function(){
				if(checkedFlag==false){
					if($(this)[0].checked==true){
						checkedFlag=true;
					}
				}
			});
			if(checkedFlag==false){
				$.alert(reqMsg);
			}
			return checkedFlag;
		},
		/**
		 * 验证输入框、下拉框非空
		 */
		empty:function(){
			var flag=false;
			//input 验证
			flag=vcheck.inputEmpty();
			if(flag==true){
				return flag;
			}
			//SELECT 验证
			flag=vcheck.selectEmpty();
			if(flag==true){
				return flag;
			}
			//textarea验证
			flag=vcheck.textAreaEmpty();
			if(flag==true){
				return flag;
			}
			return flag;
		},
		/**
		 * 是否为手机号码
		 */
		mobileNo:function(phoneNum){
			//input 验证
			var phoneReg = /^(((13[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if(phoneReg.test(phoneNum)==false){
				return false;
			}else{
				return true;
			}
		},
        /**
        *
        * @param string required
        * @returns {boolean}
        * 根据传入参数验证是否为数字,最少一位
        *
        */
         isNumber: function (string) {
           return (/^\d+$/).test(string);
        },

       /**
        *
        * @param string required
        * @returns {boolean}
        * 根据传入参数验证是否为浮点数
        *
        */
       isFloat: function (string) {
           return (/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(string);
       },

       /**
        *
        * @param string required
        * @returns {boolean}
        * 根据传入参数验证是否为整数,最少一位
        *
        */
       isInt: function (string) {
           return (/^-?\d+$/).test(string);
       },
       
       /**
        * 0和正整数
        */
       isPositiveInteger: function (string) {
           return (/^([1-9]\d*|[0]{1,1})$/).test(string);
       },
       
       /**
        * 00:00 格式
        */
       isPositiveInteger2: function (string) {
           return (/^(\d{2})(\:{1})(\d{2})$/).test(string);
       }
       
	};
})();