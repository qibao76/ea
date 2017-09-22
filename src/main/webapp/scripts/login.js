//scripts/login.js
//登陆页面中的脚本文件
$(function(){
	//页面加载完毕执行的脚本
	console.log("ok");
	$("#login").click(loginAction);
	$("#count").focus().blur(checkUserame);
	$("#password").blur(checkPassword);

	$("#regist_button").click(regisAction);
	$("#regist_username").focus().blur(checkName);
	$("#regist_password").blur(checkPwd);

});
function regisAction(){
//	console.log("注册");
	var url='user/regis.do';
	var param={
		name:$("#regist_username").val(),
		pwd:$("#regist_password").val(),
		confirm:$("#final_password").val(),
		nick:$("#nickname").val()
	};
	console.log(param);
	$.post(url,param,function(result){
		console.log(result);
		if(result.state==0){
			console.log(result.message);
			var user=result.data;
			$("#back").click();
			$("#count").val(user.name);
			$("#count-msg").empty();
			$("#password").focus();
		}else if(result.state==2){
			//用户名错误
			$('#count-msg2').empty().html(result.message);
			$('#warning_1').show();
		}else if(result.state==3){
			//密码错误
			$('#password-msg2').empty().html(result.message);
			$('#warning_2').show();
		}else{
			//其他错误
			alert(result.message);
		}
	});
	
	
	
	
}
function checkName(){
	
}
function checkPwd(){
	
}


function checkUserame(){
	console.log('名字');
	var name=$('#count').val();
	if(!name){
		console.log('kong'); 
		$('#count-msg').empty().html('不能为空');
		return false;
	}
	var reg=/^\w{3,10}$/;
	if(reg.test(name)){
		$('#count-msg').empty();
		return true;
	}else{
		$('#count-msg').empty().html('必须3-10个字母');
		return false;
	}


}
function checkPassword(){
	console.log('密码');
	var pwd=$("#password").val();
	var pwdms=$("#password-msg");
	if(!pwd){
		pwdms.empty().html('密码不能为空');
		return false;
	}
	var reg=/^\w{3,10}$/;
	if(reg.test(pwd)){
		pwdms.empty();
		return true;
	}else{
		pwdms.html('密码3-10个字符');
		return false;
	}
}
function loginAction(){
	if(checkPassword()+checkUserame()!=2){
		return;
	}
	//console.log('登陆');
	//获取表单参数利用ajax发送到控制器
	//检查控制器返回值，如果state==0成功跳转
	var user=$('#count').val();
	var pwd=$('#password').val();
//	console.log(user);
//	console.log(pwd);
	var url="user/login.do";
	var param={name:user,pwd:pwd};
	$.post(url,param,function(result){
		if(result.state==0){
			console.log(result.message);
			//成功跳转到edit。html
			var user=result.data;
			
			setCookie('userId',user.id);
			
			location.href='edit.html';
		}else if(result.state==2){
			//用户名错误
			$('#count-msg').empty().html(result.message);
		}else if(result.state==3){
			//密码错误
			$('#password-msg').empty().html(result.message);
		}else{
			//其他错误
			alert(result.message);
		}
	});
}





