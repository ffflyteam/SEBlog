package com.other.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.other.client.User;

public class UserOtherDAO {
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	private static final String SELECT_USER_RELATION = "SELECT * FROM `user_relation` WHERE UserId = ? AND OtherId = ?";
	private static final String INSERT_USER_RELATION = "INSERT INTO `user_relation` VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE Type = ?";
	private static final String CHANGE_USER_RELATION = "UPDATE `user_relation` SET `Type` = 0 WHERE UserId = ? AND OtherId = ? AND Type = ?";
	private static final String INSERT_MESSAGE = "INSERT INTO `message` VALUES(0,?,?,?,?,0,?)";
	
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
	
	public boolean isRelated(int accountId, int otherId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_RELATION, new Object[] {accountId, otherId});
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int cancleRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(CHANGE_USER_RELATION, new Object[] {accountId,otherId,type});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	public int happenRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(INSERT_USER_RELATION, new Object[] {accountId,otherId,type,type, new Date()});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	public int makeMessage(int receiverId, int messageType, int senderId, int blogId) {
		int rs =  DBConnection.instance.executeQuery(INSERT_MESSAGE, new Object[] {receiverId, messageType, senderId, blogId, new Date()});
		return rs;
	}
	
}
