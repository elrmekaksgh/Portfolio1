package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyUpdateActoin implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("ReplyUpdate excute()");
		
		request.setCharacterEncoding("UTF-8");
		
//		String pageNum = request.getParameter("pageNum");
		
		int h_or_s = Integer.parseInt(request.getParameter("secretChk"));
		int num = Integer.parseInt(request.getParameter("num"));		
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		
		System.out.println("reply update >> " + content);
		System.out.println(num);
		System.out.println("reply update >> " + h_or_s);
		
		ReplyDAO rdao = new ReplyDAO();
		
		
		rdao.updateReply(content, num, h_or_s);
		
		
		
		//ActoinForward 이동정보 담아서 로그인 이동
//		ActionForward forward = new ActionForward();
//		forward.setPath("./PackContent.po");
//		forward.setRedirect(false);
//		return forward;
		return null;
	}

}
