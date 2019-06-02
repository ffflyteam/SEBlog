package com.detail.server;

import com.DAO.UserDAO;
import com.detail.client.TransferOrCollectBlogService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("transfer_or_collect")
public class TransferOrCollectBlogServiceImpl extends RemoteServiceServlet implements TransferOrCollectBlogService{

	@Override
	public int transferOrCollectBlog(int accountId, int blogId, int type, int flag) {
		int res;
		if(flag == 0) {
			res = UserDAO.instance.collectOrTransferBlog(blogId, accountId, type);
		} else {
			res = UserDAO.instance.cancleCollectOrTransfer(blogId, accountId, type);
		}
		return res;
	}

}
