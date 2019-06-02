package com.index.shared;

import java.util.HashMap;
import java.util.Map;

public enum ResultConst {
	SUCCESS(0, "ִ�гɹ�"),			
	ACCOUNT_HAS_BEEN_REGISTERED(1, "�ʺ��ѱ�ע��"), //�ʺ��ѱ�ע��
	TOW_PASSWORD_IS_DIFFERENT(2, "������������벻һ��"),  //������������벻һ��
	REGISTER_ERROR(3, "ע���쳣"),			//ע���쳣
	ACCOUNT_NOT_EXIST(4, "�ʺŲ�����"),  //�ʺŲ�����
	PASSWORD_ERROR(5, "�����������"),   //�����������
	CAN_NOT_DELETE_COMMENT(6, "�޷�ɾ������"),  //�޷�ɾ������
	COMMENT_NOT_EXIST(7, "���۲�����"),   //���۲�����
	PARAMS_ERROR(8, "��������"),  //��������
	BLOG_NOT_EXIST(9, "���Ͳ�����"),		//���Ͳ�����
	EXECUTE_SQL_ERROR(10, "SQLִ�д���"),		//SQLִ�д���
	HAS_COLLECT_OR_TRANSFER_BLOG(11, "�ò����Ѿ��ղػ�ת�ع�"), //�ò����Ѿ��ղػ�ת�ع�
	HAS_NO_RELATION_WITH_BLOG(12, "δ�ղػ�ת�ع��ò���"),   //δ�ղػ�ת�ع��ò���
	ACCOUNT_HAS_BEEN_FROZEN(13, "�ʺ��Ѿ�������"),    //�ʺ��Ѿ�������
	MANAGER_ACCOUNT_NOT_EXIST(14, "����Ա�ʺŲ�����"),  //����Ա�ʺŲ�����
	CLICK_LIKE_ERROR(15, "�����쳣"),  //�����쳣
	CANCLE_LIKE_ERROR(16, "ȡ�����쳣"),  //ȡ�����쳣
	CACLE_RELATION_BLOG_ERROR(17, "ȡ���ղػ�ת������"),
	LOGIN_ERROR(18, "��½�쳣"),
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
