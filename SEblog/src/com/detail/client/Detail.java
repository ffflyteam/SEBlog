package com.detail.client;

import java.util.List;

import com.detail.shared.ResultConst;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Detail implements EntryPoint{

	private final DetailServiceAsync Detail = GWT.create(DetailService.class);
	private final CommentDetailServiceAsync comment = GWT.create(CommentDetailService.class);
	private final CollectAndRelationServiceAsync collAR = GWT.create(CollectAndRelationService.class);
	private final MakeRelationWithOtherServiceAsync makeRelation = GWT.create(MakeRelationWithOtherService.class);
	private final MakeCommentServiceAsync makeComment = GWT.create(MakeCommentService.class);
	private final UserInfoServiceAsync getUserInfo = GWT.create(UserInfoService.class);
	private final TransferOrCollectBlogServiceAsync TAC = GWT.create(TransferOrCollectBlogService.class);
	public User Author;
	public Blog blog;
	public User user;
	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		final String blogIdStr = Window.Location.getQueryString().substring(1).split("=")[1];
		int blogId = Integer.valueOf(blogIdStr);
		Window.alert(blogIdStr);
		
		
		//请求登录状态及个人信息
		getUserinfo();
		
		//请求博客的内容等信息
		getContent(blogId);
		
		//获取是否已经关注该作者,是否收藏该博客
//		getFocusOrCollect();
		
		//请求相关评论
		getComment(blogId);
		
		//点击关注事件
		//String authorIdString = Operate.getAttr("nickName","data-authorid");
		Element focus = DOM.getElementById("focus");
		DOM.sinkEvents(focus, Event.ONCLICK);
		DOM.setEventListener(focus, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					if(user.getAccountId() == Author.getAccountId()) {
						Operate.setAlert("您不能关注自己哦", false);
						return;
					}
					int flag = focus.getInnerHTML()=="关注"?0:1;
					//发送添加关注请求
					makeRelation.makeRelation(Author.getAccountId(), 1, flag, new AsyncCallback<Integer>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							String alerString = flag == 0? "关注失败，再试一次！":"取消关注失败，再试一次！";
							Operate.setAlert(alerString, false);
						}

						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub
							Window.alert("关注结果"+result);
							focus.setInnerHTML(flag==0? "已关注":"关注");
							Operate.setAlert("操作成功！", true);
						}
					});
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
					if(comString == null) {
						Operate.setAlert("评论内容不能为空！", false);
						return;
					}
					//发送请求传送数据
					makeComment.makeComment(blog.getBlogId(), comString, new AsyncCallback<Integer>() {
						
						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub
							if(result==0) {
								Operate.setAlert("评论成功", true);
								Operate.cleanValue("comment-text");
								getComment(blogId);
							}else {
								Operate.setAlert("评论失败", false);
							}
						}
						
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Operate.setAlert("网络原因连接不到服务器", false);
						}
					});
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
					TAC.transferOrCollectBlog(blog.getBlogId(), 2, 0, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Operate.setAlert("网络原因转发失败，请重试！", false);
						}

						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub
							Operate.setAlert(ResultConst.getRsById(result).getDescribe(), false);
						}
					});
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
					int type = collectElement.getInnerHTML()=="收藏"?0:1;
					TAC.transferOrCollectBlog(blog.getBlogId(), 1, type, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Operate.setAlert("网络原因收藏失败，请重试！", false);
						}

						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub
							boolean a = result==0?true:false;
							if(a) {
								DOM.getElementById("collect").setInnerHTML(type==0?"已收藏":"收藏");
							}
							Operate.setAlert(ResultConst.getRsById(result).getDescribe(), a);
						}
					});
				}
			}
		});
	}
	


	public void getUserinfo() {
		getUserInfo.getUserInfo(new AsyncCallback<User>() {
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("获取登录信息失败，请刷新页面", false);
			}

			@Override
			public void onSuccess(User result) {
				// TODO Auto-generated method stub
				user = result;
				DOM.getElementById("us").setInnerHTML(result.getUserName());
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
				Author = result.getUser();
				blog = result;
				Operate.loadBlog(result);
				getFocusOrCollect();
			}
		});
	}
	
	public void getFocusOrCollect() {
		collAR.getStatus(blog.getBlogId(), Author.getAccountId(), new AsyncCallback<boolean[]>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				Operate.setAlert("获取相关信息失败，请刷新重试", false);
			}

			@Override
			public void onSuccess(boolean[] result) {
				// TODO Auto-generated method stub
				Element collElement = DOM.getElementById("collect");
				Element focusElement = DOM.getElementById("focus");
				collElement.setInnerHTML(result[0]==true?"已收藏":"收藏");
				focusElement.setInnerHTML(result[1]==true?"关注":"已关注");
			}
		});
		
	}
}
