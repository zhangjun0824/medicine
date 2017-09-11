<%@ page contentType="text/html; charset=utf-8" language="java"  %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>中医医药信息平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" href="${ctx}/sys/loginSource/css/font-awesome.min.css" />
<link rel="stylesheet" href="${ctx}/sys/loginSource/css/supersized.css">
<link rel="stylesheet" href="${ctx}/sys/loginSource/css/login.css">
<link rel="stylesheet" href="${ctx}/sys/loginSource/css/bootstrap.min.css">

<script type="text/javascript" src="${ctx}/sys/loginSource/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/sys/loginSource/js/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/sys/loginSource/js/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/sys/loginSource/js/tooltips.js"></script>
<script type="text/javascript" src="${ctx}/sys/loginSource/js/login.js"></script>
</head>

<body>

<div class="page-container">
	<div class="main_box">
		<div class="login_box">
			<div class="login_logo">
				中医医药信息平台
			</div>
			<div class="login_form">
				 <div class="error ${param.error == true ? '' : 'hide'}" align="center"> 
				    <h4 style="color: red;">
				        <i class="icon-coffee green"></i>
					    <span>用户名或密码错误！</span> 
				    </h4>
			    </div>
				<form action="<c:url value="/login" />" method="post">
					<input type="hidden" value="browser" name="j_type"/>
					<div class="form-group">
						<label for="j_username" class="t">账   号：</label> 
						<input id="j_username" value="" name="j_username" type="text" class="form-control x319 in" 
						autocomplete="off">
					</div>
					<div class="form-group">
						<label for="j_password" class="t">密　码：</label> 
						<input id="j_password" value="" name="j_password" type="password" 
						class="password form-control x319 in">
					</div>
					<div class="form-group">
						<label class="t"></label>
						<label for="j_remember" class="m">
						<input name="remember_me" id="j_remember" type="checkbox" value="true">&nbsp;记住登录账号!</label>
					</div>
					<div class="form-group space">
						<label class="t"></label>　　
							<button type="submit" id="submit_btn" class="btn btn-primary btn-lg">
								<i class="icon-key"></i>
								&nbsp;登&nbsp;录&nbsp 
							</button>　
						<input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="btn btn-default btn-lg">
					</div>
				</form>
			</div>
		</div>
	</div>
</div>

<!-- Javascript -->

<script src="${ctx}/sys/loginSource/js/supersized.3.2.7.min.js"></script>
<script src="${ctx}/sys/loginSource/js/supersized-init.js"></script>
<script src="${ctx}/sys/loginSource/js/scripts.js"></script>
</body>
</html>