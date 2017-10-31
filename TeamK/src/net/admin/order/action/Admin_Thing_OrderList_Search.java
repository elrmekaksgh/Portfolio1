package net.admin.order.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Admin_Thing_OrderList_Search implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		String pageNum = request.getParameter("pageNum");
		String stat = request.getParameter("status");
		String stat2 = request.getParameter("status2");
		String search_type=request.getParameter("search_type");
		String search=request.getParameter("search");
		String ti_status="";
		String to_status="";
		
		if(stat ==null)stat = "ing";
		if(stat2==null)stat2="none";
		switch(stat2){
			case "none": to_status="<8";break;
			case "bank":to_status="=1";break;
			case "ready":to_status="=2";break;
			case "sending":to_status="=3";break;
			case "arrived":to_status="=4";break;
			case "exchange":to_status="=5";break;
			case "cancel":to_status="=6";break;
		}
		switch(stat){
			case "ing": ti_status="<8";break;
			case "completed":ti_status="=10";
				to_status=">8";break;
		}
		switch(search_type){
			case "trade_num":
				ti_status+=" and ti_num="+search; break;
			case "id":
				ti_status+=" and id like '%"+search+"%'"; break;
			case "payer":
				ti_status+=" and ti_trade_payer like '%"+search+"%'"; break;
			case "moblie":
				ti_status+=" and ti_receive_mobile like '%"+search+"%'"; break;
			case "name":
				ti_status+=" and ti_receive_name like '%"+search+"%'"; break;
			case "trans_num":
				to_status+=" and o_trans_num like '%"+search+"%'"; break;
		}
		System.out.println(to_status+">>>>>>"+ti_status);
		AdminDAO adao = new AdminDAO();
		ModDAO moddao = new ModDAO();
		int count = adao.Thing_Order_Count(to_status, ti_status);
		if (pageNum == null)
			pageNum = "1";
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 5;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		int pblock = 10;
		int startp=((curpage-1)/pblock)*pblock+1;
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		List<Vector> Thing_Order_List = new ArrayList<Vector>();
		ModTradeInfoBEAN mtib;
		Vector v;
		if (count != 0) {
			List<ModTradeInfoBEAN> TradeInfoList = adao.Thing_Order_List(to_status,ti_status,start, pagesize);
			for (int i = 0; i < TradeInfoList.size(); i++) {
				v = new Vector();
				mtib = TradeInfoList.get(i);
				String [] t_type = mtib.getTrade_type().split(",");
				mtib.setTrade_type(t_type[0]);
				List<ModTradeInfoBEAN> ModThingList = adao.ADThingOrder(mtib.getTi_num(),to_status);
				v.addElement(mtib);
				v.addElement(ModThingList);
				Thing_Order_List.add(v);
			}
		}
		request.setAttribute("Thing_Order_List", Thing_Order_List);
		request.setAttribute("status", stat);
		request.setAttribute("status2", stat2);
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		afo.setPath("./Admin/Admin_Thing_OrderList_Search.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
