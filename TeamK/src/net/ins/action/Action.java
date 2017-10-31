package net.ins.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	//추상 메서드 - 메서트 틀
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
