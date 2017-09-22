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
				throw new NotebookNotFindException("�Ҳ�����book");
			}
			//�ҵıʼǱ���ȫ���ıʼ�ID
			List<Map<String,Object>> list=noteService.listNote(id);
			String[] idList=new String[list.size()];
			int i=0;
			for(Map<String,Object> map:list){
				idList[i++]=(String)map.get("id");
			}
			//ɾ����Щ�ʼ�
			noteService.deleteNotes(idList);
			//ɾ���ʼǱ�
			notebookDao.deleteNotebookByid(id);
			
			
			
		}
		return 0;
	}
	//��ѯ�ʼǱ��б�
	public List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new NameException("�û���û��");
		}
		User user=userDao.findUserById(userId);
		if(user==null){
			throw new UserNotFoundException("��ѯ�������û�");
		}
		
		return notebookDao.findNotebooksByUserId(userId);
	}
	
	//��ӱʼǱ�
	public boolean addNotebook(NoteBook n) throws AddNotebookException {
		if(n==null){
			throw new AddNotebookException("û��Ҫ��ӵıʼǱ�");
		}
		User user=userDao.findUserById(n.getUserId());
		if(user==null){
			throw new UserNotFoundException("��ѯ�������û�");
		}
		return 1==notebookDao.addNoteBook(n);
	}
	//��ҳ
	public List<Map<String, Object>> listNotebooks(String userId, int page) throws UserNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("userId����Ϊ��");
		}
		User u=userDao.findUserById(userId);
		if(u==null){
			throw new UserNotFoundException("�Ҳ����û�");
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
