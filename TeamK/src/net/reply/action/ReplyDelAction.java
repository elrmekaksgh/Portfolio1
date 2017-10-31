package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.db.ReplyDAO;

public class ReplyDelAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("ReplyDel excute()");
		
		int renum = Integer.parseInt(request.getParameter("renum"));
		String id = request.getParameter("id");
		
		System.out.println("ReplyDel renum >> " + renum);
		System.out.println("ReplyDel id >> " + id);
		
		ReplyDAO rdao = new ReplyDAO();
		
		rdao.deleteReply(renum, id);
		
		//ActoinForward 이동정보 담아서 로그인 이동
//		ActionForward forward = new ActionForward();
//		forward.setPath("./Package_content.jsp");
//		forward.setRedirect(false);
//		return forward;
		return null;
	}
	

}
