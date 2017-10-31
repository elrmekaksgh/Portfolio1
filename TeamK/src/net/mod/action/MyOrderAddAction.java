 package net.mod.action;



import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.bns.db.bnsDAO;
import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;
import net.mod.db.PMDAO;
import net.mod.db.PackMemberBEAN;


public class MyOrderAddAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		String []tch = request.getParameterValues("tch");
		String []pch=request.getParameterValues("pch");
		String pnum = request.getParameter("pnum");
		String [] tnum = request.getParameterValues("tnum");
		int to_null_check = 0;
		if(tch==null&&tnum==null)to_null_check=1;
		ModTradeInfoBEAN mtib = new ModTradeInfoBEAN();
		ModDAO moddao = new ModDAO();
		bnsDAO bnsdao = new bnsDAO();
		PackMemberBEAN pm;
		PMDAO pmdao = new PMDAO();
		ActionForward afo = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		mtib.setId(id);
		mtib.setTotal_cost(Integer.parseInt(request.getParameter("total_cost")));
		mtib.setAddress1(request.getParameter("o_receive_address1"));
		mtib.setAddress2(request.getParameter("o_receive_address2"));
		mtib.setPostcode(request.getParameter("o_receive_postcode"));
		mtib.setName(request.getParameter("o_receive_name"));
		mtib.setPayer(request.getParameter("o_receive_name"));
		mtib.setMobile(request.getParameter("o_receive_mobile"));
		mtib.setMemo(request.getParameter("o_memo"));
		mtib.setTo_null_check(to_null_check);
		String receive_check = request.getParameter("po_receive_check");
		if(receive_check!=null){
			if(receive_check.equals("0")&&tnum==null&&tch==null){
				mtib.setPostcode(null);
				mtib.setAddress1(null);
				mtib.setAddress2(null);
			}
			mtib.setPo_receive_check(Integer.parseInt(receive_check));
		}
		String Monthly_pay = request.getParameter("monthly_pay");
		if(Monthly_pay==null)Monthly_pay="1";
		int status = 2;
		String trade_type = request.getParameter("t_type");
		switch(trade_type){
			case "카드 결제":
				trade_type+=", "+request.getParameter("select_card")+
					", 할부 : "+Integer.parseInt(Monthly_pay)+"개월";
				break;
			case "무통장 입금":
				trade_type+=", "+request.getParameter("select_bank");
				if(request.getParameter("cash_receipt_check")!=null){
					trade_type+=", "+request.getParameter("cash_receipt_type_select")+
							", "+request.getParameter("cash_receipt_number");
				}
				status = 1;
					break;
		}
		int check = 0;
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		mtib.setStatus(status);
		mtib.setTrade_type(trade_type);
		mtib=moddao.CreateTradeInfo(mtib);
		if(status==9)mtib.setStatus(2);
		if(tch!=null){
			int length = tch.length;
			for(int i = 0; i<length;i++){
				mtib=moddao.TBasketInfoToMTIB(Integer.parseInt(tch[i]),mtib);
				mtib=moddao.Thing_Stock_Check(mtib.getOri_num(), mtib);
				if(mtib.getStock_check()==1){
					out.println("<script>");
					out.println("alert('죄송합니다.\\n"
							+mtib.getSubject()+", 색상 : "+mtib.getColor()+
							", 사이즈 : "+mtib.getSize()+"의 재고가 "+mtib.getStock()+
							"개 밖에 남아 있지 않아 결제가 진행되지 않았습니다!\\n"+"');");
							
					out.println("history.back();");
					out.println("</script>");
					out.close();
				}
			}
			for(int i = 0; i<length;i++){
				mtib=moddao.TBasketInfoToMTIB(Integer.parseInt(tch[i]),mtib);
				mtib=moddao.Thing_Stock_Check(mtib.getOri_num(), mtib);
				mtib.setStock(mtib.getStock()-mtib.getThing_count());
				moddao.Mul_Thing_Stock(mtib);
				moddao.InsertThingOrder(mtib);
				bnsdao.ThingBasketDelete(Integer.parseInt(tch[i]));
			}
		}
		
		if(pch!=null){
			for(int i = 0; i<pch.length;i++){
				mtib=moddao.PBasketInfoToMTIB(Integer.parseInt(pch[i]),mtib);
				String [] count =mtib.getPack_count().split(",");
				mtib.setThing_count(Integer.parseInt(count[0])+
									Integer.parseInt(count[1]));	
				mtib=moddao.Pack_Stock_Check(mtib.getOri_num(), mtib);
				if(mtib.getStock_check()==1){
					out.println("<script>");
					out.println("alert('죄송합니다. \\n"
							+mtib.getSubject()+"의 좌석이 "+mtib.getStock()+
							"개 밖에 남아 있지 않아 결제가 진행되지 않았습니다!\\n"+
							"주문 수량을 변경해 주시기 바랍니다.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				}else {
					mtib.setStock(mtib.getStock()-mtib.getThing_count());
					moddao.Mul_Pack_Stock(mtib);
					int po_num = moddao.InsertPackOrder(mtib);
					String [] a_or_c = mtib.getPack_count().split(",");
					pm = new PackMemberBEAN();
					pm.setPo_num(po_num);
					pm.setPm_id(id);
					pm.setLeader_check(1);
					pm.setAdult_or_child(1);
					pmdao.PM_Create(pm);
					pm.setLeader_check(0);
					for(int j = 0; j < Integer.parseInt(a_or_c[0])-1; j++){
						pmdao.PM_Create(pm);
					}
					pm.setAdult_or_child(2);
					for(int j = 0; j<Integer.parseInt(a_or_c[1]);j++){
						pmdao.PM_Create(pm);
					}
					
					check = 1;
					bnsdao.PackBasketDelete(Integer.parseInt(pch[i]));
				}
			}
		}

		if(tnum!=null){
			String [] color = request.getParameterValues("color");
			String [] cost = request.getParameterValues("cost");
			String [] size = request.getParameterValues("size");
			String [] count = request.getParameterValues("count");
			int length = tnum.length;
			for(int i=0; i<length; i++){
				mtib.setOri_num(Integer.parseInt(tnum[i]));
				mtib.setThing_count(Integer.parseInt(count[i]));
				mtib=moddao.Thing_Stock_Check(mtib.getOri_num(), mtib);
				if(mtib.getStock_check()==1){
					out.println("<script>");
					out.println("alert('죄송합니다. \\n"
							+mtib.getSubject()+", 색상 : "+mtib.getColor()+
							", 사이즈 : "+mtib.getSize()+"의 재고가 "+mtib.getStock()+
							"개 밖에 남아 있지 않아 결제가 진행되지 않았습니다!\\n"+
							"주문 수량을 변경해 주시기 바랍니다.');");
					out.println("history.back();");
					out.println("</script>");
					out.close();
				}
			
			}
			for(int i=0;i<length;i++){
				mtib.setOri_num(Integer.parseInt(tnum[i]));
				mtib.setThing_count(Integer.parseInt(count[i]));
				mtib=moddao.Thing_Stock_Check(mtib.getOri_num(), mtib);
				mtib.setColor(color[i]);
				mtib.setSize(size[i]);
				mtib.setCost(Integer.parseInt(cost[i]));
				mtib.setStock(mtib.getStock()-mtib.getThing_count());
				moddao.Mul_Thing_Stock(mtib);
				moddao.InsertThingOrder(mtib);
			}
		}
		
		if(pnum!=null){
			mtib=moddao.CreateTradeInfo(mtib);
			int num = Integer.parseInt(pnum);
			mtib.setOri_num(num);
			String adult = request.getParameter("adult");
			String child= request.getParameter("child");
			mtib.setThing_count(Integer.parseInt(adult)+Integer.parseInt(child));
			mtib=moddao.Pack_Stock_Check(mtib.getOri_num(), mtib);
			if(mtib.getStock_check()==1){
				out.println("<script>");
				out.println("alert('죄송합니다. \\n"
						+mtib.getSubject()+"의 좌석이 "+mtib.getStock()+
						"개 밖에 남아 있지 않아 결제가 진행되지 않았습니다!\\n"+
						"주문 수량을 변경해 주시기 바랍니다.');");
				out.println("history.back();");
				out.println("</script>");
				out.close();
			}else {
				mtib.setCost(mtib.getTotal_cost());
				mtib.setPack_count(adult+","+child);
				mtib.setStock(mtib.getStock()-mtib.getThing_count());
				moddao.Mul_Pack_Stock(mtib);
				int po_num = moddao.InsertPackOrder(mtib);
				pm = new PackMemberBEAN();
				pm.setPo_num(po_num);
				pm.setPm_id(id);
				pm.setLeader_check(1);
				pm.setAdult_or_child(1);
				pmdao.PM_Create(pm);
				pm.setLeader_check(0);
				for(int i = 0; i < Integer.parseInt(adult)-1; i++){
					pmdao.PM_Create(pm);
				}
				pm.setAdult_or_child(2);
				for(int i = 0; i<Integer.parseInt(child);i++){
					pmdao.PM_Create(pm);
				}
				
				check = 1;
			}
		}
		out.println("<script>");
		out.println("location.href='./MyOrderPayed.mo?check="+check+"'");
		out.println("</script>");
		out.close();
	return null;	
	}
}
