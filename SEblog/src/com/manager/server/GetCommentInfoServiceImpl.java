package com.manager.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.client.Comment;
import com.manager.client.GetCommentInfoService;
import com.manager.shared.ManagerBlogDAO;

@SuppressWarnings("serial")
public class GetCommentInfoServiceImpl extends RemoteServiceServlet implements GetCommentInfoService{

	@Override
	public Comment getComment(int commentId) {
		Comment comment = ManagerBlogDAO.instance.getCommentById(commentId);
		return comment;
	}

}
