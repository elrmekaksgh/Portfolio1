package net.admin.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModDAO;

public class Pack_Res_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String [] pnum = request.getParameterValues("pnum");
		String stat = request.getParameter("stat");
		String [] memo = request.getParameterValues("Cancel_info");
		String po_memo="";
		AdminDAO adao = new AdminDAO();
		int status = 0;
		switch(stat){
			case "bank":status=2;break;
			case "waiting":status=3;break;
			case "cancel_call":status=9;break;
		}
		for(int i =0; i<pnum.length;i++){
			adao.PO_Status_Update(status, pnum[i]);
		}
		if(memo!=null){
			po_memo=memo[1]+","+memo[0];
			if(memo.length>2){
				po_memo +=","+memo[2]+","+memo[3]
							+","+memo[4];
			}
			ModDAO moddao = new ModDAO();
			moddao.Res_Cancel(po_memo,Integer.parseInt(request.getParameter("pnum")));
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('취소 요청 완료');");
			out.println("opener.location.reload();");
			out.println("window.close();");
			out.println("</script>");
			out.close();
		}else{
		ActionForward afo = new ActionForward();
		afo.setPath("./Pack_res.ao");
		afo.setRedirect(true);
		return afo;
		}
		return null;
	}

}
