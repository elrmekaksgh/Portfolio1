package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.member.db.ProductBean;
import net.member.db.ProductDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;
import net.member.db.*;
public class ProductModifyAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProductModify excute()");
		
		
		int num = Integer.parseInt(request.getParameter("num"));	
		String color = request.getParameter("color");
		String size = request.getParameter("size");
		int stock = Integer.parseInt(request.getParameter("stock"));
		int cost = Integer.parseInt(request.getParameter("cost"));
		//디비객체 생성

		//디비객체 생성
		ProductDAO pdao=new ProductDAO();
		
		ProductBean pb =new ProductBean();


		
		pb.setNum(num);
		pb.setColor(color);
		pb.setSize(size);
		pb.setStock(stock);
		pb.setCost(cost);
		
		pdao.updateProduct(pb);
			
//				pdao.updatePackcontent(pb, num);
		
//		pdao.updatePackcontent(pb, ori_subject);
		
		return null;
//		return null;
	}

}

