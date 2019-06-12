package com.detail.client;

import java.util.List;

import com.detail.shared.BlogType;
import com.google.gwt.dom.client.Element;
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
		Element userNamElement = DOM.getElementById("nickName");
		userNamElement.setInnerHTML(user.getUserName());
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
	
	
	//加载评论
	public static void loadComment(List<Comment> comment){
		Window.alert(comment.toString());
		for(int i = 0; i < comment.size();i++) {
			commentToArticle(comment.get(i));
		}
	}
	//创建对博客的评论
	public static void commentToArticle(Comment comment) {
		Element ulElement = DOM.createElement("ul");
		ulElement.addClassName("comment-list");
		Element li1 = DOM.createElement("li");
		li1.addClassName("comment-line-container");
		li1.setAttribute("commentId", comment.getCommentId()+"");
		Element li2 = DOM.createElement("li");
		li2.addClassName("replay-container");
		ulElement.appendChild(li1);
		ulElement.appendChild(li2);
		DOM.getElementById("comment-container").appendChild(ulElement);
		
		Element aElement = DOM.createElement("a");
		li1.appendChild(aElement);
		Element imgElement = DOM.createElement("img");
		aElement.appendChild(imgElement);
		imgElement.setAttribute("src", "../images/user_default.jpg");
		Element commenterElement = DOM.createElement("span");
		
		commenterElement.setInnerHTML("评论人");
		
		aElement.appendChild(commenterElement);
		
		Element commentInfoContainerElement = DOM.createElement("div");
		li1.appendChild(commentInfoContainerElement);
		commentInfoContainerElement.addClassName("commentInfo-container");
		Element commentContentElement = DOM.createElement("span");
		commentContentElement.setInnerHTML(comment.getContent());
		Element timeElement = DOM.createElement("span");
		timeElement.setInnerHTML(comment.getCommentDateTime().toString());
		commentInfoContainerElement.appendChild(commentContentElement);
		commentInfoContainerElement.appendChild(timeElement);
		
		Element opElement = DOM.createElement("div");
		opElement.addClassName("like right");
		li1.appendChild(opElement);
		Element span1 = DOM.createElement("span");
		Element span2 = DOM.createElement("span");
		Element span3 = DOM.createElement("span");
		opElement.appendChild(span1);
		opElement.appendChild(span2);
		opElement.appendChild(span3);
		Element a1 = DOM.createElement("a");
		Element a2 = DOM.createElement("a");
		Element a3 = DOM.createElement("a");
		a1.setAttribute("href", "#modal2");
		a2.setAttribute("href", "#modal3");
		span1.appendChild(a1);
		span2.appendChild(a2);
		span3.appendChild(a3);
		
	}
}