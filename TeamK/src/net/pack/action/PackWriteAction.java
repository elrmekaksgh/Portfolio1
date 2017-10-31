package net.pack.action;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.pack.db.CategoryDAO;
import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class PackWriteAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("WriteAction");
		request.setCharacterEncoding("utf-8");

		// upload 폴더 만들기  5*1024*1024
		// MultipartRequest 객체 생성
		ServletContext context=request.getServletContext();
		String realPath=context.getRealPath("/upload");
		int maxSize = 10*1024*1024; // 10MB  실제 첨부 제한은 5MB
		
		MultipartRequest multi=new MultipartRequest(request, realPath,maxSize,"utf-8",new DefaultFileRenamePolicy());

		String subject = multi.getParameter("subject");
		String intro = multi.getParameter("intro");
		String content = multi.getParameter("content");
		String type = multi.getParameter("type");
		String area = multi.getParameter("area");
		String city = multi.getParameter("city");
		String sarea = multi.getParameter("sarea");
		int cost = Integer.parseInt(multi.getParameter("cost"));
		int stock = Integer.parseInt(multi.getParameter("stock"));
		String sdate = multi.getParameter("startDate");
		String file1 = multi.getFilesystemName("file1");
		String file2 = multi.getFilesystemName("file2");
		String file3 = multi.getFilesystemName("file3");
		String file4 = multi.getFilesystemName("file4");
		String file5 = multi.getFilesystemName("file5");
		
		
		String aa[] = sdate.split("-");
		
		String serial = aa[0] + aa[1] + aa[2];
		
		System.out.println("Write content >> " + content);
		PackBean pb = new  PackBean();
		PackDAO pdao = new PackDAO();
		
		
		pb.setSerial(Integer.parseInt(serial));
		pb.setIntro(intro);
		pb.setSubject(subject);
		pb.setContent(content);
		pb.setType(type);
		pb.setArea(area);
		pb.setCity(city);
		pb.setSarea(sarea);
		pb.setCost(cost);
		pb.setStock(stock);
		pb.setDate(sdate);
		
		
		
		if (file1 == null)
			pb.setFile1("");
		else
			pb.setFile1(file1);
		
		if (file2 == null)
			pb.setFile2("");
		else
			pb.setFile2(file2);
		
		if (file3 == null)
			pb.setFile3("");
		else
			pb.setFile3(file3);
		
		if (file4 == null)
			pb.setFile4("");
		else
			pb.setFile4(file4);
		
		if (file5 == null)
			pb.setFile5("");
		else
			pb.setFile5(file5);

		pdao.insertPack(pb);
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("PackList.po");
		forward.setRedirect(true);
		return forward;
	}

}
