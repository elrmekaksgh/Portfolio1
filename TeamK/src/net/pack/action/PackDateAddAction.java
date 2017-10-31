package net.pack.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class PackDateAddAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		System.out.println("DateAddAction");
		request.setCharacterEncoding("utf-8");

		String subject = request.getParameter("subject");
		String intro = request.getParameter("intro");
		String type = request.getParameter("type");
		String area = request.getParameter("area");
		String city = request.getParameter("city");
		String sarea = request.getParameter("sarea");
		int cost = Integer.parseInt(request.getParameter("cost"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		String date = request.getParameter("date");
		String file1 = request.getParameter("file1");
		String file2 = request.getParameter("file2");
		String file3 = request.getParameter("file3");
		String file4 = request.getParameter("file4");
		String file5 = request.getParameter("file5");

		PackBean pb = new PackBean();
		PackDAO pdao = new PackDAO();
		
		System.out.println("DateAddAction >> " + subject);
		System.out.println("DateAddAction >> " + date);
		System.out.println("DateAddAction >> " + cost);
		System.out.println("DateAddAction >> " + stock);
		System.out.println("DateAddAction >> " + file1);
		System.out.println("DateAddAction city >> " + city);	
		System.out.println("DateAddAction sarea >> " + sarea);	
		
		
		String aa[] = date.split("-");
		String serial = aa[0] + aa[1] + aa[2];
		
		pb.setSerial(Integer.parseInt(serial));
		pb.setSubject(subject);
		pb.setIntro(intro);
		pb.setType(type);
		pb.setArea(area);
		pb.setCity(city);
		pb.setSarea(sarea);
		pb.setCost(cost);
		pb.setStock(stock);
		pb.setDate(date);
		pb.setFile1(file1);
		pb.setFile2(file2);
		pb.setFile3(file3);
		pb.setFile4(file4);
		pb.setFile5(file5);
		
		pdao.insertPack(pb);
		
		return null;
	}

}
