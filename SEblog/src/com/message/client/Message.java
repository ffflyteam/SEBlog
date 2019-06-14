package com.message.client;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int messageId;
	private User receiver;
	private int messageType;
	private User sender;
	private int blogId;
	private String blogTitle;
	private int readFlag;
	private Date createTime;
	
	public Message() {
	}

	public Message(int messageId, User receiver, int messageType, User sender, int blogId, String blogTitle, int readFlag, Date createTime) {
		super();
		this.messageId = messageId;
		this.receiver = receiver;
		this.messageType = messageType;
		this.sender = sender;
		this.blogId = blogId;
		this.readFlag = readFlag;
		this.blogTitle = blogTitle;
		this.createTime = createTime;
	}

	public int getMessageId() {
		return messageId;
	}

	public User getReceiver() {
		return receiver;
	}

	public int getMessageType() {
		return messageType;
	}

	public User getSender() {
		return sender;
	}
	
	public int getBlogId() {
		return blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}
	
	public int getReadFlag() {
		return readFlag;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	
}
