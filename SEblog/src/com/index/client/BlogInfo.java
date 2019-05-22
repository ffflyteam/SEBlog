package com.index.client;

import com.google.gwt.user.client.rpc.IsSerializable;

/*
* 单条博客数据
* {
* 	"username" : 用户名，
*  "userurl" : 用户头像路径，
*  "type" : 文章类型，
*  "time" : 发表时间，
*  "read" : 阅读数，
*  "title" : 文章标题，
*  "titleId" : 文章id（用于访问文章详情），
*  "summary" : 文章摘要
* }
*/
public class BlogInfo implements IsSerializable {
	public String username;
	public String userurl;
}
