package net.mod.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModFrontController extends HttpServlet{
	protected void doprocess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		System.out.println("Do Process Called");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println(command);
		Action action = null;
		ActionForward afo = null;
		if(command.equals("/MyOrderAddAction.mo")){
			action = new MyOrderAddAction();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyOrderPay.mo")){
			action = new MyOrderPay();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPackOrderList.mo")){
			action = new MyPackOrderList();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyThingOrderList.mo")){
			action = new MyThingOrderList();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Receive_Setting.mo")){
			action = new ReceiveSetting();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Receive_Change.mo")){
			action = new Receive_Change();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Add_Address.mo")){
			afo = new ActionForward();
			afo.setPath("./MyOrder/ReceiveAdd.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/Add_AddressAction.mo")){
			action = new Add_AddressAction();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}else if(command.equals("/Basic_change.mo")){
			action = new BasicAddressChange();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(command.equals("/receive_changeAction.mo")){
			action = new receive_chageAction();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(command.equals("/MyOrderPayed.mo")){
			afo = new ActionForward();
			afo.setPath("/MyOrder/MyOrderPayed.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/PM_Insert.mo")){
			action = new PM_Insert();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(command.equals("/PM_Info_Update.mo")){
			action = new PM_Info_Update();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}else if(command.equals("/Res_Cancel.mo")){
			action = new Res_Cancel();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyOrderList.mo")){
			afo = new ActionForward();
			afo.setPath("/MyOrder/MyOrderList.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/Res_Cancel_Action.mo")){
			action = new Res_Cancel_Action();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/TO_Status_Update.mo")){
			action = new TO_Status_Update();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/TO_Cancel_or_Exchange.mo")){
			action = new TO_Cancel_or_Exchange();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyPackPopup.mo")){
			action = new MyPackPopup();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Trade_Update_Info.mo")){
			action = new Trade_Update_Info();
			try {
				afo = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/MyThingPopup.mo")){
			action = new MyThingPopup();
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
