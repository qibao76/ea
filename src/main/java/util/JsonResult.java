package util;

import java.io.Serializable;

/**
 * 用于封装ajax调用以后的JSON返回值
 * 其中正确返回值
 * {state：0，data：返回数据，message：错误消息}
 * 错误返回值
 * {state：1，data：null，message：错误消息}
 * @author Java
 *
 */
public class JsonResult implements Serializable{
	
	private static final long serialVersionUID = 1L;
	public static final int SUCCESS=0;
	public static final int ERROR=1;
	/**
	 * 返回是否成功的状态，0表示成功，1或者其他值表示失败
	 */
	private int state;
	//成功时候返回的JSON数据
	private Object data;
	//错误消息
	private String message;
	
	public JsonResult() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public JsonResult(int state, Object data, String message) {
		super();
		this.state = state;
		this.data = data;
		this.message = message;
	}
	public JsonResult(int s,Throwable e){
		this.state=s;
		this.message=e.getMessage();
		this.data=null;
	}
	public JsonResult(Throwable e){
		state=ERROR;
		data=null;
		message=e.getMessage();
	}
	public JsonResult(Object data) {
		state=SUCCESS;
		this.data = data;
		message="登录成功";
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", data=" + data + ", message=" + message + "]";
	}
	
}
