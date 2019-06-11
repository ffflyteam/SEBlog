package com.message.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GetAllMessageServiceAsync {
	public void getAllMessage(AsyncCallback<List<Message>> callback);
}
