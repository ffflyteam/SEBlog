package com.other.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.other.client.Blog;
import com.other.client.User;

public class BlogOtherDAO {
	private static final String SELECT_USER_BLOGS_INFO = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
		+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info`) AS A"
		+ " LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
		+ " LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.BlogId = A.BlogId "
		+ " LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId "
		+ " WHERE UserId = ? ORDER BY PublishDateTime DESC";
	private static final String SELECT_BLOG_BY_ID = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
			+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info` WHERE BlogId = ?) AS A "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.BlogId = A.BlogId "
			+ "LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId "
			+ "ORDER BY PublishDateTime DESC";
	private static final String SELECT_ALL_COLLECT_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY CollectTime DESC";
	private static final String SELECT_ALL_TRANSFER_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY TransferTime DESC";
	
	public static final BlogOtherDAO instance = new BlogOtherDAO();
	
	public List<Blog> getUserAllBlogInfo(int accountId) {
		List<Blog> allBlogs = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_BLOGS_INFO, new Object[] {accountId});
		try {
			while(rs.next()) {
				int blogId = rs.getInt("BlogId");
				User user = UserOtherDAO.instance.getUserInfo(rs.getInt("UserId"));
				Blog blog = new Blog(blogId, rs.getDate("PublishDateTime"), user, rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"),
						rs.getInt("ReadNum"), rs.getInt("Type"));
				allBlogs.add(blog);
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
				User user = UserOtherDAO.instance.getUserInfo(rs.getInt("UserId"));
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
	
	public List<Blog> getAllCollectBlog(int accountId) {
		ArrayList<Blog> allCollectBlogList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COLLECT_BLOGS, new Object[] {accountId, RelationWithBlog.COLLECT.getId()});
		try {
			while(rs.next()) {
				try {
					int blogId = rs.getInt("BlogId");
					Blog blog = getBlogById(blogId);
					if(blog == null) {
						continue;
					}
					allCollectBlogList.add(blog);
				} catch (Throwable t) {
					return Collections.emptyList();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allCollectBlogList;
	}
	
	public List<Blog> getAllTransferBlog(int accountId) {
		ArrayList<Blog> allTransferBlogList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_TRANSFER_BLOGS, new Object[] {accountId, RelationWithBlog.TRANSFER.getId()});
		if(rs == null) {
			return Collections.emptyList();
		}
		try {
			while(rs.next()) {
				try {
					int blogId = rs.getInt("BlogId");
					Blog blog = getBlogById(blogId);
					if(blog == null) {
						continue;
					}
					allTransferBlogList.add(blog);
				} catch (Throwable t) {
					return Collections.emptyList();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allTransferBlogList;
	}
}
