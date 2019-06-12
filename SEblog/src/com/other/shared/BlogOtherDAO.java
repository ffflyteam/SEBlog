package com.other.shared;

import java.sql.ResultSet;
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
}
