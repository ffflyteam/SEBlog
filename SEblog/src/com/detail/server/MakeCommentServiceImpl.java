package com.detail.server;

import com.detail.client.Blog;
import com.detail.client.Comment;
import com.detail.client.MakeCommentService;
import com.detail.shared.BlogDetailDAO;
import com.detail.shared.CommonHelper;
import com.detail.shared.MessageType;
import com.detail.shared.ResultConst;
import com.detail.shared.UserDetailDAO;
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
		int rs = UserDetailDAO.instance.makeComment(objectId, accountId, content);
		if(rs == ResultConst.SUCCESS.getId()) {		//传送消息
			int receiverId;
			if(BlogDetailDAO.instance.isBlog(objectId)) {
				Blog blog = BlogDetailDAO.instance.getBlogById(objectId);
				receiverId = blog.getUser().getAccountId();
				UserDetailDAO.instance.makeMessage(receiverId, MessageType.MAKE_COMMENT.getId(), accountId);
			} else {
				Comment comment = BlogDetailDAO.instance.getCommentById(objectId);
				receiverId = comment.getUser().getAccountId();
				UserDetailDAO.instance.makeMessage(receiverId, MessageType.MAKE_COMMENT.getId(), accountId);
			}
		}
		return rs;
	}

}
