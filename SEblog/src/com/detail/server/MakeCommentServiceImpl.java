package com.detail.server;

import com.DAO.UserDAO;
import com.detail.client.MakeCommentService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("make_comment")
public class MakeCommentServiceImpl extends RemoteServiceServlet implements MakeCommentService{
	
	/*
	 *评论
	 *param(评论主体id，评论内容)
	 */
	@Override
	public int makeComment(int objectId, String content) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		int rs = UserDAO.instance.makeComment(objectId, accountId, content);
		return rs;
	}

}
