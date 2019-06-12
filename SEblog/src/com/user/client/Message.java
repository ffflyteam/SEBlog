package com.user.client;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int messageId;
	private User receiver;
	private int messageType;
	private User sender;
	private int readFlag;
	private Date createTime;
	
	public Message() {
	}

	public Message(int messageId, User receiver, int messageType, User sender, int readFlag, Date createTime) {
		super();
		this.messageId = messageId;
		this.receiver = receiver;
		this.messageType = messageType;
		this.sender = sender;
		this.readFlag = readFlag;
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

	public int getReadFlag() {
		return readFlag;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	
}
