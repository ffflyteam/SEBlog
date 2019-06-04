package com.index.client;

import com.DAO.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	void getUserInfo(AsyncCallback<User> callback);
}
