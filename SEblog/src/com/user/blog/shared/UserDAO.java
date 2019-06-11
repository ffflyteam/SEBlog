package com.user.blog.shared;

import java.util.Date;

import com.DAO.DBConnection;
import com.detail.shared.CommonHelper;

public class UserDAO {
	private static final String INSERT_BLOG_INFO = "INSERT INTO `blog_info` VALUES(0,?,?,?,?,0,0,0,?)";
	
	public static final UserDAO instance = new UserDAO();
	
	//创建博客
		public int makeBlog(int accountId, String title, String content, int type) {
			Object[] params = new Object[] {new Date(),accountId, title, content, type};
			int res = DBConnection.instance.executeQuery(INSERT_BLOG_INFO, params);
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		}
}
