package net.ins.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ins.db.interestBEAN;
import net.ins.db.interestDAO;

public class MyInterestList implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		System.out.println(id);
		if(id==null){
			afo.setPath("./Main.bns");
			afo.setRedirect(true);
			return afo;
		}
		interestDAO inDao = new interestDAO();
		
		List<interestBEAN> InterestPack = inDao.MyInterest(id,"P", 1, 5);
		List<interestBEAN> InterestThing = inDao.MyInterest(id,"T", 1, 5);
		int []count = {0,0};
		count[0]=inDao.InterestCount(id, "P");
		count[1]=inDao.InterestCount(id, "T");
		request.setAttribute("InterestPack", InterestPack);
		request.setAttribute("InterestThing", InterestThing);
		request.setAttribute("count", count);
		afo.setPath("./ins/MyInterestList.jsp");
		afo.setRedirect(false);
		return afo;
	}
	

}
