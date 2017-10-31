package net.bns.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.bns.db.PBasketBEAN;
import net.bns.db.bnsDAO;

public class MyPackBasketList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session =request.getSession();
		ActionForward afo = new ActionForward();
		String id = (String)session.getAttribute("id");
		if(id==null){
			afo.setPath("./Main.bns");
			afo.setRedirect(true);
			return afo;
		}
		bnsDAO bnsdao = new bnsDAO();
		
		int count = bnsdao.BasketCount(id,"P");
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 10;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		
		List<PBasketBEAN> MyPackBasket = null;
		if(count!=0){
			MyPackBasket = bnsdao.PackBasket(id,start,pagesize);
		}
		int pblock = 10;
		//시작페이지 번호ㅑ 구하기 1~10=>1  11~20=>11
		int startp=((curpage-1)/pblock)*pblock+1;
		//끝페이지 구하기
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		request.setAttribute("MyPackBasket", MyPackBasket);
		afo.setPath("./MyBasket/MyPackBasketList.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
