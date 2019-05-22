package com.index.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("article")
public interface ArticleService extends RemoteService{
	String articleServer(String input)throws IllegalArgumentException; 
}
