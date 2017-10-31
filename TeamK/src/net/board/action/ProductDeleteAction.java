package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.member.db.ProductDAO;
import net.pack.db.PackDAO;

public class ProductDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		int num = Integer.parseInt(request.getParameter("num"));
		ProductDAO pdao = new ProductDAO();
		
		pdao.deleteProduct(num);
		
		
		return null;
	}

}
