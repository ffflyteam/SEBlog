package com.manager.client;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int commentId;
	private int objectId;
	private User user;
	private Date commentDateTime;
	private String content;
	//private List<Comment> allComments;
	private int commentNum;
	public Comment() {
	}
	
	public Comment(int commentId, int objectId, User user, Date commentDateTime, String content, int commentNum ) {//List<Comment> allComments
		super();
		this.commentId = commentId;
		this.objectId = objectId;
		this.user = user;
		this.commentDateTime = commentDateTime;
		this.content = content;
		//this.allComments = allComments;
		this.commentNum = commentNum;
	}

	public int getCommentId() {
		return commentId;
	}

	public int getObjectId() {
		return objectId;
	}

	public User getUser() {
		return user;
	}

	public Date getCommentDateTime() {
		return commentDateTime;
	}

	public String getContent() {
		return content;
	}
	
	public int getCommentNum() {
		return commentNum;
	}
	
	/*
	 * public List<Comment> getAllComments() { return allComments; }
	 */

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("CommentId:").append(commentId);
		sb.append(",ObjectId:").append(objectId);
		//sb.append(",User:").append(user.toString());
		sb.append(",CommentDateTime:").append(commentDateTime);
		sb.append(",Content:").append(content);
		sb.append(",CommentNums:").append(commentNum);
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + commentId;
		result = prime * result + objectId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Comment other = (Comment) obj;
		if (commentId != other.commentId)
			return false;
		if (objectId != other.objectId)
			return false;
		return true;
	}
	
	

}
