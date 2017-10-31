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

public class CategoryDAO {
	

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
	
	public void insertCategory(CategoryBean cb) {
		// 1단계 드라이버로더
		// 2단계 디비연결
		// 3단게 sql 객체 생성
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int car_num = 0;
		try {
			con = getConnection();
			sql = "select max(car_num) from category ";
			pstmt =  con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				car_num = rs.getInt(1) + 1;
			}

			sql = "insert into category(car_num,car_name,car_pt) values(?,?,?) ";
			pstmt =  con.prepareStatement(sql);
			pstmt.setInt(1, car_num);
			pstmt.setString(2, cb.getCar_name());
			pstmt.setString(3, cb.getCar_pt());
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
	
	public List getTypeList() {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List productList2 = new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from category where car_pt = 'T' ";
			pstmt =con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryBean cb = new CategoryBean();
				cb.setCar_num(rs.getInt("car_num"));
				cb.setCar_name(rs.getString("car_name"));
				productList2.add(cb);
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
		return productList2;
	}
	
	
	public List getTypeList2() {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List CategoryList = new ArrayList();
		try {
			//1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from category where car_pt = 'P' ";
			pstmt =con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				CategoryBean cb = new CategoryBean();
				cb.setCar_num(rs.getInt("car_num"));
				cb.setCar_name(rs.getString("car_name"));
				CategoryList.add(cb);
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
		return CategoryList;
	}
	
}
