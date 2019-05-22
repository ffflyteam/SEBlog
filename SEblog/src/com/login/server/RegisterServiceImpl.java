package com.login.server;

import org.json.JSONObject;

import com.DAO.ResultConst;
import com.DAO.UserDao;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.login.client.RegisterService;

@SuppressWarnings("serial")
public class RegisterServiceImpl extends RemoteServiceServlet implements RegisterService{

	@Override
	public int registerServer(String input) {
		try {
			JSONObject jsonObject = new JSONObject(input);
			int account = jsonObject.getInt("accountId");
			String password = jsonObject.getString("password");
			String name = jsonObject.getString("nickName");
			short sex = (short) jsonObject.getInt("sex");
			return UserDao.instance.register(account, password, name, sex, null, null);
		} catch (Throwable e) {
			e.printStackTrace();
			return ResultConst.REGISTER_ERROR.getId();
		}
	}

}
