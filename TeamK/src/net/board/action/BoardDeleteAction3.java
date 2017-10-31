package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class BoardDeleteAction3 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		int num = Integer.parseInt(request.getParameter("num")); 
		String id = request.getParameter("id");
		
		BoardDAO bdao = new BoardDAO();
		bdao.deleteBoard(num, id);
		
		PrintWriter out=response.getWriter();
		out.println("<script>");
		out.println("alert('삭제 성공');");
		out.println("location.href='./BoardList3.bo'"); 
		out.println("</script>");
		out.close();
		return null;
		
	}

}
