package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class ProductDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum= request.getParameter("pageNum");

		CategoryBean cb = new CategoryBean();
		CategoryDAO cdao = new CategoryDAO();
		ProductBean pb = new ProductBean();
		ProductDAO pdao = new ProductDAO();
		String car_num = request.getParameter("car_num");
		List productList2 = null;
		productList2 =cdao.getTypeList();
		List productList = null;
		productList =pdao.getProdcutList3(num);
		 
		
		
		request.setAttribute("car_num", car_num);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("productList2", productList2);
		request.setAttribute("productList", productList);
		
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("/product/imgdeleteForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
