package dto;

public class DTO_api_response<T> {

	private String message;
	private String code;
	private int http_code;
	private T data;
//	private StackTraceElement[] stackTrace;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getHttp_code() {
		return http_code;
	}
	public void setHttp_code(int http_code) {
		this.http_code = http_code;
	}
	
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
//	public void setStackTrace(Exception e) {
//		this.stackTrace = e.getStackTrace();
//	}
//	
//	public StackTraceElement[] getStackTrace() {
//		return stackTrace;
//	}
	
}
