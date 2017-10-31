package net.board.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.tq.util.common;

public class BoardDAO {
	
	common com = new common();
	//디비연결메서드 getConnection

	
	public void insertBoard(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		int re_ref=0;
		try {
			//1,2 디비연결
			con = com.connect();
			
			// num 게시판 글 번호 구하기
			sql="select max(num) from board";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			sql="select max(re_ref) from board where type=1";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				re_ref=rs.getInt(1)+1;
			}
			//3 sql insert now()
			sql="insert into board(num,id,subject,content,readcount,date,file1,file2,file3,file4,file5,type,re_ref,type_select) values(?,?,?,?,?,now(),?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2,bb.getId());
			pstmt.setString(3,bb.getSubject());
			pstmt.setString(4,bb.getContent());
			pstmt.setInt(5,0);//readcount 조회수 0
			pstmt.setString(6,bb.getFile1()); //re_ref 답변글 그룹==일반글의 글번호 동일
			pstmt.setString(7,bb.getFile2());
			pstmt.setString(8,bb.getFile3());
			pstmt.setString(9,bb.getFile4());
			pstmt.setString(10,bb.getFile5());
			pstmt.setInt(11,bb.getType());
			pstmt.setInt(12,re_ref); //re_ref 답변글 들여쓰기 일반글 들여쓰기 없음
			pstmt.setString(13, bb.getType_select());
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		com.close(con, pstmt, rs);
		}			
	}//insertBoard(bb)
	
	public void insertBoard2(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		int re_ref=0;
		try {
			//1,2 디비연결
			con = com.connect();
			
			// num 게시판 글 번호 구하기
			sql="select max(num) from board";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			sql="select max(re_ref) from board where type=2";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				re_ref=rs.getInt(1)+1;
			}
			
			//3 sql insert now()
			sql="insert into board(num,id,subject,content,readcount,date,file1,file2,file3,file4,file5,type,re_ref,email,type_select) values(?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2,bb.getId());
			pstmt.setString(3,bb.getSubject());
			pstmt.setString(4,bb.getContent());
			pstmt.setInt(5,0);//readcount 조회수 0
			pstmt.setString(6,bb.getFile1()); //re_ref 답변글 그룹==일반글의 글번호 동일
			pstmt.setString(7,bb.getFile2());
			pstmt.setString(8,bb.getFile3());
			pstmt.setString(9,bb.getFile4());
			pstmt.setString(10,bb.getFile5());
			pstmt.setInt(11,bb.getType());
			pstmt.setInt(12,re_ref); //re_ref 답변글 들여쓰기 일반글 들여쓰기 없음
			pstmt.setString(13,bb.getEmail());
			pstmt.setString(14,"답변준비중");
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}			
	}//insertBoard2(bb)
	
	public void insertBoard3(BoardBean bb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		int re_ref=0;
		try {
			//1,2 디비연결
			con = com.connect();
			
			// num 게시판 글 번호 구하기
			sql="select max(num) from board";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			sql="select max(re_ref) from board where type=3";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			String gong="공지사항";
			if(rs.next()){
				re_ref=rs.getInt(1)+1;
			}
			
			//3 sql insert now()
			sql="insert into board(num,id,subject,content,readcount,date,file1,file2,file3,file4,file5,type,re_ref,email,type_select) values(?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2,bb.getId());
			pstmt.setString(3,bb.getSubject());
			pstmt.setString(4,bb.getContent());
			pstmt.setInt(5,0);//readcount 조회수 0
			pstmt.setString(6,bb.getFile1()); //re_ref 답변글 그룹==일반글의 글번호 동일
			pstmt.setString(7,bb.getFile2());
			pstmt.setString(8,bb.getFile3());
			pstmt.setString(9,bb.getFile4());
			pstmt.setString(10,bb.getFile5());
			pstmt.setInt(11,bb.getType());
			pstmt.setInt(12,re_ref); //re_ref 답변글 들여쓰기 일반글 들여쓰기 없음
			pstmt.setString(13,bb.getEmail());
			pstmt.setString(14,gong);
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}			
	}//insertBoard3(bb)
	
	//type=1(리뷰게시판)의 글 갯수 가져오기
	public int getBoardCount(){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=1";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);	
		}
		return count;
	}//getBoardCount	
	
	//type=1(리뷰게시판)의 검색된 글 갯수 가져오기 search=검색어, ss=컬럼명
	public int getBoardCount(String search, String ss){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=1 and " +ss+ " like ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return count;
	}//getBoardCount	
	
	//type=2(Q&A게시판)의 글 갯수 가져오기
	public int getBoardCount2(){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=2";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return count;
	}//getBoardCount2	
	
	//type=2(Q&A게시판)의 검색된 글 갯수 가져오기 search=검색어, ss=컬럼명
	public int getBoardCount2(String search, String ss){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=2 and " + ss + " like ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return count;
	}//getBoardCount2	
	
	//type=3(공지사항)의 글 갯수 가져오기
	public int getBoardCount3(){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=3";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return count;
	}//getBoardCount3	
	
	//type=3(공지사항)의 검색된 글 갯수 가져오기 search=검색어, ss=컬럼명
	public int getBoardCount3(String search, String ss){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int count=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from board where type=3 and " +ss+ " like ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return count;
	}//getBoardCount3	
	
	//게시판의 한 페이지 글을 전부 boardList에 저장
	public List getBoardList(int startRow,int pageSize){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List boardList = new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql 객체 생성
			  // sql select * from board
			  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
			  //     re_ref desc,re_seq asc
		      // 글잘라오기   limit 시작행-1,개수
			sql="select * from board where type=1 order by re_ref desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);//시작행-1
			pstmt.setInt(2, pageSize);//몇개글
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs while 데이터 있으면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb게시판 글 하나 => boardList저장
		    while(rs.next()){
		    	BoardBean bb = new BoardBean();
		    	bb.setNum(rs.getInt("num"));
		    	bb.setId(rs.getString("id"));
		    	bb.setSubject(rs.getString("subject"));
		    	bb.setContent(rs.getString("content"));
		    	bb.setReadcount(rs.getInt("readcount"));
		    	bb.setDate(rs.getDate("date"));
		    	bb.setFile1(rs.getString("file1"));
		    	bb.setFile2(rs.getString("file2"));	
		    	bb.setFile3(rs.getString("file3"));	
		    	bb.setFile4(rs.getString("file4"));	
		    	bb.setFile5(rs.getString("file5"));
		    	bb.setType(rs.getInt("type"));
		    	bb.setRe_ref(rs.getInt("re_ref"));
		    	bb.setType_select(rs.getString("type_select"));
		    	
		    		
		    	//boardList한칸저장
		    	boardList.add(bb);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}return boardList;
	}//getBoardList
	
	//(search)
public List getBoardList(int startRow,int pageSize,String search,String ss){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List boardList = new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql 객체 생성
			  // sql select * from board
			  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
			  //     re_ref desc,re_seq asc
		      // 글잘라오기   limit 시작행-1,개수
			sql="select * from board where " +ss+ " like ? and type=1 order by re_ref desc limit ?,?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow-1);//시작행-1
			pstmt.setInt(3, pageSize);//몇개글
			
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs while 데이터 있으면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb게시판 글 하나 => boardList저장
		    while(rs.next()){
		    	BoardBean bb = new BoardBean();
		    	bb.setNum(rs.getInt("num"));
		    	bb.setId(rs.getString("id"));
		    	bb.setSubject(rs.getString("subject"));
		    	bb.setContent(rs.getString("content"));
		    	bb.setReadcount(rs.getInt("readcount"));
		    	bb.setDate(rs.getDate("date"));
		    	bb.setFile1(rs.getString("file1"));
		    	bb.setFile2(rs.getString("file2"));	
		    	bb.setFile3(rs.getString("file3"));	
		    	bb.setFile4(rs.getString("file4"));	
		    	bb.setFile5(rs.getString("file5"));
		    	bb.setType(rs.getInt("type"));
		    	bb.setRe_ref(rs.getInt("re_ref"));
		    	bb.setType_select(rs.getString("type_select"));
		    		
		    	//boardList한칸저장
		    	boardList.add(bb);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}return boardList;
	}//getBoardList search

public List getBoardList2(int startRow,int pageSize){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List boardList2 = new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql 객체 생성
			  // sql select * from board
			  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
			  //     re_ref desc,re_seq asc
		      // 글잘라오기   limit 시작행-1,개수
			sql="select * from board where type=2 order by re_ref desc limit ?,?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, startRow-1);//시작행-1
			pstmt.setInt(2, pageSize);//몇개글
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs while 데이터 있으면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb게시판 글 하나 => boardList저장
		    while(rs.next()){
		    	BoardBean bb = new BoardBean();
		    	bb.setNum(rs.getInt("num"));
		    	bb.setId(rs.getString("id"));
		    	bb.setSubject(rs.getString("subject"));
		    	bb.setContent(rs.getString("content"));
		    	bb.setReadcount(rs.getInt("readcount"));
		    	bb.setDate(rs.getDate("date"));
		    	bb.setFile1(rs.getString("file1"));
		    	bb.setFile2(rs.getString("file2"));	
		    	bb.setFile3(rs.getString("file3"));	
		    	bb.setFile4(rs.getString("file4"));	
		    	bb.setFile5(rs.getString("file5"));
		    	bb.setType(rs.getInt("type"));
		    	bb.setRe_ref(rs.getInt("re_ref"));
		    	bb.setType_select(rs.getString("type_select"));		
		    	//boardList한칸저장
		    	boardList2.add(bb);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
			
		}return boardList2;
	}//getBoardList2

//(search)
public List getBoardList2(int startRow,int pageSize,String search,String ss){
	
	Connection con=null;
	PreparedStatement pstmt=null;
	String sql="";
	ResultSet rs=null;
	List boardList = new ArrayList();
	try {
		//1,2 디비연결 메서드호출
		con = com.connect();
		//3 sql 객체 생성
		  // sql select * from board
		  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
		  //     re_ref desc,re_seq asc
	      // 글잘라오기   limit 시작행-1,개수
		sql="select * from board where " + ss + " like ? and type=2 order by re_ref desc limit ?,?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, startRow-1);//시작행-1
		pstmt.setInt(3, pageSize);//몇개글
		//4 rs 실행 저장
		rs=pstmt.executeQuery();
		//5 rs while 데이터 있으면
		// 자바빈 객체 생성 BoardBean bb
		// bb 멤버변수 <= rs열데이터 가져와서 저장
		// bb게시판 글 하나 => boardList저장
	    while(rs.next()){
	    	BoardBean bb = new BoardBean();
	    	bb.setNum(rs.getInt("num"));
	    	bb.setId(rs.getString("id"));
	    	bb.setSubject(rs.getString("subject"));
	    	bb.setContent(rs.getString("content"));
	    	bb.setReadcount(rs.getInt("readcount"));
	    	bb.setDate(rs.getDate("date"));
	    	bb.setFile1(rs.getString("file1"));
	    	bb.setFile2(rs.getString("file2"));	
	    	bb.setFile3(rs.getString("file3"));	
	    	bb.setFile4(rs.getString("file4"));	
	    	bb.setFile5(rs.getString("file5"));
	    	bb.setType(rs.getInt("type"));
	    	bb.setRe_ref(rs.getInt("re_ref"));
	    	bb.setType_select(rs.getString("type_select"));		
	    	//boardList한칸저장
	    	boardList.add(bb);
	    }
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		com.close(con, pstmt, rs);
		
	}return boardList;
}//getBoardList2 search

public List getBoardList3(int startRow,int pageSize){
	
	Connection con=null;
	PreparedStatement pstmt=null;
	String sql="";
	ResultSet rs=null;
	List boardList3 = new ArrayList();
	try {
		//1,2 디비연결 메서드호출
		con = com.connect();
		//3 sql 객체 생성
		  // sql select * from board
		  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
		  //     re_ref desc,re_seq asc
	      // 글잘라오기   limit 시작행-1,개수
		sql="select * from board where type=3 order by re_ref desc limit ?,?";
		pstmt=con.prepareStatement(sql);
		pstmt.setInt(1, startRow-1);//시작행-1
		pstmt.setInt(2, pageSize);//몇개글
		//4 rs 실행 저장
		rs=pstmt.executeQuery();
		//5 rs while 데이터 있으면
		// 자바빈 객체 생성 BoardBean bb
		// bb 멤버변수 <= rs열데이터 가져와서 저장
		// bb게시판 글 하나 => boardList저장
	    while(rs.next()){
	    	BoardBean bb = new BoardBean();
	    	bb.setNum(rs.getInt("num"));
	    	bb.setId(rs.getString("id"));
	    	bb.setSubject(rs.getString("subject"));
	    	bb.setContent(rs.getString("content"));
	    	bb.setReadcount(rs.getInt("readcount"));
	    	bb.setDate(rs.getDate("date"));
	    	bb.setFile1(rs.getString("file1"));
	    	bb.setFile2(rs.getString("file2"));	
	    	bb.setFile3(rs.getString("file3"));	
	    	bb.setFile4(rs.getString("file4"));	
	    	bb.setFile5(rs.getString("file5"));
	    	bb.setType(rs.getInt("type"));
	    	bb.setRe_ref(rs.getInt("re_ref"));
	    	bb.setType_select(rs.getString("type_select"));
	    	
	    	//boardList한칸저장
	    	boardList3.add(bb);
	    }
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		com.close(con, pstmt, rs);
		
	}return boardList3;
}//getBoardList2

//(search)
public List getBoardList3(int startRow,int pageSize,String search, String ss){

Connection con=null;
PreparedStatement pstmt=null;
String sql="";
ResultSet rs=null;
List boardList = new ArrayList();
try {
	//1,2 디비연결 메서드호출
	con = com.connect();
	//3 sql 객체 생성
	  // sql select * from board
	  // 최근글위로 re_ref 그룹별내림차순 정렬 re_seq오름차순   
	  //     re_ref desc,re_seq asc
      // 글잘라오기   limit 시작행-1,개수
	sql="select * from board where " +ss+ " like ? and type=3 order by re_ref desc limit ?,?";
	pstmt=con.prepareStatement(sql);
	pstmt.setString(1, "%"+search+"%");
	pstmt.setInt(2, startRow-1);//시작행-1
	pstmt.setInt(3, pageSize);//몇개글
	//4 rs 실행 저장
	rs=pstmt.executeQuery();
	//5 rs while 데이터 있으면
	// 자바빈 객체 생성 BoardBean bb
	// bb 멤버변수 <= rs열데이터 가져와서 저장
	// bb게시판 글 하나 => boardList저장
    while(rs.next()){
    	BoardBean bb = new BoardBean();
    	bb.setNum(rs.getInt("num"));
    	bb.setId(rs.getString("id"));
    	bb.setSubject(rs.getString("subject"));
    	bb.setContent(rs.getString("content"));
    	bb.setReadcount(rs.getInt("readcount"));
    	bb.setDate(rs.getDate("date"));
    	bb.setFile1(rs.getString("file1"));
    	bb.setFile2(rs.getString("file2"));	
    	bb.setFile3(rs.getString("file3"));	
    	bb.setFile4(rs.getString("file4"));	
    	bb.setFile5(rs.getString("file5"));
    	bb.setType(rs.getInt("type"));
    	bb.setRe_ref(rs.getInt("re_ref"));
    	bb.setType_select(rs.getString("type_select"));
    	
    	//boardList한칸저장
    	boardList.add(bb);
    }
} catch (Exception e) {
	e.printStackTrace();
}finally {
	com.close(con, pstmt, rs);
	
}return boardList;
}//getBoardList3 search

    //num값에 해당하는 게시물 정보 전부 BoardBean bb에 저장
	public BoardBean getBoard(int num){
		BoardBean bb = null;
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql 객체 생성 조건 num값에 해당하는 게시판글 전체 가져오기
			sql="select * from board where num=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫번째행 데이터있으면 자바빈 bb 객체 생성
			//  bb set메서드 멤버변수 저장 <= rs 열내용
			if(rs.next()){
				bb = new BoardBean();
				bb.setNum(rs.getInt("num"));
		    	bb.setId(rs.getString("id"));
		    	bb.setSubject(rs.getString("subject"));
		    	bb.setContent(rs.getString("content"));
		    	bb.setReadcount(rs.getInt("readcount"));
		    	bb.setDate(rs.getDate("date"));
		    	bb.setFile1(rs.getString("file1"));
		    	bb.setFile2(rs.getString("file2"));	
		    	bb.setFile3(rs.getString("file3"));	
		    	bb.setFile4(rs.getString("file4"));	
		    	bb.setFile5(rs.getString("file5"));
		    	bb.setType(rs.getInt("type"));
		    	bb.setRe_ref(rs.getInt("re_ref"));
		    	bb.setEmail(rs.getString("email"));
		    	bb.setType_select(rs.getString("type_select"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			com.close(con, pstmt, rs);
		}
		return bb;	
	}
	

	
	public void updateReadcount(int num){			
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";			
			try {
				//1,2드라이버로더
				con = com.connect();
				//3 sql 객체 생성
				// readcount 1증가 update  readcount=readcount+1
				sql="update board set readcount=readcount+1 where num=?";					
				pstmt=con.prepareStatement(sql);
				pstmt.setInt(1,num);
				//4 실행
				pstmt.executeUpdate();		
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
	
				com.close(con, pstmt);
			}
		}//updateReadcount(int num)
	
	public int updateBoard(BoardBean bb){
		int check=-1;
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs = null;
		
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql num에 해당되는 id 가져오기
			sql="select id from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,bb.getNum());
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면
			//            비밀번호 비교 맞으면 check=1
			//      3 num에 해당하는 name, subject, content 수정
			//      4 실행
			//                    틀리면 check=0
			//         없으면 check=-1
			if(rs.next()){
				if(bb.getId().equals(rs.getString("id"))){
					check=1;
					sql="update board set subject=?,content=?,file1=?,file2=?,file3=?,file4=?,file5=?,email=?,type_select=? where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1,bb.getSubject());
					pstmt.setString(2,bb.getContent());
					pstmt.setString(3,bb.getFile1());
					pstmt.setString(4,bb.getFile2());
					pstmt.setString(5,bb.getFile3());
					pstmt.setString(6,bb.getFile4());
					pstmt.setString(7,bb.getFile5());
					pstmt.setString(8,bb.getEmail());
					pstmt.setString(9,bb.getType_select());
					pstmt.setInt(10,bb.getNum());
				
					pstmt.executeUpdate();
				}
			}
			else{
				check=-1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}
		return check;
	}//updateBoard
	
	public int updateType_select(int rnum, String ts){
		int check=-1;
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs = null;
		
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql num에 해당되는 id 가져오기
			sql="select id from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,rnum);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면
			//            비밀번호 비교 맞으면 check=1
			//      3 num에 해당하는 name, subject, content 수정
			//      4 실행
			//                    틀리면 check=0
			//         없으면 check=-1
			if(rs.next()){
				
					check=1;
					sql="update board set type_select=? where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setString(1,ts);
					pstmt.setInt(2,rnum);
				
					pstmt.executeUpdate();
			}
			else{
				check=-1;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}
		return check;
	}//updateType_select
	
	public void deleteBoard(int num, String id){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs = null;
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql num에 해당되는 id 가져오기
			sql="select id from board where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,num);
			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면
			//            비밀번호 비교 맞으면 check=1
			//      3 num에 해당하는 글삭제
			//      4 실행
			//                    틀리면 check=0
			//         없으면 check=-1
			if(rs.next()){
				if(id.equals(rs.getString("id"))||id.equals("admin")){
					
					sql="delete from board where num=?";
					pstmt=con.prepareStatement(sql);
					pstmt.setInt(1,num);
					pstmt.executeUpdate();
				}
			}
			}catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}
		return;
	}//deleteBoard
	
public void deleteReply(int num){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs = null;
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			
			sql="delete from reply where num=?";
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.executeUpdate();
				
			}catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}
		return;
	}//deleteReply
	
	public void insertReplyBoard(BoardReplyBean rb){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int num=0;
		try {
			//1,2 디비연결
			con = com.connect();
			
			// num 게시판 글 번호 구하기
			sql="select max(num) from reply";
			pstmt = con.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				num=rs.getInt(1)+1;
			}
			System.out.println("num="+num);
			//3 sql insert now()
			sql="insert into reply(num,id,date,content,re_ref,re_lev,re_seq,group_del) values(?,?,now(),?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,num);
			pstmt.setString(2,rb.getId());
			pstmt.setString(3,rb.getContent());
			pstmt.setInt(4,num); //re_ref 답변글 들여쓰기 일반글 들여쓰기 없음
			pstmt.setInt(5,0); //re_lev 답변글 순서 일반글 순서 맨위	
			pstmt.setInt(6,0); //seq
			pstmt.setInt(7,rb.getGroup_del());
			//4  실행
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
		}			
	}//insertReplyBoard(rb)
	
public List getBoardReplyList(int num){
		
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		List boardReplyList = new ArrayList();
		try {

			con = com.connect();		 
			sql="select * from reply where group_del=? order by re_ref asc";
			pstmt=con.prepareStatement(sql);
			
			pstmt.setInt(1, num);

			//4 rs 실행 저장
			rs=pstmt.executeQuery();
			//5 rs while 데이터 있으면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb게시판 글 하나 => boardList저장
		    while(rs.next()){
		    	BoardReplyBean rb = new BoardReplyBean();
		    	
		    	rb.setNum(rs.getInt("num"));
		    	rb.setId(rs.getString("id"));
		    	rb.setContent(rs.getString("content"));
		    	rb.setDate(rs.getDate("date"));
		    	rb.setRe_ref(rs.getInt("re_ref"));
		    	rb.setRe_lev(rs.getInt("re_lev"));
		    	rb.setRe_seq(rs.getInt("re_seq"));	
		    	rb.setGroup_del(rs.getInt("group_del"));
		    	
		    	//boardReplyList한칸저장
		    	boardReplyList.add(rb);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			com.close(con, pstmt, rs);
			
		}return boardReplyList;
	}//getBoardReplyList

public List getBoardReplyList2(int num){
	
	Connection con=null;
	PreparedStatement pstmt=null;
	String sql="";
	ResultSet rs=null;
	List boardReplyList2 = new ArrayList();
	try {

		con = com.connect();		 
		sql="select * from reply where group_del=? order by re_ref asc";
		pstmt=con.prepareStatement(sql);
		
		pstmt.setInt(1, num);

		//4 rs 실행 저장
		rs=pstmt.executeQuery();
		//5 rs while 데이터 있으면
		// 자바빈 객체 생성 BoardBean bb
		// bb 멤버변수 <= rs열데이터 가져와서 저장
		// bb게시판 글 하나 => boardList저장
	    while(rs.next()){
	    	BoardReplyBean rb = new BoardReplyBean();
	    	
	    	rb.setNum(rs.getInt("num"));
	    	rb.setId(rs.getString("id"));
	    	rb.setContent(rs.getString("content"));
	    	rb.setDate(rs.getDate("date"));
	    	rb.setRe_ref(rs.getInt("re_ref"));
	    	rb.setRe_lev(rs.getInt("re_lev"));
	    	rb.setRe_seq(rs.getInt("re_seq"));	
	    	rb.setGroup_del(rs.getInt("group_del"));
	    	//boardReplyList한칸저장
	    	boardReplyList2.add(rb);
	    }
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		com.close(con, pstmt, rs);
		
	}return boardReplyList2;
}//getBoardReplyList2

	public BoardReplyBean getBoardReply(int num){
		BoardReplyBean rb = null;
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		try {
			//1,2 디비연결 메서드호출
			con = com.connect();
			//3 sql 객체 생성 조건 num값에 해당하는 게시판글 전체 가져오기
			sql="select * from reply where group_del=?";
			
			pstmt=con.prepareStatement(sql);
			pstmt.setInt(1, num);
			//4 rs = 실행 저장
			rs=pstmt.executeQuery();
			//5 rs 첫번째행 데이터있으면 자바빈 bb 객체 생성
			//  bb set메서드 멤버변수 저장 <= rs 열내용
			if(rs.next()){
				rb = new BoardReplyBean();
				rb.setNum(rs.getInt("num"));
		    	rb.setId(rs.getString("id"));
		    	rb.setContent(rs.getString("content"));
		    	rb.setDate(rs.getDate("date"));
		    	rb.setRe_ref(rs.getInt("re_ref"));
		    	rb.setRe_lev(rs.getInt("re_lev"));
		    	rb.setRe_seq(rs.getInt("re_seq"));
		    	rb.setGroup_del(rs.getInt("group_del"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			com.close(con, pstmt, rs);
		}
		return rb;	
	}
	
	//num 값의 게시물의 리플 갯수 불러오기
	public int getBoardReplyCount(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int rcount=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from reply where group_del = ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				rcount=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return rcount;
	}//getBoardReplyCount	
	
	public int getBoardReplyCount2(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		int rcount=0;
		try {
			//1,2 디비연결 메서드 호출
			con = com.connect();
			//3 sql  함수 count(*) 이용
			sql="select count(*) from reply where group_del = ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				rcount=rs.getInt(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return rcount;
	}//getBoardReplyCount2	
			
	public String getFile(int num){
		Connection con=null;
		PreparedStatement pstmt=null;
		String sql="";
		ResultSet rs=null;
		String gfile="";
		try {
			con = com.connect();
			sql="select file1 from board where num = ?";
			//4 rs 실행 저장
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
			//5 rs 데이터 있으면  count 저장
			if(rs.next()){
				gfile=rs.getString(1);
			}
		} catch (Exception e) {			
		}finally {
			com.close(con, pstmt, rs);		
		}
		return gfile;
	}//getFile

		
		

			
		}//클래스
