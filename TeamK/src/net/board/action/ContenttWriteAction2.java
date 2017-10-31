package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;

public class ContenttWriteAction2 implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("CategoryWriteAction excute()");
		
		request.setCharacterEncoding("UTF-8");
		//request 파라미터 정보 가져오기
		int num = Integer.parseInt(request.getParameter("num"));
		int re_ref = Integer.parseInt(request.getParameter("re_ref"));
		int re_lev = Integer.parseInt(request.getParameter("re_lev"));
		int re_seq = Integer.parseInt(request.getParameter("re_seq"));		
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		int h_or_s = Integer.parseInt(request.getParameter("secretChk"));
		
		//자바빈 패키지 net.member.db 파일 MemberBean
				//MemberBean 객체 생성
		CommentBean comb = new CommentBean();
				//파라미터 정보 => 자바빈 저장
		comb.setId(id);
		comb.setContent(content);
		comb.setRe_ref(re_ref);
		comb.setRe_lev(re_lev);
		comb.setRe_seq(re_seq);
		comb.setGroup_del(num);
		comb.setH_or_s(h_or_s);
		
		CommentDAO cmodao = new CommentDAO();
		cmodao.reinsertComment(comb);
		
				
				//ActoinForward 이동정보 담아서 로그인 이동
		return null;
	}

}
