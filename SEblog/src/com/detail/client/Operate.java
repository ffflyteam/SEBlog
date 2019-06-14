package com.detail.client;

import java.util.List;

import com.detail.shared.BlogType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Window;

public class Operate {
	
	//获取input标签的值
	public static native String getValue(String id)
	/*-{
	 	var value = $doc.getElementById(id).value;
	 	if(value)
	 		return value;
	 	else
	 		return null;
	 }-*/;
	
	//清空input标签的value
	public static native void cleanValue(String id) 
	/*-{
		$doc.getElementById(id).value = "";
	}-*/;
	
	//根据类名获取元素
		public static native NodeList<Element> getElementsByClassName(String classname)
		/*-{
		 	return $doc.getElementsByClassName(classname);
		 }-*/;
	public static native String getAttr(String id,String attr)
	/*-{
		console.log($doc.getElementById(id));
		var authorId = $doc.getElementById(id).getAttribute(attr);
		return authorId;
	}-*/;
	
	//新增弹框，前提是在页面body标签添加id，值为"body",并将弹框的相关css代码拷贝
	//弹框的html结构如下
	//	<div class="mask"></div>
	//	<div id="error">
	//		<div id="photo">
	//			<img src="../images/alert.gif" alt="" width="100%" height="100%">
	//		</div>
	//		<div id="information">
	//			<div><h5>error</h5></div>
	//			<div><a href="#" id="ok">确定</a></div>
	//		</div>
	//	</div> -->
	
	public static  void setAlert(String str,boolean flag){
		//判断页面是否存在弹框层和阴影层，避免重复构建
		Element mask = DOM.getElementById("mask");
		if(mask!=null) {
			DOM.getElementById("mask").setAttribute("style", "display:block");
			DOM.getElementById("error").setAttribute("style", "display:block");
			DOM.getElementById("msg").setInnerHTML(str);
			DOM.getElementById("img").setAttribute("src", "../images/" + (flag==true?"happy":"alert") + ".gif");
			return;
		}
		//设置阴影层
		Element maskElement = DOM.createElement("div");
		maskElement.addClassName("mask");
		maskElement.setId("mask");
		
		//设置弹框内容
		Element errorElement = DOM.createElement("div");
		errorElement.setId("error");
		Element photoContainerElement = DOM.createElement("div");
		photoContainerElement.setId("photo");
		Element informationElement = DOM.createElement("div");
		informationElement.setId("information");
		
		errorElement.appendChild(photoContainerElement);
		errorElement.appendChild(informationElement);
		
		Element imgElement = DOM.createElement("img");
		if(flag) {
			imgElement.setAttribute("src", "../images/happy.gif");
		}else {
			imgElement.setAttribute("src", "../images/alert.gif");
		}
		imgElement.setAttribute("width","100%");
		imgElement.setAttribute("height", "100%");
		imgElement.setId("img");
		photoContainerElement.appendChild(imgElement);
		
		Element h5 = DOM.createElement("h5");
		h5.setInnerHTML(str);
		h5.setId("msg");
		Element h5ContainerElement = DOM.createElement("div");
		h5ContainerElement.appendChild(h5);
		informationElement.appendChild(h5ContainerElement);
		
		Element a = DOM.createElement("a");
		a.setId("ok");
		a.setAttribute("href", "#");
		a.setInnerHTML("确定");
		Element aContainerElement = DOM.createElement("div");
		aContainerElement.appendChild(a);
		informationElement.appendChild(aContainerElement);
		
		//将阴影层和内容加入页面
		DOM.getElementById("body").appendChild(maskElement);
		DOM.getElementById("body").appendChild(errorElement);
		
		errorElement.setAttribute("style", "display: block");
		maskElement.setAttribute("style", "display: block");
		
		//给确定按钮注册事件，隐藏弹框
		DOM.sinkEvents(a, Event.ONCLICK);
		DOM.setEventListener(a, new EventListener() {
			public void onBrowserEvent(Event event) {
				if(DOM.eventGetType(event) == Event.ONCLICK){
					DOM.getElementById("mask").setAttribute("style", "display:none");
					DOM.getElementById("error").setAttribute("style", "display:none");
				}
			}
		});
	};
	
	public static native String getChoice(String id)
	/*-{
		var select = $doc.getElementById(id);
		var index=select.selectedIndex ; // selectedIndex代表的是你所选中项的index
		return select.options[index].value;
		
	}-*/;
	
	
	//加载博客内容
	public static void loadBlog(Blog blog) {
		User user = blog.getUser();
		Element typElement = DOM.getElementById("type");
		typElement.setInnerHTML(BlogType.getBlogTypeById(blog.getType()).getDesc());
		Element readNunElement = DOM.getElementById("read");
		readNunElement.setInnerHTML(blog.getReadNum()+"");
		Element tranElement = DOM.getElementById("tran");
		tranElement.setInnerHTML(blog.getTransfersNum()+"");
		Element collElement = DOM.getElementById("coll");
		collElement.setInnerHTML(blog.getCollectsNum()+"");
		Element userNameElement = DOM.getElementById("nickName");
		userNameElement.setInnerHTML("<a href=\"./other.html?otherid="+ user.getAccountId() +"\">"+user.getUserName()+"</a>");
		Element sexElement = DOM.getElementById("sex");
		sexElement.setInnerHTML(user.getSex()==1?"男":"女");
		Element birthElement = DOM.getElementById("birt");
		birthElement.setInnerHTML(user.getBirthDay().toString());
		
		//文章内容设置
		Element titleElement = DOM.getElementById("title");
		titleElement.setInnerHTML(blog.getTitle());
		Element timeElement = DOM.getElementById("time");
		timeElement.setInnerHTML(blog.getPublishDateTime().toString());
		Element authElement = DOM.getElementById("author");
		authElement.setInnerHTML(user.getUserName());
		Element readNElement = DOM.getElementById("readNum");
		readNElement.setInnerHTML(blog.getReadNum()+"");
		Element contentElement = DOM.getElementById("content");
		contentElement.setInnerHTML(blog.getContent());
		
	}
	
	
	//加载评论flag0为评论文章，1为评论的评论
	public static void loadComment(List<Comment> comment,String id,int flag){
		String content = "";
		if(flag==0) {
			for(int i = 0; i < comment.size();i++) {
				content += commentToArticle(comment.get(i));
			}
		}else {
			content += DOM.getElementById(id).getInnerHTML();
			for(int i = 0; i < comment.size();i++) {
				content += commentToComment(comment.get(i));
			}
		}
		
		DOM.getElementById(id).setInnerHTML(content);
	}
	//创建对博客的评论
	public static String commentToArticle(Comment comment) {
		String content = 
				"<ul id=\""+ comment.getCommentId() +"\" class=\"comment-list\">\r\n" + 
				"			<li class=\"comment-line-container\">\r\n" + 
				"				<a href=\"./other.html?otherid="+ comment.getUser().getAccountId() + "\"><img src=\"../images/user_default.jpg\" alt=\"\"><span>"+ comment.getUser().getUserName() +"</span></a>\r\n" + 
				"				<div class=\"commentInfo-container\">\r\n" + 
				"					<span id=\"commentContent\">"+ comment.getContent() +"</span>\r\n" + 
				"					<span id=\"commentTime\">"+ comment.getCommentDateTime() +"</span>\r\n" + 
				"				</div>\r\n" + 
				"				<div class=\"like right\">\r\n" + 
				"					<span><a class=\"jubao\" href=\"#modal2\">举报</a></span>\r\n" + 
				"					<span><a class=\"huifu\" href=\"#modal3\">回复</a></span>\r\n" +
				"					<span><a class=\"seehuifu\" href=\"#\">查看回复(" + comment.getCommentNum() + ")</a></span>"+
				"				</div>\r\n" + 
				"			</li>"+
				"		</ul>";
		return content;
	}
	public static String commentToComment(Comment comment) {
		String content = 
				"<li class=\"replay-container\">\r\n" + 
				"	<ul class=\"comment-list\" id=\"" + comment.getCommentId() + 
				"		\">\r\n<li class=\"comment-line-container\">\r\n" + 
				"			<div>\r\n" + 
				"				<a href=\"./otherid.html?otherid="+ comment.getUser().getAccountId() +"\"><img src=\"../images/user_default.jpg\" alt=\"\"><span id=\"commentName\">"+ comment.getUser().getUserName() +"</span></a>\r\n" + 
				"				<span>回复</span>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"commentInfo-container\">\r\n" + 
				"				<span id=\"commentContent\">"+ comment.getContent() +"</span>\r\n" + 
				"				<span id=\"commentTime\">"+ comment.getCommentDateTime() +"</span>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"like right\">\r\n" + 
				"				<span><a class=\"jubao\" href=\"#\">举报</a></span>\r\n" + 
				"				<span><a class=\"huifu\" href=\"#\">回复</a></span>\r\n" + 
				"				<span><a class=\"seehuifu\" href=\"#\">查看回复("+ comment.getCommentNum() + ")</a></span>"+
				"			</div>\r\n" + 
				"		</li>" + 
				"	</ul>\r\n" + 
				"</li>";
		return content;
	}
}