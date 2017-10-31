package net.mod.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.bns.db.PBasketBEAN;
import net.bns.db.TBasketBEAN;
import net.bns.db.bnsDAO;
import net.member.db.MemberBean;
import net.member.db.MemberDAO;
import net.mod.db.RIDAO;
import net.mod.db.ReceiveInfoBEAN;

public class MyOrderPay implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String []tch = request.getParameterValues("tch");
		String []pch = request.getParameterValues("pch");
		String pnum = request.getParameter("pnum");
		String [] tnum = request.getParameterValues("tnum");
		ActionForward afo = new ActionForward();
		List<TBasketBEAN>ModThingList = new ArrayList<TBasketBEAN>();
		List<PBasketBEAN>ModPackList= new ArrayList<PBasketBEAN>();
		PBasketBEAN pbb;
		TBasketBEAN tbb;
		//memberdao mdao = new memberdao();
		//MemberBEAN mb = mdao.getmember_info();
		RIDAO ridao = new RIDAO();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ReceiveInfoBEAN rib=ridao.BasicReceiveAddress(id);
		MemberDAO mdao = new MemberDAO();
		MemberBean mb = mdao.getMember(id);
		bnsDAO bndao = new bnsDAO();
		if(tch!=null){
			for(int i =0 ; i<tch.length; i++){
				tbb = bndao.ThingBasketToPay(Integer.parseInt(tch[i]));
				ModThingList.add(tbb);
				}
		}
		if(pch!=null){
			for(int i =0 ; i<pch.length; i++){
				pbb = bndao.PackBasketToPay(Integer.parseInt(pch[i]));
				ModPackList.add(pbb);
			}
		}
		if(tnum!=null){
			String [] count = request.getParameterValues("count");
			String [] cost = request.getParameterValues("cost");
			for(int i=0; i<tnum.length; i++){
				tbb = new TBasketBEAN();
				tbb.setOri_num(Integer.parseInt(tnum[i]));
				tbb.setNum(0);
				tbb.setCount(Integer.parseInt(count[i]));
				tbb.setCost(Integer.parseInt(cost[i]));;
				tbb=bndao.ThingBasketToPay(tbb);
				ModThingList.add(tbb);
			}
		}
		if(pnum!=null){
			System.out.println(pnum);
			pbb =new PBasketBEAN();
			pbb.setPb_num(0);
			pbb.setOri_num(Integer.parseInt(pnum));
			String [] countp={request.getParameter("adult"),request.getParameter("child")};
			pbb.setCountp(countp);
			pbb.setCost(Integer.parseInt(request.getParameter("cost")));
			pbb = bndao.PackBasketToPay(pbb);
			ModPackList.add(pbb);
		}
		request.setAttribute("mb", mb);
		request.setAttribute("ModThingList", ModThingList);
		request.setAttribute("ModPackList", ModPackList);
		request.setAttribute("rib", rib);
		afo.setPath("./MyOrder/MyOrderPay.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
