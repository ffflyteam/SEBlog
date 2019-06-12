package com.user.client;

import java.io.Serializable;
import java.util.Date;

public class Blog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int blogId;
	private Date publishDateTime;
	private User user;
	private String title;
	private String content;
	private int commentsNum;
	private int transfersNum;
	private int collectsNum;
	private int readNum;
	private int type;
	//private List<Comment> allComments;
	
	public Blog() {
	}
	
	public Blog(int blogId, Date publishDateTime, User user, String title, String content, int commentsNum,
			int transfersNum, int collectsNum, int readNum, int type) {//List<Comment> allComments
		super();
		this.blogId = blogId;
		this.publishDateTime = publishDateTime;
		this.user = user;
		this.title = title;
		this.content = content;
		this.commentsNum = commentsNum;
		this.transfersNum = transfersNum;
		this.collectsNum = collectsNum;
		this.readNum = readNum;
		this.type = type;
		//this.allComments = allComments;
	}

	public final int getBlogId() {
		return blogId;
	}

	public final Date getPublishDateTime() {
		return publishDateTime;
	}

	public final User getUser() {
		return user;
	}

	public final String getTitle() {
		return title;
	}

	public final String getContent() {
		return content;
	}

	public final int getCommentsNum() {
		return commentsNum;
	}

	public final int getTransfersNum() {
		return transfersNum;
	}

	public final int getCollectsNum() {
		return collectsNum;
	}

	public final int getReadNum() {
		return readNum;
	}

	public final int getType() {
		return type;
	}

	/*public final List<Comment> getAllComments() {
		return allComments;
	}*/

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("BlogId:").append(blogId);
		sb.append(",PublishDateTime:").append(publishDateTime);
		sb.append(",UserId:").append(user.getUserName());
		sb.append(",Title:").append(title);
		sb.append(",Content:").append(content);
		sb.append(",CommentsNum:").append(commentsNum);
		sb.append(",TransfersNum:").append(transfersNum);
		sb.append(",CollectsNum:").append(collectsNum);
		sb.append(",ReadNum:").append(readNum);
		sb.append(",Type:").append(type);
		return sb.toString();
	}

	public synchronized void setPublishDateTime(Date publishDateTime) {
		this.publishDateTime = publishDateTime;
	}

	public synchronized void setTitle(String title) {
		this.title = title;
	}

	public synchronized void setContent(String content) {
		this.content = content;
	}

	public synchronized void setCommentsNum(int commentsNum) {
		this.commentsNum = commentsNum;
	}

	public synchronized  void setTransfersNum(int transfersNum) {
		this.transfersNum = transfersNum;
	}

	public synchronized void setCollectsNum(int collectsNum) {
		this.collectsNum = collectsNum;
	}

	public synchronized void setReadNum(int readNum) {
		this.readNum = readNum;
	}

	public synchronized void setType(int type) {
		this.type = type;
	}
	
	/*public void setAllComments(List<Comment> allComments) {
		this.allComments = allComments;
	}*/

	public synchronized void increaseCollectsNum() {
		this.collectsNum++;
	}
	
	public synchronized void increaseTransferNum() {
		this.transfersNum++;
	}
	
	public synchronized void increaseReadNum() {
		this.readNum++;
	}
	
	public synchronized void increaseCommentsNum() {
		this.commentsNum++;
	}
	
	public synchronized void decreaseCollectsNum() {
		this.collectsNum--;
	}
	
	public synchronized void decreaseTransferNum() {
		this.transfersNum--;
	}
	
	public synchronized void decreaseReadNum() {
		this.readNum--;
	}
	
	public synchronized void decreaseCommentsNum() {
		this.commentsNum--;
	}
	
	/*public void removeCommentById(int commentId) {
		Comment c = null;
		for(Comment comment : allComments) {
			if(comment.getCommentId() == commentId) {
				c = comment;
				break;
			}
		}
		if(c != null) {
			allComments.remove(c);
		}
	}
	
	public void addComment(Comment comment) {
		allComments.add(comment);
	}*/
}
