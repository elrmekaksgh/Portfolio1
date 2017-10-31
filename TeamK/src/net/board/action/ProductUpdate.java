package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;

public class ProductUpdate implements Action{

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		int num = Integer.parseInt(request.getParameter("num"));
		String pageNum= request.getParameter("pageNum");
		ProductDAO pdao = new ProductDAO();
		 ProductBean pb= pdao.getProduct(num);
		CategoryBean cb = new CategoryBean();
		CategoryDAO cdao = new CategoryDAO();
		CommentBean comb = new CommentBean();
		CommentDAO comdao = new CommentDAO();
		int commentcount= comdao.getcommentCount(num);
		List<CommentBean> commentList = null;
		String car_num = request.getParameter("car_num");
		pdao.UpdateReadcount(num);
			
			if(car_num == null){
				car_num ="1";
			}
			int count = pdao.getProductCount(Integer.parseInt(car_num));
			int pageSize= 10;//한페이지에 보여줄 글의 개수
			if(pageNum == null){
				pageNum="1";
			}
			//시작행구하기 1 11 21 31 ... <=  pagenum, pageSize 조합
			int currentPage = Integer.parseInt(pageNum);
			int startRow = (currentPage-1)* pageSize+1;
			// 현페이지가 몇페이지 인지 가져오기
			int endRow = currentPage*pageSize;
			List productList2 = null;
			productList2 =cdao.getTypeList();
			
			List productList3 = null;
			productList3 = pdao.getProdcutList(num);
			
			List productList = null;
			if(count!=0){
				productList = pdao.getProdcutList(startRow, pageSize,Integer.parseInt(car_num));
			}
			if(commentcount!=0){
				commentList = comdao.getCommentList(startRow, pageSize,num);
				//sql select * from board
				// 최근글위로  re_ref 그룹별내림차순 정렬 re_seq오름차순 
				// 	re_ref desc, re_seq asc
				//글잘라오기 limit 시작행-1,개수
						
		}
			List CategoryList = null;
			CategoryList = cdao.getTypeList2();
			
			
			
			int pageCount = count/pageSize+(count%pageSize==0?0:1); // 삼항연산자를 써서 나머지 부분을 0과 1을 구한다
			//한화면에 보여줄 페이지 번호 개수
			int pageBlock=10;
			// 시작 페이지 번호 구하기 1~10=>1 11~20=>11 21~30=>21
			int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
			//끝페이지 번호 구하기
			int endPage = startPage+pageBlock-1;
			request.setAttribute("num", num);
			request.setAttribute("count", count);
			request.setAttribute("pageSize", pageSize);
			request.setAttribute("pageNum", pageNum);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("startRow", startRow);
			request.setAttribute("endRow", endRow);
			request.setAttribute("productList", productList);
			request.setAttribute("productList2", productList2);
			request.setAttribute("pageCount", pageCount);
			request.setAttribute("pageBlock", pageBlock);
			request.setAttribute("startPage", startPage);
			request.setAttribute("endPage", endPage);
			request.setAttribute("productList3", productList3);
			request.setAttribute("commentcount", commentcount);
			request.setAttribute("commentList", commentList);
			request.setAttribute("CategoryList", CategoryList);
			request.setAttribute("pb", pb);
		 
		 
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("/product/imgupdateForm.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
