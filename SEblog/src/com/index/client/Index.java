package com.index.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Index implements EntryPoint{

	private final IndexServiceAsync index = GWT.create(IndexService.class);
	private final UserInfoServiceAsync userinfo = GWT.create(UserInfoService.class);
	public User user;
	public void onModuleLoad() {
		
		//请求登录信息，用来判断导航栏显示内容
		userinfo.getUserInfo(new AsyncCallback<User>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("获取用户信息失败，请刷新页面重试", false);
				Window.open("./login.html", "_self", null);
			}

			@Override
			public void onSuccess(User result) {
				if(result==null) {
					Window.open("./login.html", "_self", null);
				}
				user = result;
				DOM.getElementById("name").setInnerHTML(result.getUserName());
				
				Window.alert("sss");
				//请求获得推荐博客内容
				index.index(new AsyncCallback<Map<Integer,List<Blog>>>() {

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Operate.setAlert("请刷新页面获取热门博客", false);
						Window.alert("aaa");
					}

					@Override
					public void onSuccess(Map<Integer, List<Blog>> result) {
						// TODO Auto-generated method stub
						Window.alert(result.toString());
						Operate.addArticle(result,0);
					}
				});
				Window.alert("sss2");
			}
		});
		
		//注册侧边导航栏的点击事件
		final Element sideNavElement = DOM.getElementById("s-nav");
		final NodeList<Element> list = sideNavElement.getElementsByTagName("a");
		for (int i = 0; i < list.getLength(); i++) {
			DOM.sinkEvents(list.getItem(i), Event.ONCLICK);
			DOM.setEventListener(list.getItem(i),new EventListener() {
				
				@Override
				public void onBrowserEvent(Event event) {
					// TODO Auto-generated method stub
					if(DOM.eventGetType(event)==Event.ONCLICK) {
						for (int j = 0; j < list.getLength(); j++) {
							list.getItem(j).removeClassName("selected");
						}
						Element aTargetElement = DOM.eventGetCurrentTarget(event);
						aTargetElement.addClassName("selected");
						String type = aTargetElement.getParentElement().getAttribute("type");
						final int typeNum = Integer.valueOf(type);
						
						index.index(new AsyncCallback<Map<Integer,List<Blog>>>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Operate.setAlert("加载失败，请重试", false);
							}

							@Override
							public void onSuccess(Map<Integer, List<Blog>> result) {
								// TODO Auto-generated method stub
								Operate.addArticle(result, typeNum);
							}
						});
						
					}
				}
			});
		}
		


		
	}
}
