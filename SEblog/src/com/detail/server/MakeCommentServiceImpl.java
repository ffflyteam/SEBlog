package com.detail.server;

import com.detail.client.MakeCommentService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("make_comment")
public class MakeCommentServiceImpl extends RemoteServiceServlet implements MakeCommentService{

	@Override
	public int makeComment(int objectId, int userId, String content) {
		int rs = UserDetailDAO.instance.makeComment(objectId, userId, content);
		return rs;
	}

}
