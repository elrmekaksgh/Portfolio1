package net.ins.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ins.db.interestBEAN;
import net.ins.db.interestDAO;

public class MyInterestCheck implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		interestBEAN inb = new interestBEAN();
		inb.setOri_num(Integer.parseInt(request.getParameter("num")));
		inb.setId(id);
		inb.setType(request.getParameter("type"));
		interestDAO idao = new interestDAO();
		int check = idao.MyInterestCheck(inb);
		request.setAttribute("check", check);
		ActionForward afo = new ActionForward();
		afo.setPath("/ins/MyInterestCheck.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
