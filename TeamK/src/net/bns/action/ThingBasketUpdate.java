package net.bns.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bns.db.TBasketBEAN;
import net.bns.db.bnsDAO;

public class ThingBasketUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		TBasketBEAN tbb = new TBasketBEAN();
		tbb.setNum(Integer.parseInt(request.getParameter("num")));
		tbb.setCount(Integer.parseInt(request.getParameter("tcount")));
		tbb.setCost(Integer.parseInt(request.getParameter("tcost")));
		bnsDAO bnsdao = new bnsDAO();
		bnsdao.TBasketUpdate(tbb);		
		return null;
	}
	
}
