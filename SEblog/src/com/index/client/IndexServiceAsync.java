package com.index.client;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IndexServiceAsync {
	void index(AsyncCallback<Map<Integer, List<Blog>>> asyncCallback);
}
