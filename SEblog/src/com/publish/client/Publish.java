package com.publish.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Publish implements EntryPoint {
	
	public User user;
	
	private final UserInfoServiceAsync userinfo = GWT.create(UserInfoService.class);
	private final MakeBlogServiceAsync makeblog = GWT.create(MakeBlogService.class);
	private final LogoutServiceAsync logout = GWT.create(LogoutService.class);
	
	@Override
	public void onModuleLoad() {
		getUserInfo();
		userLogout();
		makeBlog();
	}
	
	public void getUserInfo() {
		userinfo.getUserInfo(new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请刷新页面获取登录状态", false);
			}
			@Override
			public void onSuccess(User result) {
				if(result==null) {
					Window.open("./login.html", "_self", null);
					return;
				}
				user = result;
				DOM.getElementById("name").setInnerHTML(result.getUserName());
			}
		});
	}
	
	public void userLogout() {
		Element logoutBtn = DOM.getElementById("logout");
		DOM.sinkEvents(logoutBtn, Event.ONCLICK);
		DOM.setEventListener(logoutBtn, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					logout.logout(new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("注销失败，请重试！", false);
						}

						@Override
						public void onSuccess(Boolean result) {
							if(result==true) {
								Window.open("./login.html", "_self", null);
							}else {
								Operate.setAlert("注销失败，请重试！", false);
							}
						}
					});
				}
			}
		});
	}

	public void makeBlog() {
		Element submit = DOM.getElementById("submit");
		DOM.sinkEvents(submit, Event.ONCLICK);
		DOM.setEventListener(submit, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					String title = Operate.getValue("title");
					int type = Operate.getChoice("choice");
					String content = DOM.getElementById("editor").getInnerHTML();
					Window.alert(type+"");
					if(title.equals(null)||type==0||content.equals(null)) {
						Operate.setAlert("博客的标题、内容和类型不能缺少", false);
						return;
					}
					
					makeblog.makeBlog(title, content, type, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("发表博客失败，请重试！", false);
						}
						@Override
						public void onSuccess(Integer result) {
							if(result==0) {
								Operate.cleanValue("title");
								DOM.getElementById("editor").setInnerHTML("");
								Window.open("./index.html", "_self", null);
							}else {
								Operate.setAlert("发表博客失败，请重试！", false);
							}
						}
					});
				}
			}
		});
	}
}
