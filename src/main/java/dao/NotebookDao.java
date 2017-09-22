package dao;

import java.util.List;
import java.util.Map;

import entity.NoteBook;

public interface NotebookDao {

	List<Map<String,Object>> findNotebooksByUserId(String userId);

	NoteBook findNotebooksBynoteBookId(String notebookId);
	
	int addNoteBook(NoteBook n);
	
	int deleteNotebookByid(String id);
	
	List<Map<String,Object>> findNotebooksByParam(Map<String,Object> param);
	
}
