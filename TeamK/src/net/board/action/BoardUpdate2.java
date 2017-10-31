package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardUpdate2 implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardUpdate2 execute()");
		request.setCharacterEncoding("utf-8");
	
		
		BoardDAO bdao = new BoardDAO();
		
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum=request.getParameter("pageNum"); 
		
		BoardBean bb = bdao.getBoard(num);
		
		request.setAttribute("bb", bb);
		request.setAttribute("pageNum", pageNum);
		
		ActionForward forward = new ActionForward();
   		forward.setPath("./board2/updateForm2.jsp");
   		forward.setRedirect(false);	
   	
		
		return forward;
	}

}
