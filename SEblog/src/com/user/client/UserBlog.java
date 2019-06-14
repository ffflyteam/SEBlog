package com.user.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UserBlog implements EntryPoint {
	public User user;
	public NodeList<Element> lis = DOM.getElementById("select-ul").getElementsByTagName("li");
	public NodeList<Element> dvs = Operate.getElementsByClassName("mycontainer");
	
	private final UserInfoServiceAsync userInfo = GWT.create(UserInfoService.class);
	private final UserRelationServiceAsync userrelation = GWT.create(UserRelationService.class);
	private final UserBlogServiceAsync userblog = GWT.create(UserBlogService.class);
	private final ChangeInfoServiceAsync chaninfo = GWT.create(ChangeInfoService.class);
	private final MakeRelationWithOtherServiceAsync makeRela = GWT.create(MakeRelationWithOtherService.class);
	private final DeleteSelfBlogServiceAsync deleblog = GWT.create(DeleteSelfBlogService.class);
	private final LogoutServiceAsync logout = GWT.create(LogoutService.class);
	
	
	public void onModuleLoad() {
		//获取用户登录状态
		getUserInfo();
		
		//设置侧边菜单栏切换
		setUlCLickEvent();
		
		//修改资料事件注册
		changeInfo();
		
		//修改密码事件注册
		changePassword();
		
		//根据路径展示某个部分
		showWhichOne();
		
		//用户注销登录
		userLogout();
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
		
		DateTimeFormat formate = DateTimeFormat.getFormat("yyyy-MM-dd");
		String dateString = formate.format(user.getBirthDay());
		DOM.getElementById("birth-info").setInnerHTML(dateString);
		DOM.getElementById("address-info").setInnerHTML(user.getAddress());
		
		Operate.setValue("username", user.getUserName());
		Operate.setValue("sex", user.getSex()==0?"女":"男");
		Operate.setValue("date", user.getBirthDay().toString());
		Operate.setValue("address", user.getAddress());
	}
	
	public void setUlCLickEvent() {
		
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
				cancelFocus();
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
				Operate.addMyBlog(result, "selfBlog",1);
				deleteBlog();
			}
		});
		
		userblog.getAllBlog(1, new AsyncCallback<List<Blog>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取收藏博客失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<Blog> result) {
				Operate.addMyBlog(result, "collBlog",0);
			}
		});
		
		userblog.getAllBlog(2, new AsyncCallback<List<Blog>>() {
			@Override
			public void onFailure(Throwable caught) {
				Operate.setAlert("获取转载博客失败，请重试！", false);
			}

			@Override
			public void onSuccess(List<Blog> result) {
				Operate.addMyBlog(result, "forwardBlog",0);
			}
		});
	}

	public void changeInfo() {
		Element infoSubmit = DOM.getElementById("submitInfo");
		DOM.sinkEvents(infoSubmit,Event.ONCLICK);
		DOM.setEventListener(infoSubmit, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					String username = Operate.getValue("username");
					String sex;
					String t_sex = Operate.getValue("sex");
					String birthday = Operate.getValue("date");
					String address = Operate.getValue("address");
					if(username==null || t_sex==null || birthday==null || address==null) {
						Operate.setAlert("资料不能为空", false);
						return;
					}
					if( t_sex.equals("男")) {
						sex = "1";
					}else if(t_sex.equals("女")) {
						sex = "0";
					}else {
						Operate.setAlert("性别只能填写男和女", false);
						return;
					}
//					Window.alert(user.toString());
//					user.setUserName(username);
//					user.setAddress(address);
//					Date birthDate = new Date(birthday);
//					user.setBirthDay(birthDate);
//					user.setSex(Short.valueOf(sex));
					JSONObject us = new JSONObject();
					us.put("nickName", new JSONString(username));
					us.put("password", new JSONString(user.getPassword()));
					us.put("address", new JSONString(address));
					us.put("sex", new JSONString(sex));
					us.put("birthDay", new JSONString(birthday));
					chaninfo.changeInfo(us.toString(), new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("更新资料失败，请重试！", false);
						}
						@Override
						public void onSuccess(Boolean result) {
							if(result==true) {
								getUserInfo();
								Operate.setAlert("更新成功！", true);
							}else {
								Operate.setAlert("更新资料失败，请重试！", false);
							}
						}
					});
				}
			}
		});
	}
	
	public void changePassword() {
		Element passwordSubmit = DOM.getElementById("submitPassword");
		DOM.sinkEvents(passwordSubmit,Event.ONCLICK);
		DOM.setEventListener(passwordSubmit, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK) {
					String oldpassword = Operate.getValue("password");
					String newpassword = Operate.getValue("newpassword");
					String newpassword1 = Operate.getValue("newpassword1");
					if(!Operate.isValidPassword(oldpassword)||!Operate.isValidPassword(newpassword)||!Operate.isValidPassword(newpassword1)) {
						Operate.setAlert("密码长度应为6-10位", false);
						return;
					}
					if(!newpassword.equals(newpassword1)) {
						Operate.setAlert("两次输入密码不一致", false);
						return;
					}
					if(!oldpassword.equals(user.getPassword())) {
						Operate.setAlert("原密码错误", false);
						return;
					}
					JSONObject us = new JSONObject();
					us.put("nickName", new JSONString(user.getUserName()));
					us.put("password", new JSONString(newpassword));
					us.put("address", new JSONString(user.getAddress()));
					us.put("sex", new JSONString(user.getSex()==0?"0":"1"));
					us.put("birthDay", new JSONString(user.getBirthDay().toString()));
					chaninfo.changeInfo(us.toString(), new AsyncCallback<Boolean>() {
						@Override
						public void onFailure(Throwable caught) {
							Operate.setAlert("修改密码失败，请重试！", false);
						}
						@Override
						public void onSuccess(Boolean result) {
							if(result==true) {
								getUserInfo();
								Operate.setAlert("修改成功！", true);
								Operate.cleanValue("password");
								Operate.cleanValue("newpassword");
								Operate.cleanValue("newpassword1");
							}else {
								Operate.setAlert("修改密码失败，请重试！", false);
							}
						}
					});
				}
			}
		});
	}

	public void showWhichOne() {
		String url = Window.Location.getQueryString();
		url = url.substring(6);
		if(url.equals("focus")) {
			for(int i=0; i < lis.getLength();i++ ) {
				lis.getItem(i).removeClassName("selected");
				dvs.getItem(i).setAttribute("style", "display:none;");
			}
			lis.getItem(1).addClassName("selected");
			dvs.getItem(1).setAttribute("style", "display:block;");
			getRelation();
		}
		if(url.equals("blog")){
			for(int i=0; i < lis.getLength();i++ ) {
				lis.getItem(i).removeClassName("selected");
				dvs.getItem(i).setAttribute("style", "display:none;");
			}
			lis.getItem(2).addClassName("selected");
			dvs.getItem(2).setAttribute("style", "display:block;");
			getMyBlog();
		}
	}

	public void cancelFocus() {
		NodeList<Element> asList = Operate.getElementsByClassName("cancel");
		for (int i = 0; i < asList.getLength(); i++) {
			DOM.sinkEvents(asList.getItem(i), Event.ONCLICK);
			DOM.setEventListener(asList.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String idString = event.getCurrentTarget().getAttribute("userid");
						int otherId = Integer.valueOf(idString);
						makeRela.makeRelation(otherId, 1, 1, new AsyncCallback<Integer>() {
							@Override
							public void onSuccess(Integer result) {
								Operate.setAlert("操作成功！", true);
								getRelation();
							}
							@Override
							public void onFailure(Throwable caught) {
								Operate.setAlert("取消关注失败，请重试！", false);
							}
						});
					}
				}
			});
		}
	}
	
	public void deleteBlog() {
		NodeList<Element> asList = Operate.getElementsByClassName("dele");
		for (int i = 0; i < asList.getLength(); i++) {
			DOM.sinkEvents(asList.getItem(i), Event.ONCLICK);
			DOM.setEventListener(asList.getItem(i), new EventListener() {
				@Override
				public void onBrowserEvent(Event event) {
					if(DOM.eventGetType(event) == Event.ONCLICK) {
						String idString = event.getCurrentTarget().getAttribute("blogid");
						Window.alert(idString);
						int blogId = Integer.valueOf(idString);
						deleblog.deleteSelfBlog(blogId, new AsyncCallback<Integer>() {
							@Override
							public void onSuccess(Integer result) {
								Operate.setAlert("操作成功！", true);
								getMyBlog();
							}
							@Override
							public void onFailure(Throwable caught) {
								Operate.setAlert("取消关注失败，请重试！", false);
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