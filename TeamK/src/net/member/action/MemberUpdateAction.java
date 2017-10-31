package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tq.util.BCrypt;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberUpdateAction implements Action {

	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Teamk MemberUpdateAction execute()");
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
//		String str = mb.getPass();
//		String pass2 = new String(Base64.decode(str));
		String hashPass=mb.getPass();
		//BCrypt.checkpw(mb.getPass(), hashPass);

		if (BCrypt.checkpw(pass, hashPass)) {

			mb.setEmail(request.getParameter("email"));
			mb.setAddress1(request.getParameter("address1"));
			mb.setAddress2(request.getParameter("address2"));
			mb.setPostcode(request.getParameter("postcode"));
			mb.setMobile(request.getParameter("mobile"));
			mb.setId(request.getParameter("id"));
			mb.setName(request.getParameter("name"));
			mb.setPass(pass);

			int check = mdao.updateMember(mb);

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
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				session.setAttribute("id", id);
				out.println("<script>");
				out.println("alert('수정완료');");
				out.println("location.href='./main.fo'");
				out.println("</script>");
				out.close();
				return null;
			}
		} else {
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
