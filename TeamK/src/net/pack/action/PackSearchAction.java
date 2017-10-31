package net.pack.action;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.pack.db.CategoryDAO;
import net.pack.db.PackDAO;

public class PackSearchAction implements Action{

	@Override
	public ActionForward excute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		String user_id = (String) session.getAttribute("id");
		if(user_id == null)
			user_id = "";
		String search = request.getParameter("area");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		
		
		switch(search)
		{
			case "ccnd": 
				search = "충청남도";
				break;
			case "jjd": 
				search = "제주도";
				break;
			case "gsnd": 
				search = "경상남도";
				break;
			case "gsbd": 
				search = "경상북도";
				break;
			case "jrbd": 
				search = "전라남도";
				break;
			case "ccbd": 
				search = "충청북도";
				break;
			case "gwd": 
				search = "강원도";
				break;
			case "ggd": 
				search = "경기도";
				break;
			case "jrnd": 
				search = "전라남도";
				break;
			case "bs": 
				search = "부산";
				break;
			case "su": 
				search = "서울";
				break;
		}
		
		
		if (startDate == null || startDate == "")
		{
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-MM-dd");

			startDate = dayTime.format(new Date(time));
		}
		if (endDate == null || endDate == "")
		{
			endDate = "";
		}
		
		System.out.println("start >> " + startDate);
		System.out.println("end >> " + endDate);
		
		System.out.println("search >> " + search);
		// 디비 객체 생성 BoardDAO
		PackDAO pdao = new PackDAO();
		CategoryDAO cdao = new CategoryDAO();
		
		//전체글 횟수 구하기 int count = getBoardCount()
		int count = pdao.getPackCount(search, startDate, user_id);
		
		System.out.println("count >> " + count);
		//한페이지에 보여줄 글의 갯수
		int pagesize = 10;
		//시작행 구하기   1,  11,  21,  31,  41  ...... 
		
		//현재페이지가 몇페이지인지 가져오기
		String pageNum = request.getParameter("pageNum");
		
		
		if(pageNum == null)
			pageNum = "1";
		
		int currentPage = Integer.parseInt(pageNum);
		int startRow = (currentPage-1)*pagesize+1;
		
		
		//끝행 구하기
		int endRow = currentPage*pagesize;	
		
			
		
		// 페이지 갯수 구하기
		int pageCount = count/pagesize + (count%pagesize == 0 ? 0 : 1);
		//한 화면에 보여줄 페이지 번호 갯수
		int pageBlock = 10;
		// 시작 페이지 구하기
		int startPage = ((currentPage-1)/pageBlock)*pageBlock+1;
		// 끝페이지 구하기
		int endPage = startPage+pageBlock-1;
		

		// area로 검색할때
		List list = pdao.getPackList_search(search, startDate, user_id);
		request.setAttribute("list", list);

		// 패키지 페이지  패키지슬라이드에 추천 상품 4개 
		List PackList = pdao.getPackList(0, 4);
		request.setAttribute("PackReCommentList", PackList);
		
		
		// 지역 분류명 리스트 구하기&보내기
		List CategoryList = cdao.getCategoryList();
		request.setAttribute("CategoryList", CategoryList);
		
		
		request.setAttribute("count", count);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("pageCount", pageCount);
		request.setAttribute("pageBlock", pageBlock);
		request.setAttribute("startPage", startPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("search", search);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("startDate", startDate);
		
		//ActoinForward 이동정보 담아서 로그인 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./pack/Package_search.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
