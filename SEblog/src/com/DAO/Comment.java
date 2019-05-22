package com.DAO;

import java.util.Date;

public class Comment {
	private final int commentId;
	private final int objectId;
	private final int accountId;
	private final Date commentDateTime;
	private final String content;

	public Comment(int commentId, int objectId, int accountId, Date commentDateTime, String content) {
		super();
		this.commentId = commentId;
		this.objectId = objectId;
		this.accountId = accountId;
		this.commentDateTime = commentDateTime;
		this.content = content;
	}

	public int getCommentId() {
		return commentId;
	}

	public int getObjectId() {
		return objectId;
	}

	public int getAccountId() {
		return accountId;
	}

	public Date getCommentDateTime() {
		return commentDateTime;
	}

	public String getContent() {
		return content;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CommentId:").append(commentId);
		sb.append(",ObjectId:").append(objectId);
		sb.append(",AccountId:").append(accountId);
		sb.append(",CommentDateTime:").append(commentDateTime);
		sb.append(",Content:").append(content);
		return sb.toString();
	}

}
