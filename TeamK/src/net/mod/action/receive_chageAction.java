package net.mod.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;
import net.mod.db.ReceiveInfoBEAN;

public class receive_chageAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ReceiveInfoBEAN rib = new ReceiveInfoBEAN();
		rib.setName(request.getParameter("name"));
		rib.setRa_num(Integer.parseInt(request.getParameter("ra_num")));
		rib.setMobile(request.getParameter("mobile"));
		rib.setPostcode(request.getParameter("postcode"));
		rib.setAddress1(request.getParameter("address1"));
		rib.setAddress2(request.getParameter("address2"));
		ModDAO moddao = new ModDAO();
		moddao.receive_change(rib);
		return null;
	}

}
