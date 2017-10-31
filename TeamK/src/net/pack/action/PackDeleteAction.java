package net.pack.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.PackDAO;

public class PackDeleteAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		int num = Integer.parseInt(request.getParameter("num"));
		PackDAO pdao = new PackDAO();
		
		pdao.deletePack(num);
		
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('글이 삭제되었습니다');");
		out.println("location.href='./PackList.po';");
		out.println("</script>");
		out.close();
	
		
//		return null;
		
//		ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("PackList.po");
		forward.setRedirect(false);
		return forward;
	}
}
