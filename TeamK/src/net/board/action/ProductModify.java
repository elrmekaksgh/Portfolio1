package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.*;

import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class ProductModify implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProductModify excute()");
		
		int select_num = Integer.parseInt(request.getParameter("select_num"));
		int num = Integer.parseInt(request.getParameter("num"));
		
		//디비객체 생성

		//디비객체 생성
		ProductDAO pdao=new ProductDAO();
		
		ProductBean pb = pdao.getProduct(num);

		request.setAttribute("pb_up", pb);
		request.setAttribute("select_num", select_num);
		
		ActionForward forward=new ActionForward();
		forward.setPath("./product/Product_modify.jsp");
		forward.setRedirect(false);
		return forward;
//		return null;
	}

}

