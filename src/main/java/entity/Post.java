package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Post implements Serializable{
	private static final long serialVersionUID = 8958411428421018244L;
	
	private Integer id;
	private String content;
	private Long time;
	private Person personId;
	private List<Comment> list=new ArrayList<Comment>();
	
	public List<Comment> getList() {
		return list;
	}

	public void setList(List<Comment> list) {
		this.list = list;
	}

	public Post() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Person getPersonId() {
		return personId;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() { 
		return "Post [id=" + id + ", content=" + content + ", time=" + time + ", personId=" + personId + ", list="
				+ list + "]";
	}

	
	
	
	
}
