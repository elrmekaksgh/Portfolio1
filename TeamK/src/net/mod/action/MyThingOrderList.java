package net.mod.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class MyThingOrderList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("id");
		ModDAO moddao = new ModDAO();
		String pageNum = request.getParameter("pageNum");
		if (pageNum == null)pageNum = "1";
		String stat = request.getParameter("status");
		if(stat==null)stat = "ing";
		String status="";
		if(stat.equals("ing")){
			status = "<10"; 
		}else if(stat.equals("completed")){
			status ="=10";
		}
		int count = moddao.TI_Count(id,status);
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 5;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		int pblock = 10;
		int startp=((curpage-1)/pblock)*pblock+1;
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		List<Vector> ModList = new ArrayList<Vector>(  );
		ModTradeInfoBEAN mtib;
		Vector v;
		if (count != 0) {
			List<ModTradeInfoBEAN> TradeInfoList = moddao.TO_ReadModTI(id, start, pagesize,status);
			for (int i = 0; i < TradeInfoList.size(); i++) {
				v = new Vector();
				mtib = TradeInfoList.get(i);
				String [] t_type = mtib.getTrade_type().split(",");
				mtib.setTrade_type(t_type[0]);
				List<ModTradeInfoBEAN> ModThingList = moddao.MyThingOrder(mtib.getTi_num());
				v.addElement(mtib);
				v.addElement(ModThingList);
				ModList.add(v);
			}
		}
		request.setAttribute("status", stat);
		request.setAttribute("ModList", ModList);
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		ActionForward afo = new ActionForward();
		afo.setPath("./MyOrder/MyThingOrderList.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
