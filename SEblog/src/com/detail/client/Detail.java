package com.detail.client;


import java.util.List;


import com.detail.shared.ResultConst;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
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
	private final LogoutServiceAsync logout = GWT.create(LogoutService.class);
	
	public User Author;
	public Blog blog;
	public User user;
	
	public int jubaoId;
	public int huifuId;
	public int seehuifuId;

	public void onModuleLoad() {
		
		final String blogIdStr = Window.Location.getQueryString().substring(1).split("=")[1];
		final int blogId = Integer.valueOf(blogIdStr);
		
		
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
		final Element focus = DOM.getElementById("focus");
		DOM.sinkEvents(focus, Event.ONCLICK);
		DOM.setEventListener(focus, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					if(user.getAccountId() == Author.getAccountId()) {
						Operate.setAlert("您不能关注自己哦", false);
						return;
					}
					final int flag = focus.getInnerHTML()=="关注"?0:1;
					//发送添加关注请求
					makeRelation.makeRelation(Author.getAccountId(), 1, flag, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							String alerString = flag == 0? "关注失败，再试一次！":"取消关注失败，再试一次！";
							Operate.setAlert(alerString, false);
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

		//提交评论事件
		Element commentElement = DOM.getElementById("comment");
		DOM.sinkEvents(commentElement, Event.ONCLICK);
		DOM.setEventListener(commentElement, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
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
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					int flag = fowardeElement.getInnerHTML().equals("转发")?0:1;
					TAC.transferOrCollectBlog(blog.getBlogId(), 2, flag, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("网络原因转发失败，请重试！", false);
						}
						@Override
						public void onSuccess(Integer result) {
							fowardeElement.setInnerHTML(flag==0?"已转发":"转发");
							if(result==0) {
								Operate.setAlert("执行成功", true);
							}else {
								Operate.setAlert(ResultConst.getRsById(result).getDescribe(), false);
							}
							
						}
					});
				}
			}
		});
		
		//收藏事件
		final Element collectElement = DOM.getElementById("collect");
		DOM.sinkEvents(collectElement, Event.ONCLICK);
		DOM.setEventListener(collectElement, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					int flag = collectElement.getInnerHTML().equals("收藏") ? 0 : 1;
					TAC.transferOrCollectBlog(blog.getBlogId(), 1, flag, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("网络原因收藏失败，请重试！", false);
						}

						@Override
						public void onSuccess(Integer result) {
							boolean a = result==0?true:false;
							if(a) {
								DOM.getElementById("collect").setInnerHTML(flag==0?"已收藏":"收藏");
							}
							Operate.setAlert(ResultConst.getRsById(result).getDescribe(), a);
						}
					});
				}
			}
		});
		
		userLogout();
	}
	


	public void getUserinfo() {
		getUserInfo.getUserInfo(new AsyncCallback<User>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取登录信息失败，请刷新页面" + caught.toString(), false);
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
				Window.alert(result.toString());
				Operate.loadComment(result, "comment-container", 0);
				setCommentOperate();
			}
		});
	}
	
	public void getContent(int blogId) {
		Detail.getDetail(blogId, new AsyncCallback<Blog>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("请求博客信息失败，请重试！", false);
			}

			@Override
			public void onSuccess(Blog result) {
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
				Operate.setAlert("获取相关信息失败，请刷新重试" + caught.toString(), false);
			}
			@Override
			public void onSuccess(boolean[] result) {
				Element collElement = DOM.getElementById("collect");
				Element focusElement = DOM.getElementById("focus");
				collElement.setInnerHTML(result[0] ? "已收藏" : "收藏");
				focusElement.setInnerHTML(result[1] ?"关注" : "已关注");
			}
		});
	}
	
	//设置回复评论、举报、查看回复点击事件
	public void setCommentOperate() {
		NodeList<Element> jubao = Operate.getElementsByClassName("jubao");
		NodeList<Element> huifu = Operate.getElementsByClassName("huifu");
		NodeList<Element> seehuifu = Operate.getElementsByClassName("seehuifu");
		
		
		for (int i = 0; i < jubao.getLength(); i++) {
			DOM.sinkEvents(jubao.getItem(i), Event.ONCLICK);
			DOM.setEventListener(jubao.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String id = event.getCurrentTarget().getParentElement().getParentElement()
						.getParentElement().getParentElement().getId();
						jubaoId = Integer.valueOf(id);
					}
				}
			});
		}
		for (int i = 0; i < huifu.getLength(); i++) {
			DOM.sinkEvents(huifu.getItem(i), Event.ONCLICK);
			DOM.setEventListener(huifu.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String id = event.getCurrentTarget().getParentElement().getParentElement()
						.getParentElement().getParentElement().getId();
						huifuId = Integer.valueOf(id);
						Window.alert("回复"+id);
					}
				}
			});
		}
		
		for (int i = 0; i < seehuifu.getLength(); i++) {
			DOM.sinkEvents(seehuifu.getItem(i), Event.ONCLICK);
			DOM.setEventListener(seehuifu.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String id = event.getCurrentTarget().getParentElement().getParentElement()
						.getParentElement().getParentElement().getId();
						seehuifuId = Integer.valueOf(id);
						Window.alert("see"+id);
						comment.getCommentDetail(seehuifuId, new AsyncCallback<List<Comment>>() {
							@Override
							public void onFailure(Throwable caught) {
								Operate.setAlert("请重新尝试获取评论回复", false);
							}
							@Override
							public void onSuccess(List<Comment> result) {
								if(result == null) {
									Operate.setAlert("该评论暂时没有回复", false);
									return;
								}
								Operate.loadComment(result, seehuifuId+"", 1);
							}
						});
					}
				}
			});
		}
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
}
