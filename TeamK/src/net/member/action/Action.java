package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	// 추상매서드 - 매서드 틀
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
