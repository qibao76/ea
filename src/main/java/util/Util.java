package util;

import org.apache.commons.codec.digest.DigestUtils;

public class Util {
//	//password ���������
//	String salt="tedu.cn��һ����ѵ����";
//	String md5=DigestUtils.md5Hex(pwd+salt);
//	//�Ƚ�ժҪ�����ժҪһ�������ģ����룩һ��
	private static final String salt="tedu.cn��һ����ѵ����";
	//��װ��������㷨
	public static String salMd5(String data){
		return DigestUtils.md5Hex(data+salt);
	}

}
   