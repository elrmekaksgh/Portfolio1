package net.main.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.CategoryDAO;

public class Index implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		
		CategoryDAO cdao = new CategoryDAO();
		
		// 지역 분류명 리스트 구하기&보내기
		List CategoryList = cdao.getCategoryList();
		request.setAttribute("CategoryList", CategoryList);

		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./main/index.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
