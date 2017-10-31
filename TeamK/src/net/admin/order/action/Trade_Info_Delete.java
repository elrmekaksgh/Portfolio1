package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class Trade_Info_Delete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String [] tich = request.getParameterValues("tich[]");
		AdminDAO adao = new AdminDAO();
		for(int i =0; i< tich.length; i++){
			adao.Trade_Info_Delete(Integer.parseInt(tich[i]));
		}
		
		return null;
	}

}
