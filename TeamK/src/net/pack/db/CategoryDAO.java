package net.pack.db;

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
	Connection conn = null;
	PreparedStatement pstm = null;
	String sql = null;
	ResultSet rs = null;
	int num = 0;

	private Connection getConnection() throws Exception {
	
		//커넥션 풀 (Connection Poll)
		// 데이터베이스와 연결된 Connection 객체를 미리 생성
		// Pool 속에 저장해 두고 필요할때마다 접근하여 사용
		// 작업 끝나면 다시 반환
		
		//자카르타 DBCP API 이용한 커텍션 풀 
		//http://commons.apache.org/
		//commons-collections-3.2.1.jar
		//commons-dbcp-1.4.jar
		//commons-pool-1.6.jar
		
		// 1. WebContent - META-INF - context.xml 생성
		//    1단계, 2단계 기술 -> 디비연동 이름 정의
		// 2. WebContent - META-INF - web.xml 수정
		//    context.xml에 디비연동 해놓은 이름을 모든페이지에 알려줌
		// 3. DB작업(DAO) - 사용
		
		
		// Context 객체 생성
		Context init = new InitialContext();
		// DataSource = 디비연동 이름 불러오기
		DataSource ds = (DataSource)init.lookup("java:comp/env/jdbc/mysqlDB");
		// con = DataSource
		conn = ds.getConnection();
		return conn;
	}
	
	// 지역별로 분류
	public List getCategoryList() {
		List list = new ArrayList();
		try {
			conn = getConnection();
			sql = "select * from category where car_pt=? order by car_num";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, "P");

			rs = pstm.executeQuery();
			while (rs.next()) {
				CategoryBean CB = new CategoryBean();

				CB.setCar_num(rs.getInt("car_num"));
				CB.setCar_name(rs.getString("car_name"));
				CB.setCar_pt(rs.getString("car_pt"));
				
				list.add(CB);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (pstm != null) {
				try {
					pstm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return list;
	}
}
