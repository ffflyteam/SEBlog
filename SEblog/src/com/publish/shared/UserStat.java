package com.publish.shared;

public enum UserStat {
	NORMAL(0),   //����ʹ����
	BE_FROZEN(1),  //������
	;
	
	private int id;
	private UserStat(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
}
