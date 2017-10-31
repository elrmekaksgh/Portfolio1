package net.mod.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.ModDAO;

public class MyOrderDelete implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String []tch = request.getParameterValues("tch");
		String []pch=request.getParameterValues("pch");
		ModDAO moddao = new ModDAO();
		
		
		if(tch!=null){
			for(int i = 0; i<tch.length;i++){
				
			}
		}
		
		if(pch!=null){
			for(int i = 0; i<pch.length;i++){
				
				
			}
		}
		return null;
	}

}
