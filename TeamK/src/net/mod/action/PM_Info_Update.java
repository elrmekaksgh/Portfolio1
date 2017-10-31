package net.mod.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.mod.db.PMDAO;
import net.mod.db.PackMemberBEAN;

public class PM_Info_Update implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		String[]pm_num = request.getParameterValues("pm_num");
		String[]name = request.getParameterValues("name");
		String[]mobile=request.getParameterValues("mobile");
		String[]birthday=request.getParameterValues("birthday");
		String[]First_name=request.getParameterValues("First_name");
		String[]Last_name=request.getParameterValues("Last_name");
		System.out.println(pm_num.length);
		for(int i = 0; i < pm_num.length;i++){
			if(name[i].length()!=0){
				PackMemberBEAN pm = new PackMemberBEAN();
				pm.setPm_num(Integer.parseInt(pm_num[i]));
				pm.setFirst_name(First_name[i]);
				pm.setLast_name(Last_name[i]);
				pm.setHangul_name(name[i]);
				pm.setMobile(mobile[i]);
				pm.setBirth_day(Integer.parseInt(birthday[i]));
				PMDAO pmdao = new PMDAO();
				pmdao.PM_Update(pm);
			}
		}
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<script>");
		out.println("alert('입력 되었습니다');");
		out.println("window.close();");
		out.println("</script>");
		out.close();
		return null;
	}

}
