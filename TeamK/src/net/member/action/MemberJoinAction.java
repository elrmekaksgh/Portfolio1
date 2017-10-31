package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tq.util.*;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("Teamk MemberJoinAction execute()");
		request.setCharacterEncoding("utf-8");

		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String mobile = request.getParameter("mobile");
		String postcode = request.getParameter("postcode");

		MemberBean mb = new MemberBean();
		MemberDAO mdao = new MemberDAO();
		
		int check = mdao.joinIdCheck(id);
		if (check == 1) {
			out.println("<script>");
			out.println("alert('사용중인 아이디 입니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		} else if (check == 0) {
			mdao = new MemberDAO();
			
			String repass = request.getParameter("pass");
			String pass = BCrypt.hashpw(repass, BCrypt.gensalt(12));

			mb.setId(id);
			mb.setPass(pass);
			mb.setName(name);
			mb.setPostcode(postcode);
			mb.setAddress1(address1);
			mb.setAddress2(address2);
			mb.setMobile(mobile);
			mb.setEmail(email);
			
			mdao.insertMember(mb);
			
			out.println("<script>");
			out.println("alert('회원가입완료.');");
			out.println("location.href='main.fo'");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
