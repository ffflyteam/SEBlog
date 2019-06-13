package com.message.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BlogMessage implements EntryPoint {
	
	public User user;
	public List<Message> readList;
	public List<Message> unreadList;
	
	private final UserInfoServiceAsync userinfo = GWT.create(UserInfoService.class);
	private final GetAllMessageServiceAsync getallmess = GWT.create(GetAllMessageService.class);
	
	public void onModuleLoad() {
		
	}
	
	public void getUserInfo() {
		userinfo.getUserInfo(new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请刷新页面获取登录状态", false);
			}
			@Override
			public void onSuccess(User result) {
				user = result;
				DOM.getElementById("name").setInnerHTML(result.getUserName());
			}
		});
	}
	
	public void getAllMessage() {
		getallmess.getAllMessage(new AsyncCallback<List<Message>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请刷新页面加载消息", false);
			}
			@Override
			public void onSuccess(List<Message> result) {
				for (int i = 0; i < result.size(); i++) {
					if(result.get(i).getReadFlag() == 0) {
						unreadList.add(result.get(i));
					}else {
						readList.add(result.get(i));
					}
				}
				Operate.addMessage(unreadList,"unreadlist");
			}
		});
	}
}
