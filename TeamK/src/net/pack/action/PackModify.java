package net.pack.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.CategoryDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;


public class PackModify implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("PackModify excute()");
		//int num가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		//디비객체 생성
		PackDAO pdao=new PackDAO();
		CategoryDAO cdao = new CategoryDAO();
		
		PackBean pb = pdao.getPack(num);
		
		List CategoryList = cdao.getCategoryList();
		request.setAttribute("CategoryList", CategoryList);
		
		request.setAttribute("pb", pb);
		request.setAttribute("num", num);
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./pack/Package_modify.jsp");
		return forward;
	}

}
