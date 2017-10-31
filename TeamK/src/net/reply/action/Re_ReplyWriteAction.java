package net.reply.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.db.ReplyBean;
import net.reply.db.ReplyDAO;

public class Re_ReplyWriteAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("Re_ReplyWrite excute()");
		
		request.setCharacterEncoding("UTF-8");
		
		String pageNum = request.getParameter("repageNum");
		int num = Integer.parseInt(request.getParameter("num"));
		int re_ref = Integer.parseInt(request.getParameter("re_ref"));
		int re_lev = Integer.parseInt(request.getParameter("re_lev"));
		int re_seq = Integer.parseInt(request.getParameter("re_seq"));		
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		int h_or_s = Integer.parseInt(request.getParameter("secretChk"));
		
		System.out.println(pageNum);
		System.out.println(num);
		System.out.println(re_ref);
		System.out.println(re_lev);
		System.out.println(re_seq);

		ReplyDAO rdao = new ReplyDAO();
		ReplyBean rb = new ReplyBean();
		
		rb.setId(id);
		rb.setContent(content);
		rb.setRe_ref(re_ref);
		rb.setRe_lev(re_lev);
		rb.setRe_seq(re_seq);
		rb.setGroup_del(num);
		rb.setH_or_s(h_or_s);
		
		rdao.re_insertReply(rb);
		
		
		
		//ActoinForward 이동정보 담아서 로그인 이동
//		ActionForward forward = new ActionForward();
//		forward.setPath("./PackContent.po");
//		forward.setRedirect(false);
//		return forward;
		return null;
	}

}
