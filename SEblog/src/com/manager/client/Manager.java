package com.manager.client;

import java.util.List;

import javax.validation.constraints.Pattern.Flag;

import com.google.gwt.codegen.server.StringGenerator;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.manager.shared.ResultConst;

public class Manager implements EntryPoint {
	public NodeList<Element> trs;
	
	public User user;
	public int searchBlogId;
	public int deleteBlogId;
	
	public int searchUserId;
	public int changeUserId;
	
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
		setTableSwitch();
		getBlogInfo();
		getUserInfo();
		getUserFeedBack();
	}
	
	public void getUserInfo() {
		Element userSearch = DOM.getElementById("userSearch-btn");
		DOM.sinkEvents(userSearch, Event.ONCLICK);
		DOM.setEventListener(userSearch, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					Window.alert("查询用户");
					String useridStr = Operate.getValue("user-input");
					if(useridStr == null) {
						Operate.setAlert("查询的用户id不能为空", false);
						return;
					}
					searchUserId = Integer.valueOf(useridStr);
					changeUserId = searchUserId;
					if(searchUserId == 12) {
						Operate.setAlert("查询不到该用户", false);
					}
					userinfo.getUserInfo(searchUserId, new AsyncCallback<User>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("查询失败，请重试！", false);
						}
						@Override
						public void onSuccess(User result) {
							if(result==null) {
								Operate.setAlert("查询不到该用户", false);
								return;
							}
							Operate.addUser(result);
							operaToUser();
						}
					});
				}
			}
		});
	}
	
	public void getBlogInfo() {
		Element blogSearch = DOM.getElementById("article-btn");
		DOM.sinkEvents(blogSearch, Event.ONCLICK);
		DOM.setEventListener(blogSearch, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					String blogidStr = Operate.getValue("article-input");
					if(blogidStr ==null) {
						Operate.setAlert("查询的博客id不能为空", false);
						return;
					}
					searchBlogId = Integer.valueOf(blogidStr);
					deleteBlogId = searchBlogId;
					getblog.getBlog(searchBlogId, new AsyncCallback<Blog>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("查找博客失败，请重试！", false);
						}
						@Override
						public void onSuccess(Blog result) {
							if(result == null) {
								Operate.setAlert("该博客不存在，请检查博客id", false);
							}else {
								Operate.addBlog(result);
								deleteBlog();
							}
						}
					});
				}
			}
		});
		
	}

	public void getComment() {
		for(int i = 0; i <trs.getLength();i++) {
			String comIdStr = trs.getItem(i).getAttribute("commentid");
			int commentId = Integer.valueOf(comIdStr);
			getcomment.getComment(commentId, new AsyncCallback<Comment>() {
				@Override
				public void onFailure(Throwable caught) {
					
				}
				@Override
				public void onSuccess(Comment result) {
					for(int i = 0; i <trs.getLength();i++) {
						if(trs.getItem(i).getAttribute("commentid")==String.valueOf(result.getCommentId())) {
							NodeList<Element> tds = trs.getItem(i).getElementsByTagName("td");
							String content =
									"<a href=\"./other.html?otherid="+result.getUser().getAccountId()+
									"\" target=\"_blank\">" + result.getUser().getUserName()+
									"</a>";
							tds.getItem(1).setInnerHTML(content);
							tds.getItem(2).setInnerHTML(result.getContent());
						}
					}
				}
				
			});
		}
		deleteComment();
		setReadFlag();
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
				trs = DOM.getElementById("feedback-table-body").getElementsByTagName("tr");
				getComment();
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

	public void deleteBlog() {
		Element deleteBlogBtn = DOM.getElementById("deleblog");
		DOM.sinkEvents(deleteBlogBtn, Event.ONCLICK);
		DOM.setEventListener(deleteBlogBtn, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					deleblog.deleteBlog(deleteBlogId, new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("操作失败，请重试", false);
						}

						@Override
						public void onSuccess(Integer result) {
							if(result==1) {
								Operate.setAlert("操作成功", true);
								DOM.getElementById("user-ariticle-body").setInnerHTML("");
							}else {
								String msg = ResultConst.getRsById(result).getDescribe();
								Operate.setAlert(msg, false);
							}
						}
					});
				}
				
			}
		});
	}

	public void operaToUser() {
		Element changeUserBtn = DOM.getElementById("operate");
		DOM.sinkEvents(changeUserBtn, Event.ONCLICK);
		DOM.setEventListener(changeUserBtn, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event)==Event.ONCLICK) {
					int type = changeUserBtn.getInnerHTML() == "冻结"? 1 : 0;
					changestat.changeUserStat(changeUserId, type ,new AsyncCallback<Integer>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("操作失败，请重试", false);
						}

						@Override
						public void onSuccess(Integer result) {
							if(result==1) {
								Operate.setAlert("操作成功", true);
								DOM.getElementById("user-table-body").setInnerHTML("");
							}else {
								String msg = ResultConst.getRsById(result).getDescribe();
								Operate.setAlert(msg, false);
							}
						}
					});
				}
				
			}
		});
	}
	
	public void deleteComment() {
		NodeList<Element> delecoms = Operate.getElementsByClassName("delecomment");
		for (int i = 0; i < delecoms.getLength(); i++) {
			DOM.sinkEvents(delecoms.getItem(i), Event.ONCLICK);
			DOM.setEventListener(delecoms.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event)==Event.ONCLICK) {
						String commentIdStr = event.getCurrentTarget().getParentElement().getParentElement().getAttribute("commentid");
						Window.alert(commentIdStr);
						int commentid = Integer.valueOf(commentIdStr);
						delecomment.deleteComment(commentid, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								
							}
							@Override
							public void onSuccess(Integer result) {
								if(result == 0) {
									Operate.setAlert("操作成功", true);
								}else {
									String c = ResultConst.getRsById(result).getDescribe();
									Operate.setAlert(c,false);
								}
							}
						});
					}
				}
			});
		}
	}
	
	public void setReadFlag() {
		NodeList<Element> reads = Operate.getElementsByClassName("setRead");
		for (int i = 0; i < reads.getLength(); i++) {
			DOM.sinkEvents(reads.getItem(i), Event.ONCLICK);
			DOM.setEventListener(reads.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String feIDStr = event.getCurrentTarget().getParentElement().getParentElement().getAttribute("feedbackid");
						Window.alert(feIDStr);
						int feId = Integer.valueOf(feIDStr);
						setRead.setReadFlag(feId, new AsyncCallback<Integer>() {
							@Override
							public void onFailure(Throwable caught) {
								
							}
							@Override
							public void onSuccess(Integer result) {
								if(result==1) {
									Operate.setAlert("操作成功", true);
									getUserFeedBack();
								}
							}
						});
					}
				}
			});
		}
	}
}