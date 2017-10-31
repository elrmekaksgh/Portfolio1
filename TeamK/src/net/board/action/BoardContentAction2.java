package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.board.db.BoardReplyBean;

public class BoardContentAction2 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardContentAction2 execute()");
		request.setCharacterEncoding("utf-8"); 
		
		int num = Integer.parseInt(request.getParameter("num")); //글 번호 num에 불러오기
		String pageNum=request.getParameter("pageNum"); //페이지 번호 불러오기
		
		BoardDAO bdao = new BoardDAO();
		bdao.updateReadcount(num); // num 글번호 조회수 증가시키기
		BoardBean bb = bdao.getBoard(num); //getBoard로 num값의 게시물 정보를 불러와서 BoardBean bb에 넣기 
		int rcount = bdao.getBoardReplyCount(num); //rcount에 리플갯수 저장
		List<BoardReplyBean> lrb = bdao.getBoardReplyList2(num); //num값의 게시물의 모든 리플 lrb에 넣기
		
		request.setAttribute("bb", bb);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("rcount", rcount);
		request.setAttribute("lrb", lrb);
		
		ActionForward forward = new ActionForward();
   		forward.setPath("./board2/content2.jsp");
   		forward.setRedirect(false);	
		
		return forward;
	}

}
