var player_content = function(){
	var $play = $("#id_video_container");
	this.width = $play.offsetWidth;
	this.height = $play.offsetHeight;
}

player_content.prototype.init = function(options){
	options.width = this.width;
	options.height = this.height;
	var listener = function(status){
		if(status == 'ready'){
			//阻止IOS下video自动全屏播放
			var _videos = document.querySelectorAll('video');
			for(var i=0; i<_videos.length; i++){
				_videos[i].setAttribute('webkit-playsinline','true');
				_videos[i].setAttribute('preload','auto');
				//针对QQ浏览器X5内核
				_videos[i].setAttribute("x5-video-player-type","h5");
				_videos[i].setAttribute("x5-video-player-fullscreen","true");
			}
		}
	}
	var player = new qcVideo.Player("id_video_container", options, listener);
}

player_content.prototype.mobil = function(){
	//针对移动的设置播放器
	var _videos = document.querySelectorAll('video');
	console.log(_videos);
	for(var i=0; i<_videos.length; i++){
		_videos[i].setAttribute('webkit-playsinline','true');
		_videos[i].setAttribute('preload','auto');
		//针对QQ浏览器X5内核
		_videos[i].setAttribute("x5-video-player-type","h5");
		_videos[i].setAttribute("x5-video-player-fullscreen","true");
	}
}