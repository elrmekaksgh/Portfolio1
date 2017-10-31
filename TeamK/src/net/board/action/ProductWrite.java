package net.board.action;

import java.util.List;
import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;


public class ProductWrite implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProductWrite excute()");
		
		
		request.setCharacterEncoding("UTF-8");
		CategoryBean cb = new CategoryBean();
		CategoryDAO cdao = new CategoryDAO();
		ProductBean pb = new ProductBean();
		ProductDAO pdao = new ProductDAO();
		//request 파라미터 정보 가져오기
		String id = "admin";
		
		
		
		List productList = null;
		productList =pdao.getProdcutList2();
		
		
		List productList2 = null;
		productList2 =cdao.getTypeList();
		
		List CategoryList = null;
		CategoryList = cdao.getTypeList2();
		
		for (int i = 0; i < productList2.size(); i++) {

			cb = (CategoryBean) productList2.get(i);
		
		}
		request.setAttribute("productList", productList);
		request.setAttribute("productList2", productList2);
		request.setAttribute("CategoryList", CategoryList);
		request.setAttribute("id", id);
				
				//ActoinForward 이동정보 담아서 로그인 이동
				ActionForward forward = new ActionForward();
				forward.setPath("/product/imgwriteForm.jsp");
				forward.setRedirect(false);
				return forward;
	}

}
