package net.bns.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bns.db.PBasketBEAN;
import net.bns.db.bnsDAO;

public class PackBasketUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		System.out.println("pbasket update call");
		PBasketBEAN pbb = new PBasketBEAN();
		pbb.setPb_num(Integer.parseInt(request.getParameter("num")));
		String [] countp = {request.getParameter("adult_count"),request.getParameter("child_count")};
		pbb.setCountp(countp);
		pbb.setCost(Integer.parseInt(request.getParameter("pcost")));
		bnsDAO bnsdao = new bnsDAO();
		int check = bnsdao.PBasketUpdate(pbb);
		if(check == 0){
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('성공');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}else{
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('실패');");
			out.println("location.href='"+request.getHeader("referer")+"'");
			out.println("</script>");
			out.close();
		}
		
		return null;
	}

}
