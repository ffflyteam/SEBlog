package com.manager.index.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.manager.index.client.Comment;
import com.manager.index.client.FeedBack;
import com.manager.index.client.User;

public class ManagerIndexDAO {
	private static final String SELECT_FEEDBACK = "SELECT * FROM `feedback` ORDER BY CreateTime DESC";
	private static final String UPDATE_FEEDBACK_READFLAG = "UPDATE `feedback` SET ReadFlag = 1 WHERE FeedBackId = ?";
	private static final String DELETE_FEEDBACK = "DELETE FROM `feedback` WHERE FeedBackId = ?";
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	private static final String DELETE_BLOG = "DELETE FROM `blog_info` WHERE BlogId = ?";
	private static final String DELETE_COMMENT = "DELETE FROM `comments` WHERE CommentId = ?";
	private static final String DELETE_ALL_COMMENTS_BY_OBJECTID = "DELETE FROM `comments` WHERE ObjectId = ?";
	private static final String DELECT_COMMENT_LIKE = "DELETE FROM user_comment_like WHERE CommentId = ?";
	private static final String DELETE_USER_BLOG_RELATION = "DELETE FROM user_blog_relation WHERE BlogId = ?";
	
	private static final String UPDATE_USER_STAT = "UPDATE `user_info` SET `Stat` = ? WHERE UserId = ?";
	
	public static final ManagerIndexDAO instance = new ManagerIndexDAO();
	
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
	
	public int deleteBlog(int blogId) {
		try {
			Object[] params = new Object[] {blogId};
			List<Comment> allComments = ManagerBlogDAO.instance.getAllCommentById(blogId);
			for(Comment comment : allComments) {			
				deleteComment(comment.getCommentId());
			}
			DBConnection.instance.executeQuery(DELETE_ALL_COMMENTS_BY_OBJECTID, params);  
			DBConnection.instance.executeQuery(DELETE_BLOG, params);  
			DBConnection.instance.executeQuery(DELETE_USER_BLOG_RELATION, params); 
		} catch (Throwable t) {
			return ResultConst.CAN_NOT_DELETE_BLOG.getId();
		}
		return ResultConst.SUCCESS.getId();
	};
	
	public int deleteComment(int commentId) {
		try {
			List<Comment> allComments = ManagerBlogDAO.instance.getAllCommentById(commentId);
			if(!allComments.isEmpty()) {
				for(Comment comment : allComments) {
					deleteComment(comment.getCommentId());
				}
			}
			Object[] arr = new Object[] {commentId};
			DBConnection.instance.executeQuery(DELETE_COMMENT, arr);
			DBConnection.instance.executeQuery(DELECT_COMMENT_LIKE, arr);
		} catch (Throwable t) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		return ResultConst.SUCCESS.getId();
	}
	
	public List<FeedBack> getAllUserFeedBack() {
		List<FeedBack> result = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_FEEDBACK, new Object[] {});
		try {
			while(rs.next()) {
				FeedBack feedBack = new FeedBack(rs.getInt("FeedBackId"), rs.getInt("ObjectId"), rs.getInt("UserId"), 
						rs.getInt("FeedBackType"), rs.getInt("ReadFlag"), rs.getDate("CreateTime"));
				result.add(feedBack);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyList();
		}
		return result;
	}
	
	public int setReadFlag(int feedBackId) {
		int rs = DBConnection.instance.executeQuery(UPDATE_FEEDBACK_READFLAG, new Object[] {feedBackId});
		return rs;
	}
	
	public int deleteFeedBack(int feedBackId) {
		int rs = DBConnection.instance.executeQuery(DELETE_FEEDBACK, new Object[] {feedBackId});
		return rs;
	}
	
	public int changeUserStat(int accountId, int stat) {
		int rs = DBConnection.instance.executeQuery(UPDATE_USER_STAT, new Object[] {stat, accountId});
		return rs;
	}
}
