package com.index.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.index.client.ArticleService;

/*
 * 前端传递数据的结构
 * {
 * 	"type" : 文章的类型
 * }
 * 
 * 
 * 整个数据的结构
 * {
 * 	"code" : "ok",
 *  "data" : []  一个数组，有多条博客数据组成
 * }
 * 
 * 
 * 单条博客数据
 * {
 * 	"nickName" : 用户名，
 *  "userurl" : 用户头像路径，
 *  "type" : 文章类型，
 *  "time" : 发表时间，
 *  "read" : 阅读数，
 *  "title" : 文章标题，
 *  "titleId" : 文章id（用于访问文章详情），
 *  "summary" : 文章摘要
 * }
 */


public class ArticleServiceImpl extends RemoteServiceServlet implements ArticleService {

	@Override
	public String articleServer(String input) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		String aString = "{"
				+ "\"code\":\"ok\","
				+ "\"data\" : "
				+ "["
				+ "{\"username\" : \"groot\"," + 
				"\"userurl\" : \"../images/user_default.jpg\"," + 
				"\"type\" : \"历史\"," + 
				"\"time\" : \"2019-09-09\"," + 
				"\"read\" : \"3000\"," + 
				"\"title\" : \"软工课设GWT\"," + 
				"\"titleId\" : \"123\","+
				"\"summary\" : \"文章摘要，体会GWT的魅力\"" + 
				"}"
				+ "]"
				+ "}";
		return aString;
	}
	
}
