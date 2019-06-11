package com.user.server;

import org.json.JSONObject;

import com.DAO.UserDAO;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.user.client.ChangeInfoService;

@SuppressWarnings("serial")
@RemoteServiceRelativePath("user_info_change")
public class ChangeInfoServiceImpl extends RemoteServiceServlet implements ChangeInfoService{

	/*
	 * 用户改变自己信息
	 * param(Json格式的String，password，nickName，sex，birthDay，address)
	 */
	@Override
	public boolean changeInfo(String info) {
		try {
			JSONObject jsonObject = new JSONObject(info);
			int account = Integer.parseInt((String) this.getThreadLocalRequest().getSession().getAttribute("accountId"));
			String password = jsonObject.getString("password");
			String name = jsonObject.getString("nickName");
			short sex = (short) jsonObject.getInt("sex");
			String birthDay = jsonObject.getString("birthDay");
			String address = jsonObject.getString("address");
			return UserDAO.instance.setUserInfo(account, password, name, sex, birthDay, address);
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

}
