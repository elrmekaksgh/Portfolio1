package net.admin.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModTradeInfoBEAN;

public class TO_Status_Update implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminDAO admindao = new AdminDAO();
		String set="";
		int status = Integer.parseInt(request.getParameter("status"));
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(status==2){
			String [] o_num = request.getParameterValues("o_num");
			String [] Trans_Num = request.getParameterValues("Trans_Num");
			for(int i =0 ; i<o_num.length; i++){
				if(Trans_Num[i].length()!=0){
					admindao.Trans_Num_Insert(o_num[i], Trans_Num[i]);
				}
			}
			out.println("<script>");
			out.println("alert('송장 번호가 입력되었습니다');");
			out.println("location.href='"+request.getHeader("referer")+"';");
			out.println("</script>");
			out.close();
		}else{
			int o_num = Integer.parseInt(request.getParameter("o_num"));
			String trans_num= request.getParameter("trans_num");
			String memo = request.getParameter("trans_memo");
			ModTradeInfoBEAN mtib = admindao.TO_Info_Read(o_num);
			String [] reason = mtib.getO_memo().split("ㅨ");
			String []memoarr=reason[0].split(",");
			if(status==3){
				int t_count = Integer.parseInt(memoarr[1]);
				admindao.Thing_Stock_Plus(mtib.getOri_num(),
					mtib.getThing_count()+t_count);
				String stat = " o_status="+status+", o_trans_num='"+trans_num+
						"', o_memo='"+mtib.getO_memo()+"ㅨ"+memo+"'";
				admindao.TO_Str_Status_Update(stat,o_num);
			}else if(status==9){
				int t_count = Integer.parseInt(memoarr[2]);
				admindao.Thing_Stock_Plus(mtib.getOri_num(),
						mtib.getThing_count()+t_count);
				String stat = " o_status="+status;
				admindao.TO_Str_Status_Update(stat, o_num);
				admindao.Ti_Status_Complet_Update(
						Integer.parseInt(request.getParameter("ti_num")));
			}
		}
		return null;
	}

}
