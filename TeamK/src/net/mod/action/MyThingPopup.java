package net.mod.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class MyThingPopup implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("MyThingPopUp");
		request.setCharacterEncoding("utf-8");

		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println("ProductContent num >> " + num);
		
		ProductDAO pddao = new ProductDAO();
		ProductBean pdb = new ProductBean();
		
		pdb = pddao.getProduct2(num);

		request.setAttribute("pdb", pdb);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./MyOrder/MyThingPopup.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
