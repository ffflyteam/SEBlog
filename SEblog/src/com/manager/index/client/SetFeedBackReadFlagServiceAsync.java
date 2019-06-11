package com.manager.index.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SetFeedBackReadFlagServiceAsync {
	public void setReadFlag(int feedBackId, AsyncCallback<Integer> callback);
}
