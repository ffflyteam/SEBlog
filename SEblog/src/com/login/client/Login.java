package com.login.client;

import com.login.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class Login implements EntryPoint {
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);
	public void onModuleLoad() {
		Element btn = DOM.getElementById("login");
		DOM.sinkEvents(btn, Event.ONCLICK);
		DOM.setEventListener(btn, new EventListener() {
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK){
					String username = Operate.getValue("username");
					String password = Operate.getValue("password");
					Window.alert(username + password);
					if(username==null || password==null) {
						Window.alert("账号或密码不能为空");
						return;
					}
					if(!FieldVerifier.isValidPassword(password)) {
						Window.alert("密码长度应该为6-10位");
						return;
					}
					JSONObject info = new JSONObject();
					JSONString userName = new JSONString(username);
					JSONString passWord = new JSONString(password);
					info.put("username", userName);
					info.put("password",passWord);
					String form = info.toString();
					loginService.loginServer(form, new AsyncCallback<String>() {
						public void onFailure(Throwable caught) {
							Window.alert("连接服务器失败，请重试");
						}
						public void onSuccess(String result) {
							Window.alert("msg");
							Window.alert(result);
						}
					});
				}
			}
		});
	}
}