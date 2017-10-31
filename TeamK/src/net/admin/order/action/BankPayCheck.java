package net.admin.order.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class BankPayCheck implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String pageNum = request.getParameter("pageNum");
		AdminDAO adao = new AdminDAO();
		ModDAO moddao = new ModDAO();
		int count = adao.Ti_Count(1);
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
		List<Vector> BankPayList = new ArrayList<Vector>();
		ModTradeInfoBEAN mtib;
		Vector v;
		if (count != 0) {
			List<ModTradeInfoBEAN> TradeInfoList = adao.StatusPayList(1,start, pagesize);
			for (int i = 0; i < TradeInfoList.size(); i++) {
				v = new Vector();
				mtib = TradeInfoList.get(i);
				String [] t_type = mtib.getTrade_type().split(",");
				mtib.setTrade_type(t_type[0]);
				List<ModTradeInfoBEAN> ModPackList = moddao.PackOrder(mtib.getTi_num());
				List<ModTradeInfoBEAN> ModThingList = moddao.MyThingOrder(mtib.getTi_num());
				v.addElement(mtib);
				v.addElement(ModPackList);
				v.addElement(ModThingList);
				BankPayList.add(v);
			}
		}
		request.setAttribute("ModList", BankPayList);
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		ActionForward afo = new ActionForward();
		afo.setPath("./Admin/BankPayCheck.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
