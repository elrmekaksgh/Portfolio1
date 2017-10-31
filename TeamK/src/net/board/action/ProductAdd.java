package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;
import net.member.db.ProductDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class ProductAdd implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
System.out.println("ProductAdd excute()");
		
		
		int num = Integer.parseInt(request.getParameter("num"));
		String name = request.getParameter("name");
		//디비객체 생성
		ProductDAO pdao=new ProductDAO();

		
		List productaddList;
		productaddList = pdao.getProductAddList(num);
		
		request.setAttribute("productaddList", productaddList);
		request.setAttribute("name", name);
		
		
		ActionForward forward=new ActionForward();
		forward.setPath("./product/ProductAdd.jsp");
		forward.setRedirect(false);
		return forward;
//		return null;
	}

}