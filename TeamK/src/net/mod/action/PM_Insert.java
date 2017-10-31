package net.mod.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.PMDAO;
import net.mod.db.PackMemberBEAN;


public class PM_Insert implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ActionForward afo = new ActionForward();
		int po_num = Integer.parseInt(request.getParameter("num"));
		PMDAO pmdao = new PMDAO();
		List<PackMemberBEAN> PM_List = pmdao.PM_Read(po_num); 
		request.setAttribute("PM_List", PM_List);
		afo.setPath("./MyOrder/PM.jsp");
		afo.setRedirect(false);
		return afo;
	}

}
