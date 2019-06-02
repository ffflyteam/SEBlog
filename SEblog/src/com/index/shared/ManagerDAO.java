package com.index.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.index.client.Comment;

public class ManagerDAO {
	private static final String SELECT_MANAGER_INFO = "SELECT * FROM `manager_info` WHERE ManagerId = ?";
	private static final String SELECT_MANAGER_INFO_BY_ID_AND_PASSWORD = "SELECT * FROM `manager_info` WHERE ManagerId = ? AND PassWord = ?";
	private static final String DELETE_BLOG = "DELETE FROM `blog_info` WHERE BlogId = ?";
	private static final String DELETE_COMMENT = "DELETE FROM `comments` WHERE CommentId = ?";
	private static final String DELETE_ALL_COMMENTS_BY_OBJECTID = "DELETE FROM `comments` WHERE ObjectId = ?";
	private static final String DELECT_COMMENT_LIKE = "DELETE FROM user_comment_like WHERE CommentId = ?";
	private static final String DELETE_USER_BLOG_RELATION = "DELETE FROM user_blog_relation WHERE BlogId = ?";
	private static final String UPDATE_USER_STAT = "UPDATE `user_info` SET `Stat` = ? WHERE UserId = ?";
	
	public static final ManagerDAO instance = new ManagerDAO();
	
	private ManagerDAO() {
	}
	
	public int login(String managerId, String password) {
		if(!isAccountExist(managerId)) {
			return ResultConst.MANAGER_ACCOUNT_NOT_EXIST.getId();
		}
		if(!isPasswordCorrect(managerId, password)) {
			return ResultConst.PASSWORD_ERROR.getId();
		}
		return ResultConst.SUCCESS.getId();
	}
	
	public void deleteBlog(int blogId) {
		Object[] params = new Object[] {blogId};
		List<Comment> allComments = BlogDAO.instance.getAllCommentById(blogId);
		for(Comment comment : allComments) {			//ɾ�����۵����ۣ���������
			deleteComment(comment.getCommentId());
		}
		DBConnection.instance.executeQuery(DELETE_ALL_COMMENTS_BY_OBJECTID, params);  //ɾ�����͵�����
		DBConnection.instance.executeQuery(DELETE_BLOG, params);  //ɾ������
		DBConnection.instance.executeQuery(DELETE_USER_BLOG_RELATION, params);  //ɾ���û��Ͳ��͵Ĺ�ϵ
	};
	
	public boolean deleteComment(int commentId) {
		List<Comment> allComments = BlogDAO.instance.getAllCommentById(commentId);
		if(!allComments.isEmpty()) {
			for(Comment comment : allComments) {
				deleteComment(comment.getCommentId());//�ݹ�
			}
		}
		Object[] arr = new Object[] {commentId};
		int result = DBConnection.instance.executeQuery(DELETE_COMMENT, arr);
		DBConnection.instance.executeQuery(DELECT_COMMENT_LIKE, arr);
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	public boolean changeUserStat(int accountId, int stat) {
		int result = DBConnection.instance.executeQuery(UPDATE_USER_STAT, new Object[] {stat, accountId});
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	public boolean isPasswordCorrect(String managerId, String password) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_MANAGER_INFO_BY_ID_AND_PASSWORD, new Object[] {managerId, password});
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isAccountExist(String managerId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_MANAGER_INFO, new Object[] {managerId});
		return rs != null;
	}
}
