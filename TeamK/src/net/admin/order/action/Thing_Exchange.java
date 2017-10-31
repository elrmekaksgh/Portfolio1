package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Thing_Exchange implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int tnum = Integer.parseInt(request.getParameter("num"));
		AdminDAO admindao = new AdminDAO();
		ModTradeInfoBEAN mtib = admindao.Exchange_info(tnum);
		String [] type = mtib.getTrade_type().split(",");
		mtib.setTrade_type(type[0]);
		request.setAttribute("Color_List", admindao.Color_List(mtib.getOri_num()));
		request.setAttribute("mtib",mtib);
		ActionForward afo = new ActionForward();
		afo.setPath("./Admin/Thing_Exchange.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
