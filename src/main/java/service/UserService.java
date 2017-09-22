package service;

import org.springframework.stereotype.Repository;

import entity.User;

/**
 * ҵ���ӿ�
 * �������ҵ���ܷ���
 * @author Java
 *
 */
public interface UserService {
	/**
	 * ��½���ܷ���
	 * @param name ��½�û�
	 * @param pwd ����
	 * @return ��½�ɹ����ص�½���û�
	 * @throws NameException �û�������
	 * @throws PasswordException �������
	 */
	User login(String name,String pwd)throws NameException,PasswordException;
	
	User regist(String name,String nick,String pwd,String confirm)
			throws NameException,PasswordException;

	boolean checkToken(String userId, String token);
	
	
}
