package net.pack.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.PackDAO;

public class PackDateAddChk implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("PackDateAddChk excute()");
		
		request.setCharacterEncoding("UTF-8");
		
		String subject = request.getParameter("subject");
		String date = request.getParameter("date");
		
		PackDAO pdao = new PackDAO();
		
		System.out.println("PackDateChk subject >> " + subject);
		System.out.println("PackDateChk date >> " + date);
		
		int check = pdao.PackDateAddChk(subject, date);
		
		System.out.println("PackDateChk check >> " + check);
		
//		if (check == 1)
//		{
//			response.setContentType("text/html; charset=UTF-8");
//			PrintWriter out = response.getWriter();
//			out.println("<script>");
//			out.println("alert('이미 추가된 날짜입니다');");
////			out.println("history.go(-1)");
//			out.println("</script>");
//			out.close();
//		}
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./pack/PackageDateAddCheck.jsp?check=" + check);
		forward.setRedirect(false);
		return forward;
//		return null;
	}

}
