package net.pack.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.CategoryDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class PackDateModify implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("PackDateModify excute()");
		//int num가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		int select_num = Integer.parseInt(request.getParameter("select_num"));
		System.out.println("PackDateModify num >> " + num);
		//디비객체 생성
		PackDAO pdao=new PackDAO();
		
		PackBean pb = pdao.getPack(num);
		
		request.setAttribute("pb_up", pb);
		request.setAttribute("select_num", select_num);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./pack/Package_date_modify.jsp");
		forward.setRedirect(false);
		return forward;
//		return null;
	}

}
