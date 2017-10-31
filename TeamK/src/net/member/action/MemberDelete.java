package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberDelete implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("MemberDelete execute()");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		ActionForward forward = new ActionForward();

		if (id == null) {
		//아이디 값이 없으면 다시 로그인창으로 이동
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}

		MemberDAO mdao = new MemberDAO();

		MemberBean mb = mdao.getMember(id);
		//맴버의 아이디값에 대한 정보를 가지고 삭제창으로 이동
		request.setAttribute("mb", mb);

		forward.setPath("./member/deleteForm.jsp");
		forward.setRedirect(false);
		return forward;

	}

}
