package com.manager.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.manager.index.client.Comment;
import com.manager.index.client.GetCommentInfoService;
import com.manager.index.shared.BlogDAO;

@SuppressWarnings("serial")
public class GetCommentInfoServiceImpl extends RemoteServiceServlet implements GetCommentInfoService{

	@Override
	public Comment getComment(int commentId) {
		Comment comment = BlogDAO.instance.getCommentById(commentId);
		return comment;
	}

}
