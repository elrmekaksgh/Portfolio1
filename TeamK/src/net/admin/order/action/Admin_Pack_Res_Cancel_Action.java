package net.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;

public class Admin_Pack_Res_Cancel_Action implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String [] pnum = request.getParameterValues("pnum");
		AdminDAO admindao = new AdminDAO();
		for(int i = 0; i<pnum.length; i++){
			admindao.PO_Status_Update(9, pnum[i]);	
		}
		ActionForward afo = new ActionForward();
		afo.setPath(request.getHeader("referer"));
		afo.setRedirect(true);
		return afo;
	}
	

}
