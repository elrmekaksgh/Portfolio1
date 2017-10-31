package net.bns.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.bns.db.bnsDAO;


public class MyBasketDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String []tch = request.getParameterValues("tch");
		String []pch=request.getParameterValues("pch");
		bnsDAO bnsdao = new bnsDAO();
		
		//상품 장바구니 삭제
		if(tch!=null){
			for(int i = 0; i<tch.length;i++){
				System.out.println(tch[i]);
				bnsdao.ThingBasketDelete(Integer.parseInt(tch[i]));
			}
		}
		//패키지 장바구니 삭제
		if(pch!=null){
			for(int i = 0; i<pch.length;i++){
				System.out.println(pch[i]);
				bnsdao.PackBasketDelete(Integer.parseInt(pch[i]));
			}
		}
		response.setContentType("text/html; charset=UTF-8");//JAVA���� JSPȣ���Ҷ� ���(response Ÿ�� ����)
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('삭제되었습니다');");
		out.println("location.href='"+request.getHeader("referer")+"';");
		out.println("</script>");
		out.close();
		return null;
	}

}
