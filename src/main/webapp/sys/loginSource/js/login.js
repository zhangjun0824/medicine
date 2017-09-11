// JavaScript Document
//支持Enter键登录
		document.onkeydown = function(e){
			if($(".bac").length==0)
			{
				if(!e) e = window.event;
				if((e.keyCode || e.which) == 13){
					var obtnLogin=document.getElementById("submit_btn")
					obtnLogin.focus();
				}
			}
		}

    	$(function(){
    		if($.cookie("remember_me")){
    			$('#j_username').val($.cookie("remember_me"));
    		}
			//提交表单
			$('#submit_btn').click(function(){
				show_loading();
				if($('#j_username').val() == ''){
					show_err_msg('账号还没填呢！');	
					$('#j_username').focus();
				}else if($('#j_password').val() == ''){
					show_err_msg('密码还没填呢！');
					$('#j_password').focus();
				}else{
					$('#login_form').submit();
				}
			});
		});