package com.DAO;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int blogId;
	private Date publishDateTime;
	private int userId;
	private String title;
	private String content;
	private int commentsNum;
	private int transfersNum;
	private int collectsNum;
	private int type;

	public Blog() {
	}
	
	public Blog(int blogId, Date publishDateTime, int userId, String title, String content, int commentsNum,
			int transfersNum, int collectsNum, int type) {
		super();
		this.blogId = blogId;
		this.publishDateTime = publishDateTime;
		this.userId = userId;
		this.title = title;
		this.content = content;
		this.commentsNum = commentsNum;
		this.transfersNum = transfersNum;
		this.collectsNum = collectsNum;
		this.type = type;
	}

	public int getBlogId() {
		return blogId;
	}

	public Date getPublishDateTime() {
		return publishDateTime;
	}

	public int getUserId() {
		return userId;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public int getCommentsNum() {
		return commentsNum;
	}

	public int getTransfersNum() {
		return transfersNum;
	}

	public int getCollectsNum() {
		return collectsNum;
	}

	public int getType() {
		return type;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BlogId:").append(blogId);
		sb.append(",PublishDateTime:").append(publishDateTime);
		sb.append(",UserId:").append(userId);
		sb.append(",Title:").append(title);
		sb.append(",Content:").append(content);
		sb.append(",CommentsNum:").append(commentsNum);
		sb.append(",TransfersNum:").append(transfersNum);
		sb.append(",CollectsNum:").append(collectsNum);
		sb.append(",Type:").append(type);
		return sb.toString();
	}
}
