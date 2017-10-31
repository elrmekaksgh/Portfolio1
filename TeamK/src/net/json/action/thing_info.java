package net.json.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class thing_info implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		int num = Integer.parseInt(request.getParameter("num"));
		AdminDAO admindao = new AdminDAO();
		request.setAttribute("obj", admindao.Thing_Exchange_Add(num));
		afo.setPath("./Admin/thing_info.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
