package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tq.util.BCrypt;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberDeleteAction implements Action {
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("MemberDeleteAction execute()");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String pass = request.getParameter("pass");
		ActionForward forward = new ActionForward();

		if (id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		MemberDAO mdao = new MemberDAO();
		MemberBean mb = new MemberBean();

		mb = mdao.getMember(id);
		String hashPass = mb.getPass();
		

		if (BCrypt.checkpw(pass, hashPass)) {

			int check = mdao.deleteMember(mb);

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
				session.removeAttribute("id");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('삭제완료');");
				out.println("location.href='main.fo';");
				out.println("</script>");
				out.close();
				return null;
			}
		}else {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호틀림');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		}
		
	}
}
