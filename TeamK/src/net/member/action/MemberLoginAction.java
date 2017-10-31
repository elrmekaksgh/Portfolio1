package net.member.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberLoginAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("MemberLoginAction execute()");
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String pass = request.getParameter("pass");
		String exurl = request.getParameter("exurl");

		MemberBean mb = new MemberBean();

		mb.setId(id);
		mb.setPass(pass);

		MemberDAO mdao = new MemberDAO();
		int check = mdao.idCheck(id, pass);

		if (check == 0) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호틀림');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;

		} else if (check == -1) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('아이디없음');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;

		} else {
			HttpSession session = request.getSession();
			session.setAttribute("id", id);
			ActionForward forward = new ActionForward();
			
			//아이디랑 패스워드 찾은후 로그인시에 로그인으로 액션 제어
			if (exurl.equals("http://localhost:8080/TeamK/MemberFindIdAction.me")
					|| exurl.equals("http://localhost:8080/TeamK/MemberFindPassAction.me")) {
				forward.setPath("./main.fo");
				forward.setRedirect(true);
				return forward;
			} else {
				forward.setPath(exurl);
				forward.setRedirect(true);
			}
			return forward;
		}

	}
}
