package service;

import java.util.List;
import java.util.Map;

import entity.Note;

public interface NoteService {
	List<Map<String,Object>> listNote(String notebookId)throws NotebookNotFindException;
	Note findNoteBynoteId(String noteId)throws NoteNotFindException;
	
	boolean saveNote(String id,String title,String body)throws NotebookNotFindException;

	Note addNote(String notebookId,String userId,String title)throws AddNoteException;
	
	Boolean deleteNote(String noteId);
	
	int deleteNotes(String... id);

}
