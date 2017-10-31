package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyUpdate implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		System.out.println("Re_ReplyUpdate excute()");
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		
		String pageNum = request.getParameter("pageNum");
		int num = Integer.parseInt(request.getParameter("num"));

		System.out.println(pageNum);
		System.out.println(num);
		
		ReplyDAO rdao = new ReplyDAO();
		ReplyBean rb = new ReplyBean();

		rb.setGroup_del(num);
		
		rb = rdao.getComment(num);
		
		session.setAttribute("rb", rb);
		
		//ActoinForward 이동정보 담아서 로그인 이동
//		ActionForward forward = new ActionForward();
//		forward.setPath("./pack/Package_content.jsp");
//		forward.setRedirect(false);
//		return forward;
		return null;
	}

}
