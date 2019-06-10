package com.user.message.client;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int accountId;
	private String password;
	private String userName;
	private short sex;
	private Date birthDay;
	private String address;
	private int stat;
	
	public User() {
	}
	
	public User(int accountId, String password, String userName, short sex, Date birthDay, String address, int stat) {
		super();
		this.accountId = accountId;
		this.password = password;
		this.userName = userName;
		this.sex = sex;
		this.birthDay = birthDay;
		this.address = address;
		this.stat = stat;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public short getSex() {
		return sex;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public String getAddress() {
		return address;
	}
	
	public int getStat() {
		return stat;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("'Account':").append(accountId);
		sb.append(",'UserName':").append(userName);
		sb.append(",'Sex':").append(sex == 0 ? "Ů" : "��");
		sb.append(",'BirthDay':").append(birthDay.toString());
		sb.append(",'Address':").append(address);
		return sb.toString();
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setSex(short sex) {
		this.sex = sex;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
