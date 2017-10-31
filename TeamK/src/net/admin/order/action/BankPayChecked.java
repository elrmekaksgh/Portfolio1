package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class BankPayChecked implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String [] tich = request.getParameterValues("tich");
		AdminDAO adao = new AdminDAO();
		if(tich!=null){
			for(int i =0; i< tich.length; i++){
				adao.BankPayChecked(2,Integer.parseInt(tich[i]));
			}
		}
		ActionForward afo = new ActionForward();
		afo.setPath(request.getHeader("referer"));
		afo.setRedirect(true);
		return afo;
	}
	
}
