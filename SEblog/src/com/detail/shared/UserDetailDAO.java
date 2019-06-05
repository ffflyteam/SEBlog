package com.detail.shared;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.detail.client.Blog;
import com.detail.client.Comment;
import com.detail.client.User;

public class UserDetailDAO {
	//用户信息
	//private static final String INSERT_USER_INFO = "INSERT INTO `user_info` VALUES(?,?,?,?,?,?,0)";
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	//private static final String SELECT_USER_STAT_BY_ID = "SELECT `Stat` FROM `user_info` WHERE UserId = ?";
	//private static final String SELECT_USER_INFO_BY_ID_AND_PASSWORD = "SELECT * FROM `user_info` WHERE UserId = ? AND Password = ?";
	//private static final String UPDATE_USER_INFO = "UPDATE `user_info` SET PassWord = ?,UserName = ?,Sex = ?,BirthDay = ?,Address = ? WHERE UserId = ?";
	//用户收藏或转载博客
	private static final String SELECT_USER_AND_BLOG_RELATION = "SELECT `Type` FROM `user_blog_relation` WHERE UserId = ? AND BlogId = ?";
	//private static final String SELECT_ALL_COLLECT_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY CollectTime DESC";
	//private static final String SELECT_ALL_TRANSFER_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY TransferTime DESC";
	private static final String INSERT_USER_AND_BLOG_RELATION_FOR_COLLECT = "INSERT INTO `user_blog_relation` VALUES(?,?,?,?,null)";
	private static final String INSERT_USER_AND_BLOG_RELATION_FOR_TRANSFER = "INSERT INTO `user_blog_relation` VALUES(?,?,?,null,?)";
	private static final String UPDATE_RELATION_WITH_BLOG_FOR_COLLECT = "UPDATE `user_blog_relation` SET `Type` = ?,CollectTime = ? WHERE UserId = ? AND BlogId = ?";
	private static final String UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER = "UPDATE `user_blog_relation` SET `Type` = ?,TransferTime = ? WHERE UserId = ? AND BlogId = ?";
	//用户自己的博客
	private static final String INSERT_BLOG_INFO = "INSERT INTO `blog_info` VALUES(0,?,?,?,?,0,0,0,?)";
	//private static final String DELETE_BLOG = "DELETE FROM `blog_info` WHERE BlogId = ? AND UserId = ?";
	//private static final String CHANGE_BLOG_TYPE = "UPDATE `blog_info` SET `Type` = ? WHERE `BlogId` = ?";
	//private static final String DELETE_USER_BLOG_RELATION = "DELETE FROM user_blog_relation WHERE BlogId = ?";
	//private static final String SELECT_ALL_BLOG = "SELECT `BlogId` FROM blog_info WHERE UserId = ? ORDER BY PublishDateTime DESC";
	//博客评论
	private static final String INSERT_COMMENT_INFO = "INSERT INTO `comments` VALUES(0,?,?,?,?,0)";
	private static final String DELETE_COMMENT_BY_ID_USERID = "DELETE FROM `comments` WHERE CommentId = ? AND UserId = ?";
	private static final String DELETE_COMMENT = "DELETE FROM `comments` WHERE CommentId = ?";
	//private static final String DELETE_ALL_COMMENTS_BY_OBJECTID = "DELETE FROM `comments` WHERE ObjectId = ?";
	//评论点赞或取消赞
	private static final String CLICK_LIKE = "INSERT INTO `user_comment_like` VALUES(?,?)";
	private static final String CANCLE_LIKE = "DELETE FROM `user_comment_like` WHERE CommentId = ? AND UserId = ?";
	private static final String INCREASE_LIKE_NUM = "UPDATE `comments` SET LikeNum = LikeNum + 1 WHERE CommentId = ?";
	private static final String DECREASE_LIKE_NUM = "UPDATE `comments` SET LikeNum = LikeNum - 1 WHERE CommentId = ?";
	private static final String DELECT_COMMENT_LIKE = "DELETE FROM user_comment_like WHERE CommentId = ?";
	//用户关系
	private static final String INSERT_USER_RELATION = "INSERT INTO `user_relation` VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE Type = ?";
	//private static final String SELECT_USER_RELATIONS = "SELECT * FROM `user_relation` WHERE UserId = ? AND Type <> 0 ORDER BY CreateTime DESC";
	private static final String CHANGE_USER_RELATION = "UPDATE `user_relation` SET `Type` = 0 WHERE UserId = ? AND OtherId = ? AND Type = ?";
	private static final String SELECT_USER_RELATION = "SELECT * FROM `user_relation` WHERE UserId = ? AND OtherId = ?";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
	public static final UserDetailDAO instance = new UserDetailDAO();
	
	private static final Map<Integer, User> userSecondDao = new LinkedHashMap<>();//可改成Redis
	private static final int userCacheSize = 64;
	
	private UserDetailDAO() {
	}
	
	//获取用户自己信息
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
	
	//用户注册
	/*public int register(int accountId, String password, String userName, short sex, Date birthDay, String address) throws SQLException {
		if(isUserRegistered(accountId)) {
			return ResultConst.ACCOUNT_HAS_BEEN_REGISTERED.getId();
		} 
		Object[] params = new Object[] {accountId, password, userName, sex, birthDay, address};
		int res = DBConnection.instance.executeQuery(INSERT_USER_INFO, params);
		return !CommonHelper.instance.isSqlExecuteSucc(res) ? ResultConst.REGISTER_ERROR.getId() : ResultConst.SUCCESS.getId();
	}
	
	//用户登录
	public int login(int accountId, String password) {
		if(!isUserRegistered(accountId)) {
			return ResultConst.ACCOUNT_NOT_EXIST.getId();
		}
		if(!isPasswordCorrect(accountId, password)) {
			return ResultConst.PASSWORD_ERROR.getId();
		}
		if(!userStatIsNormal(accountId)) {
			return ResultConst.ACCOUNT_HAS_BEEN_FROZEN.getId();
		}
		return ResultConst.SUCCESS.getId();
	}*/
	
	/*//修改资料
	public boolean setUserInfo(int accountId, String password, String userName, short sex, String birthDayStr, String address) {
		Date birthDay;
		try {
			birthDay = sdf.parse(birthDayStr);
		} catch (ParseException e1) {
			e1.printStackTrace();
			birthDay = new Date();
		}
		int result = DBConnection.instance.executeQuery(UPDATE_USER_INFO, new Object[] {password, userName, sex, birthDay, address, accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(result)) {
			User user = userSecondDao.get(accountId);
			if(user != null) {
				user.setPassword(password);
				user.setSex(sex);
				user.setBirthDay(birthDay);
				user.setAddress(address);
			} else {
				ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {accountId});
				try {
					if(rs.next()) {
						user = new User(rs.getInt("UserId"), rs.getString("PassWord"), rs.getString("UserName"), 
								rs.getShort("Sex"), rs.getDate("BirthDay"), rs.getString("Address"), rs.getInt("Stat"));
						userSecondDao.putIfAbsent(accountId, user);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		return false;
	}*/
	
	//创建博客
	public int makeBlog(int accountId, String title, String content, int type) {
		Object[] params = new Object[] {new Date(),accountId, title, content, type};
		int res = DBConnection.instance.executeQuery(INSERT_BLOG_INFO, params);
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	/*//删除博客
	public int deleteBlog(int blogId, int accountId) {
		Object[] params = new Object[] {blogId};
		try {
			List<Comment> allComments = BlogDetailDAO.instance.getAllCommentById(blogId);
			for(Comment comment : allComments) {			//删除评论的评论，及点赞数
				deleteComment(comment.getCommentId());
			}
		} catch (Throwable t) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		int rs = DBConnection.instance.executeQuery(DELETE_ALL_COMMENTS_BY_OBJECTID, params);  //删除博客的评论
		if(!CommonHelper.instance.isSqlExecuteSucc(rs)) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		rs = DBConnection.instance.executeQuery(DELETE_USER_BLOG_RELATION, params);  //删除用户和博客的关系
		if(!CommonHelper.instance.isSqlExecuteSucc(rs)) {
			return ResultConst.CACLE_RELATION_BLOG_ERROR.getId();
		}
		rs = DBConnection.instance.executeQuery(DELETE_BLOG, new Object[] {blogId, accountId});  //删除博客
		BlogDetailDAO.instance.removeBlogByIdFromSecondDAO(blogId);
		return CommonHelper.instance.getSqlExecuteResultConst(rs);
	}*/
	
	/*//获取自己的所有博客
	public List<Blog> getAllBlog(int accountId) {
		ArrayList<Blog> allBlogList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_BLOG, new Object[] {accountId});
		try {
			while(rs.next()) {
				try {
					int blogId = rs.getInt("BlogId");
					Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
					if(blog == null) {
						continue;
					}
					allBlogList.add(blog);
				} catch (Throwable t) {
					return Collections.emptyList();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return allBlogList;
	}
	*/
	/*//修改博客分类
	public boolean changeBlogType(int blogId, int type) {
		Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
		if(blog != null) {
			blog.setType(type);
		}
		int result = DBConnection.instance.executeQuery(CHANGE_BLOG_TYPE, new Object[] {type, blogId});
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	//获取收藏的所有博客
	public List<Blog> getAllCollectBlog(int accountId) {
		ArrayList<Blog> allCollectBlogList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COLLECT_BLOGS, new Object[] {accountId, RelationWithBlog.COLLECT.getId()});
		try {
			while(rs.next()) {
				try {
					int blogId = rs.getInt("BlogId");
					Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
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
	
	//获取转载的所有博客
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
					Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
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
	*/
	//评论
	public int makeComment(int objectId, int userId, String content) {
		String dateStr = sdf.format(new Date());
		int res = DBConnection.instance.executeQuery(INSERT_COMMENT_INFO, new Object[] {userId, objectId, dateStr, content});
		/*if(CommonHelper.instance.isSqlExecuteSucc(res) && BlogDAO.instance.isBlog(objectId) && BlogDAO.instance.hitCache(objectId)) {
			Blog blog = BlogDAO.instance.getBlogById(objectId);
			//BlogDAO.instance.reloadCommentByBlogId(blog);
		}*/
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	//删除评论，只能删除自己博客下的评论，或别人博客下自己的评论
	public int deleteComment(int commentId, int accountId, int objectId) {
		Blog blog = BlogDetailDAO.instance.getBlogById(objectId);
		if(blog == null) {
			Comment comment = BlogDetailDAO.instance.getCommentById(commentId);
			if(comment == null) {
				return ResultConst.COMMENT_NOT_EXIST.getId();
			}
			if(comment.getUser().getAccountId() != accountId) {
				return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
			}
			return deleteComment(commentId) ? ResultConst.SUCCESS.getId() : ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		if(blog.getUser().getAccountId() != accountId) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		if(!deleteComment(commentId)) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		int result = DBConnection.instance.executeQuery(DELETE_COMMENT_BY_ID_USERID, new Object[] {commentId, accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(result) && BlogDetailDAO.instance.hitCache(objectId)) {
			blog.decreaseCommentsNum();
			//blog.removeCommentById(commentId);
		}
		return result;
	}
	
	public boolean deleteComment(int commentId) {
		List<Comment> allComments = BlogDetailDAO.instance.getAllCommentById(commentId);
		if(!allComments.isEmpty()) {
			for(Comment comment : allComments) {
				deleteComment(comment.getCommentId());//递归
			}
		}
		//BlogDAO.instance.removeCommentByIdFromSecondDAO(commentId);
		Object[] arr = new Object[] {commentId};
		DBConnection.instance.executeQuery(DELECT_COMMENT_LIKE, arr);
		int result = DBConnection.instance.executeQuery(DELETE_COMMENT, arr);
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	//收藏或转载博客,收藏type=1，转载type=2,详情见RelationWithBlog
	public int collectOrTransferBlog(int blogId, int accountId, int type) {
		if(type != RelationWithBlog.COLLECT.getId() && type != RelationWithBlog.TRANSFER.getId()) {
			return ResultConst.PARAMS_ERROR.getId();
		}
		Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
		if(blog == null) {
			return ResultConst.BLOG_NOT_EXIST.getId();
		}
		String dateStr = sdf.format(new Date());
		try {
			ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_AND_BLOG_RELATION, new Object[] {accountId, blogId});
			String sql;
			if(rs.next()) {//|| (rs.getInt("Type") != 1 && rs.getInt("Type") != 2)
				int typeId = rs.getInt("Type");
				if(typeId == type || typeId == RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId()) {  //3为即转载又收藏标志
					return ResultConst.HAS_COLLECT_OR_TRANSFER_BLOG.getId();
				}
				if(type == RelationWithBlog.COLLECT.getId() && BlogDetailDAO.instance.hitCache(blogId)) {
					blog.increaseCollectsNum();
				}
				if(type == RelationWithBlog.TRANSFER.getId() && BlogDetailDAO.instance.hitCache(blogId)) {
					blog.increaseTransferNum();
				}
				int flag = typeId == RelationWithBlog.NO_RELATION.getId() ? type : RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId();
				sql = type == RelationWithBlog.COLLECT.getId() ? UPDATE_RELATION_WITH_BLOG_FOR_COLLECT : UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER;
				int res = DBConnection.instance.executeQuery(sql, new Object[] {flag, dateStr, accountId, blogId});
				return CommonHelper.instance.getSqlExecuteResultConst(res);
			}
			sql = type == RelationWithBlog.COLLECT.getId() ? INSERT_USER_AND_BLOG_RELATION_FOR_COLLECT : INSERT_USER_AND_BLOG_RELATION_FOR_TRANSFER;
			int res = DBConnection.instance.executeQuery(sql, new Object[] {accountId,blogId,type,dateStr});
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		} catch (Throwable t) {
			return ResultConst.EXECUTE_SQL_ERROR.getId();
		}
	}
	
	public boolean isCollected(int accountId, int blogId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_AND_BLOG_RELATION, new Object[] {accountId, blogId});
		try {
			if(rs.next()) {
				int typeId = rs.getInt("Type");
				return typeId == RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId() || typeId == RelationWithBlog.COLLECT.getId();
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	//取消收藏或转载
	public int cancleCollectOrTransfer(int blogId, int accountId, int type) {
		if(type != RelationWithBlog.COLLECT.getId() && type != RelationWithBlog.TRANSFER.getId()) {
			return ResultConst.PARAMS_ERROR.getId();
		}
		Blog blog = BlogDetailDAO.instance.getBlogById(blogId);
		if(blog == null) {
			return ResultConst.BLOG_NOT_EXIST.getId();
		}
		try {
			ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_AND_BLOG_RELATION, new Object[] {accountId, blogId});
			if(!rs.next()) {
				return ResultConst.HAS_NO_RELATION_WITH_BLOG.getId();
			}
			int typeId = rs.getInt("Type");
			int flag;
			int res;
			String sql = type == RelationWithBlog.COLLECT.getId() ? UPDATE_RELATION_WITH_BLOG_FOR_COLLECT : UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER;
			if(typeId != type) {
				return ResultConst.HAS_NO_RELATION_WITH_BLOG.getId();
			}
			if(typeId == RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId()) {
				flag = type ^ typeId;
				res = DBConnection.instance.executeQuery(sql, new Object[] {flag, null, accountId, blogId});
			} else {
				res = DBConnection.instance.executeQuery(sql, new Object[] {RelationWithBlog.NO_RELATION.getId(), null, accountId, blogId});
			}
			if(CommonHelper.instance.isSqlExecuteSucc(res)) {
				if(type == RelationWithBlog.COLLECT.getId() && BlogDetailDAO.instance.hitCache(blogId)) {
					blog.decreaseCollectsNum();
				}
				if(type == RelationWithBlog.TRANSFER.getId() && BlogDetailDAO.instance.hitCache(blogId)) {
					blog.decreaseTransferNum();
				}
			}
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		} catch (Throwable t) {
			return ResultConst.EXECUTE_SQL_ERROR.getId();
		}
	}
	
	//给评论点赞
	public int clickLike(int accountId, int commentId) {
		int res = DBConnection.instance.executeQuery(CLICK_LIKE, new Object[] {commentId,accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(res)) {
			res = DBConnection.instance.executeQuery(INCREASE_LIKE_NUM, new Object[] {commentId});
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		}
		return ResultConst.CLICK_LIKE_ERROR.getId();
	}
	
	//取消之前点的赞
	public int cancleLike(int accountId, int commentId) {
		int result = DBConnection.instance.executeQuery(CANCLE_LIKE, new Object[] {commentId,accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(result)) {
			result = DBConnection.instance.executeQuery(DECREASE_LIKE_NUM, new Object[] {commentId});
			return CommonHelper.instance.getSqlExecuteResultConst(result);
		}
		return ResultConst.CANCLE_LIKE_ERROR.getId();
	}
	
	//与他人的关系
	public int happenRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(INSERT_USER_RELATION, new Object[] {accountId,otherId,type,type, new Date()});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	//取消与他人的关系
	public int cancleRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(CHANGE_USER_RELATION, new Object[] {accountId,otherId,type});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	/*//获取所有有关系的用户
	public Map<User, Integer> getAllRelationWithOthers(int accountId) {
		Map<User, Integer> resMap = new LinkedHashMap<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_RELATIONS, new Object[] {accountId});
		try{
			while(rs.next()) {
				try {
					int otherId = rs.getInt("OtherId");
					User otherUser = getUserInfo(otherId);
					int type = rs.getInt("Type");
					resMap.put(otherUser, type);
				} catch (Throwable t) {
					return Collections.emptyMap();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return resMap;
	}*/
	
	public boolean isRelated(int accountId, int otherId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_RELATION, new Object[] {accountId, otherId});
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/*public boolean isUserRegistered(int accountId) {
		if(userSecondDao.containsKey(accountId)) {
			return true;
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {accountId});
		try {
			if(rs.next()) {
				User user = new User(rs.getInt("UserId"), rs.getString("PassWord"), rs.getString("UserName"), 
						rs.getShort("Sex"), rs.getDate("BirthDay"), rs.getString("Address"), rs.getInt("Stat"));
				userSecondDao.putIfAbsent(accountId, user);
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isPasswordCorrect(int accountId, String password) {
		if(userSecondDao.containsKey(accountId)) {
			return userSecondDao.get(accountId).getPassword().equals(password);
		}
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID_AND_PASSWORD, new Object[] {accountId, password});
		try {
			if(rs.next()) {
				User user = new User(rs.getInt("UserId"), rs.getString("PassWord"), rs.getString("UserName"), 
						rs.getShort("Sex"), rs.getDate("BirthDay"), rs.getString("Address"), rs.getInt("Stat"));
				userSecondDao.putIfAbsent(accountId, user);
				return true;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean userStatIsNormal(int accountId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_STAT_BY_ID, new Object[] {accountId});
		try {
			if(rs.next()) {
				int stat = rs.getInt("Stat");
				return stat == UserStat.NORMAL.getId();
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}*/
	
}
