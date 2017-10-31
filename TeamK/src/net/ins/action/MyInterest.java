package net.ins.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ins.db.interestBEAN;
import net.ins.db.interestDAO;

public class MyInterest implements Action{

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
		String ty = (String)request.getParameter("TY");
		String type = "";
		interestDAO insdao = new interestDAO();
		
		int count = insdao.InterestCount(id, ty);
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		int curpage = Integer.parseInt(pageNum);
		int pagesize = 10;
		int start = (curpage - 1) * pagesize + 1;
		int pcount = count / pagesize + (count % pagesize == 0 ? 0 : 1);
		
		List<interestBEAN> MyInterest = null;
		if(count!=0){
			MyInterest = insdao.MyInterest(id,ty,start,pagesize);
		}
		int pblock = 10;
		//���������� ��ȣ�� ���ϱ� 1~10=>1  11~20=>11
		int startp=((curpage-1)/pblock)*pblock+1;
		//�������� ���ϱ�
		int endpage=startp+pblock-1;
		if(endpage > pcount)endpage = pcount;
		if(ty.equals("P"))type = "내가 찜한 여행 패키지 상품";
		else if(ty.equals("T"))type = "내가 찜한 여행에 필요한 것들";
		request.setAttribute("type", type);
		request.setAttribute("ty", ty);
		request.setAttribute("pblock", pblock);
		request.setAttribute("endpage", endpage);
		request.setAttribute("startp", startp);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pcount", pcount);
		request.setAttribute("count", count);
		request.setAttribute("MyInterest", MyInterest);
		afo.setPath("./ins/MyInterest.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
