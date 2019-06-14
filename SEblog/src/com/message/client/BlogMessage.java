package com.message.client;

import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class BlogMessage implements EntryPoint {
	
	public User user;
	public NodeList<Element> lis = DOM.getElementById("ul").getElementsByTagName("li");
	public NodeList<Element> uls = Operate.getChildren("messageUlContainer");
	
	private final UserInfoServiceAsync userinfo = GWT.create(UserInfoService.class);
	private final GetAllMessageServiceAsync getallmess = GWT.create(GetAllMessageService.class);
	private final LogoutServiceAsync logout = GWT.create(LogoutService.class);
	private final SetReadFlagServiceAsync setRead = GWT.create(SetReadFlagService.class);
	private final DeleteMessageServiceAsync delemess = GWT.create(DeleteMessageService.class);
	
	public void onModuleLoad() {
		getUserInfo();
		
		getAllMessage();
		
		setMessageListSwtich();
		
		userLogout();
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
	
	public void getAllMessage() {
		getallmess.getAllMessage(new AsyncCallback<List<Message>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请刷新页面加载消息", false);
			}
			@Override
			public void onSuccess(List<Message> result) {
				Operate.addMessage(result);
				setReadAndDeleteClick();
			}
		});
	}

	public void userLogout() {
		Element logoutElement = DOM.getElementById("logout");
		DOM.sinkEvents(logoutElement, Event.ONCLICK);
		DOM.setEventListener(logoutElement, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
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

	public void setReadAndDeleteClick() {
		NodeList<Element> reads = Operate.getElementsByClassName("readFlag");
		NodeList<Element> deles = Operate.getElementsByClassName("dele");
		for(int i = 0; i < reads.getLength();i++) {
			DOM.sinkEvents(reads.getItem(i), Event.ONCLICK);
			DOM.setEventListener(reads.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String idStr = event.getCurrentTarget().getId();
						int id = Integer.valueOf(idStr);
						setRead.setReadFlag(id, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								Operate.setAlert("标记已读失败，请重试！", false);
							}
							@Override
							public void onSuccess(Integer result) {
								if(result == 1) {
									Operate.setAlert("标记成功！", true);
									getAllMessage();
								}else {
									Operate.setAlert("标记已读失败，请重试！", false);
								}
							}
						});
					}
				}
			});
		}
		for(int i = 0; i < deles.getLength();i++) {
			DOM.sinkEvents(deles.getItem(i), Event.ONCLICK);
			DOM.setEventListener(deles.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String idStr = event.getCurrentTarget().getId();
						int id = Integer.valueOf(idStr);
						delemess.deleteMessage(id, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								Operate.setAlert("删除消息失败，请重试！", false);
							}
							@Override
							public void onSuccess(Integer result) {
								if(result == 1) {
									Operate.setAlert("删除成功！", true);
									getAllMessage();
								}else {
									Operate.setAlert("删除消息失败，请重试！", false);
								}
							}
						});
					}
				}
			});
		}
	}

	public void setMessageListSwtich() {
		for (int i = 0; i < lis.getLength(); i++) {
			lis.getItem(i).setAttribute("index", i+"");
			DOM.sinkEvents(lis.getItem(i), Event.ONCLICK);
			DOM.setEventListener(lis.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						for (int j = 0; j < lis.getLength(); j++) {
							lis.getItem(j).removeClassName("selected-li");
						}
						event.getCurrentTarget().addClassName("selected-li");
						for(int j=0; j < uls.getLength();j++) {
							uls.getItem(j).setAttribute("style", "display:none;");
						}
						String indexStr = event.getCurrentTarget().getAttribute("index");
						int index = Integer.valueOf(indexStr);
						uls.getItem(index).setAttribute("style", "display:block;");
					}
				}
			});
		}
	}
}