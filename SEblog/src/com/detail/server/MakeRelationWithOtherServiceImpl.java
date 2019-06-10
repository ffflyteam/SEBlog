package com.detail.server;

import com.detail.client.MakeRelationWithOtherService;
import com.detail.shared.MessageType;
import com.detail.shared.ResultConst;
import com.detail.shared.UserDetailDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class MakeRelationWithOtherServiceImpl extends RemoteServiceServlet implements MakeRelationWithOtherService{

	/*
	 * 关注他人
	 * param(被关在人Id,类型(默认1，标志位：0为发生关系，1为取消关系)
	 */
	@Override
	public int makeRelation(int otherId, int type, int flag) {
		int res;
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(flag == 0) {
			res = UserDetailDAO.instance.happenRelation(accountId, otherId, type);
			if(res == ResultConst.SUCCESS.getId()) {		//生成消息
				UserDetailDAO.instance.makeMessage(otherId, MessageType.HAPPEN_RELATION.getId(), accountId);
			}
		} else {
			res = UserDetailDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
