package com.publish.shared;

import java.util.HashMap;
import java.util.Map;

public enum ResultConst {
	 SUCCESS(0, "执行成功"),   
	 ACCOUNT_HAS_BEEN_REGISTERED(1, "帐号已被注册"), //帐号已被注册
	 TOW_PASSWORD_IS_DIFFERENT(2, "两次输入的密码不一致"),  //两次输入的密码不一致
	 REGISTER_ERROR(3, "注册异常"),   //注册异常
	 ACCOUNT_NOT_EXIST(4, "帐号不存在"),  //帐号不存在
	 PASSWORD_ERROR(5, "密码输入错误"),   //密码输入错误
	 CAN_NOT_DELETE_COMMENT(6, "无法删除评论"),  //无法删除评论
	 COMMENT_NOT_EXIST(7, "评论不存在"),   //评论不存在
	 PARAMS_ERROR(8, "参数错误"),  //参数错误
	 BLOG_NOT_EXIST(9, "博客不存在"),  //博客不存在
	 EXECUTE_SQL_ERROR(10, "SQL执行错误"),  //SQL执行错误
	 HAS_COLLECT_OR_TRANSFER_BLOG(11, "该博客已经收藏或转载过"), //该博客已经收藏或转载过
	 HAS_NO_RELATION_WITH_BLOG(12, "未收藏或转载过该博客"),   //未收藏或转载过该博客
	 ACCOUNT_HAS_BEEN_FROZEN(13, "帐号已经被冻结"),    //帐号已经被冻结
	 MANAGER_ACCOUNT_NOT_EXIST(14, "管理员帐号不存在"),  //管理员帐号不存在
	 CLICK_LIKE_ERROR(15, "点赞异常"),  //点赞异常
	 CANCLE_LIKE_ERROR(16, "取消赞异常"),  //取消赞异常
	 CACLE_RELATION_BLOG_ERROR(17, "取消收藏或转发错误"),
	 LOGIN_ERROR(18, "登陆异常"),
	 CAN_NOT_DELETE_BLOG(19, "不能删除该博客"),
	;
	
	private int id;
	private String desc;
	private static Map<Integer, ResultConst> allRs = new HashMap<>();
	static {
		for(ResultConst rs : ResultConst.values()) {
			allRs.put(rs.getId(), rs);
		}
	}
	
	public static ResultConst getRsById(int id) {
		return allRs.get(id);
	}
	
	ResultConst(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}
	
	public int getId() {
		return id;
	}
	
	public String getDescribe() {
		return desc;
	}
	
	public String toString() {
		return desc;
	}
}
