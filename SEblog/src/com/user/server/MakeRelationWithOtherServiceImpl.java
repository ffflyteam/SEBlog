package com.user.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.MakeRelationWithOtherService;
import com.user.shared.MessageType;
import com.user.shared.ResultConst;
import com.user.shared.UserDAO;

@SuppressWarnings("serial")
public class MakeRelationWithOtherServiceImpl extends RemoteServiceServlet implements MakeRelationWithOtherService{

	/*
	 * 关注他人
	 * param(被关在人Id,类型(默认1，标志位：0为发生关系，1为取消关系)
	 */
	public int makeRelation(int otherId, int type, int flag) {
		int res;
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		if(flag == 0) {
			res = UserDAO.instance.happenRelation(accountId, otherId, type);
			if(res == ResultConst.SUCCESS.getId()) {		//生成消息
				UserDAO.instance.makeMessage(otherId, MessageType.HAPPEN_RELATION.getId(), accountId);
			}
		} else {
			res = UserDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
