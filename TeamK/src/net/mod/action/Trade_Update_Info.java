package net.mod.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Trade_Update_Info implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		int o_num = Integer.parseInt(request.getParameter("num"));
		ModDAO moddao = new ModDAO();
		ModTradeInfoBEAN mtib = moddao.TO_Cancel_or_Exchange(o_num);
		request.setAttribute("mtib", mtib);
		afo.setPath("./MyOrder/Trade_Update_Info.jsp");
		afo.setRedirect(false);
		return afo;
		
	}
	

}
