package net.json.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class size implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		int ori_num = Integer.parseInt(request.getParameter("ori_num"));
		String color = request.getParameter("color");
		AdminDAO admindao = new AdminDAO();
		request.setAttribute("arr", admindao.Size_Info_Read(ori_num, color));
		afo.setPath("./Admin/size.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
