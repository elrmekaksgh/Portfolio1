package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;

public class ContentDeleteAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ContentDeleteAction excute()");
		
		request.setCharacterEncoding("UTF-8");
		//request 파라미터 정보 가져오기
		String id = request.getParameter("id");
	
		int num = Integer.parseInt(request.getParameter("num"));
		//자바빈 패키지 net.member.db 파일 MemberBean
				//MemberBean 객체 생성
		CommentBean comb = new CommentBean();
				//파라미터 정보 => 자바빈 저장
		comb.setId(id);
		comb.setNum(num);

		CommentDAO cmodao = new CommentDAO();
		cmodao.deleteComment(comb);
		
				
				//ActoinForward 이동정보 담아서 로그인 이동
//				ActionForward forward = new ActionForward();
//				forward.setPath("./ProductContent.bo?num="+num+ "&pageNum="+pageNum);
//				forward.setRedirect(true);
				return null;
	}

}

