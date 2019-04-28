package com.login.client;

public class Operate {
	public static native String getValue(String id)
	/*-{
	 	var value = $doc.getElementById(id).value;
	 	return value;
	 }-*/;
}
