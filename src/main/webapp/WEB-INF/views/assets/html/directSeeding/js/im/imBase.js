var imBase = function(){
	
}

imBase.prototype.sendMsg = function(msg,userAction){
	//发送消息
	var msgJson = {
		"headPic":"img/userHeader.jpg",
	    "msg":msg,
	    "nickName":loginInfo.identifierNick,
	    "userAction":userAction,
	    "userId":loginInfo.identifier,
	};
	new im().sendMsg(JSON.stringify(msgJson),function(){
		//消息发送成功
	});
}

imBase.prototype.getMsg = function(data){
	//接收消息
	switch(data.userAction){
		case 1:
			//聊天消息
			var html = '\
						<li>\
							<div>\
								<span>'+ data.nickName +':</span>'+ data.msg +'\
							</div>\
						</li>\
					';
			msgListContent(html);
		break;
		case 2:
			//用户加入直播
			var html = '\
						<li>\
							<div>\
								<span>通知:</span>'+ data.nickName +'进入直播间\
							</div>\
						</li>\
					';
			msgListContent(html);	
			userJoin();
		break;
		case 3:
			//用户退出
			userOut();
		break;
		case 4:
			//点赞消息
			if(data.userId != loginInfo.identifier){
				zan($(".zan"));
			}
		break;
		
	}
}

imBase.prototype.out = function(){
	//主播退出
	layer.open({
	    content: '直播已结束,请稍后查看回放',
	});
}

imBase.prototype.zan = function(msgData){
	//点赞消息
	console.log(msgData);
}

imBase.prototype.join = function(msgData){
	//用户进入
	console.log(msgData);
}

//点赞
function zan(selecotr){
	var $zanNum = $(".zanNum");
	$zanNum.innerText = parseInt($zanNum.innerText) + 1;
	var $span = document.createElement("span");
	$span.innerText = "+1";
	$span.style.webkitAnimation = 'anim linear 1s';
	$span.style.animation = 'anim linear 1s';
	selecotr.querySelector("div").appendChild($span);
	$span.addEventListener('animationend',function(e){
		this.remove();
	},false);
}

//用户进入
function userJoin(){
	var $div = $(".count");
	$div.innerText = parseInt($div.innerText) + 1;
}

//用户退出
function userOut(){
	var $div = $(".count");
	$div.innerText = parseInt($div.innerText) - 1;
}

//显示消息
function msgListContent(html){
	var $ul = $(".lisgMsg ul");
	$ul.innerHTML = $ul.innerHTML + html;
}
