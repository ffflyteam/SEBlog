package com.manager.index.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//直接根据反馈类型来区分是对博客、评论、用户的举报
public enum FeedBackType {

	//博客类，从0开始
	POMOGRAPHIC(0, "色情淫秽"),
	ANTI_COMMUNIST(1, "内容反共"),
	//用户类，从100开始
	USER_BEHAVIOR_UNNOMAL(100, "用户行为不正常"),
	//评论类，从200开始
	COMMENT_CONTENT_ABUSE(200, "评论内容辱骂他人"),
	
	;
	
	private int id;
	private String desc;
	private static Map<Integer, FeedBackType> map = new HashMap<>();
	private static List<FeedBackType> allList = new ArrayList<>();
	static {
		for(FeedBackType type : values()) {
			map.put(type.id, type);
			allList.add(type);
		}
	}
	
	public static FeedBackType getFeedBackById(int id) {
		return map.get(id);
	}
	
	public static List<FeedBackType> getAllFeedBack() {
		return allList;
	}
	
	FeedBackType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}

	public static Map<Integer, FeedBackType> getMap() {
		return map;
	}
	
}
