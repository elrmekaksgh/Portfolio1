package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.java.swing.plaf.windows.resources.windows;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tq.util.BCrypt;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberPasschangeAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("Teamk MemberPasschangeAction execute()");
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String pass = request.getParameter("pass");// 기존패스워드
		String pass2 = request.getParameter("pass2");// 기존 패스워드와 비교할 확인용 패스워드
		String changepass = request.getParameter("changepass");// 앞에서받은 변경할 비밀번호
		String changepass2 = request.getParameter("changepass2");// 비밀번호 확인
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		MemberDAO mdao = new MemberDAO();
	
		MemberBean mb = mdao.getMember(id);

		if (BCrypt.checkpw(pass2, pass) == false) {
			out.println("<script>");
			out.println("alert('기존 비밀번호와 틀립니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} else if (changepass == null) {
			out.println("<script>");
			out.println("alert('변경할 비밀번호를 적어주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} else if (BCrypt.checkpw(changepass, pass)) {
			out.println("<script>");
			out.println("alert('기존 비밀번호와 똑같습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} 
		else if (!changepass.equals(changepass2)) {
			out.println("<script>");
			out.println("alert('변경할 비밀번호를 똑같이 적어주세요.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
			return null;
		} 
		else if (BCrypt.checkpw(pass2, pass) && changepass != pass2) {
			
			String newpass = BCrypt.hashpw(changepass, BCrypt.gensalt(12));
			mdao.passchange(id, newpass);
			out.println("<script>");
			out.println("alert('성공!! 회원정보 수정시에는 변경된 비밀번호를 입력해주세요.');");
			out.println("window.close();");
			out.println("</script>");
			out.close();
			return null;
		}

		ActionForward forward = new ActionForward();
		forward.setPath("./MemberUpdate.me");
		forward.setRedirect(true);
		return forward;

	}

}
