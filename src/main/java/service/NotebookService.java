package service;

import java.util.List;
import java.util.Map;

import entity.NoteBook;


public interface NotebookService {

	List<Map<String,Object>> listNotebooks(String userId) throws UserNotFoundException;
	boolean addNotebook(NoteBook n)throws AddNotebookException;
	int deleteNoteBookById(String... id)throws NotebookNotFindException; 
	List<Map<String,Object>> listNotebooks(String userId,int param) throws UserNotFoundException;
	
	
	
}
