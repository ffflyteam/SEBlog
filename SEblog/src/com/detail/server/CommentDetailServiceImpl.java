package com.detail.server;

import java.util.List;

import com.detail.client.Comment;
import com.detail.client.CommentDetailService;
import com.detail.shared.BlogDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CommentDetailServiceImpl extends RemoteServiceServlet implements CommentDetailService{

	@Override
	public List<Comment> getCommentDetail(int objectId) {
		List<Comment> allComments = BlogDAO.instance.getAllCommentById(objectId);
		return allComments;
	}

}
