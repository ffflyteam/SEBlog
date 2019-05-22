package com.login.server;

import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.login.client.LoginService;
import com.DAO.ResultConst;
import com.DAO.UserDao;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

	@Override
	public int login(String input) {
		try {
			JSONObject jsonObject = new JSONObject(input);
			int accountId = jsonObject.getInt("accountId");
			String password = jsonObject.getString("password");
			this.getThreadLocalRequest().getSession().setAttribute("accountId", accountId);  //����session
			return UserDao.instance.login(accountId, password);
		} catch (Throwable t) {
			t.printStackTrace();
			return ResultConst.LOGIN_ERROR.getId();
		}
	}

}
