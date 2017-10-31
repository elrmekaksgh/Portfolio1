package net.board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.action.Action;
import net.board.action.ActionForward;
import net.member.db.CategoryBean;
import net.member.db.CategoryDAO;
import net.member.db.ProductBean;
import net.member.db.ProductDAO;
import net.member.db.CommentBean;
import net.member.db.CommentDAO;
import net.pack.db.PackDAO;
public class ProductContent implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("Productlist excute()");
		int num = Integer.parseInt(request.getParameter("num"));
		ProductBean pb = new ProductBean();
		ProductDAO pdao = new ProductDAO();
		PackDAO pddao = new PackDAO();
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
		int pageSize2= 5;
		String pageNum = request.getParameter("pageNum");
		String pageNum2 = request.getParameter("pageNum2");
		if(pageNum == null){
			pageNum="1";
		}
		if(pageNum2 == null){
			pageNum2="1";
		}
		//시작행구하기 1 11 21 31 ... <=  pagenum, pageSize 조합
		int currentPage = Integer.parseInt(pageNum);
		int currentPage2 = Integer.parseInt(pageNum2);
		int startRow = (currentPage-1)* pageSize+1;
		int startRow2 = (currentPage2-1)* pageSize2+1;
		// 현페이지가 몇페이지 인지 가져오기
		int endRow = currentPage*pageSize;
		int endRow2 = currentPage2*pageSize2;
		List productList2 = null;
		productList2 =cdao.getTypeList();
		
		List productList3 = null;
		productList3 = pdao.getProdcutList(num);
		
		pb= pdao.getProduct(num);
		
		
		List ProductImgList = pdao.getProductImgList();
		request.setAttribute("ProductImgList", ProductImgList);
		
		List RecommendPack = null;
		RecommendPack = pddao.getRecommendProduct(pb.getType());
		
		List productList = null;
		if(count!=0){
			productList = pdao.getProdcutList(startRow, pageSize,Integer.parseInt(car_num),num);
		}
		if(commentcount!=0){
			commentList = comdao.getCommentList(startRow2, pageSize2,num);
			//sql select * from board
			// 최근글위로  re_ref 그룹별내림차순 정렬 re_seq오름차순 
			// 	re_ref desc, re_seq asc
			//글잘라오기 limit 시작행-1,개수
					
	}
		
		
		
		
		int pageCount = count/pageSize+(count%pageSize==0?0:1); // 삼항연산자를 써서 나머지 부분을 0과 1을 구한다
		int pageCount2 = commentcount/pageSize2+(commentcount%pageSize2==0?0:1);
		//한화면에 보여줄 페이지 번호 개수
		int pageBlock=10;
		int pageBlock2=10;
		// 시작 페이지 번호 구하기 1~10=>1 11~20=>11 21~30=>21
		int startPage=((currentPage-1)/pageBlock)*pageBlock+1;
		int startPage2=((currentPage2-1)/pageBlock2)*pageBlock2+1;
		//끝페이지 번호 구하기
		int endPage = startPage+pageBlock-1;
		int endPage2 = startPage2+pageBlock2-1;
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
		request.setAttribute("pageSize2", pageSize2);
		request.setAttribute("pageNum2", pageNum2);
		request.setAttribute("currentPage2", currentPage2);
		request.setAttribute("startRow2", startRow2);
		request.setAttribute("endRow2", endRow2);
		request.setAttribute("startPage2", startPage2);
		request.setAttribute("endPage2", endPage2);
		request.setAttribute("pageCount2", pageCount2);
		request.setAttribute("pageBlock2", pageBlock2);
		request.setAttribute("RecommendPack", RecommendPack);
		
		
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("/product/imgcontent.jsp");
		forward.setRedirect(false);
		return forward;
	}
}