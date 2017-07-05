<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String contextPath = request.getContextPath();%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LIVE MANAGER</title>
<jsp:include page="./common.jsp" flush="true" />
</head>
<body>
	<div id="header-nav">
		<div>
			<a href="javascript:;" target="_blank">LIVE MANAGER</a>
		</div>
	</div>
	<div class="navbar navbar-default" id="navbar">
		<div class="navbar-container" id="navbar-container">
			<div class="navbar-header pull-left">
				<a href="#" class="navbar-brand">
					<span class="glyphicon glyphicon-registration-mark"></span>
					<small>LIVE&nbsp;MANAGER</small>
				</a>
			</div>
			<div class="navbar-header pull-right" role="navigation">
				<ul class="nav ace-nav">
					<li class="light-blue">
						<a href="#" class="dropdown-toggle">
						<img class="nav-user-photo" src="<%=contextPath%>/assets/img/headerPic.png" />
						<span class="user-info">
							<small>${sessionScope.ADMIN_USER.userName}</small> 
						</span>
						<i class="glyphicon glyphicon-pushpin"></i>
						</a>
						<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
							<%-- <li>
								<a href="<%=contextPath%>/web/admin/loginPage">
									<i class="glyphicon glyphicon-user"></i> 个人资料
								</a>
							</li>
							<li class="divider"></li> --%>
							<li><a href="javascript:viod(0);"> <i class="glyphicon glyphicon-off"></i> 注销
							</a></li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="main-container" id="main-container">
		<div class="main-container-inner">
			<a class="menu-toggler" id="menu-toggler" href="#"> <span class="menu-text"></span>
			</a>

			<div class="sidebar" id="sidebar">
				<ul class="nav nav-list">
					<li class="active">
						<a href="<%=contextPath%>/web/live/list">
							<i class="glyphicon glyphicon-list"></i>
							<span class="menu-text"> 直播管理 </span>
						</a>
					</li>
					<li class="">
						<a href="<%=contextPath%>/web/admin/muser">
							<i class="glyphicon glyphicon-list"></i>
							<span class="menu-text"> 用户管理 </span>
						</a>
					</li>
				</ul>
				<div class="sidebar-collapse" id="sidebar-collapse">
					<i class="glyphicon glyphicon-transfer" data-icon1="glyphicon glyphicon-transfer" data-icon2="glyphicon glyphicon-transfer"></i>
				</div>
			</div>
			<div class="main-content"></div>
		</div>
	</div>
	
	<script>
		$(function(){
			//刷新页面时加载最后点击的页面
			var href = window.sessionStorage.getItem('linkUrl');
			var index = window.sessionStorage.getItem('linkIndex');
			if(NULL(href)){
				$(".main-content").load(href);
				var li = $('#sidebar ul li').eq(index);
				li.siblings('li').removeClass('active');
				li.addClass('active');
			}
			//点击左侧菜单加载右侧页面
			$("#sidebar").on("click","li a",function(){
				var href = $(this).attr("href");
				var index = $(this).parent('li').index();
				window.sessionStorage.setItem("linkUrl", href);
				window.sessionStorage.setItem("linkIndex", index);
				$(".main-content").load(href);
				location.reload();
				return false;
			});
			//点击引入右侧页面
			$('#navbar,.main-content').on('click','a:not([target="_blank"])',function(){
				try{
					var href = $(this).attr('href');
					if(href.split(':')[0] == 'javascript' || href == '#' || href == '') return;
					$('.main-content').load(href);
				}catch(e){
					console.log(e);
				}
				return false;
			});
		});
	</script>
	
</body>
</html>