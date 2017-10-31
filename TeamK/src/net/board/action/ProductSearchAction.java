package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class ProductSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("ProductSearchAction excute()");

		request.setCharacterEncoding("UTF-8");
		
		String serch_data = request.getParameter("serch_data");
		
		ProductBean pb = new ProductBean();
		ProductDAO pdao = new ProductDAO();
		
		int serch_count = pdao.getProductSerchCount(serch_data);
		int pageSize= 10;//한페이지에 보여줄 글의 개수
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null){
			pageNum="1";
		}
		//시작행구하기 1 11 21 31 ... <=  pagenum, pageSize 조합
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)* pageSize+1;
		// 현페이지가 몇페이지 인지 가져오기
		int endRow = currentPage*pageSize;
		
		
		
		List productSerchList = null;
		if(serch_count!=0){
			productSerchList = pdao.getProdcutSerchList(startRow, pageSize,serch_data);
		}
		List ProductImgList = pdao.getProductImgList();
		request.setAttribute("ProductImgList", ProductImgList);
		int pageCount = serch_count/pageSize+(serch_count%pageSize==0?0:1); // 삼항연산자를 써서 나머지 부분을 0과 1을 구한다
		//한화면에 보여줄 페이지 번호 개수
		int pageBlock=10;
		// 시작 페이지 번호 구하기 1~10=>1 11~20=>11 21~30=>21
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		//끝페이지 번호 구하기
		int endPage = startPage+pageBlock-1;
		
		
		request.setAttribute("count", serch_count);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("startRow", startRow);
		request.setAttribute("endRow", endRow);
		request.setAttribute("productList", productSerchList);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("serch_data", serch_data);
		
		
		
		ActionForward forward = new ActionForward();
		forward.setPath("./product/ProductSearch.jsp");
		forward.setRedirect(false);
		return forward;
		// return null;
	}

}