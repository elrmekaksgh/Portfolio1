package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.action.ActionForward;
import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class ReplyWrite implements Action{

	@Override
	public net.reply.action.ActionForward excute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ReplyWrite excute()");
		
		request.setCharacterEncoding("UTF-8");
		
		String pageNum = request.getParameter("pageNum");
		int num = Integer.parseInt(request.getParameter("num"));
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		int h_or_s = Integer.parseInt(request.getParameter("secretChk"));
		
		System.out.println("ReplyWrite num >> " + num);
		System.out.println("ReplyWrite pagenum >> " + pageNum);
		
		
		ReplyBean rb = new ReplyBean();
		ReplyDAO rd = new ReplyDAO();
		
		rb.setId(id);
		rb.setContent(content);
		rb.setGroup_del(num);
		rb.setH_or_s(h_or_s);
		
		rd.insertReply(rb);
		
		//ActoinForward 이동정보 담아서 로그인 이동
//		ActionForward forward = new ActionForward();
//		forward.setPath("./PackContent.po");
//		forward.setRedirect(false);
//		return forward;
		return null;
	}
}
