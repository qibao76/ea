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
	
	//添加笔记
	public Note addNote(String notebookId, String userId, String title) {
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new AddNoteException("notebookId为空");
		}
		if(userId==null||userId.trim().isEmpty()){
			throw new AddNoteException("userId为空");
		}
		if(title==null||title.trim().isEmpty()){
			title="新笔记"+(++i);
		}
		NoteBook nn=notebookDao.findNotebooksBynoteBookId(notebookId);
		if(nn==null){
			throw new NotebookNotFindException("查询不到该笔记本");
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

	//显示笔记列表
	@Transactional
	public List<Map<String, Object>> listNote(String notebookId) throws NotebookNotFindException {
		if(notebookId==null||notebookId.trim().isEmpty()){
			throw new NotebookNotFindException("notebookId不能为空");
		}
		NoteBook n=notebookDao.findNotebooksBynoteBookId(notebookId);
		if(n==null){
			throw new NotebookNotFindException("查询不到该笔记本");
		}
		List<Map<String, Object>> list=noteDao.findByNoteBookId(notebookId);
		return list;
	}

	//显示笔记的标题，和body
	public Note findNoteBynoteId(String noteId) throws NoteNotFindException {
		if(noteId==null||noteId.trim().isEmpty()){
			throw new NotebookNotFindException("noteId不能为空");
		}
		Note n=noteDao.findNoteById(noteId);

		return n;
	}

	//保存笔记
	public boolean saveNote(String id, String title, String body) throws NotebookNotFindException {
		if(id==null||id.trim().isEmpty()){
			throw new NoteNotFindException("id不能为空");
		}
		if(title==null||title.trim().isEmpty()){
			String reg="<p>[^<>]+<\\/p>";
			Pattern p=Pattern.compile(reg);
			Matcher m=p.matcher(body);
			if(m.find()){
				String str=m.group();
				title=str.substring(3, str.length()>13?13:str.length()-4).trim();
			}else{
				title="无标题";
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
	//删除笔记
	public Boolean deleteNote(String id) {
		if(id==null||id.trim().isEmpty()){
			throw new NoteNotFindException("id不能为空");
		}
		Map<String,Object> note=new HashMap<String,Object>();
		note.put("id", id);
		note.put("statusId", "2");
		return 1==noteDao.updataNote(note);
	}
	
	//删除笔记
	public int deleteNotes(String... ary){
		int n=0;
		for(String id:ary){
			Note note=noteDao.findNoteById(id);
			if(note==null){
				throw new NoteNotFindException("找不到删除的笔记");
			}
			n+=noteDao.deleteNoteById(id);
		}
		return n;
	}


}
