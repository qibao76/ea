package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.NotebookDao;
import dao.UserDao;
import entity.NoteBook;
import entity.User;
@Service("notebookService")
public class NotebookServiceImpl implements NotebookService{
	@Resource
	private NotebookDao notebookDao;
	@Resource
	private UserDao userDao;
	@Resource
	private NoteService noteService;
	@Transactional
	public int deleteNoteBookById(String... ary) throws NotebookNotFindException {
		for(String id:ary){
			NoteBook nb=notebookDao.findNotebooksBynoteBookId(id);
			if(nb==null){
				throw new NotebookNotFindException("找不到该book");
			}
			//找的笔记本中全部的笔记ID
			List<Map<String,Object>> list=noteService.listNote(id);
			String[] idList=new String[list.size()];
			int i=0;
			for(Map<String,Object> map:list){
				idList[i++]=(String)map.get("id");
			}
			//删除这些笔记
			noteService.deleteNotes(idList);
			//删除笔记本
			notebookDao.deleteNotebookByid(id);
			
			
			
		}
		return 0;
	}
	//查询笔记本列表
	public List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new NameException("用户名没有");
		}
		User user=userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("查询不到该用户");
		}
		
		return notebookDao.findNotebooksByUserId(userId);
	}
	
	//添加笔记本
	public boolean addNotebook(NoteBook n) throws AddNotebookException {
		if(n==null){
			throw new AddNotebookException("没有要添加的笔记本");
		}
		User user=userDao.findUserById(n.getUserId());
		if(user==null){
			throw new UserNotFoundException("查询不到该用户");
		}
		return 1==notebookDao.addNoteBook(n);
	}
	//分页
	public List<Map<String, Object>> listNotebooks(String userId, int page) throws UserNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("userId不能为空");
		}
		User u=userDao.findUserById(userId);
		if(u==null){
			throw new UserNotFoundException("找不到用户");
		}
		int size=6;
		int start=page*size;
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("userId", userId);
		param.put("tableName", "cn_notebook");
		param.put("start", start);
		param.put("rows", size);
		
		return notebookDao.findNotebooksByParam(param);
	}
	
	

	
	
	
}
