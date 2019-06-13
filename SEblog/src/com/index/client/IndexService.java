package com.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.Map;
import java.util.List;

@RemoteServiceRelativePath("article")
public interface IndexService extends RemoteService {
	Map<Integer, List<Blog>> index() throws IllegalArgumentException;
}
