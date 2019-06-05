package com.detail.server;

import java.util.List;

import com.detail.client.Comment;
import com.detail.client.CommentDetailService;
import com.detail.shared.BlogDetailDAO;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CommentDetailServiceImpl extends RemoteServiceServlet implements CommentDetailService{

	/*
	 * 获取某个博客或评论下的所有评论
	 * param(目标Id)
	 */
	@Override
	public List<Comment> getCommentDetail(int objectId) throws IllegalArgumentException {
		List<Comment> allComments = BlogDetailDAO.instance.getAllCommentById(objectId);
		return allComments;
	}

}
