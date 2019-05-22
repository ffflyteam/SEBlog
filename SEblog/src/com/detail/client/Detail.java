package com.detail.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;

public class Detail implements EntryPoint{

	@Override
	public void onModuleLoad() {
		// TODO Auto-generated method stub
		String id = Window.Location.getQueryString().substring(1).split("=")[1];
		Window.alert(id);
		
	}

}
