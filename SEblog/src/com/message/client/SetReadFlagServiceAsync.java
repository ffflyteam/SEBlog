package com.message.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SetReadFlagServiceAsync {
	public void setReadFlag(int messageId, AsyncCallback<Integer> callback);
}
