package net.mod.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.admin.order.db.AdminDAO;
import net.mod.db.ModDAO;

public class TO_Status_Update implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ModDAO moddao = new ModDAO();
		int num = Integer.parseInt(request.getParameter("num"));
		String stat = request.getParameter("status");
		String memo="";
		int status =0;
		switch(stat){
			case "10": status=10;break;//구매완료
			case "Exchange":status=5;break;//교환
			case "Cancel":status=6;break;//환불
		}
		if(status==5){
			memo = "교환,"+request.getParameter("tcount")+"ㅨ"+
					request.getParameter("memo");
		}else if(status==6){
			String type = request.getParameter("trade_type");
			memo="환불,"+type+","+request.getParameter("tcount")+","+
					request.getParameter("payback_co");
			if(type.equals("무통장 입금")){
				String[]cancelinfo = request.getParameterValues("Cancel_info");
				for(int i = 0 ; i<cancelinfo.length;i++){
					memo+=","+cancelinfo[i];
				}
			}
			memo+="ㅨ"+request.getParameter("memo");
		}else if(status ==10){
			int ti_num = Integer.parseInt(request.getParameter("ti_num"));
			AdminDAO admindao = new AdminDAO();
			admindao.Ti_Status_Complet_Update(ti_num);
		}		
		moddao.To_Status_Update(status, num, memo);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		switch(status){
			case 10:memo="감사합니다.";break;
			case 5:memo="교환 신청이 정상적으로 완료 되었습니다.";break;
			case 6:memo="환불 신청이 정상적으로 완료 되었습니다.";break;
		}
		out.println("<script>");
		out.println("alert('"+memo+"');");
		out.println("window.opener.location.reload();");
		out.println("window.close();");
		out.println("</script>");
		out.close();
		return null;
	}

}
