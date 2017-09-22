package service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.UserDao;
import entity.User;

@Service("userService")
public class UserServiceImpl implements UserService{
	@Resource
	private UserDao userDao;
	public User login(String name, String pwd) throws NameException, PasswordException {
	
		System.out.println("Login");
		if(name==null||name.trim().isEmpty()){
			throw new NameException("����Ϊ��");
		}
		if(pwd==null||pwd.trim().isEmpty()){
			throw new PasswordException("���벻��Ϊ��");
		}
		User u=userDao.findUserByName(name);
		if(u==null){
			throw new NameException("�û�������");
 		}
//		//password ���������
//		String salt="tedu.cn��һ����ѵ����";
//		String md5=DigestUtils.md5Hex(pwd+salt);
//		//�Ƚ�ժҪ�����ժҪһ�������ģ����룩һ��
		if(u.getPwd().equals(util.Util.salMd5(pwd))){
			
			String token=UUID.randomUUID().toString();
			u.setToken(token);
			Map<String,Object> userInfo=new HashMap<String,Object>();
			userInfo.put("id", u.getId());
			userInfo.put("token", token);
			userDao.updateUser(userInfo);
			return u;
		}else{
			throw new PasswordException("�������");
		}
	}
	public User regist(String name, String nick, String pwd, String confirm) throws NameException, PasswordException {
		if(name==null||name.trim().isEmpty()){
			throw new NameException("�û�������Ϊ��");
		}
		if(pwd==null||pwd.trim().isEmpty()){
			throw new PasswordException("���벻��Ϊ��");
		}
		if(!pwd.equals(confirm)){
			throw new PasswordException("���벻һ��");
		}
		User user=userDao.findUserByName(name);
		if(user!=null){
			throw new NameException("��ע��");
		}
		if(nick==null||nick.trim().isEmpty()){
			nick=name;
		}
		String id=UUID.randomUUID().toString();
		userDao.addUser(new User(id,name,util.Util.salMd5(pwd),"",nick));
		return new User(id,name,util.Util.salMd5(pwd),"",nick);
			
	}
	@Transactional(readOnly=true)
	public boolean checkToken(String userId, String token) {
		User user=userDao.findUserById(userId);
		return token.equals(user.getToken());
	}


}
