package net.mod.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;
import net.mod.db.ModTradeInfoBEAN;

public class Res_Cancel implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		int po_num = Integer.parseInt(request.getParameter("num"));
		ModDAO moddao = new ModDAO();
		ModTradeInfoBEAN mtib = moddao.PO_Info_Read(po_num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String po_date = sdf.format(mtib.getDate());
		String today = sdf.format(new Date());
		Date startDate = sdf.parse(po_date);
		Date endDate = sdf.parse(today);
		long mul_date = (startDate.getTime()-endDate.getTime())/(24 * 60 * 60 * 1000);
		int cost = mtib.getCost();
		String memo = "";
		if(mul_date<=0){
			cost = 0;
			memo = "100%";
		}
		else if(mul_date<2){
			cost *= 0.5;
			memo = "50%";
		}
		else if(mul_date<4){
			cost *= 0.7;
			memo = "30%";
		}
		else if(mul_date<6){
			cost *= 0.8;
			memo = "20%";
		}
		else if(mul_date<8){
			cost *= 0.9;
			memo = "10%";
		}
		mtib.setMemo(memo);
		mtib.setCost(cost);
		request.setAttribute("mtib", mtib);
		ActionForward afo = new ActionForward();
		afo.setPath("./MyOrder/Res_Cancel.jsp");
		afo.setRedirect(false);
		return afo;
	}

}