package com.detail.server;

import com.detail.client.MakeCommentService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MakeCommentServiceImpl extends RemoteServiceServlet implements MakeCommentService{
	
	/*
	 *评论
	 *param(评论主体id，评论内容)
	 */
	@Override
	public int makeComment(int objectId, String content) throws IllegalArgumentException {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int rs = UserDetailDAO.instance.makeComment(objectId, accountId, content);
		return rs;
	}

}
