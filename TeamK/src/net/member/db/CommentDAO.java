package net.member.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class CommentDAO {
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	private Connection getConnection() throws Exception {
		/*String url = "jdbc:mysql://localhost:3306/jspdb2";
		String id2 = "jspid";
		String pwd = "jsppass";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = null;
		con = DriverManager.getConnection(url, id2, pwd);
		return con;*/
		
		//케넥션 풀(connection Pool)
		//데이터베이스와 연결된 Connection 객체를 미리 생성하여
		//풀(pool)속에 저장해 두고 필요할때마다 이 풀을 접근하여 Connection 객체 사용
		// 작업이 끝나면 다시 반환
		
		// 자카르타 DBCP API 이용한 커넥션 풀
		// http://commons.apache.org/ 다운
		// WebContent - WEB-INF - lib
		//commons-collections-3.2.1.jar
		//commons-dbcp-1.4.jar
		//commons-pool-1.6.jar
		// 아파치톰캣 7.0이상부턴 안넣어줘도 된다.
		
		//1. WebContent  - META-INF - context.xml 만들기
		//  1단계 , 2단계 기술 -> 이름정의
		//2. WebContent - WEB_INF - web.xml 수정
		// context.xml 에 디비연동 해놓은 이름을 모든 페이지에 알려줌
		// 3. DB작업(DAO) - 사용
		
		//Context 객체 생성
		// DataSource = 디비연동 이름 불러오기
		// con = DataSource
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mysqlDB");
		con= ds.getConnection();
		return con;
		
		
	}
	
	public void insertComment(CommentBean comb){
		
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int num = 0;
		try {
			con = getConnection();
			sql = "select max(num) from comment ";
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}
		
			sql = "insert into comment values(?,?,now(),?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, comb.getId());
			pstmt.setString(3, comb.getContent());
			pstmt.setInt(4, num); // 답변글 그룹 == 일반글 번호
			pstmt.setInt(5, 0); // 답변글 들여쓰기 - 일반글 들여쓰기없음
			pstmt.setInt(6, 0); // 답변글 순서 - 일반글 순서 맨위
			pstmt.setInt(7, comb.getGroup_del());
			pstmt.setInt(8, comb.getH_or_s());
			
			pstmt.executeUpdate();
			// 4단계 실행
		
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException ex) {
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException ex) {
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (SQLException ex) {
					}
				}
				// 예외상관없이 마무리작업
				// 객체 생성 닫기

			}

}
	
	public int getcommentCount(int num) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			sql = "select count(*) from comment where group_del=?  ";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
			// 예외상관없이 마무리작업
			// 객체 생성 닫기

		}

		return count;
	}
	
	public List<CommentBean> getCommentList(int startRow, int pageSize,int num) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List<CommentBean> commentList = new ArrayList<CommentBean>();
		try {
			//1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from comment where group_del=? order by re_ref desc,re_seq limit ?,? ";
			pstmt =con.prepareStatement(sql);
			pstmt.setInt(1, num);//시작행-1
			pstmt.setInt(2, startRow-1);//시작행-1
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CommentBean cb = new CommentBean();
				cb.setNum(rs.getInt("num"));
				cb.setId(rs.getString("id"));
				cb.setDate(rs.getString("date"));
				cb.setContent(rs.getString("content"));
				cb.setRe_ref(rs.getInt("re_ref"));
				cb.setRe_lev(rs.getInt("re_lev"));
				cb.setRe_seq(rs.getInt("re_seq"));
				cb.setGroup_del(rs.getInt("group_del"));
				cb.setH_or_s(rs.getInt("h_or_s"));
				commentList.add(cb);
		} }catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
		}
		return commentList;
	}
	
	public int updateComment(CommentBean comb) {
		// 1단계 드라이버로더
		// 2단계 디비연결
		// 3단게 sql 객체 생성
		
		Connection con = null;
		try {
			
			con = getConnection();
			
			String sql2 = "select num from comment where num=?";
			pstmt =  con.prepareStatement(sql2);
			pstmt.setInt(1, comb.getNum());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 디비중에서 하나라도 맞을경우에 쓰면 나온다.
				if (rs.getInt("num")== comb.getNum()) {
					String sql = "update comment set content =?, h_or_s=? WHERE  num = ? ";
					pstmt = (PreparedStatement) con.prepareStatement(sql);
					// ? 값 저장 // 첫번째 물음표1, id에 입력될값
					pstmt.setString(1, comb.getContent());// 두번째 물음표2, pass에 입력될값
					pstmt.setInt(2, comb.getH_or_s());
					pstmt.setInt(3, comb.getNum());
					pstmt.executeUpdate();
					return 1;
				}else {
					return 0;
				}

			}
			// 4단계 실행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
			// 예외상관없이 마무리작업
			// 객체 생성 닫기

		}
		return 1;

	}
	

	public int deleteComment(CommentBean comb) {
		// 1단계 드라이버로더
		// 2단계 디비연결
		// 3단게 sql 객체 생성
		Connection con = null;
		try {

			con = getConnection();
			String sql2 = "select id,num from comment where id= ?";
			pstmt =  con.prepareStatement(sql2);
			pstmt.setString(1, comb.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) { // 디비중에서 하나라도 맞을경우에 쓰면 나온다.
				if (rs.getString("id").equals(comb.getId())) {
					String sql = "delete from comment where num = ? ";
					pstmt = (PreparedStatement) con.prepareStatement(sql);
					// ? 값 저장 // 첫번째 물음표1, id에 입력될값
					pstmt.setInt(1, comb.getNum());// 두번째 물음표2, pass에 입력될값
					pstmt.executeUpdate();
					return 1;
				}else {
					return 0;
				}

			}
			// 4단계 실행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
			// 예외상관없이 마무리작업
			// 객체 생성 닫기

		}
		return -1;

	}
	
	
	public void reinsertComment(CommentBean comb) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int num = 0;
		try {
			//1,2 디비연결
			// 3sql select 최대 num 구하기
			// 4 rs=실행저장
			// 5 rs데이터잇으면 num = 1번째열을 가져와서 +1
			
			con = getConnection();
			sql = "select max(num) from comment ";
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}
			// 답글순서 재배치
			//3update 조건  re_ref 같은그룹 re_seq 기존값보다 큰값이 있으면
			// re_seq 1증가
			sql = "update comment set re_seq = re_seq + 1 where re_ref=? and re_seq > ?";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, comb.getRe_ref());
			pstmt.setInt(2, comb.getRe_seq());
			pstmt.executeUpdate();
			//3 sql insert num 구현값 re_ref 그대로 re_lev+1 re_sql+1
			//4 실행
			sql = "insert into comment values(?,?,now(),?,?,?,?,?,?) ";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, comb.getId());
			pstmt.setString(3, comb.getContent());
			pstmt.setInt(4, comb.getRe_ref()); // 답변글 그룹 == 일반글 번호
			pstmt.setInt(5, comb.getRe_lev() + 1); // 답변글 들여쓰기 - 일반글 들여쓰기없음
			pstmt.setInt(6, comb.getRe_seq() + 1); // 답변글 순서 - 일반글 순서 맨위
			pstmt.setInt(7, comb.getGroup_del());
			pstmt.setInt(8, comb.getH_or_s());
			pstmt.executeUpdate();
			// 4단계 실행
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException ex) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException ex) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException ex) {
				}
			}
			// 예외상관없이 마무리작업
			// 객체 생성 닫기

		}

	}
	
}
