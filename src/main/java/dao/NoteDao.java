package dao;

import java.util.List;
import java.util.Map;

import entity.Note;

public interface NoteDao {
	List<Map<String,Object>> findByNoteBookId(String notebookId);
	Note findNoteById(String noteId);
	/**
	 * 动态Note数据跟新
	 * 参数note中包含更新数据：
	 * 必须包含的数据： id：笔记本id
	 * 可选参数：
	 *    notebookId：笔记本ID
	 *    title：标题
	 *    body：笔记本内容
	 *    userId：作者ID
	 *    lastModifyTime：最后编辑时间
	 *    。。。。
	 * @param note
	 * @return 更新数量
	 */
	int updataNote(Map<String,Object> note);
	
	int addNote(Note n);
	
	int deleteNoteById(String id);
	//动态参数查询
	List<Map<String,Object>> findNotesByParam(Map<String,Object> param);
	
	//批量删除方法
	int deleteNotessByParam(Map<String,Object> param);
	//用list添加删除
	int deleteNotessByList(List<String> list);
	
	
}
