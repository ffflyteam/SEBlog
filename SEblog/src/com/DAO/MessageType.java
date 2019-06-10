package com.DAO;

import java.util.HashMap;
import java.util.Map;

public enum MessageType {
	HAPPEN_RELATION(0,"有人关注你哟！"),//被他人关注
	GET_COMMENT(1,"有人在你博客/评论下写了评论"),//自己博客或评论被评论
	;
	
	private int id;
	private String message;
	
	private static Map<Integer, MessageType> map = new HashMap<>();
	static {
		for(MessageType type : values()) {
			map.put(type.id, type);
		}
	}
	
	public static MessageType getMessageTypeById(int id) {
		return map.get(id);
	}
	
	MessageType(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public String getMessage() {
		return message;
	}
	
}
