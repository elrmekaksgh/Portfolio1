package net.ins.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ins.db.interestBEAN;
import net.ins.db.interestDAO;

public class MyInterestAdd implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		interestBEAN inb = new interestBEAN();
		interestDAO indao = new interestDAO();
		inb.setId(id);
		inb.setOri_num(Integer.parseInt(request.getParameter("num")));
		inb.setType(request.getParameter("type"));
		indao.MyInterestAdd(inb);
		return null;
		
	}
	
}
