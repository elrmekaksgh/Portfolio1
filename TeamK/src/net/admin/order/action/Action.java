package net.admin.order.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Action {
	//�߻� �޼��� - �޼�Ʈ Ʋ
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)throws Exception;
}
