package test1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.NoteDao;
import dao.NotebookDao;
import dao.PersonDao;
import dao.UserDao;
import entity.NoteBook;
import entity.Person;
import entity.User;
import service.NoteService;
import service.NotebookService;
import service.UserService;

public class TestCase {
	private  ClassPathXmlApplicationContext ac;
	@Before
	public void init(){
		ac=new ClassPathXmlApplicationContext("spring-mybatis.xml","spring-service.xml");
		SqlSessionFactoryBean f= ac.getBean("sqlSessionFactory",SqlSessionFactoryBean.class);
	//	f.setSqlSessionFactoryBuilder();
	}
	@Test
	public void testConnection() throws SQLException{
		DataSource d=ac.getBean("dataSource",DataSource.class);
		Connection conn=d.getConnection();
		String sql="select 'hello world' as s";
		Statement st=conn.createStatement();
		ResultSet rs=st.executeQuery(sql);
		while(rs.next()){
			System.out.println(rs.getString("s"));
		}
		conn.close();
	}
	@Test
	public void testAddUser(){
		/*
		 * mapper扫描器将mapper接口UserDao创建为Bean对象
		 */
		UserDao dao=ac.getBean("userDao",UserDao.class);
		User u=new User("100","tom","123","","Tom");
	//	dao.addUser(u);
		//查找User
		//39295a3d-cc9b-42b4-b206-a2e7fab7e77c
		User u1=dao.findUserByName("zhoujia");
		System.out.println(u1);
	}
	@Test
	public void testLogin(){
		String name="tom";
		String pwd="123";
		UserService u=ac.getBean("userService",UserService.class);
		System.out.println(u.login(name, pwd));
	//	System.out.println(u.login("",""));
		System.out.println(u.login("tom", ""));
	}
	@Test
	public void testMd5(){
		String str="123";
		String salt="tedu.cn是一家培训机构";
		String md5=DigestUtils.md5Hex(str+salt);
		System.out.println(md5);
		//92538b3c4bcbabf7ab3fd502f0f8ab55
	}
	@Test
	public void testMd5Sal(){
		
		
		System.out.println(3<<3);
		
	}
	//注册
	@Test
	public void testRegist(){
		UserService u=ac.getBean("userService",UserService.class);
		User us=u.regist("jerry", "", "123","123");
		System.out.println(us);
	}
	@Test
	public void testFindNoteBookId(){
		String id="39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		NotebookDao dao=ac.getBean("notebookDao",NotebookDao.class);
		List<Map<String,Object>> list=dao.findNotebooksByUserId(id);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	//测试笔记本的显示
	@Test
	public void testListNotebooks(){
		String id="03590914-a934-4da9-ba4d-b41799f917d1";
		NotebookService se=ac.getBean("notebookService",NotebookService.class);
		List<Map<String,Object>> list=se.listNotebooks(id);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	//测试笔记的显示
	@Test
	public void testListnote(){
		NoteDao n=ac.getBean("noteDao",NoteDao.class);
		List<Map<String,Object>> list=
				n.findByNoteBookId("af8be968-66bb-4b75-ac5d-d7deb772640f");
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	//查询笔记本的所有信息
	@Test
	public void testFindNoteboo(){
		NotebookDao dao=ac.getBean("notebookDao",NotebookDao.class);
		NoteBook n=dao.findNotebooksBynoteBookId("af8be968-66bb-4b75-ac5d-d7deb772640f");
		System.out.println(n);
	}
	//更新修改后的笔记信息
	@Test
	public void testUpdataNote(){
		String id="5d1a0678-57f7-4bbd-ac4a-323505da94b2";
		Map<String,Object> note=new HashMap<String,Object>();
		note.put("id", id);
		note.put("title", "java基础成功修改");
		NoteDao n=ac.getBean("noteDao",NoteDao.class);
		n.updataNote(note);
		System.out.println(n.findNoteById(id));
		
	}
	@Test
	public void testSaveNote(){
		String id="5d1a0678-57f7-4bbd-ac4a-323505da94b2";
		NoteService ns=ac.getBean("noteService",NoteService.class);
		boolean ok=ns.saveNote(id, "哈哈哈", "靖哥哥是个大美女");
		System.out.println(ok);
		System.out.println(ns.findNoteBynoteId(id));
	}
	//正则
	@Test
	public void testZz(){
		String str="<p>即可获得发生</p><p>Fjhsf</p>";
		String reg="<p>[^<>]+<\\/p>";
		//reg=/<p>[^<>]+<\/p>/
		//创建正则表达式对象
		Pattern p=Pattern.compile(reg);
		//利用正则表达式匹配字符串m 代表匹配结果
		Matcher m=p.matcher(str);
		//m.find()返回值表示找到了要匹配的数据
		if(m.find()){
			//m.group（）获取找到的子字符串
			String s=m.group();
			s=s.substring(3,s.length()-4);
			System.out.println(s);
		}
	}
	//删除笔记
	@Test
	public void testDeleteNotes(){
		/*
		 * d8a09d67-d453-49b6-8ebc-f9852a96809e
		*	f2697200-63d9-40a2-a711-aeee2e196eef
		 */
		String id1="b4a6efd7-1c65-4e11-b253-8b271d5dca77";
		String id2="d8a09d67-d453-49b6-8ebc-f9852a96809e";
		String id3="f2697200-63d9-40a2-a711-aeee2e196eef";
		NoteService ns=ac.getBean("noteService",NoteService.class);
		ns.deleteNotes(id2,id3);
		 
		
	}
	@Test
	public void testFindNoteByParam(){
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("key", "%1%");
		List<Map<String,Object>> list=dao.findNotesByParam(param);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
	}
	
	@Test
	public void testDeleteNotes1(){
		
		String[] idList={"5d1a0678-57f7-4bbd-ac4a-323505da94b2","","","",""};
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("idList", idList);
		int n=dao.deleteNotessByParam(map);
		System.out.println(n);
	}
	@Test
	public void testDeleteNotes2(){
		/*
 			84b2d98b-af39-4655-8aa8-d8869d043cca
 			c347f832-e2b2-4cb7-af6f-6710241bcdf6
 			07305c91-d9fa-420d-af09-c3ff209608ff
		 */
		NoteDao dao=ac.getBean("noteDao",NoteDao.class);
		List<String> list=  
			new ArrayList<String>();
		list.add("84b2d98b-af39-4655-8aa8-d8869d043cca");
		list.add("c347f832-e2b2-4cb7-af6f-6710241bcdf6");
		list.add("07305c91-d9fa-420d-af09-c3ff209608ff");
		int n = dao.deleteNotessByList(list);
		System.out.println(n); 
	}
	@Test
	public void testFindNotebooksByParam(){
		String userid="39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		String table="cn_notebook";
		int start=0;
		int rows=6;
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userid);
		param.put("tableName", table);
		param.put("start", start);
		param.put("rows", rows);
		NotebookDao dao=ac.getBean("notebookDao",NotebookDao.class);
		List<Map<String,Object>> list=dao.findNotebooksByParam(param);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
	}
	@Test
	public void testFindNotebooksByParamService(){
		String userid="39295a3d-cc9b-42b4-b206-a2e7fab7e77c";
		NotebookService dao=ac.getBean("notebookService",NotebookService.class);
		List<Map<String,Object>> list=dao.listNotebooks(userid, 1);
		for (Map<String, Object> map : list) {
			System.out.println(map);
		}
		
	}
	
	@Test
	public void testPerson(){
		PersonDao dao=ac.getBean("personDao",PersonDao.class);
		Person p=new Person();
		p.setAge(12);
		p.setName("tom");
		System.out.println(p);
		int n=dao.savePerson(p);
		System.out.println(p);
		
	}
	@Test
	public void test(){
		int[] a=new int[20];
		Random r=new Random();
		for (int i = 0; i < a.length; i++) {
			a[i]=r.nextInt(10);
		}
		System.out.println(Arrays.toString(a));
		int[] b=new int[10];
		for(int i=0;i<a.length;i++){
			b[a[i]]++;
		}
		System.out.println(Arrays.toString(b));
		
	}
	
	
	

} 
