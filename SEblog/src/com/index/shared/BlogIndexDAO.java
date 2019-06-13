package com.index.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.index.client.Blog;
import com.index.client.Comment;
import com.index.client.User;

public class BlogIndexDAO {
	/*private static final String SELECT_USER_BLOGS_INFO = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
			+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info`) AS A"
			+ " LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
			+ " LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.Blog = A.BlogId "
			+ " LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId "
			+ " WHERE UserId = ? ORDER BY PublishDateTime DESC";*/
	private static final String SELECT_ALL_HOT_BLOG = "SELECT `Type`,GROUP_CONCAT(BlogId ORDER BY ReadNum DESC) AS AllBlogs FROM `blog_info` GROUP BY `Type` LIMIT 3";

	private static final String SELECT_BLOG_BY_ID = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
			+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info` WHERE BlogId = ?) AS A "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.BlogId = A.BlogId "
			+ "LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId ";
	private static final String SELECT_ALL_COMMENTS = "SELECT * FROM `comments` WHERE ObjectId = ? ORDER BY LikeNum DESC";
	//private static final String SELECT_COMMENT_BY_ID = "SELECT * FROM `comments` WHERE CommentId = ?";
	//private static final String INCREASE_READ_NUM = "UPDATE `blog_info` SET ReadNum = ReadNum + 1 WHERE BlogId = ?";
	public static final BlogIndexDAO instance = new BlogIndexDAO();
	
	private static final ConcurrentHashMap<Integer, Blog> blogSecondDAO = new ConcurrentHashMap<>();
	//private static final Map<Integer, Comment> commentSecondDao = new LinkedHashMap<>();
	private static final int blogCacheSize = 512;
	//private static final int commentCacheSize = 1024;
	
	private BlogIndexDAO() {
	}
	
	/*public List<Blog> getUserAllBlogInfo(int accountId) {
		ArrayList<Blog> allBlogs = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_BLOGS_INFO, new Object[] {accountId});
		try {
			while(rs.next()) {
				int blogId = rs.getInt("BlogId");
				//List<Comment> allComments = getAllCommentById(blogId);
				User user = UserDAO.instance.getUserInfo(rs.getInt("UserId"));
				Blog blog = new Blog(blogId, rs.getDate("PublishDateTime"), user, rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"),
						rs.getInt("ReadNum"), rs.getInt("Type"));
				//blogSecondDAO.putIfAbsent(blogId, blog);
				allBlogs.add(blog);
			}
		} catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyList();
		}
		return allBlogs;
	}*/
	
	public List<Blog> getRecommend(Map<Integer, List<Blog>> data) {
		ArrayList<Blog> blogList = new ArrayList<>();
		for(Entry<Integer, List<Blog>> entry : data.entrySet()) {
			List<Blog> blogs = entry.getValue();
			for(int i=0; i<2 && i<blogs.size(); i++) {
				blogList.add(blogs.get(i));
			}
		}
		blogList.sort(new Comparator<Blog>() {
			@Override
			public int compare(Blog o1, Blog o2) {
				return o2.getReadNum() - o1.getReadNum();
			}
		});
		return blogList;
	}
	
	public Map<Integer, List<Blog>> getAllHotBlogs() {
		Map<Integer, List<Blog>> resMap = new HashMap<>();
		ResultSet rs = null;
		try {
			rs = DBConnection.instance.executeCommand(SELECT_ALL_HOT_BLOG, new Object[] {});
			while(rs.next()) {
				try {
					int type = rs.getInt("Type");
					String allBlogIdStr = rs.getString("AllBlogs");
					String[] allBlogIdStrArr = allBlogIdStr.split(",");
					List<Blog> allBlogs = new ArrayList<>();
					for(String blogIdStr : allBlogIdStrArr) {
						if(allBlogs.size() > 3) {
							break;
						}
						int blogId = Integer.parseInt(blogIdStr);
						Blog blog = getBlogById(blogId);
						if(blogSecondDAO.size() < blogCacheSize) {
							blogSecondDAO.putIfAbsent(blogId, blog);
						}
						allBlogs.add(blog);
					}
					resMap.put(type, allBlogs);
				} catch (Throwable t) {
					t.printStackTrace();
					continue;
				}
			}
			return resMap;
		} catch (Throwable t) {
			t.printStackTrace();
			return Collections.emptyMap();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}
	}
	
	/*public Blog getBlogWithIncreaseReadNum(int blogId) {
		if(hitCache(blogId)) {
			increaseBlogReadNum(blogId);
			return blogSecondDAO.get(blogId);
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_BLOG_BY_ID, new Object[] {blogId});
		if(rs == null) {
			return null;
		}
		try {
			if(rs.next()) {
				//List<Comment> allComments = getAllCommentById(blogId);
				User user = UserDAO.instance.getUserInfo(rs.getInt("UserId"));
				Blog blog = new Blog(blogId, rs.getDate("PublishDateTime"), user, rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"), 
						rs.getInt("ReadNum"), rs.getInt("Type"));
				increaseBlogReadNum(blogId, blog);
				return blog;
			}
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}*/
	
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
				//List<Comment> allComments = getAllCommentById(blogId);
				User user = UserIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
				Blog blog = new Blog(blogId, rs.getDate("PublishDateTime"), user, rs.getString("Title"),
						rs.getString("Content"), rs.getInt("CommentsNum"), rs.getInt("TransfersNum"), rs.getInt("CollectsNum"), 
						rs.getInt("ReadNum"), rs.getInt("Type"));
				return blog;
			}
			return null;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/*public int increaseBlogReadNum(int blogId, Blog blog) {//����û�е����
		int res = DBConnection.instance.executeQuery(INCREASE_READ_NUM, new Object[] {blogId});
		if(blogSecondDAO.size() < blogCacheSize) {
			blogSecondDAO.putIfAbsent(blogId, blog);
			blog.increaseReadNum();
		}
		return res;
	}*/
	
	/*public int increaseBlogReadNum(int blogId) {
		int res = DBConnection.instance.executeQuery(INCREASE_READ_NUM, new Object[] {blogId});
		if(CommonHelper.instance.isSqlExecuteSucc(res)) {
			Blog blog = blogSecondDAO.get(blogId);
			blog.increaseReadNum();
		}
		return res;
	}*/
	
	/*public boolean isBlog(int objectId) {
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
	*/
	/*public void removeCommentByIdFromSecondDAO(int commentId) {
		commentSecondDao.remove(commentId);
	}*/
	
	public List<Comment> getAllCommentById(int objectId) {
		ArrayList<Comment> allComments = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COMMENTS, new Object[] {objectId});
		try {
			while(rs.next()) {
				try {
					List<Comment> allComment = getAllCommentById(objectId);
					User user = UserIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
					Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), user, rs.getDate("CommentDateTime"), rs.getString("Content"), allComment);
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
	
	/*public Comment getCommentById(int commentId) {
		if(commentSecondDao.containsKey(commentId)) {
			return commentSecondDao.get(commentId);
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_COMMENT_BY_ID, new Object[] {commentId});
		try {
			List<Comment> allComments = getAllCommentById(commentId);
			User user = UserIndexDAO.instance.getUserInfo(rs.getInt("UserId"));
			Comment comment = new Comment(rs.getInt("CommentId"), rs.getInt("ObjectId"), user, rs.getDate("CommentDateTime"), rs.getString("Content"), allComments);
			return comment;
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}*/
	
	public boolean hitCache(int blogId) {
		return blogSecondDAO.contains(blogId);
	}
	
	/*public void reloadCommentByBlogId(Blog blog) {
		List<Comment> allComments = getAllCommentById(blog.getBlogId());
		blog.setAllComments(allComments);
	}*/
}
