package net.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.MemberBean;
import net.member.db.MemberDAO;

public class MemberFindIdAction implements Action {


	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String name=request.getParameter("name");
		String email=request.getParameter("email");
		
		MemberDAO mdao=new MemberDAO();
		String id = mdao.findid(name, email);
		
		if(id!=""){
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('ID는 ["+id+"] 입니다');");
			out.println("location.href='./MemberLogin.me'");
			out.println("</script>");
			out.close();
		}else if(id==""){
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('이름,이메일이 일치하지 않습니다.');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}

}
