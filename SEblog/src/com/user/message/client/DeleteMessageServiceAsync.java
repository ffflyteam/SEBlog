package com.user.message.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface DeleteMessageServiceAsync {
	public void deleteMessage(int messageId, AsyncCallback<Integer> callback);
}
