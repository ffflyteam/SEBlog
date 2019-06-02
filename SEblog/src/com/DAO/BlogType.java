package com.DAO;

import java.util.HashMap;
import java.util.Map;

public enum BlogType {
	
	DEFAULT(0, "默认/无分类"),
	JI_SHU(1, "技术"),
	;
	
	private int id;
	private String desc;
	
	private static Map<Integer, BlogType> blogTypeMap = new HashMap<>();
	static {
		for(BlogType blogType : values()) {
			blogTypeMap.put(blogType.getId(), blogType);
		}
	}
	
	public static BlogType getBlogTypeById(int id) {
		return blogTypeMap.getOrDefault(id, DEFAULT);
	}
	
	private BlogType(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public String getDesc() {
		return desc;
	}
	
}
