package com.detail.client;

import java.util.List;

import com.detail.client.Blog;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java_cup.internal_error;

public class Detail implements EntryPoint{

	private final DetailServiceAsync Detail = GWT.create(DetailService.class);
	private final CommentDetailServiceAsync comment = GWT.create(CommentDetailService.class);
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		String blogIdStr = Window.Location.getQueryString().substring(1).split("=")[1];
		int blogId = Integer.valueOf(blogIdStr);
		Window.alert(blogIdStr);
		
		
		//请求登录状态及个人信息
		
		
		//请求博客的内容等信息
		getContent(blogId);
		
		//请求作者相关信息并将作者ID赋值给id为name的span，获取是否已经关注该作者
		
		
		//请求相关评论
		//getComment(blogId);
		
		//点击关注事件
		String authorIdString = Operate.getAttr("nickName","data-authorid");
		Element focus = DOM.getElementById("focus");
		DOM.sinkEvents(focus, Event.ONCLICK);
		DOM.setEventListener(focus, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					JSONObject focusJsonObject = new JSONObject();
					focusJsonObject.put("focusId",new JSONString(authorIdString));
					//发送添加关注请求
				}
			}
		});

		//提交评论事件
		Element commentElement = DOM.getElementById("comment");
		DOM.sinkEvents(commentElement, Event.ONCLICK);
		DOM.setEventListener(commentElement, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					String comString = Operate.getValue("comment-text");
					Window.alert(comString);
					if(comString == null) {
						Operate.setAlert("评论内容不能为空！", false);
						return;
					}
					JSONObject json = new JSONObject();
					json.put("blogId", new JSONString(blogIdStr));
					json.put("commentText", new JSONString(comString));
					//发送请求传送数据
				}
			}
		});
		
		//转载事件
		Element fowardeElement = DOM.getElementById("forward");
		DOM.sinkEvents(fowardeElement, Event.ONCLICK);
		DOM.setEventListener(fowardeElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					//发送转载请求
				}
			}
		});
		
		//收藏事件
		Element collectElement = DOM.getElementById("collect");
		DOM.sinkEvents(collectElement, Event.ONCLICK);
		DOM.setEventListener(collectElement, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					//发送收藏请求
				}
			}
		});
	}
	
	
	
	public void getComment(int blogId) {
		comment.getCommentDetail(blogId, new AsyncCallback<List<Comment>>() {
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("刷新以获取评论", false);
			}

			@Override
			public void onSuccess(List<Comment> result) {
				// TODO Auto-generated method stub
				Operate.loadComment(result);
			}
		});
	}
	
	public void getContent(int blogId) {
		Detail.getDetail(blogId, new AsyncCallback<Blog>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("请求博客信息失败，请重试！", false);
			}

			@Override
			public void onSuccess(Blog result) {
				// TODO Auto-generated method stub
				Window.alert(result.toString());
				Operate.loadBlog(result);
			}
		});
	}
}
