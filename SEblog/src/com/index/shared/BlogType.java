package com.index.shared;

import java.util.HashMap;
import java.util.Map;

public enum BlogType {
	
	DEFAULT(0, "Ĭ��/�޷���"),
	JI_SHU(1, "����"),
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
		return blogTypeMap.get(id);
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
