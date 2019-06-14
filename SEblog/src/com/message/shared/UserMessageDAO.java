package com.message.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.message.client.Message;
import com.message.client.User;

public class UserMessageDAO {
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	private static final String DELETE_MESSAGE = "DELETE FROM `message` WHERE MessageId = ?";
	private static final String GET_MESSAGES = "SELECT * FROM `message` WHERE ReceiverId = ? ORDER BY CreateTime DESC";
	private static final String UPDATE_MESSAGE_READ_FLAG = "UPDATE `message` SET ReadFlag = 1 WHERE MessageId = ?";
	public static final UserMessageDAO instance = new UserMessageDAO();
	
	private UserMessageDAO() {
	}
	
	public List<Message> getAllMessage(int accountId) {
		List<Message> resultList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(GET_MESSAGES, new Object[] {accountId});
		if(rs == null) {
			return Collections.emptyList();
		}
		try {
			while(rs.next()) {
				User receiver = getUserInfo(rs.getInt("ReceiverId"));
				User sender = getUserInfo(rs.getInt("SenderId"));
				int blogId = rs.getInt("BlogId");
				String blogTitle = "";
				if(blogId > 0) {
					blogTitle = BlogMessageDAO.instance.getBlogById(blogId).getTitle();
				}
				Message message = new Message(rs.getInt("MessageId"), receiver, rs.getInt("MessageType"), sender, blogId, blogTitle, rs.getInt("ReadFlag"), rs.getDate("CreateTime"));
				resultList.add(message);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyList();
		}
		return resultList;
	}
	
	public int deleteMessage(int messageId) {
		int rs = DBConnection.instance.executeQuery(DELETE_MESSAGE, new Object[] {messageId});
		return rs;
	}
	
	public int setReadFlag(int messageId) {
		int rs = DBConnection.instance.executeQuery(UPDATE_MESSAGE_READ_FLAG, new Object[] {messageId});
		return rs;
	}
	
	//获取用户自己信息
		public User getUserInfo(int userId) {
			ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {userId});
			try {
				if(rs.next()) {
					try {
						User user = new User(rs.getInt("UserId"), rs.getString("PassWord"), rs.getString("UserName"), 
								rs.getShort("Sex"), rs.getDate("BirthDay"), rs.getString("Address"), rs.getInt("Stat"));
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
		}
}
