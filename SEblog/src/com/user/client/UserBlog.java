package com.user.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserBlog implements EntryPoint {
	public User user;
	private final UserInfoServiceAsync userInfo = GWT.create(UserInfoService.class);
	private final UserRelationServiceAsync userrelation = GWT.create(UserRelationService.class);
	private final UserBlogServiceAsync userblog = GWT.create(UserBlogService.class);
	public void onModuleLoad() {
		//获取用户登录状态
		getUserInfo();
		//设置侧边菜单栏切换
		setUlCLickEvent();
		//修改资料
	}
	
	public void getUserInfo() {
		userInfo.getUserInfo(new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User result) {
				user = result;
				DOM.getElementById("name").setInnerHTML(result.getUserName());
				setinfo();
			}
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请求用户状态失败，请重试！", false);
			}
		});
	}
	
	public void setinfo() {
		DOM.getElementById("nickname").setInnerHTML(user.getUserName());
		DOM.getElementById("sex-info").setInnerHTML(user.getSex()==0?"女":"男");
		DOM.getElementById("birth-info").setInnerHTML(user.getBirthDay().toString());
		DOM.getElementById("address-info").setInnerHTML(user.getAddress());
	}
	
	public void setUlCLickEvent() {
		NodeList<Element> lis = DOM.getElementById("select-ul").getElementsByTagName("li");
		NodeList<Element> dvs = Operate.getElementsByClassName("mycontainer");
		for(int i = 0;i < lis.getLength();i++) {
			DOM.sinkEvents(lis.getItem(i),Event.ONCLICK);
			DOM.setEventListener(lis.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						for(int j=0;j < lis.getLength();j++){
							lis.getItem(j).removeClassName("selected");
							lis.getItem(j).setAttribute("index", String.valueOf(j));
						}
						for(int j=0;j < dvs.getLength();j++) {
							dvs.getItem(j).setAttribute("style", "display:none;");
						}
						Element thisElement = event.getCurrentTarget();
						thisElement.addClassName("selected");
						int index = Integer.valueOf(thisElement.getAttribute("index"));
						dvs.getItem(index).setAttribute("style", "display:block;");
						if(index==1) {
							getRelation();
						}
						if(index==2) {
							getMyBlog();
						}
					}
				}
			});
		}
	}
	
	public void getRelation() {
		userrelation.getAllRelatedUser(new AsyncCallback<Map<User,Integer>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请求关注人失败，刷新页面重试哦！", false);
			}

			@Override
			public void onSuccess(Map<User, Integer> result) {
				Operate.addRelationList(result);
			}
		});
	}
	
	public void getMyBlog() {
		userblog.getAllBlog(0, new AsyncCallback<List<Blog>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取原创博客失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<Blog> result) {
				Operate.addMyBlog(result, "selfBlog");
			}
		});
		
		userblog.getAllBlog(1, new AsyncCallback<List<Blog>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取收藏博客失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<Blog> result) {
				Operate.addMyBlog(result, "collBlog");
			}
		});
		
		userblog.getAllBlog(2, new AsyncCallback<List<Blog>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取转载博客失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<Blog> result) {
				Operate.addMyBlog(result, "forwardBlog");
			}
		});
	}

	public void changeInfo() {
		
	}
}