package net.mod.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mod.db.RIDAO;

public class BasicAddressChange implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		int ra_num=Integer.parseInt(request.getParameter("ra_num"));
		String id = request.getParameter("id");
		RIDAO ridao = new RIDAO();
		ridao.basic_change(ra_num, id);
		return null;
	}

}
