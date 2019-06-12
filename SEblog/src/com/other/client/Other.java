package com.other.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.user.client.User;

public class Other implements EntryPoint {
	public User user;
	
	private final UserInfoServiceAsync userInfo = GWT.create(UserInfoService.class);
	
	public void onModuleLoad() {
		
	}
	
	public void getUserInfo() {
		
	}
}
