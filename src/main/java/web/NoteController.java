package web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Note;
import service.NoteService;
import util.JsonResult;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController{
	@Resource
	private NoteService noteService;
	@ResponseBody
	@RequestMapping("/list2.do")
	public JsonResult listNote(String notebookId){
		List<Map<String,Object>> list=noteService.listNote(notebookId);
		return new JsonResult(list);
	}
	@ResponseBody
	@RequestMapping("/list3.do")
	public JsonResult listNotes(String noteId){
		
		return new JsonResult(noteService.findNoteBynoteId(noteId));
	}
	@ResponseBody
	@RequestMapping("/save.do")
	public JsonResult saveNote(String noteId,String title,String body){
		
		Boolean b=noteService.saveNote(noteId, title, body);
		
		return new JsonResult(b);
	}
	@ResponseBody
	@RequestMapping("/addnote.do")
	public JsonResult addNote(String notebookId,String title,String userId){
		Thread t=Thread.currentThread();
		System.out.println(t.getName());
		Note ok=noteService.addNote(notebookId, userId, title);
		return new JsonResult(ok);
	}
	@ResponseBody
	@RequestMapping("/deleteNote.do")
	public JsonResult deleteNote(String noteId){
		Boolean b=noteService.deleteNote(noteId);
		return new JsonResult(b);
	}	
	                                                                                                                                                                      
	
}
