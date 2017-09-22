package web;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.NoteBook;
import service.NotebookService;
import util.JsonResult;

@Controller
@RequestMapping("/note")
public class NotebookController extends AbstractController{
	@Resource
	private NotebookService notebookService;
	
	@RequestMapping("/list.do")
	@ResponseBody
	public JsonResult list(String userId){
		
		List<Map<String,Object>> list=notebookService.listNotebooks(userId);
		return new JsonResult(list);
	}
	@RequestMapping("/add.do")
	@ResponseBody
	public JsonResult addNoteBook(String userId,String name){
		NoteBook n=new NoteBook();
		n.setCreateTime(new Timestamp(System.currentTimeMillis()));
		n.setId(String.valueOf(UUID.randomUUID()));
		n.setName(name);
		n.setUserId(userId);
		boolean b=notebookService.addNotebook(n);
		return new JsonResult(b);
	}
	@RequestMapping("/list7.do")
	@ResponseBody
	public JsonResult list(String userId,int page){
		
		List<Map<String,Object>> list=notebookService.listNotebooks(userId,page);
		return new JsonResult(list);
	}

}
