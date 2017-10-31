package net.pack.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.pack.db.CategoryDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class PackDateAdd implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("PackDateAdd excute()");
		
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		if(user_id == null)
			user_id = "";
//		String subject = request.getParameter("subject");
		
		int num = Integer.parseInt(request.getParameter("num"));
		System.out.println("PackDateAdd num >> " + num);
		
		//디비객체 생성
		PackDAO pdao=new PackDAO();
		
		PackBean pb = pdao.getPack(num);
		
//		request.setAttribute("pb_up", pb);
		
		List date_list;
		date_list = pdao.getPackList(pb.getSubject(), user_id);
		
		request.setAttribute("date_list", date_list);
		
		ActionForward forward=new ActionForward();
		forward.setRedirect(false);
		forward.setPath("./pack/Package_dateAdd.jsp");
		return forward;
//		return null;
	}

}
