package com.index.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.index.client.Operate;
import com.index.shared.BlogType;

public class Index implements EntryPoint{
	
	private final ArticleServiceAsync article = GWT.create(ArticleService.class);
	private final InfoServiceAsync info = GWT.create(InfoService.class);
	public void onModuleLoad() {
		
		//请求登录信息，用来判断导航栏显示内容
		info.infoServer("", new AsyncCallback<BlogInfo>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("请刷新页面获取登录状态", false);
			}

			@Override
			public void onSuccess(BlogInfo result) {
				// TODO Auto-generated method stub
				if(result!=null) {
					DOM.getElementById("name").setInnerHTML("w喂喂喂");
					DOM.getElementById("us").setAttribute("style", "display:block");
					DOM.getElementById("lg").setAttribute("style", "display:none;");
				}
			}
		});
		//请求获得推荐博客内容
		JSONObject json = new JSONObject();
		json.put("type", new JSONString("2"));
		article.articleServer(json.toString(), new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("请刷新页面重新获取推荐博客内容", false);
			}

			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				Operate.addArticle(result);
			}
		});
		
		//注册侧边导航栏的点击事件
		Element sideNavElement = DOM.getElementById("s-nav");
		NodeList<Element> list = sideNavElement.getElementsByTagName("a");
		Window.alert(list.toString());
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
						Window.alert(type);
						JSONObject json = new JSONObject();
						json.put("type", new JSONString(type));
						article.articleServer(json.toString(), new AsyncCallback<String>() {

							@Override
							public void onFailure(Throwable caught) {
								// TODO Auto-generated method stub
								Operate.setAlert("网络有问题，请重试", false);
							}

							@Override
							public void onSuccess(String result) {
								// TODO Auto-generated method stub
								Operate.addArticle(result);
							}
						});
						
					}
				}
			});
		}
		


		
	}
}
