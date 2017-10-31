package net.reply.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reply.action.Action;
import net.reply.action.ActionForward;

public class ReplyFrontController extends HttpServlet{

	
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doProcess() 메서드 호출");
		
		
		ActionForward forward = null;
		Action action = null;
		
		
		//가상주소 뽑아오기
		String requestURL = request.getRequestURI();    //  /Model2/*.me
		String contextPath = request.getContextPath();  //  /Model2
		int contextPathlen = contextPath.length();
		String command = requestURL.substring(contextPathlen);  //   /*.me      맨 뒤에 주소를 따온다
		//뽑아온 가상주소
		System.out.println("getRequestURI >> " + requestURL);
		System.out.println("getContextPath >> " + contextPath);
		System.out.println("command >> " + command);
		
		
		// 뽑아온 가상주소와 /MemberJoin.me가 같으면
		// /member/insertForm.jsp로 이동
		if(command.equals("/Main.po"))
		{
			// 1. response 이동
			//  ./ 가상주소 현재 위치
//			response.sendRedirect("./member/insertForm.jsp");
			
			
			// 2. forward 이동   A정보를 가지고 => B이동,   주소줄 A페이지보임 실행화면 B페이지
			// jsp로 이동할땐 무조건 forward
//			RequestDispatcher rd = request.getRequestDispatcher("./member/insertForm.jsp");
//			rd.forward(request, response);
			
			// ActionForward 객체 생성 기억장소 할당
			forward = new ActionForward();
			//path 이동할 페이지 주소 값 저장
			forward.setPath("Main.jsp");
			//isRedirect 이동할 방식 저장
			forward.setRedirect(false);
			
		}
		else if(command.equals("/ReplyList.ro"))
		{
			//회원가입 처리작업 자바파일 객체 생성 메서드호출()
			//처리작업할 파일의 틀을 제시 interface 만들고
			//그 틀에 맞추어서 처리파일 만들기
			//interface
			//패키지 net.member.action 파일Action
			
			forward = new ActionForward();
			//path 이동할 페이지 주소 값 저장
			forward.setPath("Package_content.jsp");
			//isRedirect 이동할 방식 저장
			forward.setRedirect(false);
			
			//패키지 net.member.action 파일MemberJoinAction
		}

		else if(command.equals("/ReplyWrite.ro"))
		{
			//회원가입 처리작업 자바파일 객체 생성 메서드호출()
			//처리작업할 파일의 틀을 제시 interface 만들고
			//그 틀에 맞추어서 처리파일 만들기
			//interface
			//패키지 net.member.action 파일Action
			
			action = new ReplyWrite();
			try {
				forward = action.excute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//패키지 net.member.action 파일MemberJoinAction
		}
		
		else if(command.equals("/Re_ReplyWriteAction.ro"))
		{
			//회원가입 처리작업 자바파일 객체 생성 메서드호출()
			//처리작업할 파일의 틀을 제시 interface 만들고
			//그 틀에 맞추어서 처리파일 만들기
			//interface
			//패키지 net.member.action 파일Action
			
			action = new Re_ReplyWriteAction();
			try {
				forward = action.excute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//패키지 net.member.action 파일MemberJoinAction
		}
		
		
		
		else if(command.equals("/ReplyDelAction.ro"))
		{
			//회원가입 처리작업 자바파일 객체 생성 메서드호출()
			//처리작업할 파일의 틀을 제시 interface 만들고
			//그 틀에 맞추어서 처리파일 만들기
			//interface
			//패키지 net.member.action 파일Action
			
			action = new ReplyDelAction();
			try {
				forward = action.excute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//패키지 net.member.action 파일MemberJoinAction
		}
		
		
		else if(command.equals("/ReplyUpdateActoin.ro"))
		{
			//회원가입 처리작업 자바파일 객체 생성 메서드호출()
			//처리작업할 파일의 틀을 제시 interface 만들고
			//그 틀에 맞추어서 처리파일 만들기
			//interface
			//패키지 net.member.action 파일Action
			
			action = new ReplyUpdateActoin();
			try {
				forward = action.excute(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//패키지 net.member.action 파일MemberJoinAction
		}
		
		
		
		
		
		// 이동
		if(forward != null){ // 이동할 정보가 있을 때
			if(forward.isRedirect()){  //이동방식 response.sendRedirect()이  true     
				//이동할 페이지주소
				//이동할 방식 true/false
				//true -> sendReDirect   // 가상주소 이동
				//false -> forward       // jsp 이동
				
				response.sendRedirect(forward.getPath());
			}
			else
			{
				RequestDispatcher rd = request.getRequestDispatcher(forward.getPath());
				rd.forward(request, response);
			}
		}		
	}
	
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doGet() 메서드 호출");
		doProcess(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost() 메서드 호출");
		doProcess(req, resp);
	}
	
}
