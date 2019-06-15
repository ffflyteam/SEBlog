package com.manager.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.manager.client.Blog;
import com.manager.client.Comment;
import com.manager.client.User;

public class ManagerBlogDAO {
	private static final String SELECT_BLOG_BY_ID = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
			+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info` WHERE BlogId = ?) AS A "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.BlogId = A.BlogId "
			+ "LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId "
			+ "ORDER BY PublishDateTime DESC";
	private static final String SELECT_ALL_COMMENTS = "SELECT * FROM `comments` WHERE ObjectId = ? ORDER BY LikeNum DESC";
	private static final String SELECT_COMMENT_BY_ID = "SELECT * FROM `comments` WHERE CommentId = ?";
	public static final ManagerBlogDAO instance = new ManagerBlogDAO();
	
	private static final ConcurrentHashMap<Integer, Blog> blogSecondDAO = new ConcurrentHashMap<>();
	private static final Map<Integer, Comment> commentSecondDao = new LinkedHashMap<>();
	
	private ManagerBlogDAO() {
	}
	
	
	public Blog getBlogById(int blogId) {
		if(blogSecondDAO.contains(blogId)) {
			return blogSecondDAO.get(blogId);
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_BLOG_BY_ID, new Object[] {blogId});
		if(rs == null) {
			return null;
		}
		try {
			if(rs.next()) {
				User user = ManagerIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
				Blog blog = new Blog(blogId, rs.getDate("PublishDateTime"), user, rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"), 
						rs.getInt("ReadNum"), rs.getInt("Type"));
				return blog;
			}
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	public boolean isBlog(int objectId) {
		if(blogSecondDAO.contains(objectId)) {
			return true;
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_BLOG_BY_ID, new Object[] {objectId});
		try {
			if(rs == null || !rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void removeBlogByIdFromSecondDAO(int blogId) {
		blogSecondDAO.remove(blogId);
	}
	
	public List<Comment> getAllCommentById(int objectId) {
		ArrayList<Comment> allComments = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COMMENTS, new Object[] {objectId});
		try {
			while(rs.next()) {
				try {
					User user = ManagerIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
					Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), user, rs.getDate("CommentDateTime"), rs.getString("Content"), rs.getInt("CommentNum"));
					allComments.add(comment);
				} catch (Throwable t) {
					t.printStackTrace();
					return Collections.emptyList();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allComments;
	}
	
	public Comment getCommentById(int commentId) {
		if(commentSecondDao.containsKey(commentId)) {
			return commentSecondDao.get(commentId);
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_COMMENT_BY_ID, new Object[] {commentId});
		try {
			User user = ManagerIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
			Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), user, rs.getDate("CommentDateTime"), rs.getString("Content"), rs.getInt("CommentNum"));
			return comment;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	public boolean hitCache(int blogId) {
		return blogSecondDAO.contains(blogId);
	}
}
