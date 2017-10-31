package net.bns.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BNSFrontController extends HttpServlet{
	protected void doprocess(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		System.out.println("Do Process Called");
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		System.out.println(command);
		ActionForward afo = null;
		Action action = null;
		if(command.equals("/Main.bns")){
			afo = new ActionForward();
			afo.setPath("./MyBasket/Main.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/MyBasketList.bns")){
			action = new MyBasketList();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/MyPackBasketList.bns")){
			action = new MyPackBasketList();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/MyThingBasketList.bns")){
			action = new MyThingBasketList();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/MyBasketAdd.bns")){
			afo = new ActionForward();
			afo.setPath("./MyBasket/MyBasketAdd.jsp");
			afo.setRedirect(false);
		}else if(command.equals("/MyBasketAddAction.bns")){
			action = new MyBasketAddAction();
			System.out.println("/MyBasketAddAction call");
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/ThingBasketUpdate.bns")){
			action = new ThingBasketUpdate();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/PackBasketUpdate.bns")){
			action = new PackBasketUpdate();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(command.equals("/MyBasketDelete.bns")){
			action= new MyBasketDelete();
			try{
				afo = action.execute(request, response);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		
		if(afo!=null){
			if(afo.isRedirect()){
				response.sendRedirect(afo.getPath());
			}else{
				RequestDispatcher dispatcher = request.getRequestDispatcher(afo.getPath());
				dispatcher.forward(request, response);
			}
			
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doprocess(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		doprocess(request, response);
	}
}
