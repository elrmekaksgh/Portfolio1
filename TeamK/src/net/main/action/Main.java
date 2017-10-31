package net.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.CategoryDAO;
import net.pack.db.PackDAO;
import net.member.db.ProductDAO;

public class Main implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("MainAction excute()");
		
		request.setCharacterEncoding("UTF-8");
		
		// 디비 객체 생성 DAO
		CategoryDAO cdao = new CategoryDAO();
		PackDAO pdao = new PackDAO();
		ProductDAO prodao = new ProductDAO();
		// 지역 분류명 리스트 구하기&보내기
		List CategoryList = cdao.getCategoryList();
		request.setAttribute("CategoryList", CategoryList);
		
		// 메인 페이지 패키지 추천 상품 3개 
		List PackList = pdao.getPackList(0, 3);
		request.setAttribute("PackList", PackList);
		
		List ProductImgList = prodao.getProductImgList();
		request.setAttribute("ProductImgList", ProductImgList);

		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./main/main.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
