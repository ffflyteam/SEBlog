package com.manager.client;

import java.util.Date;

public class FeedBack {
	private int feedBackId;
	private int objectId;
	private int userId;
	private int type;
	private int readFlag;
	private Date createTime;
	
	public FeedBack() {
	}

	public FeedBack(int feedBackId, int objectId, int userId, int type, int readFlag, Date createTime) {
		super();
		this.feedBackId = feedBackId;
		this.objectId = objectId;
		this.userId = userId;
		this.type = type;
		this.readFlag = readFlag;
		this.createTime = createTime;
	}

	public int getFeedBackId() {
		return feedBackId;
	}

	public int getObjectId() {
		return objectId;
	}

	public int getUserId() {
		return userId;
	}

	public int getType() {
		return type;
	}

	public int getReadFlag() {
		return readFlag;
	}

	public Date getCreateTime() {
		return createTime;
	}
	
}
