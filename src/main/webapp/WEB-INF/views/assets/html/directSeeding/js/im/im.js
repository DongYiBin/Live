var im = function(){
	
}

im.prototype.str_json = function(str){
	//字符串替换
	str = str.replace(new RegExp('&quot;','g'),'"');
	return str;
}

im.prototype.login = function(data){
	webim.login(
		data.loginInfo,
		data.listeners,
		data.options,
		function(resp){
			//加入群组
			webim.applyJoinBigGroup(
	            data.groupId,
	            function (resp){
	                //JoinedSuccess:加入成功; WaitAdminApproval:等待管理员审批
	                if (resp.JoinedStatus && resp.JoinedStatus == 'JoinedSuccess') {
	                	//进入群组成功,发送用户进入消息结构体
	                	if(loginInfo.identifier){
	                		new imBase().sendMsg("通知："+ loginInfo.identifierNick +"加入直播",2);
	                	}
	                }else{
	                	layer.open({
						    content: '进入群组失败',
						    time: 2
						});
	                }
	            },
	            function (err){
	            	layer.open({
					    content: '进入群组失败',
					    time: 2
					});
	            }
		    );
		},
		function(err){
			layer.open({
			    content: '服务器连接失败!',
			    time: 2
			});
		}
	);
}

im.prototype.onMsg = function(msg){
	//消息监听
	var elems,elem, type, content;
    elems=msg.getElems();//获取消息包含的元素数组
    for (var i in elems) {
        elem = elems[i];
        type = elem.getType();//获取元素类型
        content = elem.getContent();//获取元素对象
        switch (type) {
            case webim.MSG_ELEMENT_TYPE.TEXT:
            	//普通消息
            	new imBase().getMsg(JSON.parse(new im().str_json(content.getText())));
            break;
        }
    
    }
}

im.prototype.sendMsg = function(msgTxt,callback){
	//发送消息
	var loginInfo = this.data.loginInfo,
		//当前聊天ID
		selToID = this.data.groupId.GroupId,
		//类型
		selType = webim.SESSION_TYPE.GROUP,
		//当前聊天会话
		selSess = null,
		//群组头像
		selSessHeadUrl = 'img/2017.jpg';

    if (!selSess) {
        selSess = new webim.Session(selType, selToID, selToID, selSessHeadUrl, Math.round(new Date().getTime() / 1000));
    }
    var subType;//消息子类型
    if (selType == webim.SESSION_TYPE.GROUP) {
        subType = webim.GROUP_MSG_SUB_TYPE.COMMON;
    } else {
        subType = webim.C2C_MSG_SUB_TYPE.COMMON;
    }
    var msg = new webim.Msg(selSess, true, -1, Math.round(Math.random() * 4294967296), Math.round(new Date().getTime() / 1000), loginInfo.identifier, subType, loginInfo.identifierNick);
    var text_obj, face_obj, tmsg, emotionIndex, emotion, restMsgIndex;
	//解析文本和表情
    var expr = /\[[^[\]]{1,3}\]/mg;
    var emotions = msgTxt.match(expr);
    if (!emotions || emotions.length < 1) {
        text_obj = new webim.Msg.Elem.Text(msgTxt);
        msg.addText(text_obj);
    } else {//有表情
        for (var i = 0; i < emotions.length; i++) {
            tmsg = msgtosend.substring(0, msgtosend.indexOf(emotions[i]));
            if (tmsg) {
                text_obj = new webim.Msg.Elem.Text(tmsg);
                msg.addText(text_obj);
            }
            emotionIndex = webim.EmotionDataIndexs[emotions[i]];
            emotion = webim.Emotions[emotionIndex];
            if (emotion) {
                face_obj = new webim.Msg.Elem.Face(emotionIndex, emotions[i]);
                msg.addFace(face_obj);
            } else {
                text_obj = new webim.Msg.Elem.Text(emotions[i]);
                msg.addText(text_obj);
            }
            restMsgIndex = msgtosend.indexOf(emotions[i]) + emotions[i].length;
            msgtosend = msgtosend.substring(restMsgIndex);
        }
        if (msgtosend) {
            text_obj = new webim.Msg.Elem.Text(msgtosend);
            msg.addText(text_obj);
        }
    }
    webim.sendMsg(msg, function (resp) {
        if (selType == webim.SESSION_TYPE.C2C) {//私聊时，在聊天窗口手动添加一条发的消息，群聊时，长轮询接口会返回自己发的消息
            console.log(msg);
        }
        if(callback) callback();
        //发送成功
    }, function (err) {
    	layer.open({
		    content: err.ErrorInfo,
		    time: 1
		});
    });
}

im.prototype.data = {
	//登录信息
	"loginInfo":{
		"sdkAppID":sdkappid,
		"appIDAt3rd":sdkappid,
		"accountType":acctype,
		"identifier":"",
		"identifierNick":"",
		"userSig":""				
	},
	//事件回调
	"listeners":{
	    "onConnNotify": function(resp) {
						    switch (resp.ErrorCode) {
						        case webim.CONNECTION_STATUS.ON:
						        	//连接正常
						            break;
						        case webim.CONNECTION_STATUS.OFF:
						            break;
						        default:
						        	webim.Log.warn("未知连接状态");
						            break;
						    }
						}, //选填
	    "jsonpCallback": function(rspData) {
						    webim.setJsonpLastRspData(rspData);
						}, //IE9(含)以下浏览器用到的jsonp回调函数,移动端可不填，pc端必填
						
	    "onBigGroupMsgNotify": function(msgList) {
								    for (var i = msgList.length - 1; i >= 0; i--) {//遍历消息，按照时间从后往前
								        var msg = msgList[i];
								        webim.Log.warn('receive a new group msg: ' + msg.getFromAccountNick());
								        //显示收到的消息
								        new im().onMsg(msg);
								    }
								}, //监听新消息(大群)事件，必填
								
	    "onGroupSystemNotifys": {
								    "5": function(){
								    	new imBase().out();
								    }, //群被解散(全员接收)
								    "8": function(){
								    	
								    }, //主动退群(主动退出者接收)
								    "11": function(){
								    	
								    }, //群已被回收(全员接收)
								    "255": function(){
								    	
								    }//用户自定义通知(默认全员接收)
								} //监听（多终端同步）群系统消息事件，必填
	},
	"options":{
		"isAccessFormalEnv":true,
		"isLogOn":false
	},
	//大群ID
	"groupId":{}
};

//登录信息
im.prototype.loginInfo = function(userInfo,groupId){
	//用户帐号
	this.data.loginInfo.identifier = userInfo.identifier;
	//用户昵称
	this.data.loginInfo.identifierNick = userInfo.identifierNick;
	//鉴权Token
	this.data.loginInfo.userSig = userInfo.userSig;
	//聊天室ID
	this.data.groupId = {"GroupId":groupId}
	//登录
	this.login(this.data);
}