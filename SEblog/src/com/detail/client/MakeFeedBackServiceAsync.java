package com.detail.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MakeFeedBackServiceAsync {
	public void makeFeedBack(int objectId, int feedBackType, AsyncCallback<Integer> callback);
}
