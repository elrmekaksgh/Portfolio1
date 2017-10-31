package net.pack.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.PackDAO;

public class PackDateDeleteAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub

		int num = Integer.parseInt(request.getParameter("num"));
		PackDAO pdao = new PackDAO();
		
		pdao.deletePack(num);
		
		
		return null;
	}

}
