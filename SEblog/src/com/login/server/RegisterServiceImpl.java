package com.login.server;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.login.client.RegisterService;
import com.DAO.ResultConst;
import com.DAO.UserDAO;

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
			return UserDAO.instance.register(account, password, name, sex, null, null);
		} catch (SQLException | JSONException e) {
			e.printStackTrace();
			return ResultConst.REGISTER_ERROR.getId();
		}
	}

}
