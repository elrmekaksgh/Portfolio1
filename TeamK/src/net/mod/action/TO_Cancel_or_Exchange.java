package net.mod.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class TO_Cancel_or_Exchange implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		int o_num = Integer.parseInt(request.getParameter("num"));
		ModDAO mdao = new ModDAO();
		ModTradeInfoBEAN mtib = mdao.TO_Cancel_or_Exchange(o_num);
		String [] t_type = mtib.getTrade_type().split(",");
		mtib.setTrade_type(t_type[0]);
		afo.setPath("./MyOrder/TO_Cancel_or_Exchange.jsp");
		request.setAttribute("mtib", mtib);
		afo.setRedirect(false);
		return afo;
	}

}
