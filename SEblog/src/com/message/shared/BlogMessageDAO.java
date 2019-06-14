package com.message.shared;

import java.sql.ResultSet;

import com.message.client.Blog;
import com.message.client.User;

public class BlogMessageDAO {
	
	private static final String SELECT_BLOG_BY_ID = "SELECT A.BlogId,PublishDateTime,UserId,Title,Content,CollectsNum,TransfersNum,CommentsNum,ReadNum,Type FROM "
			+ "(SELECT BlogId,PublishDateTime,UserId,Title,Content,ReadNum,Type FROM `blog_info` WHERE BlogId = ?) AS A "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS TransfersNum FROM user_blog_relation WHERE `Type` = 2 OR `Type` = 3 GROUP BY BlogId) AS B ON A.BlogId = B.BlogId "
			+ "LEFT JOIN (SELECT BlogId,COUNT(*) AS CollectsNum FROM user_blog_relation WHERE `Type` = 1 OR `Type` =3 GROUP BY BlogId) AS C ON C.BlogId = A.BlogId "
			+ "LEFT JOIN (SELECT ObjectId,COUNT(*) AS CommentsNum FROM comments GROUP BY ObjectId) AS D ON D.ObjectId = A.BlogId "
			+ "ORDER BY PublishDateTime DESC";

	public static final BlogMessageDAO instance = new BlogMessageDAO();
	
	public Blog getBlogById(int blogId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_BLOG_BY_ID, new Object[] {blogId});
		if(rs == null) {
			return null;
		}
		try {
			if(rs.next()) {
				User user = UserMessageDAO.instance.getUserInfo(rs.getInt("UserId"));
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
}
