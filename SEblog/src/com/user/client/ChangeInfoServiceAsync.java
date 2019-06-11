package com.user.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ChangeInfoServiceAsync {
	public void changeInfo(String info, AsyncCallback<Boolean> callback);
}
