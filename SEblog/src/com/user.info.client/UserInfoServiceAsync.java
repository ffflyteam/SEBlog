package com.user.info.client;

import com.DAO.User;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {
	void getUserInfo(int accountId, AsyncCallback<User> callback);
}
