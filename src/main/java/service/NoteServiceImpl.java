package service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.NoteDao;
import dao.NotebookDao;
import entity.Note;
import entity.NoteBook;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	@Resource
	private NoteDao noteDao;
	@Resource
	private NotebookDao notebookDao;
	public static int i=0;
	
	//��ӱʼ�
	public Note addNote(String notebookId, String userId, String title) {
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new AddNoteException("notebookIdΪ��");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new AddNoteException("userIdΪ��");
		}
		if(title==null||title.trim().isEmpty()){
			title="�±ʼ�"+(++i);
		}
		NoteBook nn=notebookDao.findNotebooksBynoteBookId(notebookId);
		if(nn==null){
			throw new NotebookNotFindException("��ѯ�����ñʼǱ�");
		}
		Note n=new Note();
		n.setCreateTime(System.currentTimeMillis());
		String noteId=String.valueOf(UUID.randomUUID());
		n.setId(noteId);
		n.setNotebookId(notebookId);
		n.setTitle(title);
		n.setUser_id(userId);
		n.setStatusId("1");
		noteDao.addNote(n);
		return noteDao.findNoteById(noteId);
	}

	//��ʾ�ʼ��б�
	@Transactional
	public List<Map<String, Object>> listNote(String notebookId) throws NotebookNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFindException("notebookId����Ϊ��");
		}
		NoteBook n=notebookDao.findNotebooksBynoteBookId(notebookId);
		if(n==null){
			throw new NotebookNotFindException("��ѯ�����ñʼǱ�");
		}
		List<Map<String, Object>> list=noteDao.findByNoteBookId(notebookId);
		return list;
	}

	//��ʾ�ʼǵı��⣬��body
	public Note findNoteBynoteId(String noteId) throws NoteNotFindException {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new NotebookNotFindException("noteId����Ϊ��");
		}
		Note n=noteDao.findNoteById(noteId);

		return n;
	}

	//����ʼ�
	public boolean saveNote(String id, String title, String body) throws NotebookNotFindException {
		if(id==null||id.trim().isEmpty()){
			throw new NoteNotFindException("id����Ϊ��");
		}
		if(title==null||title.trim().isEmpty()){
			String reg="<p>[^<>]+<\\/p>";
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(body);
			if(m.find()){
				String str=m.group();
				title=str.substring(3, str.length()>13?13:str.length()-4).trim();
			}else{
				title="�ޱ���";
			}
		}
		if(body==null){
			body="";
		}
		Map<String,Object> note=new HashMap<String,Object>();
		note.put("id", id);
		note.put("title", title);
		note.put("body", body);
		note.put("lastModifyTime", System.currentTimeMillis());
		int n=noteDao.updataNote(note);
		return n==1;
	}
	//ɾ���ʼ�
	public Boolean deleteNote(String id) {
		if(id==null||id.trim().isEmpty()){
			throw new NoteNotFindException("id����Ϊ��");
		}
		Map<String,Object> note=new HashMap<String,Object>();
		note.put("id", id);
		note.put("statusId", "2");
		return 1==noteDao.updataNote(note);
	}
	
	//ɾ���ʼ�
	public int deleteNotes(String... ary){
		int n=0;
		for(String id:ary){
			Note note=noteDao.findNoteById(id);
			if(note==null){
				throw new NoteNotFindException("�Ҳ���ɾ���ıʼ�");
			}
			n+=noteDao.deleteNoteById(id);
		}
		return n;
	}


}
