function getRootPath(){
    //获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
    var curWwwPath=window.document.location.href;
    //获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
    var pathName=window.document.location.pathname;
    var pos=curWwwPath.indexOf(pathName);
    //获取主机地址，如： http://localhost:8083
    var localhostPaht=curWwwPath.substring(0,pos);
    //获取带"/"的项目名，如：/uimcardprj
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
//    return "http://123.207.15.129:8080/mz-live";
    return (localhostPaht+projectName);
}
window.addEventListener("load",function(){
	var streamId = GetUrlName("streamId");//节目ID
	if(streamId){
		window.sessionStorage.setItem("streamId",streamId);
	}
	streamId = window.sessionStorage.getItem("streamId");
	//视频状态
	var state = 1;
	server(
		{
			"url":getRootPath() + "/app/live/getLiveByStreamId",
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
					
					//加入聊天群
					var groupId = data.data.groupId;//im群ID
					new im().loginInfo(
						{
							"identifier": loginInfo.identifier,
							"identifierNick": loginInfo.identifierNick,
							"userSig":loginInfo.userSig
						},
						groupId
					);
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
					
					//移除在线聊天
					$(".msgObject").remove();
				}
				
				//播放器初始化
				var $script = document.createElement("script");
				$script.src = $scriptSrc;
				document.body.appendChild($script);
				$script.onload = function(){
					new player_content().init(options);
				}
				
				//在线人数
				$(".userList div .count").innerText = data.data.viewerCount;
				//直播头像
				$(".user img").src = NULL(data.data.user.headPic) || "img/userHeader.jpg";
				//主播名
				$(".user div span:first-child").innerText = data.data.user.account;
				//播放状态
				$(".user div span:last-child").innerText = data.data.stateName;
				//点赞数
				$(".zanNum").innerText = data.data.praiseCount;
				
				//用户加入（后台服务器）
				server(
					{
						"url":getRootPath() + "/app/live/viewer/add",
						"data":{
							"streamId":streamId
						}
					},
					function(data){
						//不管接口是否执行成功
					}
				);
			}
		}
	);
	
	//点赞
	$(".zan").addEventListener("click",function(){
		if(state == 1){
			if(!loginInfo.identifier){
				goLogin(loginInfo);
				return;
			}
			//直播中，点赞同步
			new imBase().sendMsg("通知："+ loginInfo.identifierNick +"点了个赞",4);
		}
		zan(this);
		
		//后台点赞数+1
		server(
			{
				"url":getRootPath() + "/app/live/praise/add",
				"data":{
					"streamId":streamId
				},
				"openLoad":false
			},
			function(data){
				//不管接口是否执行成功
			}
		);
	},false);
	
	//发送消息
	$(".sendMsg form").onsubmit = function(){
		if(loginInfo.identifier){
			var $input = this.querySelector("input");
			var value = $input.value;
			if(value){
				new imBase().sendMsg(value,1);
				$input.value = "";
			}
		}else{
			goLogin(loginInfo);
		}
		return false;
	}
});