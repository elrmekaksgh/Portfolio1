package net.mod.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.mod.db.RIDAO;
import net.mod.db.ReceiveInfoBEAN;

public class Add_AddressAction implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		ReceiveInfoBEAN rib = new ReceiveInfoBEAN();
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		rib.setId(id);
		rib.setName(request.getParameter("name"));
		rib.setBasic_setting(Integer.parseInt(request.getParameter("basic_setting")));
		rib.setMobile(request.getParameter("mobile"));
		rib.setPostcode(request.getParameter("postcode"));
		rib.setAddress1(request.getParameter("address1"));
		rib.setAddress2(request.getParameter("address2"));
		RIDAO ridao = new RIDAO();
		int check = ridao.Address_Insert(rib);
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if(check == 1){
			out.println("<script>");
			out.println("alert('배송지가 추가되었습니다!');");
			out.println("location.href='./Receive_Setting.mo';");
			out.println("</script>");
			out.close();
		}else{
			out.println("<script>");
			out.println("alert('Failed');");
			out.println("history.back();");
			out.println("</script>");
			out.close();
		}
		return null;
	}

}
