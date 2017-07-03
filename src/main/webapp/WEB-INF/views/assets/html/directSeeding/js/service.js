/**
 * 获取demo元素
 * @param {Object} selector
 */
function $(selector){
	return document.querySelector(selector);
}

/**
 * 获取参数
 * @param {Object} name
 */
function GetUrlName(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 对象转换成url地址参数
 */
function param(data){
	var str = '';
	for(var key in data){
		str += key + '=' + data[key] + '&';
	}
	return str.substring(0,str.lastIndexOf('&'));
}

/**
 * 字符串转换
 */
function NULL(str){
	if(str == null || str == undefined)
		return '';
	else
		return str;
}

/**
 * 检测当前是否为微信浏览器
 */
function isweixin(){
	var ua = window.navigator.userAgent.toLowerCase();
	if(ua.match(/MicroMessenger/i) == 'micromessenger'){ 
		return true; 
	}else{ 
		return false; 
	} 
}

/**
 * ajax请求
 * @param {Object} options
 * @param {Object} callback
 */
function server(options,callback){
	//加载层
	var layerIndex = null;
	if(options.openLoad != false)
		layerIndex = layer.open({type: 2});
	var _dataType = options.dataType || 'POST';
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
			var data = JSON.parse(xmlhttp.responseText);
			if(layerIndex != null){
				layer.close(layerIndex);
			}
			callback(data);
		}else if((xmlhttp.status == 400 || xmlhttp.status == 500 || xmlhttp.status == 404 || xmlhttp.status == 502)){
			layer.open({
				content:"服务器错误",
				time:2
			});
		}
	};
	xmlhttp.open(_dataType,options.url,true);
	if(_dataType == 'POST'){
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send(param(options.data));
	}else{
		xmlhttp.send();
	}
}