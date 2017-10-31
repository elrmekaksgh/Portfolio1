package net.board.action;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class ProductUpdateAction  implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
System.out.println("ProductUpdateAction excute()");

		
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
		String pageNum= request.getParameter("pageNum");
		int num = Integer.parseInt(multi.getParameter("num"));
		String backname= multi.getParameter("backname");

		//자바빈 파일 만들기 패키지 board 파일이름 BoardBean
		ProductBean pb = new ProductBean();
		//액션태그 useBean BoardBean bb객체 생성
		//setIp(request.getRemoteAddr())
		// 액션태그 setProperty 폼 내용 가져오셔서 형변화 -> 자바빈 멤버변수 저장
		// 디비작업 파일 만들기 패키지 board 파일이름 BoardDAO
		ProductDAO pdao = new ProductDAO();
		pb.setNum(num);
		pb.setName(multi.getParameter("name"));
		pb.setIntro(multi.getParameter("intro"));
		pb.setSubject(multi.getParameter("subject"));
		pb.setContent(multi.getParameter("content"));
		String color = multi.getParameter("color");
		pb.setColor(color);
		String size = multi.getParameter("size");
		pb.setSize(size);
		int stock = Integer.parseInt(multi.getParameter("stock"));
		pb.setStock(stock);
		String car_num =multi.getParameter("car_num");
		pb.setCar_num(Integer.parseInt(car_num));
		pb.setType(multi.getParameter("type"));

		pb.setCountry(multi.getParameter("country"));
		pb.setArea(multi.getParameter("area"));

		if(multi.getFilesystemName("file1") == null){
			pb.setImg(multi.getParameter("file1"));
		}else{
			pb.setImg(multi.getFilesystemName("file1"));
		}
		if(multi.getFilesystemName("file2") == null){
			pb.setImg2(multi.getParameter("file2"));
		}else{
			pb.setImg2(multi.getFilesystemName("file2"));
		}
		if(multi.getFilesystemName("file3") == null){
			pb.setImg3(multi.getParameter("file3"));
		}else{
			pb.setImg3(multi.getFilesystemName("file3"));
		}
		if(multi.getFilesystemName("file4") == null){
			pb.setImg4(multi.getParameter("file4"));
		}else{
			pb.setImg4(multi.getFilesystemName("file4"));
		}
		if(multi.getFilesystemName("file5") == null){
			pb.setImg5(multi.getParameter("file5"));
		}else{
			pb.setImg5(multi.getFilesystemName("file5"));
		}
		//upload를 폴더에 올라가 파일이름
		
		//BoardDAO 객체생성 bdao
		// 메서드 호출 insertBoard(bb)
		pdao.UpdateProduct(pb , backname);
		//list.jsp 이동
		
		 
		 
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./ProductContent.bo?num="+num+"&pageNum="+pageNum+"&car_num="+car_num+"");
		forward.setRedirect(false);
		return forward;
	}

}
