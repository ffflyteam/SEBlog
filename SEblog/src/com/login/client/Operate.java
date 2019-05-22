package com.login.client;

import org.apache.xalan.templates.ElemTemplate;

import com.gargoylesoftware.htmlunit.javascript.host.html.Image;
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
			Window.alert("sss");
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
		photoContainerElement.appendChild(imgElement);
		
		Element h5 = DOM.createElement("h5");
		h5.setInnerHTML(str);
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
}