package net.board.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




public class BoardFrontController extends HttpServlet {
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//가상주소 뽑아오기
		//  http://localhost:8080/Model2/BoardWrite.bo 
		//		/Model2/BoardWrite.bo
		String requestURI = request.getRequestURI();
		//  	/Model2
		String contextPath = request.getContextPath();
		//			   /BoardWrite.bo
		String command = requestURI.substring(contextPath.length());
		//가상주소 비교하기
		
		
		ActionForward forward=null;
		Action action = null;
		if(command.equals("/BoardWrite.bo")){
			//	./board/writeForm.jsp
			// 이동정보 저장 net.board.action.ActionForward
			forward=new ActionForward();
	   		forward.setPath("./board/writeForm.jsp");
	   		forward.setRedirect(false);			
		}else if(command.equals("/BoardWriteAction.bo")){
			// 처리할파일 틀 제시 net.board.action.Action
			// 파일 BoardWriteAction execute()
			action = new BoardWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardWrite2.bo")){
			//	./board/writeForm.jsp
			// 이동정보 저장 net.board.action.ActionForward
			forward=new ActionForward();
	   		forward.setPath("./board2/writeForm2.jsp");
	   		forward.setRedirect(false);			
		}else if(command.equals("/BoardWriteAction2.bo")){
			// 처리할파일 틀 제시 net.board.action.Action
			// 파일 BoardWriteAction execute()
			action = new BoardWriteAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardWrite3.bo")){
			//	./board/writeForm.jsp
			// 이동정보 저장 net.board.action.ActionForward
			forward=new ActionForward();
	   		forward.setPath("./board3/writeForm3.jsp");
	   		forward.setRedirect(false);			
		}else if(command.equals("/BoardWriteAction3.bo")){
			// 처리할파일 틀 제시 net.board.action.Action
			// 파일 BoardWriteAction execute()
			action = new BoardWriteAction3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardList.bo")){
			// 	 BoardListAction		execute()
			action = new BoardListAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardList2.bo")){
			// 	 BoardListAction		execute()
			action = new BoardListAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardList3.bo")){
			// 	 BoardListAction		execute()
			action = new BoardListAction3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardContent.bo")){
			action = new BoardContentAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardContent2.bo")){
			action = new BoardContentAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardContent3.bo")){
			action = new BoardContentAction3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdate.bo")){
			action = new BoardUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdate2.bo")){
			action = new BoardUpdate2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdate3.bo")){
			action = new BoardUpdate3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdateAction.bo")){
			action = new BoardUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdateAction2.bo")){
			action = new BoardUpdateAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardUpdateAction3.bo")){
			action = new BoardUpdateAction3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/BoardDelete.bo")){
	   		 forward=new ActionForward();
	   		 forward.setPath("./board/deleteForm.jsp");
	   		 forward.setRedirect(false);
	   		try {
	   			forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   	 }else if(command.equals("/BoardDeleteAction.bo")){
			   	action = new BoardDeleteAction();
			   	try {
			   		forward=action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }else if(command.equals("/BoardDelete2.bo")){
	   		 forward=new ActionForward();
	   		 forward.setPath("./board2/deleteForm2.jsp");
	   		 forward.setRedirect(false);
	   		try {
	   			forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   	 }else if(command.equals("/BoardDeleteAction2.bo")){
			   	action = new BoardDeleteAction2();
			   	try {
			   		forward=action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }else if(command.equals("/BoardDelete3.bo")){
	   		 forward=new ActionForward();
	   		 forward.setPath("./board3/deleteForm3.jsp");
	   		 forward.setRedirect(false);
	   		try {
	   			forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   	 }else if(command.equals("/BoardDeleteAction3.bo")){
			   	action = new BoardDeleteAction3();
			   	try {
			   		forward=action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		 }else if(command.equals("/BoardReplyAction.bo")){
				action = new BoardReplyAction();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}else if(command.equals("/BoardReplyAction2.bo")){
			action = new BoardReplyAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}else if(command.equals("/BoardMain.bo")){
	   		 forward=new ActionForward();
	   		 forward.setPath("./BoardMain.jsp");
	   		 forward.setRedirect(false);
	   		try {
	   			forward=action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
	   	 }else if(command.equals("/BoardReplyDel.bo")){
	   		 System.out.println("/BoardReplyDel");
				action = new BoardReplyDel();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
	   	 }else if(command.equals("/BoardReplyDel2.bo")){
				action = new BoardReplyDel();
				try {
					forward = action.execute(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
	   	 }
	   	
	   	 
	   	 else if(command.equals("/listSearch.bo")){
			action = new BoardlistSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/listSearch2.bo")){
			action = new BoardlistSearchAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/listSearch3.bo")){
			action = new BoardlistSearchAction3();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(command.equals("/Productlist.bo"))
		{
			System.out.println("Prolist cald");
			action = new Productlist();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (command.equals("/Category.bo")) {
			// 1. response 이동
			// ./ 가상주소 현재 위치
			// response.sendRedirect("./member/insertForm.jsp");

			// 2. forward 이동 A정보를 가지고 => B이동, 주소줄 A페이지보임 실행화면 B페이지
			// jsp로 이동할땐 무조건 forward
			// RequestDispatcher rd =
			// request.getRequestDispatcher("./member/insertForm.jsp");
			// rd.forward(request, response);

			// ActionForward 객체 생성 기억장소 할당
			forward = new ActionForward();
			// path 이동할 페이지 주소 값 저장
			forward.setPath("./product/categoryForm.jsp");
			// isRedirect 이동할 방식 저장
			forward.setRedirect(false);

		}
		else if(command.equals("/CategoryWriteAction.bo"))
		{
			System.out.println("CategoryWriteAction cald");
			action = new CategoryWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductWrite.bo"))
		{
			System.out.println("ProductWrite cald");
			action = new ProductWrite();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductWriteAction.bo"))
		{
			System.out.println("ProductWriteAction cald");
			action = new ProductWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductContent.bo"))
		{
			System.out.println("ProductContent cald");
			action = new ProductContent();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ContenttWriteAction.bo"))
		{
			System.out.println("ContenttWriteAction cald");
			action = new ContenttWriteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ContenttWriteAction2.bo"))
		{
			System.out.println("ContenttWriteAction2 cald");
			action = new ContenttWriteAction2();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductDelete.bo"))
		{
			System.out.println("ProductDelete cald");
			action = new ProductDelete();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductUpdate.bo"))
		{
			System.out.println("ProductUpdate cald");
			action = new ProductUpdate();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductAdd.bo"))
		{
			System.out.println("ProductAdd cald");
			action = new ProductAdd();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductAddChk.bo"))
		{
			System.out.println("ProductAddChk cald");
			action = new ProductAddChk();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductAddAction.bo"))
		{
			System.out.println("ProductAddAction cald");
			action = new ProductAddAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/ProductModify.bo"))
		{
			System.out.println("ProductModify cald");
			action = new ProductModify();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductModifyAction.bo"))
		{
			System.out.println("ProductModifyAction cald");
			action = new ProductModifyAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(command.equals("/ProductDeleteAction.bo"))
		{
			System.out.println("ProductDeleteAction cald");
			action = new ProductDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ContentUpdateAction.bo"))
		{
			System.out.println("ContentUpdateAction cald");
			action = new ContentUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ContentDeleteAction.bo"))
		{
			System.out.println("ContentDeleteAction cald");
			action = new ContentDeleteAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductSearchAction.bo"))
		{
			System.out.println("ProductSearchAction cald");
			action = new ProductSearchAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(command.equals("/ProductUpdateAction.bo"))
		{
			System.out.println("ProductUpdateAction cald");
			action = new ProductUpdateAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//이동하기
		if(forward!=null){
			if(forward.isRedirect()){
				response.sendRedirect(forward.getPath());
			}else{
				RequestDispatcher dispatcher=request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
			}
		}
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doProcess(request, response);
	}
	

}
