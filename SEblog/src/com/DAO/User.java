package com.DAO;

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
		/*accountId = 0;
		password = "";
		userName=  "";
		sex = 0;
		birthDay = new Date();
		address = "";
		stat = 0;*/
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
		/*JSONObject jsonObject = new JSONObject();
		try {
			jsonObject.put("Account", accountId);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			jsonObject.put("UserName", userName);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jsonObject.put("Sex", sex == 0 ? "Ů" : "��");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jsonObject.put("BirthDay", birthDay.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jsonObject.put("Address", address);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject.toString();*/
		StringBuilder sb = new StringBuilder();
		sb.append("'Account':").append(accountId);
		sb.append(",'UserName':").append(userName);
		sb.append(",'Sex':").append(sex == 0 ? "Ů" : "��");
		sb.append(",'BirthDay':").append(birthDay.toString());
		sb.append(",'Address':").append(address);
		return sb.toString();
	}
}
