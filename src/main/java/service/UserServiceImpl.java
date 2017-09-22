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
			throw new NameException("不能为空");
		}
		if(pwd==null||pwd.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		User u=userDao.findUserByName(name);
		if(u==null){
			throw new NameException("用户不存在");
 		}
//		//password 密码的明文
//		String salt="tedu.cn是一家培训机构";
//		String md5=DigestUtils.md5Hex(pwd+salt);
//		//比较摘要，如果摘要一样则明文（密码）一样
		if(u.getPwd().equals(util.Util.salMd5(pwd))){
			
			String token=UUID.randomUUID().toString();
			u.setToken(token);
			Map<String,Object> userInfo=new HashMap<String,Object>();
			userInfo.put("id", u.getId());
			userInfo.put("token", token);
			userDao.updateUser(userInfo);
			return u;
		}else{
			throw new PasswordException("密码错误");
		}
	}
	public User regist(String name, String nick, String pwd, String confirm) throws NameException, PasswordException {
		if(name==null||name.trim().isEmpty()){
			throw new NameException("用户名不能为空");
		}
		if(pwd==null||pwd.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		if(!pwd.equals(confirm)){
			throw new PasswordException("密码不一致");
		}
		User user=userDao.findUserByName(name);
		if(user!=null){
			throw new NameException("已注册");
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
