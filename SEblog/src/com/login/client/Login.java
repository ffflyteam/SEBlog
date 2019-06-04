package com.login.client;

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
import com.login.shared.FieldVerifier;
import com.login.shared.ResultConst;

public class Login implements EntryPoint {
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	private final RegisterServiceAsync registerService = GWT.create(RegisterService.class);
	public void onModuleLoad() {

		
		//注册和登录页面切换事件
		final Element loginContainer = DOM.getElementById("login-container");
		final Element registerContainer = DOM.getElementById("register-container");
		final Element selectLogin = DOM.getElementById("select-login");
		final Element selectRegister = DOM.getElementById("select-register");
		
		DOM.sinkEvents(selectLogin, Event.ONCLICK);
		DOM.setEventListener(selectLogin, new EventListener() {
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event) == Event.ONCLICK){
					//设置可见不可见状态
					selectLogin.addClassName("selected");
					selectRegister.removeClassName("selected");
					loginContainer.removeClassName("invisible");
					registerContainer.addClassName("invisible");
					
					//清空input标签的输入内容
					Operate.cleanValue("username2");
					Operate.cleanValue("password2");
					Operate.cleanValue("check-password");
					Operate.cleanValue("nickName");
					
				}
			}
		});
		
		DOM.sinkEvents(selectRegister, Event.ONCLICK);
		DOM.setEventListener(selectRegister, new EventListener() {
			
			@Override
			public void onBrowserEvent(Event event) {
				// TODO Auto-generated method stub
				if(DOM.eventGetType(event) == Event.ONCLICK){
					selectRegister.addClassName("selected");
					selectLogin.removeClassName("selected");
					loginContainer.addClassName("invisible");
					registerContainer.removeClassName("invisible");
					
					Operate.cleanValue("username");
					Operate.cleanValue("password");
				}
			}
		});
		
		//登录和注册请求按钮点击事件注册
		
		//登录按钮点击事件
		Element loginBtn = DOM.getElementById("login");
		Element registerBtn = DOM.getElementById("register");
		DOM.sinkEvents(loginBtn, Event.ONCLICK);
		DOM.setEventListener(loginBtn, new EventListener() {
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK){
					String username = Operate.getValue("username");
					String password = Operate.getValue("password");
					
					if(!FieldVerifier.isValidPassword(password)) {
						Operate.setAlert("密码长度应该为6-10位",false);
						return;
					}
					if(username==null) {
						Operate.setAlert("账号不能为空",false);
						return;
					}
					
					JSONObject info = new JSONObject();
					JSONString userName = new JSONString(username);
					JSONString passWord = new JSONString(password);
					info.put("accountId", userName);
					info.put("password",passWord);
					String form = info.toString();
					Window.alert(form);
					loginService.login(form, new AsyncCallback<int []>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Operate.setAlert("网络出现问题哦，请重试！", false);
						}
						@Override
						public void onSuccess(int[] result) {
							// TODO Auto-generated method stub
							String desc;
							if(result[0] == 0) {
								if(result[1]==0) {
									Window.open("./index.html","_self", null);
								}
								else {
									Window.open("./manager.html","_self", null);
								}
							}else {
								desc = ResultConst.getRsById(result[0]).getDescribe();
								Operate.setAlert(desc, false);
							}
						}
					});
					
				}
			}
		});
		
//		注册按钮注册点击事件
		DOM.sinkEvents(registerBtn, Event.ONCLICK);
		DOM.setEventListener(registerBtn, new EventListener() {
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK){
					String username = Operate.getValue("username2");
					String password = Operate.getValue("password2");
					String password2 = Operate.getValue("check-password");
					String nickName = Operate.getValue("nickName");
					String sex = Operate.getChoice("choice");
					if(sex == "2") {
						sex = "0";
					}
					Window.alert(sex);
					if(username==null || nickName==null) {
						Operate.setAlert("账号或用户名不能为空",false);
						return;
					}
					if(!FieldVerifier.isValidPassword(password)||!FieldVerifier.isValidPassword(password2)) {
						Operate.setAlert("密码长度应该为6-10位",false);
						return;
					}
					
					if(password!=password2) {
						Operate.setAlert("两次输入的密码不一致",false);
					}
					JSONObject info = new JSONObject();
					JSONString userName = new JSONString(username);
					JSONString passWord = new JSONString(password);
					info.put("accountId", userName);
					info.put("password",passWord);
					info.put("sex", new JSONString(sex));
					info.put("nickName", new JSONString(nickName));
					
					String form = info.toString();
					
					registerService.registerServer(form, new AsyncCallback<Integer>() {

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Operate.setAlert("网络出现问题哦，请重试！", false);
						}

						@Override
						public void onSuccess(Integer result) {
							// TODO Auto-generated method stub
							Window.alert(result.toString());
							if(result == 0) {
								Operate.setAlert("注册成功，请登录", true);
								Window.Location.reload();
							}else {
								String desc = ResultConst.getRsById(result).getDescribe();
								Operate.setAlert(desc, false);
							}
						}
						
					});
				}
			}
		});
		

	}
}