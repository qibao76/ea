package entity;

import java.io.Serializable;

public class Note implements Serializable{
	private String id;
	private String notebookId;
	private String user_id;
	private String type;
	private String body;
	private String title;
	private String statusId;
	private long createTime;
	private long modifyTime;
	public Note() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotebookId() {
		return notebookId;
	}
	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public long getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}
	@Override
	public String toString() {
		return "Note [id=" + id + ", notebookId=" + notebookId + ", user_id=" + user_id + ", type=" + type + ", body="
				+ body + ", title=" + title + ", statusId=" + statusId + ", createTime=" + createTime + ", modifyTime="
				+ modifyTime + "]";
	}
	
	
	
}
