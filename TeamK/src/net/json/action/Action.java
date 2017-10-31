package net.json.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

public interface Action {
	//�߻� �޼��� - �޼�Ʈ Ʋ
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
