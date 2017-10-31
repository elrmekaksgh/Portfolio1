package net.main.action;

public class ActionForward {

	//이동정보 저장(이동할 페이지주소, 이동할 방식)
	//이동정보저장하는 파일 만들기
	//패키지 net.member.action 파일 ActionForward
	//Path 맴버변수 이동할 페이지주소
	//isRedirect 맴버변수 이동할 방식
	
	
	//이동할 페이지주소
	//이동할 방식 true/false
	//true -> sendReDirect
	//false -> forward
	
	private String path;
	private boolean isRedirect;
	
	
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
	
}
