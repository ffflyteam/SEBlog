package com.DAO;

public class CommonHelper {
	public static final CommonHelper instance = new CommonHelper();
	
	private CommonHelper() {
	}
	
	public boolean isSqlExecuteSucc(int res) {
		return res != ResultConst.EXECUTE_SQL_ERROR.getId();
	}
	
	public int getSqlExecuteResultConst(int res) {
		return res == ResultConst.EXECUTE_SQL_ERROR.getId() ? res : ResultConst.SUCCESS.getId();
	}
}
