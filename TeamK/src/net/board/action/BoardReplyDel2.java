package net.board.action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.BoardReplyBean;

public class BoardReplyDel2 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		System.out.println("BoardReplyDel2");
		int num = Integer.parseInt(request.getParameter("num"));
		int rNum = Integer.parseInt(request.getParameter("rNum"));
		
		BoardDAO bdao = new BoardDAO();
		bdao.deleteReply(num);
		
		List<BoardReplyBean> lrb = bdao.getBoardReplyList(rNum);
		
		BoardReplyBean rb = new BoardReplyBean();
		
		request.setAttribute("lrb", lrb);
		int rcount = bdao.getBoardReplyCount(rNum);
		request.setAttribute("rcount", rcount);
		request.setAttribute("rNum", String.valueOf(rNum));
		ActionForward forward = new ActionForward();
   		forward.setPath("./board/reply.jsp");
   		forward.setRedirect(false);	
		
		return forward;
		
	}

}
