package net.json.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;

public class JsonController extends HttpServlet{
	protected void doprocess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		Action action = null;
		ActionForward afo = null;
		if(command.equals("/size.jc")){
			action = new size();
			try{
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/thing_info.jc")){
			action = new thing_info();
			try{
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (afo != null) {
			if (afo.isRedirect()) {
				response.sendRedirect(afo.getPath());
			} else {
				RequestDispatcher dispatcher = request.getRequestDispatcher(afo.getPath());
				dispatcher.forward(request, response);
			}

		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doprocess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doprocess(req, resp);
	}
}
