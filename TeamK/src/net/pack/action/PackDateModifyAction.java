package net.pack.action;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.pack.db.PackBean;
import net.pack.db.PackDAO;

public class PackDateModifyAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		System.out.println("DateModifyAction");

		int num = Integer.parseInt(request.getParameter("num"));	
		String date = request.getParameter("date");
		int cost = Integer.parseInt(request.getParameter("cost"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		
		System.out.println("DataModifyAcion num >> " + num);
		System.out.println("DataModifyAcion date >> " + date);
		System.out.println("DataModifyAcion cost >> " + cost);
		System.out.println("DataModifyAcion stock >> " + stock);
		
		PackBean pb = new  PackBean();
		PackDAO pdao = new PackDAO();
		
		pb.setNum(num);
		pb.setDate(date);
		pb.setCost(cost);
		pb.setStock(stock);
		
		pdao.updatePackDate(pb);
			
//				pdao.updatePackcontent(pb, num);
		
//		pdao.updatePackcontent(pb, ori_subject);
		
		return null;
	}

}
