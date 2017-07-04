/*
 * Copyright © 2013-2017 Tencent Cloud. All Rights Reserved.
 * Tencent PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package com.mz.live.dao.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mz.live.common.JsonDateSerializer;
import com.mz.live.common.constant.EnumLive;

/**
 * 
 * @author Tencent Cloud
 * @author CETC55
 * @date 2017/7/3
 * @since v1.0
 */
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class Live {
	private String streamId;

	private String userId;

	private String groupId;

	private String title;

	private String coverPic;

	private String location;

	private String pushUrl;

	private String playRtmpUrl;

	private String playFlvUrl;

	private String playHlsUrl;

	private String playUrl;

	private Integer state;
	
	private String stateName;

	private Long duration;

	private Date startTime;

	private Date createTime;

	private Long viewerCount;

	private Long praiseCount;
	
	private User user;

	private List<LiveVideo> liveVideos = new ArrayList<LiveVideo>();

	public String getStreamId() {
		return streamId;
	}

	public void setStreamId(String streamId) {
		this.streamId = streamId == null ? null : streamId.trim();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId == null ? null : userId.trim();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId == null ? null : groupId.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic == null ? null : coverPic.trim();
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location == null ? null : location.trim();
	}

	public String getPushUrl() {
		return pushUrl;
	}

	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl == null ? null : pushUrl.trim();
	}

	public String getPlayRtmpUrl() {
		return playRtmpUrl;
	}

	public void setPlayRtmpUrl(String playRtmpUrl) {
		this.playRtmpUrl = playRtmpUrl == null ? null : playRtmpUrl.trim();
	}

	public String getPlayFlvUrl() {
		return playFlvUrl;
	}

	public void setPlayFlvUrl(String playFlvUrl) {
		this.playFlvUrl = playFlvUrl == null ? null : playFlvUrl.trim();
	}

	public String getPlayHlsUrl() {
		return playHlsUrl;
	}

	public void setPlayHlsUrl(String playHlsUrl) {
		this.playHlsUrl = playHlsUrl == null ? null : playHlsUrl.trim();
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl == null ? null : playUrl.trim();
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getViewerCount() {
		return viewerCount;
	}

	public void setViewerCount(Long viewerCount) {
		this.viewerCount = viewerCount;
	}

	public Long getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Long praiseCount) {
		this.praiseCount = praiseCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<LiveVideo> getLiveVideos() {
		return liveVideos;
	}

	public void setLiveVideos(List<LiveVideo> liveVideos) {
		this.liveVideos = liveVideos;
	}

	public String getStateName() {
		if(EnumLive.STATE_CREATE.getValue() == this.state){
			return EnumLive.STATE_CREATE.getName();
		}
		if(EnumLive.STATE_STOP.getValue() == this.state){
			return EnumLive.STATE_STOP.getName();
		}
		if(EnumLive.STATE_PUSH.getValue() == this.state){
			return EnumLive.STATE_PUSH.getName();
		}
		if(EnumLive.STATE_PLAY.getValue() == this.state){
			return EnumLive.STATE_PLAY.getName();
		}
		if(EnumLive.STATE_SCREENSHO.getValue() == this.state){
			return EnumLive.STATE_SCREENSHO.getName();
		}
		if(EnumLive.STATE_FORBID.getValue() == this.state){
			return EnumLive.STATE_FORBID.getName();
		}
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	
}