package net.mod.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Res_Cancel_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String [] memo = request.getParameterValues("Cancel_info");
		String po_memo=memo[1]+","+memo[0];
		if(memo.length>2){
			po_memo +=","+memo[2]+","+memo[3]
						+","+memo[4];
		}
		ModDAO moddao = new ModDAO();
		int check =moddao.Res_Cancel(po_memo,Integer.parseInt(request.getParameter("pnum")));
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(check ==1){
			out.println("<script>");
			out.println("alert('취소 신청이 완료 되었습니다!');");
			out.println("window.opener.location.reload();");
			out.println("window.close();");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
