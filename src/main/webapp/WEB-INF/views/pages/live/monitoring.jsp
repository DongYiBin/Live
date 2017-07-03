<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" />
<%
	String contextPath = request.getContextPath();
%>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>监控-${live.title}</title>
<link rel="stylesheet" href="<%=contextPath%>/assets/html/directSeeding/css/base.css" />
<link rel="stylesheet" href="<%=contextPath%>/assets/html/directSeeding/css/index.css" />
<script src="<%=contextPath%>/assets/js/jquery-2.0.3.min.js"></script>
<script src="<%=contextPath%>/assets/html/directSeeding/js/layer/layer.js"></script>
<script src="<%=contextPath%>/assets/html/directSeeding/js/service.js"></script>
<script src="<%=contextPath%>/assets/html/directSeeding/js/player.js"></script>
</head>
<body style="background: #fff;">
<div class="page-content">
<!--播放器层-->
<div id="id_video_container"></div>
</div>
</body>
<script>
window.addEventListener("load",function(){
	var streamId = "${live.streamId}";
	//视频状态
	var state = 1;
	server(
		{
			"url":"<%=contextPath%>/web/live/getLiveByStreamId",
			"data":{
				"streamId":streamId
			}
		},
		function(data){
			if(data.code != 1){
				layer.open({
					content:data.msg,
					time:2
				});
			}else{
				if(!data.data){
					layer.open({
						content:"服务器错误"
					});
					return;
				}
				state = data.data.state;
				var options = {};
				//直播与回放js库
				var $script = document.createElement("script");
				var $scriptSrc = "";
				if(state == 1){
					//直播
					$scriptSrc = "//qzonestyle.gtimg.cn/open/qcloud/video/live/h5/live_connect.js";
					options = {
						"live_url":data.data.playHlsUrl,
					};
				}else{
					//回放
					$scriptSrc = "//qzonestyle.gtimg.cn/open/qcloud/video/h5/h5connect.js";
					if(!data.data.liveVideos){
						layer.open({
							content:"当前直播已结束，请等待回放"
						});
						return;
					}
					options = {
						"third_video": {
					    	"urls":{
					            20 : data.data.liveVideos[0].videoUrl
					        }
					    }
					};
				}
				//播放器初始化
				var $script = document.createElement("script");
				$script.src = $scriptSrc;
				document.body.appendChild($script);
				$script.onload = function(){
					new player_content().init(options);
				}
			}
		}
	);
});
</script>
</html>