package net.admin.order.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class Thing_Exchange_Action implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		AdminDAO admindao = new AdminDAO();
		int o_num = Integer.parseInt(request.getParameter("o_num"));
		String[]tnum = request.getParameterValues("tnum");
		String[]count = request.getParameterValues("count");
		String[]color = request.getParameterValues("color");
		String[]size = request.getParameterValues("size");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		for(int i = 0; i < tnum.length;i++){
			int check = admindao.Thing_Stock_Check(Integer.parseInt(tnum[i]));
			if(check<Integer.parseInt(count[i])){
				out.println("<script>");
				out.println("alert('"+color[i]+"-"+size[i]+
						"재고 부족\\n남은 수량 : "+check+"개');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}
		}
		for(int i = 0; i < tnum.length;i++){
			admindao.Stock_Mul(Integer.parseInt(tnum[i]), Integer.parseInt(count[i]));
		}
		String[]cost=request.getParameterValues("result");
		String memo=admindao.O_Memo_Read(o_num);
		int stock_add = 0;
		for(int i = 0; i <tnum.length; i++){
			stock_add+=Integer.parseInt(count[i]);
			memo+="ㅨ"+color[i]+","+size[i]+","+count[i]+","+cost[i];
					
		}
		String trans_num = request.getParameter("trans_num");
		String total_cost = request.getParameter("t_cost");
		String comment = request.getParameter("comment");
		memo+="ㅨ"+total_cost+"ㅨ"+comment;
		admindao.Stock_Add(Integer.parseInt(request.getParameter("ori_num")), stock_add);
		admindao.Thing_Exchange_Action(o_num, trans_num, memo);
		out.println("<script>");
		out.println("alert('교환 배송이 정상 적으로 처리 되었습니다');");
		out.println("opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
		out.close();
		return null;
	}

}
