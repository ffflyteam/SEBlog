package com.detail.shared;

public enum RelationWithBlog {
	NO_RELATION(0),
	COLLECT(1),
	TRANSFER(2),
	BOTH_COLLECT_AND_TRANSFER(3),
	;
	
	private int id;
	
	private RelationWithBlog(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
