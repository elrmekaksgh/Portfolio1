package net.bns.action;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.bns.db.PBasketBEAN;
import net.bns.db.TBasketBEAN;
import net.bns.db.bnsDAO;

public class MyBasketList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);
		ActionForward afo = new ActionForward();
		if(id==null){
			afo.setPath("./Main.bns");
			afo.setRedirect(true);
			return afo;
		}
		bnsDAO bnsdao = new bnsDAO();
		List<PBasketBEAN> PackBasket = bnsdao.PackBasket(id,1,5);
		//List<bnsBEAN> OrderThing = bnsdao.Order(id,1,5);
		List<TBasketBEAN> ThingBasket = bnsdao.ThingBasket(id,1,5);
		//int [] count = {0,0};
		int packcount =bnsdao.BasketCount(id,"P");
		int thingcount =bnsdao.BasketCount(id,"T");
		//count[1]=bnsdao.OrderListCount(id,"T");
		
		request.setAttribute("PackBasket", PackBasket);
		request.setAttribute("ThingBasket", ThingBasket);
		
		
		request.setAttribute("packcount", packcount);
		request.setAttribute("thingcount", thingcount);
		afo.setPath("./MyBasket/MyBasketList.jsp");
		afo.setRedirect(false);
		System.out.println("workdone");
		return afo;
	}

}
