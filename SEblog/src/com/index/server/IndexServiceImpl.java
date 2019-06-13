package com.index.server;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.Blog;
import com.index.client.IndexService;
import com.index.shared.BlogIndexDAO;

@SuppressWarnings("serial")
public class IndexServiceImpl extends RemoteServiceServlet implements IndexService{

	@Override
	public Map<Integer, List<Blog>> index() throws IllegalArgumentException {
		try {
			return BlogIndexDAO.instance.getAllHotBlogs(); 
		} catch (Throwable t) {
			return Collections.emptyMap();
		}
	}

}
