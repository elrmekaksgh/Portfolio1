package net.board.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class ProductWriteAction  implements Action{
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("FileBoardWriteAction excute()");

		
		// 파일업로드 cos.jar -> MultipartRequest 프로그램이용
		//MultipartRequest multi  객체생성 -> 파일업로드,. multi파라미터 정보저장
//	 	MultipartRequest multi = new MultipartRequest(request,파일을 업로드할 폴더 물리적위치, 파일최대크기, 한글처리, 파일이름이 동일할때 파일이름을 변경);
		//파일 업로드 할 폴더 
		//WebContent 에 폴더 만들기 (upload)
		// upload폴더를 물리적 경로 만들기
		request.setCharacterEncoding("utf-8");
		ServletContext context=request.getServletContext();
		String realPath=context.getRealPath("/upload");

		int maxSize = 5*1024*1024; //5M
		//한글 처리 utf-8
		// 파일이름이 동일할때 파일이름을 변경
		//new DefaultFileRenamePolicy()
		
		MultipartRequest multi = new MultipartRequest(request,realPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		//자바빈 객체 생성 BoardBean bb
		// set메서드호출 폼 -> 자바빈 멤버변수 저장
		
	
		//
		//한글처리

		String name = multi.getParameter("name");
		//자바빈 파일 만들기 패키지 board 파일이름 BoardBean
		ProductBean pb = new ProductBean();
		//액션태그 useBean BoardBean bb객체 생성
		//setIp(request.getRemoteAddr())
		// 액션태그 setProperty 폼 내용 가져오셔서 형변화 -> 자바빈 멤버변수 저장
		// 디비작업 파일 만들기 패키지 board 파일이름 BoardDAO
		ProductDAO pdao = new ProductDAO();
		pb.setName(name);
		pb.setIntro(multi.getParameter("intro"));
		pb.setSubject(multi.getParameter("subject"));
		pb.setContent(multi.getParameter("content"));
		pb.setColor(multi.getParameter("color"));
		pb.setSize(multi.getParameter("size"));
		pb.setCar_num(Integer.parseInt(multi.getParameter("car_num")));
		pb.setType(multi.getParameter("type"));
		String cost = multi.getParameter("cost");
		if(cost.equals("")){
			cost = "0";
		}
		pb.setCost(Integer.parseInt(cost));
		pb.setCountry(multi.getParameter("country"));
		pb.setArea(multi.getParameter("area"));
		pb.setStock(Integer.parseInt(multi.getParameter("stock")));
		pb.setImg(multi.getFilesystemName("file1"));
		pb.setImg2(multi.getFilesystemName("file2"));
		String file3 = multi.getFilesystemName("file3");
		if(file3 == null){
			file3 = "";
		}
		pb.setImg3(file3);
		String file4 = multi.getFilesystemName("file4");
		if(file4 == null){
			file4 = "";
		}
		pb.setImg4(file4);
		String file5 = multi.getFilesystemName("file5");
		if(file5 == null){
			file5 = "";
		}
		pb.setImg5(file5);
		//upload를 폴더에 올라가 파일이름
		
		//BoardDAO 객체생성 bdao
		// 메서드 호출 insertBoard(bb)
		pdao.insertProduct(pb);
		//list.jsp 이동
		
		
		
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./Productlist.bo");
		forward.setRedirect(true);
		return forward;
	}
	
}