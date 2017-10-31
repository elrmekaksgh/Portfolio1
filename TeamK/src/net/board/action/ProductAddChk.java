package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.ProductDAO;
import net.pack.db.PackDAO;

public class ProductAddChk implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProductAddChk excute()");
		
		
		String name = request.getParameter("name");
		String size = request.getParameter("size");
		String color = request.getParameter("color");
		System.out.println(name);
		System.out.println(size);
		System.out.println(color);
		
		//디비객체 생성
		ProductDAO pdao=new ProductDAO();

		int check = pdao.ProductAddChk(name, color, size);

		
		
		ActionForward forward=new ActionForward();
		forward.setPath("./product/ProductAddCheck.jsp?check=" + check);
		forward.setRedirect(false);
		return forward;
//		return null;
	}

}
