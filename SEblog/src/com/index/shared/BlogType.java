package com.index.shared;

import java.util.HashMap;
import java.util.Map;

public enum BlogType {
	
	DEFAULT(0, "默认"),
	JI_SHU(1, "IT"),
	XIN_WEN(2,"新闻"),
	LI_SHI(3,"历史"),
	WEN_XUE(4,"文学"),
	CAI_JING(5,"财经"),
	TI_YU(6,"体育"),
	RI_CHANG(7,"日常"),
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
