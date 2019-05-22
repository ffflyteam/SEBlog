package com.index.shared;

import java.util.HashMap;
import java.util.Map;



public enum BlogType {
	
	JI_SHU(1, "技术"),
	RE_MEN(2, "热门"),
	;
	
	private int id;
	private String desc;
	private static Map<Integer, BlogType> allRs = new HashMap<>();
	static {
		for(BlogType rs : BlogType.values()) {
			allRs.put(rs.getId(), rs);
		}

	}
	
	public static BlogType getBtById(int id) {
		return allRs.get(id);
		
	}
	
	BlogType(int id, String desc) {
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
