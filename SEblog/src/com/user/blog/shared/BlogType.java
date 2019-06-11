package com.user.blog.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum BlogType {
	
	DEFAULT(0, "Ĭ��/�޷���"),
	JI_SHU(1, "����"),
	;
	
	private int id;
	private String desc;
	
	private static Map<Integer, BlogType> blogTypeMap = new HashMap<>();
	private static List<BlogType> allTypes = new ArrayList<>();
	static {
		for(BlogType blogType : values()) {
			blogTypeMap.put(blogType.getId(), blogType);
			allTypes.add(blogType);
		}
	}
	
	public static BlogType getBlogTypeById(int id) {
		return blogTypeMap.getOrDefault(id, DEFAULT);
	}
	
	public static List<BlogType> getAllType() {
		return allTypes;
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
