package net.board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;


public class BoardWriteAction3 implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("BoardWriteAction execute()");
		// net.board.db.BoardBean  net.board.db.BoardDAO
		request.setCharacterEncoding("utf-8");
		
		String realPath=request.getRealPath("/upload");
		System.out.println("물리적경로:"+realPath);
		int maxSize=5*1024*1024; //5MB
		MultipartRequest multi = new MultipartRequest(request,realPath,maxSize,"utf-8",new DefaultFileRenamePolicy());
		
		// BoardBean bb 객체생성
		BoardBean bb = new BoardBean();
		// 자바빈 멤버변수 <= 파라미터 저장
		bb.setId(multi.getParameter("id"));
		bb.setSubject(multi.getParameter("subject"));
		bb.setContent(multi.getParameter("content"));
		bb.setType(Integer.parseInt(multi.getParameter("type")));
		bb.setFile1(multi.getFilesystemName("file1"));
		bb.setFile2(multi.getFilesystemName("file2"));
		bb.setFile3(multi.getFilesystemName("file3"));
		bb.setFile4(multi.getFilesystemName("file4"));
		bb.setFile5(multi.getFilesystemName("file5"));
		
	
		
		// BoardDAO bdao 객체생성
		BoardDAO bdao = new BoardDAO();
		// 메서드호출 insertBoard(bb)
		bdao.insertBoard3(bb);
		// 이동 ./BoardList.bo
		ActionForward forward = new ActionForward();
   		forward.setPath("./BoardList3.bo");
   		forward.setRedirect(true);	
		
		return forward;
	}

}
