package com.other.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Other implements EntryPoint {
	public User user;
	public User other;
	public int otherId;
	
	private final UserInfoServiceAsync userInfo = GWT.create(UserInfoService.class);
	private final OtherUserInfoServiceAsync getOther = GWT.create(OtherUserInfoService.class);
	private final GetAllBlogInfoServiceAsync getAllBlog = GWT.create(GetAllBlogInfoService.class);
	private final MakeRelationWithOtherServiceAsync makeRelation = GWT.create(MakeRelationWithOtherService.class);
	
	public void onModuleLoad() {
		String otherIdStr = Window.Location.getQueryString();
		otherIdStr = otherIdStr.substring(9);
		Window.alert(otherIdStr);
		otherId = Integer.valueOf(otherIdStr);
		
		
		getUserInfo();
		getOtherUserInfo();
		getAllBlog();
		focusOther();
	}
	
	public void getUserInfo() {
		userInfo.getUserInfo(new AsyncCallback<User>() {
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
	
	public void getOtherUserInfo() {
		getOther.getOtherUserInfo(otherId, new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请求他人信息失败", false);
			}
			@Override
			public void onSuccess(User result) {
				other = result;
				DOM.getElementById("userName").setInnerHTML(result.getUserName());
				DOM.getElementById("sex").setInnerHTML("性别："+(result.getSex()==1?"男":"女"));
				DateTimeFormat formate = DateTimeFormat.getFormat("yyyy-MM-dd");
				String dateString = formate.format(result.getBirthDay());
				DOM.getElementById("birthday").setInnerHTML("生日："+dateString);
				DOM.getElementById("address").setInnerHTML("地址："+result.getAddress());
			}
		});
	}

	public void getAllBlog() {
		getAllBlog.getAllBlog(otherId, new AsyncCallback<Map<Integer,List<Blog>>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("刷新获取该作者的博客", false);
			}

			@Override
			public void onSuccess(Map<Integer,List<Blog>> result) {
				Operate.addMyBlog(result, "blog-list");
			}
		});
	}

	public void focusOther() {
		Element focus = DOM.getElementById("focus");
		DOM.sinkEvents(focus,Event.ONCLICK);
		DOM.setEventListener(focus, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					if(otherId==user.getAccountId()) {
						Operate.setAlert("你不能对自己执行关注/取消关注操作", false);
						return;
					}
					int flag = focus.getInnerHTML().equals("关注")?0:1;
					makeRelation.makeRelation(otherId, 1, flag, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("关注失败，请重试", false);
						}
						@Override
						public void onSuccess(Integer result) {
							focus.setInnerHTML(flag==0? "已关注":"关注");
							Operate.setAlert("操作成功！", true);
						}
					});
				}
			}
		});
	}

}