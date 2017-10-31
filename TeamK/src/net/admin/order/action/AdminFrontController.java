package net.admin.order.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminFrontController extends HttpServlet{
	protected void doprocess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		System.out.println("Do Process Called");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println(command);
		Action action = null;
		ActionForward afo = null;
		if(command.equals("/BankPayCheck.ao")){
			action = new BankPayCheck();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BankPayChecked.ao")){
			action = new BankPayChecked();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Trade_Info_Delete.ao")){
			action = new Trade_Info_Delete();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/TO_Status_Update.ao")){
			action = new TO_Status_Update();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Admin_Thing_OrderList.ao")){
			action = new Admin_Thing_OrderList();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Admin_Thing_OrderList_Search.ao")){
			action = new Admin_Thing_OrderList_Search();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if(command.equals("/Pack_res.ao")){
			action = new Pack_Res();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Pack_Res_Action.ao")){
			action = new Pack_Res_Action();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/AdminOrderList.ao")){
			afo = new ActionForward();
			afo.setPath("/Admin/AdminOrderList.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/Res_Cancel.ao")){
			action = new Admin_Res_Cancel();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Pack_Res_Search.ao")){
			action = new Pack_Res_Search();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/trans_num_search.ao")){
			afo = new ActionForward();
			afo.setPath("Admin/trans_num_search.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/Thing_Exchange.ao")){
			action = new Thing_Exchange();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Thing_Exchange_Action.ao")){
			action = new Thing_Exchange_Action();
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
