package net.mod.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mod.db.RIDAO;
import net.mod.db.ReceiveInfoBEAN;

public class ReceiveSetting implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		RIDAO ridao = new RIDAO();
		List<ReceiveInfoBEAN> ribList = ridao.AddressList(id);
		request.setAttribute("ribList", ribList);
		ActionForward afo = new ActionForward();
		afo.setPath("./MyOrder/ReceiveSetting.jsp");
		afo.setRedirect(false);
		return afo;
	}
	
}
