package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;

public class CategoryWriteAction implements Action{

	/* (non-Javadoc)
	 * @see net.board.action.Action#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("CategoryWriteAction excute()");
		
		
		request.setCharacterEncoding("UTF-8");
		//request 파라미터 정보 가져오기
		String car_name = request.getParameter("car_name");
		String car_pt = request.getParameter("car_pt");

		//자바빈 패키지 net.member.db 파일 MemberBean
				//MemberBean 객체 생성
		CategoryBean cb = new CategoryBean();
				//파라미터 정보 => 자바빈 저장
		cb.setCar_name(car_name);
		cb.setCar_pt(car_pt);

				//디비연동 lib  context.xml  web.xml 설정
				//디비패키지 net.member.db 파일 MemberDAO
		CategoryDAO cdao = new CategoryDAO();
				//메서드 호출 insertMember(mb)
				cdao.insertCategory(cb);
				
				//ActoinForward 이동정보 담아서 로그인 이동
				ActionForward forward = new ActionForward();
				forward.setPath("./Productlist.bo");
				forward.setRedirect(true);
				return forward;
	}


}
