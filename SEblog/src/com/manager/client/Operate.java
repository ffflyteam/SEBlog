package com.manager.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.user.shared.BlogType;

public class Operate {
	
	//验证密码长度
	public static boolean isValidPassword(String pwd) {
		if(pwd==null)
			return false;
		return pwd.length() >= 6 && pwd.length() <= 10;
	}
	
	//获取input标签的值
	public static native String getValue(String id)
	/*-{
	 	var value = $doc.getElementById(id).value;
	 	if(value)
	 		return value;
	 	else
	 		return null;
	 }-*/;
	
	//设置input标签的值
	public static native void setValue(String id,String content)
	/*-{
		$doc.getElementById(id).value = content;
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

	
	
	public static native String getContent(String content)
	/*-{
		var reg=/<\/?.+?\/?>/g;
		return content.replace(reg,"");
	}-*/;
	

	public static void AddFeedBack(List<FeedBack> result) {
		String content = "";
		for (int i = 0; i < result.size(); i++) {
			content += AddFeedBackHelper(result.get(i));
		}
		DOM.getElementById("feedback-table-body").setInnerHTML(content);
	}

	private static String AddFeedBackHelper(FeedBack feedBack) {
		String content = 
				"<tr>\r\n" + 
				"	<td>"+ feedBack.getObjectId() +"</td>\r\n" + 
				"	<td>" +"</td>\r\n" + 
				"	<td>哈哈哈哈哈哈哈哈</td>\r\n" + 
				"	<td>举报者</td>\r\n" + 
				"	<td>\r\n" + 
				"		<a href=\"#\">已读</a>\r\n" + 
				"		<a href=\"#\">删除</a>\r\n" + 
				"	</td>\r\n" + 
				"</tr>";
		return content;
	}
	
	
}