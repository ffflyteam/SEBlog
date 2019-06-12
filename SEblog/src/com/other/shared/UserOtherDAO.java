package com.other.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.other.client.User;

public class UserOtherDAO {
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	
	public static final UserOtherDAO instance = new UserOtherDAO();
	
	private static final Map<Integer, User> userSecondDao = new LinkedHashMap<>();//�ɸĳ�Redis
	private static final int userCacheSize = 64;
	
	public User getUserInfo(int userId) {
		if(!userSecondDao.containsKey(userId)) {
			ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {userId});
			try {
				if(rs.next()) {
					try {
						User user = new User(rs.getInt("UserId"), rs.getString("PassWord"), rs.getString("UserName"), 
								rs.getShort("Sex"), rs.getDate("BirthDay"), rs.getString("Address"), rs.getInt("Stat"));
						if(userSecondDao.size() < userCacheSize) {
							userSecondDao.putIfAbsent(userId, user);
						} else {
							int accountId4Remove = userSecondDao.entrySet().iterator().next().getKey();
							userSecondDao.remove(accountId4Remove);
							userSecondDao.putIfAbsent(userId, user);
						}
						return user;
					} catch (SQLException e) {
						e.printStackTrace();
						return null;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
			return null;
		} else {
			return userSecondDao.get(userId);
		}
	}
}
