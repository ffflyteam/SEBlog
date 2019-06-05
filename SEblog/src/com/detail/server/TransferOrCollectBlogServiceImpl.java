package com.detail.server;

import com.detail.client.TransferOrCollectBlogService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("transfer_or_collect")
public class TransferOrCollectBlogServiceImpl extends RemoteServiceServlet implements TransferOrCollectBlogService{

	/*
	 * 收藏或转发博客
	 * param(博客Id,类型(见RelationWithBlog),标志位:0为收藏或转发，1为取消）
	 */
	@Override
	public int transferOrCollectBlog(int blogId, int type, int flag) {
		int res;
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(flag == 0) {
			res = UserDetailDAO.instance.collectOrTransferBlog(blogId, accountId, type);
		} else {
			res = UserDetailDAO.instance.cancleCollectOrTransfer(blogId, accountId, type);
		}
		return res;
	}

}
