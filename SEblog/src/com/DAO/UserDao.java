package com.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
	//�û���Ϣ
	private static final String INSERT_USER_INFO = "INSERT INTO `user_info` VALUES(?,?,?,?,?,?,0)";
	private static final String SELECT_USER_INFO_BY_ID = "SELECT * FROM `user_info` WHERE UserId = ?";
	private static final String SELECT_USER_STAT_BY_ID = "SELECT `Stat` FROM `user_info` WHERE UserId = ?";
	private static final String SELECT_USER_INFO_BY_ID_AND_PASSWORD = "SELECT * FROM `user_info` WHERE UserId = ? AND Password = ?";
	private static final String UPDATE_USER_INFO = "UPDATE `user_info` SET PassWord = ?,UserName = ?,Sex = ?,BirthDay = ?,Address = ? WHERE UserId = ?";
	//�û��ղػ�ת�ز���
	private static final String SELECT_USER_AND_BLOG_RELATION = "SELECT `Type` FROM `user_blog_relation` WHERE UserId = ? AND BlogId = ?";
	private static final String SELECT_ALL_COLLECT_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY CollectTime DESC";
	private static final String SELECT_ALL_TRANSFER_BLOGS = "SELECT `BlogId` FROM `user_blog_relation` WHERE UserId = ? AND (`Type` = ? OR `Type` = 3) ORDER BY TransferTime DESC";
	private static final String INSERT_USER_AND_BLOG_RELATION_FOR_COLLECT = "INSERT INTO `user_blog_relation` VALUES(?,?,?,?,null)";
	private static final String INSERT_USER_AND_BLOG_RELATION_FOR_TRANSFER = "INSERT INTO `user_blog_relation` VALUES(?,?,?,null,?)";
	private static final String UPDATE_RELATION_WITH_BLOG_FOR_COLLECT = "UPDATE `user_blog_relation` SET `Type` = ?,CollectTime = ? WHERE UserId = ? AND BlogId = ?";
	private static final String UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER = "UPDATE `user_blog_relation` SET `Type` = ?,TransferTime = ? WHERE UserId = ? AND BlogId = ?";
	//�û��Լ��Ĳ���
	private static final String INSERT_BLOG_INFO = "INSERT INTO `blog_info` VALUES(0,?,?,?,?,0,0,0,?)";
	private static final String DELETE_BLOG = "DELETE FROM `blog_info` WHERE BlogId = ? AND UserId = ?";
	private static final String CHANGE_BLOG_TYPE = "UPDATE `blog_info` SET `Type` = ? WHERE `BlogId` = ?";
	private static final String DELETE_USER_BLOG_RELATION = "DELETE FROM user_blog_relation WHERE BlogId = ?";
	//��������
	private static final String INSERT_COMMENT_INFO = "INSERT INTO `comments` VALUES(0,?,?,?,?,0)";
	private static final String DELETE_COMMENT_BY_ID_USERID = "DELETE FROM `comments` WHERE CommentId = ? AND UserId = ?";
	private static final String DELETE_COMMENT = "DELETE FROM `comments` WHERE CommentId = ?";
	private static final String DELETE_ALL_COMMENTS_BY_OBJECTID = "DELETE FROM `comments` WHERE ObjectId = ?";
	//���۵��޻�ȡ����
	private static final String CLICK_LIKE = "INSERT INTO `user_comment_like` VALUES(?,?)";
	private static final String CANCLE_LIKE = "DELETE FROM `user_comment_like` WHERE CommentId = ? AND UserId = ?";
	private static final String INCREASE_LIKE_NUM = "UPDATE `comments` SET LikeNum = VALUES(LikeNum) + 1 WHERE CommentId = ?";
	private static final String DECREASE_LIKE_NUM = "UPDATE `comments` SET LikeNum = VALUES(LikeNum) - 1 WHERE CommentId = ?";
	private static final String DELECT_COMMENT_LIKE = "DELETE FROM user_comment_like WHERE CommentId = ?";
	//�û���ϵ
	private static final String INSERT_USER_RELATION = "INSERT INTO `user_relation` VALUES(?,?,?) ON DUPLICATE KEY UPDATE Type = ?";
	private static final String SELECT_USER_RELATIONS = "SELECT * FROM `user_relation` WHERE UserId = ? AND Type <> 0";
	private static final String CHANGE_USER_RELATION = "UPDATE `user_relation` SET `Type` = 0 WHERE UserId = ? AND OtherId = ? AND Type = ?";
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
	public static final UserDao instance = new UserDao();
	
	private UserDao() {
	}
	
	//��ȡ�û��Լ���Ϣ
	public User getUserInfo(int userId) throws SQLException {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {userId});
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
		return null;
	}
	
	//�û�ע��
	public int register(int accountId, String password, String userName, short sex, Date birthDay, String address) throws SQLException {
		if(isUserRegistered(accountId)) {
			return ResultConst.ACCOUNT_HAS_BEEN_REGISTERED.getId();
		} 
		Object[] params = new Object[] {accountId, password, userName, sex, birthDay, address};
		int res = DBConnection.instance.executeQuery(INSERT_USER_INFO, params);
		return !CommonHelper.instance.isSqlExecuteSucc(res) ? ResultConst.REGISTER_ERROR.getId() : ResultConst.SUCCESS.getId();
	}
	
	//�û���¼
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
	}
	
	//�޸�����
	public boolean setUserInfo(int accountId, String password, String userName, short sex, Date birthDay, String address) {
		int result = DBConnection.instance.executeQuery(UPDATE_USER_INFO, new Object[] {password, userName, sex, birthDay, address, accountId});
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	//��������
	public int makeBlog(int accountId, String title, String content, int type) {
		Object[] params = new Object[] {new Date(),accountId, title, content, type};
		int res = DBConnection.instance.executeQuery(INSERT_BLOG_INFO, params);
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	//ɾ������
	public int deleteBlog(int blogId, int accountId) {
		Object[] params = new Object[] {blogId};
		try {
			List<Comment> allComments = BlogDAO.instance.getAllCommentById(blogId);
			for(Comment comment : allComments) {			//ɾ�����۵����ۣ���������
				deleteComment(comment.getCommentId());
			}
		} catch (Throwable t) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		int rs = DBConnection.instance.executeQuery(DELETE_ALL_COMMENTS_BY_OBJECTID, params);  //ɾ�����͵�����
		if(!CommonHelper.instance.isSqlExecuteSucc(rs)) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		rs = DBConnection.instance.executeQuery(DELETE_USER_BLOG_RELATION, params);  //ɾ���û��Ͳ��͵Ĺ�ϵ
		if(!CommonHelper.instance.isSqlExecuteSucc(rs)) {
			return ResultConst.CACLE_RELATION_BLOG_ERROR.getId();
		}
		rs = DBConnection.instance.executeQuery(DELETE_BLOG, new Object[] {blogId, accountId});  //ɾ������
		return CommonHelper.instance.getSqlExecuteResultConst(rs);
	}
	
	//�޸Ĳ��ͷ���
	public boolean changeBlogType(int blogId, int type) {
		int result = DBConnection.instance.executeQuery(CHANGE_BLOG_TYPE, new Object[] {type, blogId});
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	//��ȡ�ղص����в���
	public List<Blog> getAllCollectBlog(int accountId) {
		ArrayList<Blog> allCollectBlogList = new ArrayList<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_ALL_COLLECT_BLOGS, new Object[] {accountId, RelationWithBlog.COLLECT.getId()});
		try {
			while(rs.next()) {
				try {
					int blogId = rs.getInt("BlogId");
					Blog blog = BlogDAO.instance.getBlogById(blogId);
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
	
	//��ȡת�ص����в���
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
					Blog blog = BlogDAO.instance.getBlogById(blogId);
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
	
	//����
	public int makeComment(int objectId, int userId, String content) {
		String dateStr = sdf.format(new Date());
		int res = DBConnection.instance.executeQuery(INSERT_COMMENT_INFO, new Object[] {userId, objectId, dateStr, content});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	
	//ɾ�����ۣ�ֻ��ɾ���Լ������µ����ۣ�����˲������Լ�������
	public int deleteComment(int commentId, int accountId, int objectId) {
		Blog blog = BlogDAO.instance.getBlogById(objectId);
		if(blog == null) {
			Comment comment = BlogDAO.instance.getCommentById(commentId);
			if(comment == null) {
				return ResultConst.COMMENT_NOT_EXIST.getId();
			}
			if(comment.getAccountId() != accountId) {
				return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
			}
			return deleteComment(commentId) ? ResultConst.SUCCESS.getId() : ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		if(blog.getUserId() != accountId) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		if(!deleteComment(commentId)) {
			return ResultConst.CAN_NOT_DELETE_COMMENT.getId();
		}
		int result = DBConnection.instance.executeQuery(DELETE_COMMENT_BY_ID_USERID, new Object[] {commentId, accountId});
		return result;
	}
	
	public boolean deleteComment(int commentId) {
		List<Comment> allComments = BlogDAO.instance.getAllCommentById(commentId);
		if(!allComments.isEmpty()) {
			for(Comment comment : allComments) {
				deleteComment(comment.getCommentId());//�ݹ�
			}
		}
		Object[] arr = new Object[] {commentId};
		DBConnection.instance.executeQuery(DELECT_COMMENT_LIKE, arr);
		int result = DBConnection.instance.executeQuery(DELETE_COMMENT, arr);
		return CommonHelper.instance.isSqlExecuteSucc(result);
	}
	
	//�ղػ�ת�ز���,�ղ�type=1��ת��type=2,�����RelationWithBlog
	public int collectOrTransferBlog(int blogId, int accountId, int type) {
		if(type != RelationWithBlog.COLLECT.getId() && type != RelationWithBlog.TRANSFER.getId()) {
			return ResultConst.PARAMS_ERROR.getId();
		}
		Blog blog = BlogDAO.instance.getBlogById(blogId);
		if(blog == null) {
			return ResultConst.BLOG_NOT_EXIST.getId();
		}
		String dateStr = sdf.format(new Date());
		try {
			ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_AND_BLOG_RELATION, new Object[] {accountId, blogId});
			if(rs.next()) {//|| (rs.getInt("Type") != 1 && rs.getInt("Type") != 2)
				int typeId = rs.getInt("Type");
				if(typeId == type || typeId == RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId()) {  //3Ϊ��ת�����ղر�־
					return ResultConst.HAS_COLLECT_OR_TRANSFER_BLOG.getId();
				}
				int flag = typeId == RelationWithBlog.NO_RELATION.getId() ? type : RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId();
				String sql;
				if(type == RelationWithBlog.COLLECT.getId()) {
					sql = UPDATE_RELATION_WITH_BLOG_FOR_COLLECT;
				} else {
					sql = UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER; 
				}
				int res = DBConnection.instance.executeQuery(sql, new Object[] {flag, dateStr, accountId, blogId});
				return CommonHelper.instance.getSqlExecuteResultConst(res);
			}
			String sql = type == RelationWithBlog.COLLECT.getId() ? INSERT_USER_AND_BLOG_RELATION_FOR_COLLECT : INSERT_USER_AND_BLOG_RELATION_FOR_TRANSFER;
			int res = DBConnection.instance.executeQuery(sql, new Object[] {accountId,blogId,type,dateStr});
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		} catch (Throwable t) {
			return ResultConst.EXECUTE_SQL_ERROR.getId();
		}
	}
	
	//ȡ���ղػ�ת��
	public int cancleCollectOrTransfer(int blogId, int accountId, int type) {
		if(type != RelationWithBlog.COLLECT.getId() && type != RelationWithBlog.TRANSFER.getId()) {
			return ResultConst.PARAMS_ERROR.getId();
		}
		Blog blog = BlogDAO.instance.getBlogById(blogId);
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
			String sql;
			if(type == RelationWithBlog.COLLECT.getId()) {
				sql = UPDATE_RELATION_WITH_BLOG_FOR_COLLECT;
			} else {
				sql = UPDATE_RELATION_WITH_BLOG_FOR_TRANSFER; 
			}
			if(typeId == RelationWithBlog.BOTH_COLLECT_AND_TRANSFER.getId()) {
				flag = type ^ typeId;
				int res = DBConnection.instance.executeQuery(sql, new Object[] {flag, null, accountId, blogId});
				return CommonHelper.instance.getSqlExecuteResultConst(res);
			}
			if(typeId != type) {
				return ResultConst.HAS_NO_RELATION_WITH_BLOG.getId();
			}
			int res = DBConnection.instance.executeQuery(sql, new Object[] {RelationWithBlog.NO_RELATION.getId(), null, accountId, blogId});
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		} catch (Throwable t) {
			return ResultConst.EXECUTE_SQL_ERROR.getId();
		}
	}
	
	//�����۵���
	public int clickLike(int accountId, int commentId) {
		int res = DBConnection.instance.executeQuery(CLICK_LIKE, new Object[] {commentId,accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(res)) {
			res = DBConnection.instance.executeQuery(INCREASE_LIKE_NUM, new Object[] {commentId});
			return CommonHelper.instance.getSqlExecuteResultConst(res);
		}
		return ResultConst.CLICK_LIKE_ERROR.getId();
	}
	//ȡ��֮ǰ�����
	public int cancleLike(int accountId, int commentId) {
		int result = DBConnection.instance.executeQuery(CANCLE_LIKE, new Object[] {commentId,accountId});
		if(CommonHelper.instance.isSqlExecuteSucc(result)) {
			result = DBConnection.instance.executeQuery(DECREASE_LIKE_NUM, new Object[] {commentId});
			return CommonHelper.instance.getSqlExecuteResultConst(result);
		}
		return ResultConst.CANCLE_LIKE_ERROR.getId();
	}
	
	//�����˵Ĺ�ϵ
	public int happenRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(INSERT_USER_RELATION, new Object[] {accountId,otherId,type,type});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	//ȡ�������˵Ĺ�ϵ
	public int cancleRelation(int accountId, int otherId, int type) {
		int res = DBConnection.instance.executeQuery(CHANGE_USER_RELATION, new Object[] {accountId,otherId,type});
		return CommonHelper.instance.getSqlExecuteResultConst(res);
	}
	//��ȡ�����й�ϵ���û�
	public Map<Integer, Integer> getAllRelationWithOthers(int accountId) {
		Map<Integer, Integer> resMap = new HashMap<>();
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_RELATIONS, new Object[] {accountId});
		try{
			while(rs.next()) {
				try {
					int otherId = rs.getInt("OtherId");
					int type = rs.getInt("Type");
					resMap.put(otherId, type);
				} catch (Throwable t) {
					return Collections.emptyMap();
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return resMap;
	}
	
	public boolean isUserRegistered(int accountId) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID, new Object[] {accountId});
		try {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean isPasswordCorrect(int accountId, String password) {
		ResultSet rs = DBConnection.instance.executeCommand(SELECT_USER_INFO_BY_ID_AND_PASSWORD, new Object[] {accountId, password});
		try {
			return rs.next();
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
	}
	
}
