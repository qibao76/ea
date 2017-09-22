package util;

import org.apache.commons.codec.digest.DigestUtils;

public class Util {
//	//password 密码的明文
//	String salt="tedu.cn是一家培训机构";
//	String md5=DigestUtils.md5Hex(pwd+salt);
//	//比较摘要，如果摘要一样则明文（密码）一样
	private static final String salt="tedu.cn是一家培训机构";
	//封装密码加密算法
	public static String salMd5(String data){
		return DigestUtils.md5Hex(data+salt);
	}

}
   