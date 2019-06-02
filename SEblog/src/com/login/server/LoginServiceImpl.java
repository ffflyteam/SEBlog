package com.login.server;

import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.login.client.LoginService;
import com.DAO.ManagerDAO;
import com.DAO.ResultConst;
import com.DAO.UserDAO;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService{

	@Override
	public int[] login(String input) {
		try {
			JSONObject jsonObject = new JSONObject(input);
			String accountIdStr = jsonObject.getString("accountId");
			String password = jsonObject.getString("password");
			this.getThreadLocalRequest().getSession().setAttribute("accountId", accountIdStr);  //����session
			try {
				int accountId = Integer.parseInt(accountIdStr);
				int rs = UserDAO.instance.login(accountId, password);
				return new int[] {rs, 0};
			} catch (NumberFormatException e) {
				int rs = ManagerDAO.instance.login(accountIdStr, password);
				return new int[] {rs, 1};
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return new int[] {ResultConst.LOGIN_ERROR.getId(), 0};
		}
	}

}
