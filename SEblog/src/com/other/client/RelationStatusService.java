package com.other.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("getRelationStatus")
public interface RelationStatusService extends RemoteService{
	public boolean getRelationstatus(int otherId);
}
