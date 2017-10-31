package net.mod.action;

import java.util.ArrayList;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class MyPackOrderList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		String status ="";
		String orderby ="";
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)pageNum = "1";
		
		String stat = request.getParameter("status");
		if(stat==null){
			stat ="ing";
		}
		String stat2 = request.getParameter("status2");
		if(stat2==null){
			stat2="none";
		}
		if(stat.equals("ing")){
			if(stat2.equals("confirmyet")){
				status = "<3";
			}else if(stat2.equals("confirm")){
				status = "=3";
			}else if(stat2.equals("canceling")){
				status = "=4";
			}else{
				status = "<5"; 
			}
			orderby="B.date,A.po_res_status";
			
		}else{
			if(stat2.equals("completed")){
				status ="=10";
			}else if(stat2.equals("canceled")){
				status ="=9";
			}else{
				status =">8";
			}
			orderby = "A.po_num desc";
		}
		ModDAO moddao = new ModDAO();
		moddao.Res_Completed(id);
		int count = moddao.PO_Count(id, status);
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 10;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		int pblock = 10;
		int startp=((curpage-1)/pblock)*pblock+1;
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		List<ModTradeInfoBEAN> ModPList = new ArrayList<ModTradeInfoBEAN>();
		ModPList = moddao.MyPackOrder(id, start, pagesize,status,orderby);
		request.setAttribute("status",stat);
		request.setAttribute("status2",stat2);
		request.setAttribute("ModPList", ModPList);
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		afo.setPath("./MyOrder/MyPackOrderList.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
