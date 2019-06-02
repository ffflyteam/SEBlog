package com.DAO;

public enum UserStat {
	NORMAL(0),   //正常使用中
	BE_FROZEN(1),  //被冻结
	;
	
	private int id;
	private UserStat(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
