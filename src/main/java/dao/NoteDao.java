package dao;

import java.util.List;
import java.util.Map;

import entity.Note;

public interface NoteDao {
	List<Map<String,Object>> findByNoteBookId(String notebookId);
	Note findNoteById(String noteId);
	/**
	 * ��̬Note���ݸ���
	 * ����note�а����������ݣ�
	 * ������������ݣ� id���ʼǱ�id
	 * ��ѡ������
	 *    notebookId���ʼǱ�ID
	 *    title������
	 *    body���ʼǱ�����
	 *    userId������ID
	 *    lastModifyTime�����༭ʱ��
	 *    ��������
	 * @param note
	 * @return ��������
	 */
	int updataNote(Map<String,Object> note);
	
	int addNote(Note n);
	
	int deleteNoteById(String id);
	//��̬������ѯ
	List<Map<String,Object>> findNotesByParam(Map<String,Object> param);
	
	//����ɾ������
	int deleteNotessByParam(Map<String,Object> param);
	//��list���ɾ��
	int deleteNotessByList(List<String> list);
	
	
}
