package net.ins.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ins.action.Action;
import net.ins.action.ActionForward;
import net.ins.action.MyInterest;
import net.ins.action.MyInterestDel;
import net.ins.action.MyInterestList;

public class INSFrontController extends HttpServlet {
	protected void doprocess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Do Process Called");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println(command);
		ActionForward afo = null;
		Action action = null;
		if (command.equals("/MyInterestList.ins")) {
			action = new MyInterestList();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MyInterest.ins")) {
			action = new MyInterest();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (command.equals("/MyInterestDel.ins")) {
			action = new MyInterestDel();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/MyInterestAdd.ins")) {
			action = new MyInterestAdd();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if (command.equals("/MyInterestCheck.ins")) {
			action = new MyInterestCheck();
			try {
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doprocess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doprocess(request, response);
	}

}
