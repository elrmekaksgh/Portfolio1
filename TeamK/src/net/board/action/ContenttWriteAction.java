package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;

public class ContenttWriteAction implements Action{

	/* (non-Javadoc)
	 * @see net.member.action.Action#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("CategoryWriteAction excute()");
		
		request.setCharacterEncoding("UTF-8");
		//request 파라미터 정보 가져오기
		String pageNum = request.getParameter("pageNum");
		int num = Integer.parseInt(request.getParameter("num"));
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		int h_or_s = Integer.parseInt(request.getParameter("secretChk"));
		System.out.println(id);
		System.out.println(content);
		System.out.println(h_or_s);
		
		//자바빈 패키지 net.member.db 파일 MemberBean
				//MemberBean 객체 생성
		CommentBean comb = new CommentBean();
				//파라미터 정보 => 자바빈 저장
		comb.setId(id);
		comb.setContent(content);
		comb.setH_or_s(h_or_s);
		comb.setGroup_del(num);
		
		CommentDAO cmodao = new CommentDAO();
		cmodao.insertComment(comb);
		
				
				//ActoinForward 이동정보 담아서 로그인 이동
		return null;
	}



}
