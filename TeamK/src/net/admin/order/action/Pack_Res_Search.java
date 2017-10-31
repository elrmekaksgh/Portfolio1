package net.admin.order.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Pack_Res_Search implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		String search_type = request.getParameter("search_type");
		String search = request.getParameter("search");
		switch(search_type){
			case "id":search_type=" and C.id like '%"+search+"%'";break;
			case "payer":search_type=" and C.ti_trade_payer like '%"+search+"%'";break;
			case "trade_num":search_type=" and A.po_trade_num like '%"+search+"%'";break;
		}
		
		String stat = request.getParameter("status");
		if(stat==null){
			stat="ing";
		}
		String stat2 = request.getParameter("status2");
		if(stat2==null){
			stat2 ="none";
		}
		System.out.println(stat2);
		String status="";
		String orderby = "";
		if(stat.equals("ing")){
			orderby="A.po_num";
			if(stat2.equals("none")){
				status = "<5";
			}else if(stat2.equals("bank")){
				status = "=1";
				
			}else if(stat2.equals("waiting")){
				status = "=2";
			}else if(stat2.equals("confirm")){
				status = "=3";
			}else{
				status = "=4";
				orderby = "B.date";
			}
			
			
		}else{
			if(stat2.equals("none")){
				status =">8";
			}else if(stat2.equals("completed")){
				status ="=10";
			}else{
				status ="=9";
			}
			orderby = "A.po_num desc";
		}
		status +=search_type; 
		String pageNum = request.getParameter("pageNum");
		AdminDAO adao = new AdminDAO();
		int count = adao.Pack_Res_Count(status);
		if (pageNum == null)
			pageNum = "1";
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 10;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		int pblock = 10;
		int startp=((curpage-1)/pblock)*pblock+1;
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		List<ModTradeInfoBEAN>Pack_Res_List = adao.Pack_Res(status, start, pagesize,orderby);
		request.setAttribute("Pack_Res_List", Pack_Res_List);
		request.setAttribute("search", search);
		request.setAttribute("search_type", search_type);
		request.setAttribute("pblock", pblock);
		request.setAttribute("status2", stat2);
		request.setAttribute("status", stat);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		afo.setPath("./Admin/Pack_Res_Search.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
