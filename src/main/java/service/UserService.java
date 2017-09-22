package service;

import org.springframework.stereotype.Repository;

import entity.User;

/**
 * 业务层接口
 * 声明软件业务功能方法
 * @author Java
 *
 */
public interface UserService {
	/**
	 * 登陆功能方法
	 * @param name 登陆用户
	 * @param pwd 密码
	 * @return 登陆成功返回登陆的用户
	 * @throws NameException 用户名错误
	 * @throws PasswordException 密码错误
	 */
	User login(String name,String pwd)throws NameException,PasswordException;
	
	User regist(String name,String nick,String pwd,String confirm)
			throws NameException,PasswordException;

	boolean checkToken(String userId, String token);
	
	
}
