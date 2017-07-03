<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LIVE MANAGER</title>
<%-- <jsp:include page="./common.jsp"></jsp:include> --%>
<%@ include file="./common.jsp"%>
<style type="text/css">
html,body{
	background: #666;
}
.background_div1 {
	position: absolute;
	left: 0;
	top: 0px;
	width: 100%;
	height: 80px;
	line-height: 80px;
	background: #000;
}

.login_right_div {
	position: absolute;
	width: 400px;
	top: 38%;
	left: 50%;
	margin-left:-200px;
	background-color: #FFFFFF;
	border-radius: 8px
}
.login_right_div > div{
	padding: 50px;
}
.login_left_div {
	position: absolute;
	width: 400px;
	top: 30%;
	margin-left: 20%;
	position: absolute;
}
.background_div1 span{
	color: rgb(189, 202, 204);
	font-size: 40px;
	margin-left: 10px;
}
</style>
</head>
<body>
	<div class="background_div1">
		<span>
			<span class="glyphicon glyphicon-registration-mark"></span> LIVE&nbsp;MANAGER
		</span>
	</div>
	<div class="login_right_div">
		<div>
			<form class="bs-example bs-example-form data" role="form">
				<div class="input-group form-group">
					<span class="input-group-addon">账&nbsp;&nbsp;&nbsp;号</span>
					<input type="text" id="account" name="account" class="form-control" placeholder="管理员账号">
				</div>
				<div class="input-group form-group">
					<span class="input-group-addon">密&nbsp;&nbsp;&nbsp;码</span>
					<input type="password" id="password" name="password" class="form-control" placeholder="管理员密码">
				</div>
				<div class="text-center">
					<button type="submit" class="btn btn-primary btn-sm">登&nbsp;&nbsp;&nbsp;录</button>
				</div>
			</form>
		</div>
	</div>
	
		<script>
			function login(callback){
				var account = $("#account").val();
				var password = $("#password").val();
				if(!account || !password){
					layer.msg("请输入账号与密码");
				}else{
					var data = {
						"account":account,
						"password":password
					}
					serverLogin(data,function(rs){
						callback(rs);
					});
				}
			}
			function serverLogin(data,callback){
				data_ajax(
					{
						"url":"<%=contextPath%>/web/admin/login",
						"data":data
					},
					function(rs){
						callback(rs);
					}
				);
			}
			$(".login_right_div").on("submit","form",function(){
				login(function(rs){
					//登录成功
					window.location.href = "<%=contextPath%>/web/admin/home";
				});
				return false;
			});
	</script>
	
</body>
</html>