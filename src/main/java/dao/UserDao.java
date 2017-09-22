
package dao;

import java.util.Map;

import entity.User;

public interface UserDao {
	void addUser(User u);
	User findUserByName(String name);
	User findUserById(String userId);
	void updateUser(Map<String, Object> userInfo);
	
	
}
