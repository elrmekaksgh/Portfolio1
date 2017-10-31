package net.member.action;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.member.db.MemberDAO;

public class MemberEmailchangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Teamk MemberEmailchangeAction execute()");
		request.setCharacterEncoding("utf-8");
		String email = request.getParameter("email");
	
		String checknum = request.getParameter("checknum");

		checknum = request.getParameter("checknum");
		if (checknum.equals(request.getParameter("number"))) {

			request.setCharacterEncoding("utf-8");
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			MemberDAO mdao = new MemberDAO();

			mdao.emailchange(id, email);

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('인증 완료');");
			out.println("window.opener.location.reload();");
			out.println("window.close();");
			out.println("</script>");
			out.close();

		} else {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('인증번호를 입력해주세요!')");
			out.println("history.back()");
			out.println("</script>");

		} // if
		return null;
	}// else

}
