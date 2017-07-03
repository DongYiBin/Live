//ajax数据请求
function data_ajax(options, success) {
	//请求地址(*必传*)
	var url = options.url,
		//请求方式
		type = options.type || 'post',
		//请求超时
		timeout = options.timeout || 1000*60,
		//异步请求
		async = options.async || true,
		//浏览器缓存策略
		cache = options.cache || true,
		//发送数据
		data = options.data || {},
		//数据返回格式
		dataType = options.dataType || 'json';
	//开始加载资源
	layer.load(2);
	$.ajax({
		url : url,
		type : type,
		timeout : timeout,
		async : async,
		cache : cache,
		data : data,
		dataType : dataType,
		success : function(data, textStatus) {
			//数据加载成功
			layer.closeAll('loading');
			if(data.code != 1){
				layer.msg(data.msg, {icon: 5});
			}else{
				success(data);
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			//数据加载失败
			layer.closeAll('loading');
			layer.msg(errorThrown, {icon: 5});
		}
	});
}

//分页
function groPage(selector,pages,groups,callback){
	laypage({
	    cont: selector,
	    pages: pages,
	    skip: true, //开启跳页
	    skin: '#337ab7',
	    groups:groups,
	    jump: function(obj){
	    	callback(obj.curr);
	    }
	});
};

//时间戳转换成时间格式
function dateTimeFormatter(nS) {
	var str = '';
	if(nS == undefined || nS == '' || nS == null){
		return str;
	}
	if((nS + '').length < 13){
		nS = nS * 1000;
		var data = new Date(parseInt(nS)),
			_Month = (data.getMonth() + 1) < 10 ? '0' + (data.getMonth() + 1) : data.getMonth() + 1,
			_date = data.getDate() < 10 ? '0' + data.getDate() : data.getDate(),
			_hours = data.getHours() < 10 ? '0' + data.getHours() : data.getHours(),
			_min = data.getMinutes() < 10 ? '0' + data.getMinutes() : data.getMinutes(),
			_seconds = data.getSeconds() < 10 ? '0' + data.getSeconds() : data.getSeconds();
		str = data.getFullYear() + '-' + _Month + '-' + _date + ' ' + _hours + ':' + _min + ':' + _seconds;
		return str;
	} else {
		var data = new Date(parseInt(nS)),
		_Month = (data.getMonth() + 1) < 10 ? '0' + (data.getMonth() + 1) : data.getMonth() + 1,
		_date = data.getDate() < 10 ? '0' + data.getDate() : data.getDate(),
		_hours = data.getHours() < 10 ? '0' + data.getHours() : data.getHours(),
		_min = data.getMinutes() < 10 ? '0' + data.getMinutes() : data.getMinutes(),
		_seconds = data.getSeconds() < 10 ? '0' + data.getSeconds() : data.getSeconds();
		str = data.getFullYear() + '-' + _Month + '-' + _date + ' ' + _hours + ':' + _min + ':' + _seconds;
		return str;
	}
}

/**
 * 字符串转换
 */
function NULL(str){
	if(str == null || str == undefined){
		str = "";
	}
	return str;
}