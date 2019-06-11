package com.detail.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FeedBackType {

	POMOGRAPHIC(0, "色情淫秽"),
	ANTI_COMMUNIST(1, "反共"),
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
