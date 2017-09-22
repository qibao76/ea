package test1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.PostDao;
import entity.Post;

public class PostTest {
	private  ClassPathXmlApplicationContext ac;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("spring-mybatis.xml","spring-service.xml");
	}
	@Test
	public void testPost(){
		PostDao p=ac.getBean("postDao",PostDao.class);
		Post pos=p.findPostById(1);
		System.out.println(pos);
		//Post [id=1, content=今天天气不错!, time=null, personId=1]

	}
	@Test
	public void test2() throws ClassNotFoundException, SQLException{
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/yanfeng","root", "114318");
		System.out.println(connection);
	}
	

}
