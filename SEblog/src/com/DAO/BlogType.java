package com.DAO;

public enum BlogType {
	
	JI_SHU(1, "技术"),
	RE_MEN(2, "热门"),
	;
	
	private int id;
	private String desc;
	
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
