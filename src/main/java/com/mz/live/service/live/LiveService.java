package com.mz.live.service.live;

import com.mz.live.common.RestResult;
import com.mz.live.common.page.PageData;
import com.mz.live.controller.app.callback.vo.LiveCalbackVo;
import com.mz.live.dao.entity.Live;

public interface LiveService {

	/**
	 * 创建直播
	 * @param live
	 * @return
	 * @author zhaodun
	 */
	public RestResult createLive(Live live);

	/**
	 * 根据streamId获取直播
	 * 
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public Live getLiveByStreamId(String streamId);

	/**
	 * 获取APP直播分页列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param live
	 * @return
	 * @author zhaodun
	 */
	public PageData<Live> getAppLivePage(Integer pageNum, Integer pageSize, Live live);

	/**
	 * 直播点赞
	 * @param streamId
	 * @param num
	 * @return
	 * @author zhaodun
	 */
	public Live addLivePraiseNum(String streamId,int num);

	/**
	 * 添加直播观看次数
	 * @param streamId
	 * @param num
	 * @return
	 * @author zhaodun
	 */
	public Live addLiveViewerNum(String streamId,int num);
	
	/**
	 * 直播回调处理
	 * @param vo
	 * @return
	 * @author zhaodun
	 */
	public boolean liveCallBackDispose(LiveCalbackVo vo);

	/**
	 * 直播开始推流
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public Live pushStart(String streamId);

	/**
	 * 直播结束推流
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public Live pushStop(String streamId);

	/**
	 * 查询直播数据分页列表
	 * @param pageNum
	 * @param pageSize
	 * @param live
	 * @return
	 * @author zhaodun
	 */
	public PageData<Live> findLivePageList(Integer pageNum, Integer pageSize, Live live);

	/**
	 * 禁播直播
	 * @param streamId
	 * @return
	 * @author zhaodun
	 */
	public RestResult forbidLive(String streamId);
}
