package com.detail.server;

import com.detail.client.Blog;
import com.detail.client.Comment;
import com.detail.client.MakeCommentService;
import com.detail.shared.BlogDetailDAO;
import com.detail.shared.MessageType;
import com.detail.shared.ResultConst;
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
		if(rs == ResultConst.SUCCESS.getId()) {		//传送消息
			int receiverId;
			if(BlogDetailDAO.instance.isBlog(objectId)) {
				Blog blog = BlogDetailDAO.instance.getBlogById(objectId);
				receiverId = blog.getUser().getAccountId();
				UserDetailDAO.instance.makeMessage(receiverId, MessageType.MAKE_COMMENT.getId(), accountId, objectId);
			} else {
				Comment comment = BlogDetailDAO.instance.getCommentById(objectId);
				receiverId = comment.getUser().getAccountId();
				while(!BlogDetailDAO.instance.isBlog(comment.getObjectId())) {  //直到拿到Blog
					comment = BlogDetailDAO.instance.getCommentById(comment.getObjectId());
				}
				UserDetailDAO.instance.makeMessage(receiverId, MessageType.MAKE_COMMENT.getId(), accountId, comment.getObjectId());
			}
		}
		return rs;
	}

}
