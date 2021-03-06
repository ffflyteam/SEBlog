package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.DeleteCommentService;
import com.manager.shared.ManagerIndexDAO;

@SuppressWarnings("serial")
public class DeleteCommentServiceImpl extends RemoteServiceServlet implements DeleteCommentService{

	@Override
	public int deleteComment(int commentId) {
		int res = ManagerIndexDAO.instance.deleteComment(commentId);
		return res;
	}

}
