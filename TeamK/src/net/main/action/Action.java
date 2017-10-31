package net.main.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.main.action.ActionForward;

public interface Action {
	//추상메서드 - 메서드 틀
	
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
