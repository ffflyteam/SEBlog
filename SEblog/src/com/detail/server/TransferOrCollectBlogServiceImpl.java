package com.detail.server;

import com.detail.client.TransferOrCollectBlogService;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("transfer_or_collect")
public class TransferOrCollectBlogServiceImpl extends RemoteServiceServlet implements TransferOrCollectBlogService{

	@Override
	public int transferOrCollectBlog(int accountId, int blogId, int type, int flag) {
		int res;
		if(flag == 0) {
			res = UserDetailDAO.instance.collectOrTransferBlog(blogId, accountId, type);
		} else {
			res = UserDetailDAO.instance.cancleCollectOrTransfer(blogId, accountId, type);
		}
		return res;
	}

}
