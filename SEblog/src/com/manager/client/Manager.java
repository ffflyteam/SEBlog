package com.manager.client;

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

public class Manager implements EntryPoint {
	
	public User user;
	public int searchblogId;
	
	private final ChangeUserStatServiceAsync changestat = GWT.create(ChangeUserStatService.class);
	private final DeleteBlogServiceAsync deleblog = GWT.create(DeleteBlogService.class);
	private final DeleteCommentServiceAsync delecomment = GWT.create(DeleteCommentService.class);
	private final DeleteFeedBackServiceAsync delefeed = GWT.create(DeleteFeedBackService.class);
	private final GetBlogInfoServiceAsync getblog = GWT.create(GetBlogInfoService.class);
	private final GetCommentInfoServiceAsync getcomment = GWT.create(GetCommentInfoService.class);
	private final GetUserFeedBackServiceAsync getfeed = GWT.create(GetUserFeedBackService.class);
	private final SetFeedBackReadFlagServiceAsync setRead = GWT.create(SetFeedBackReadFlagService.class);
	private final UserInfoServiceAsync userinfo = GWT.create(UserInfoService.class);
	
	@Override
	public void onModuleLoad() {
		getUserInfo();
		setTableSwitch();
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
				DOM.getElementById("name").setInnerHTML("欢迎您，管理员："+result.getUserName());
			}
		});
	}
	
	public void getBlogInfo() {
//		getblog.getBlog(blogId, callback);
	}
	public void getUserFeedBack() {
		getfeed.getAllUserFeedBack(new AsyncCallback<List<FeedBack>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请求用户反馈失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<FeedBack> result) {
				Operate.AddFeedBack(result);
			}
		});
	}
	
	public void setTableSwitch() {
		NodeList<Element> lis = Operate.getElementsByClassName("operate");
		NodeList<Element> dvs = Operate.getElementsByClassName("tableContainer");
		for (int i = 0; i < lis.getLength(); i++) {
			lis.getItem(i).setAttribute("index", i+"");
			DOM.sinkEvents(lis.getItem(i), Event.ONCLICK);
			DOM.setEventListener(lis.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						for (int j = 0; j < dvs.getLength(); j++) {
							dvs.getItem(j).setAttribute("style", "display:none;");
						}
						String indexStr = event.getCurrentTarget().getAttribute("index");
						int index = Integer.valueOf(indexStr);
						dvs.getItem(index).setAttribute("style", "display:block");
					}
				}
			});
		}
	}
}
