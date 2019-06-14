package com.other.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.other.client.RelationStatusService;
import com.other.shared.UserOtherDAO;

@SuppressWarnings("serial")
public class RelationStatusServiceImpl extends RemoteServiceServlet implements RelationStatusService{

	@Override
	public boolean getRelationstatus(int otherId) {
		int accountId = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
		return UserOtherDAO.instance.isRelated(accountId, otherId);
	}

}
