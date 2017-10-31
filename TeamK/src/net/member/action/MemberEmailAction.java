package net.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.PrintWriter;

public class MemberEmailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("MemberEmailAction execute()");

		String checknum = request.getParameter("checknum");
		System.out.println(checknum);
		if (checknum.equals(request.getParameter("number"))) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('인증완료');");
			out.println("window.opener.document.fr.eckecknum.value = '1';");
			out.println("window.close();");
			out.println("</script>");
			out.close();
		} else if (!checknum.equals(request.getParameter("number"))) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('인증 실패 . 앞뒤 공백이나 인증번호를 다시 정확하게 확인해주세요.');");
			out.println("history.back()");
			out.println("</script>");
			out.close();
		}

		return null;
	}

}
