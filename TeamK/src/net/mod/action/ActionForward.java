package net.mod.action;

public class ActionForward {
	//이동정보 저장(이동할 페이지주소, 이동할 방식)
	//이동 정보 저장하는 파일 만들기
	//패키지 net.member.action 파일 ActionForward
	//path 멤버변수 이동할 페이지주소
	//isRedirect 멤버 변수 이동할 방식
	private String path;
	private boolean isRedirect;//이동할 방식 true sendRedirect
					   //		false forward이동
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
