package com.other.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.MakeRelationWithOtherService;
import com.other.shared.MessageType;
import com.other.shared.ResultConst;
import com.other.shared.UserOtherDAO;

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
			res = UserOtherDAO.instance.happenRelation(accountId, otherId, type);
			if(res == ResultConst.SUCCESS.getId()) {		//生成消息
				UserOtherDAO.instance.makeMessage(otherId, MessageType.HAPPEN_RELATION.getId(), accountId, -1);
			}
		} else {
			res = UserOtherDAO.instance.cancleRelation(accountId, otherId, type);
		}
		return res;
	}

}
