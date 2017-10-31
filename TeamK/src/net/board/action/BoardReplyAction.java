package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.BoardReplyBean;

public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardReplyAction execute()");
		request.setCharacterEncoding("utf-8");
		
		BoardReplyBean rb = new BoardReplyBean();
		BoardDAO bdao = new BoardDAO();
		
		String rid = request.getParameter("rId"); //rid에 리플 작성자 아이디 저장 
		String rcontent = request.getParameter("rContent"); //rcontent에 리플내용 저장
		int rNum = Integer.parseInt(request.getParameter("rNum")); //rNum에 글번호 저장
		String pageNum = request.getParameter("pageNum");
		
		rb.setId(rid);
		rb.setContent(rcontent);
		rb.setGroup_del(rNum);
		bdao.insertReplyBoard(rb); //리플 db서버에 저장
		
		int rcount = bdao.getBoardReplyCount(rNum);
		List<BoardReplyBean> lrb = bdao.getBoardReplyList(rNum);
		//AJAX 페이지 board/reply.jsp 때문에 값 다시한번 저장
		request.setAttribute("lrb", lrb);
		request.setAttribute("rcount", rcount);
		request.setAttribute("rNum", String.valueOf(rNum));
	
		
		ActionForward forward = new ActionForward();
   		forward.setPath("./board/reply.jsp");
   		forward.setRedirect(false);	
		
		return forward;
	}
}
