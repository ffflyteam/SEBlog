package com.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BlogDAO {
	private static final String SELECT_USER_BLOGS_INFO = "SELECT * FROM `blog_info` WHERE UserId = ? ORDER BY PublishDateTime DESC";
	private static final String SELECT_BLOG_BY_ID = "SELECT * FROM `blog_info` WHERE BlogId = ?";
	private static final String SELECT_ALL_COMMENTS = "SELECT * FROM `comments` WHERE ObjectId = ? ORDER BY CommentDateTime DESC";
	private static final String SELECT_COMMENT_BY_ID = "SELECT * FROM `comments` WHERE CommentId = ?";
	public static final BlogDAO instance = new BlogDAO();
	
	private BlogDAO() {
	}
	
	public List<Blog> getUserAllBlogInfo(int accountId) {
		ArrayList<Blog> allBlogs = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_BLOGS_INFO, new Object[] {accountId});
		try {
			while(rs.next()) {
				try {
					Blog blog = new Blog(rs.getInt("BlogId"), rs.getDate("PublishDateTime"), rs.getInt("UserId"), rs.getString("Title"),
							rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"), rs.getInt("Type"));
					allBlogs.add(blog);
				} catch (Throwable t) {
					return Collections.emptyList();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyList();
		}
		return allBlogs;
	}
	
	public Blog getBlogById(int blogId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_BLOG_BY_ID, new Object[] {blogId});
		if(rs == null) {
			return null;
		}
		try {
			if(rs.next()) {
				Blog blog = new Blog(rs.getInt("BlogId"), rs.getDate("PublishDateTime"), rs.getInt("UserId"), rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"), rs.getInt("Type"));
				return blog;
			}
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
	
	public boolean isBlog(int objectId) {
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
	
	public List<Comment> getAllCommentById(int objectId) {
		ArrayList<Comment> allComments = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COMMENTS, new Object[] {objectId});
		try {
			while(rs.next()) {
				try {
					Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), rs.getInt("UserId"), rs.getDate("CommentDateTime"), rs.getString("Content"));
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
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_COMMENT_BY_ID, new Object[] {commentId});
		try {
			Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), rs.getInt("UserId"), rs.getDate("CommentDateTime"), rs.getString("Content"));
			return comment;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}
}
