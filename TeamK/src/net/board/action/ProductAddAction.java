package net.board.action;

import net.member.db.ProductBean;
import net.member.db.ProductDAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;

public class ProductAddAction implements Action {
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		ProductBean pb = new ProductBean();
		
		ProductDAO pdao = new ProductDAO();
		pb.setName(name);
		pb.setIntro(request.getParameter("intro"));
		pb.setSubject(request.getParameter("subject"));
		pb.setColor(request.getParameter("color"));
		pb.setSize(request.getParameter("size"));
		pb.setCar_num(Integer.parseInt(request.getParameter("car_num")));
		pb.setType(request.getParameter("type"));
		String cost = request.getParameter("cost");
		if(cost.equals("")){
			cost = "0";
		}
		pb.setCost(Integer.parseInt(cost));
		pb.setCountry(request.getParameter("country"));
		pb.setArea(request.getParameter("area"));
		pb.setStock(Integer.parseInt(request.getParameter("stock")));
		pb.setImg(request.getParameter("img"));
		pb.setImg2(request.getParameter("img2"));
		String file3 = request.getParameter("img3");
		if(file3 == null){
			file3 = "";
		}
		pb.setImg3(file3);
		String file4 = request.getParameter("img4");
		if(file4 == null){
			file4 = "";
		}
		pb.setImg4(file4);
		String file5 = request.getParameter("img5");
		if(file5 == null){
			file5 = "";
		}
		pb.setImg5(file5);
		//upload를 폴더에 올라가 파일이름
		
		//BoardDAO 객체생성 bdao
		// 메서드 호출 insertBoard(bb)
		pdao.insertProduct(pb);
		//list.jsp 이동
		
		// list.jsp 이동
		
		return null;
	}
}
