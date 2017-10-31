package net.member.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import net.pack.db.PackBean;

public class ProductDAO {

	PreparedStatement pstmt = null;
	ResultSet rs = null;

	private Connection getConnection() throws Exception {
		/*
		 * String url = "jdbc:mysql://localhost:3306/jspdb2"; String id2 =
		 * "jspid"; String pwd = "jsppass";
		 * Class.forName("com.mysql.jdbc.Driver"); Connection con = null; con =
		 * DriverManager.getConnection(url, id2, pwd); return con;
		 */

		// 케넥션 풀(connection Pool)
		// 데이터베이스와 연결된 Connection 객체를 미리 생성하여
		// 풀(pool)속에 저장해 두고 필요할때마다 이 풀을 접근하여 Connection 객체 사용
		// 작업이 끝나면 다시 반환

		// 자카르타 DBCP API 이용한 커넥션 풀
		// http://commons.apache.org/ 다운
		// WebContent - WEB-INF - lib
		// commons-collections-3.2.1.jar
		// commons-dbcp-1.4.jar
		// commons-pool-1.6.jar
		// 아파치톰캣 7.0이상부턴 안넣어줘도 된다.

		// 1. WebContent - META-INF - context.xml 만들기
		// 1단계 , 2단계 기술 -> 이름정의
		// 2. WebContent - WEB_INF - web.xml 수정
		// context.xml 에 디비연동 해놓은 이름을 모든 페이지에 알려줌
		// 3. DB작업(DAO) - 사용

		// Context 객체 생성
		// DataSource = 디비연동 이름 불러오기
		// con = DataSource
		Connection con = null;
		Context init = new InitialContext();
		DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/mysqlDB");
		con = ds.getConnection();
		return con;

	}

	public List getProdcutList3(int a) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		String name = "";
		List productList = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select name from thing where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}
			sql = "select num, name,color,size,stock,car_num from thing where name=? order by num";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setIntro(rs.getString("intro"));
				pb.setColor(rs.getString("color"));
				pb.setSize(rs.getString("size"));
				pb.setStock(rs.getInt("stock"));
				pb.setCar_num(rs.getInt("car_num"));
				productList.add(pb);
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
		}
		return productList;
	}

	public List getProdcutSerchList(int startRow, int pageSize, String serch_data) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List productList = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from thing where subject like ? || name like ? group by name order by num desc,num limit ?,? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + serch_data + "%");
			pstmt.setString(2, "%" + serch_data + "%");
			pstmt.setInt(3, startRow - 1);// 시작행-1
			pstmt.setInt(4, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setContent(rs.getString("content"));
				pb.setCountry(rs.getString("country"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setImg(rs.getString("img"));
				pb.setCar_num(rs.getInt("car_num"));
				productList.add(pb);
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
		}
		return productList;
	}

	public ProductBean getProduct(int num) {
		ProductBean pb = null;
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			// 1,2 디비연결 메서드 호출
			// 3 sql객체 생성 조건num값에 해당하는 게시판글 전체 가져오기
			// 4 rs = 실행저장
			// 5 rs 데이터 있으면 자바빈 bb
			con = getConnection();
			sql = "select * from thing where num = ?  ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);// 시작행-1
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setIntro(rs.getString("intro"));
				pb.setContent(rs.getString("content"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setColor(rs.getString("color"));
				pb.setSize(rs.getString("size"));
				pb.setCar_num(rs.getInt("car_num"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setCountry(rs.getString("country"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setImg(rs.getString("img"));
				pb.setImg2(rs.getString("img2"));
				pb.setImg3(rs.getString("img3"));
				pb.setImg4(rs.getString("img4"));
				pb.setImg5(rs.getString("img5"));
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
		}
		return pb;
	}

	public int deleteProduct(int num) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			con = getConnection();

			sql = "delete from thing where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();
			return 1;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public void insertProduct(ProductBean pb) {
		// 1단계 드라이버로더
		// 2단계 디비연결
		// 3단게 sql 객체 생성
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int num = 0;
		try {
			con = getConnection();
			sql = "select max(num) from thing ";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}

			sql = "insert into thing(num,name,subject,intro,content,color,size,car_num,type,cost,readcount,country,area,stock,img,img2,img3,img4,img5) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.setString(2, pb.getName());
			pstmt.setString(3, pb.getSubject());
			pstmt.setString(4, pb.getIntro());
			pstmt.setString(5, pb.getContent());
			pstmt.setString(6, pb.getColor());
			pstmt.setString(7, pb.getSize());
			pstmt.setInt(8, pb.getCar_num());
			pstmt.setString(9, pb.getType());
			pstmt.setInt(10, pb.getCost());
			pstmt.setInt(11, 0);// readcount 조회수
			pstmt.setString(12, pb.getCountry());
			pstmt.setString(13, pb.getArea());
			pstmt.setInt(14, pb.getStock());
			pstmt.setString(15, pb.getImg());
			pstmt.setString(16, pb.getImg2());
			pstmt.setString(17, pb.getImg3());
			pstmt.setString(18, pb.getImg4());
			pstmt.setString(19, pb.getImg5());
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

	public void UpdateReadcount(int num) {

		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			con = getConnection();
			sql = "UPDATE thing SET readcount=readcount+1 where NUM=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			pstmt.executeUpdate();

		} catch (Exception e) {
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
	}

	public List getProdcutList(int startRow, int pageSize, int a) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List productList = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from thing where car_num = ? group by name order by num desc,num limit ?,? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a);
			pstmt.setInt(2, startRow - 1);// 시작행-1
			pstmt.setInt(3, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setIntro(rs.getString("intro"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setContent(rs.getString("content"));
				pb.setCountry(rs.getString("country"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setImg(rs.getString("img"));
				pb.setCar_num(rs.getInt("car_num"));
				productList.add(pb);
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
		}
		return productList;
	}

	public List getProdcutList(int startRow, int pageSize, int a, int b) {
		System.out.println(startRow);
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List productList = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select * from thing where car_num = ? && num = ? group by name order by num desc ,num limit ?,? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a);
			pstmt.setInt(2, b);
			pstmt.setInt(3, startRow - 1);// 시작행-1
			pstmt.setInt(4, pageSize);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setIntro(rs.getString("intro"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setContent(rs.getString("content"));
				pb.setCountry(rs.getString("country"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setImg(rs.getString("img"));
				pb.setImg2(rs.getString("img2"));
				pb.setImg3(rs.getString("img3"));
				pb.setImg4(rs.getString("img4"));
				pb.setImg5(rs.getString("img5"));
				pb.setCar_num(rs.getInt("car_num"));
				productList.add(pb);
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
		}
		return productList;
	}
	
	public ProductBean getProduct2(int num) {
		ProductBean pb = null;
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			// 1,2 디비연결 메서드 호출
			// 3 sql객체 생성 조건num값에 해당하는 게시판글 전체 가져오기
			// 4 rs = 실행저장
			// 5 rs 데이터 있으면 자바빈 bb
			con = getConnection();
			sql = "select * from thing where subject = (select subject from thing where num = ?) group by subject  ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);// 시작행-1
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pb = new ProductBean();
				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setIntro(rs.getString("intro"));
				pb.setContent(rs.getString("content"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setColor(rs.getString("color"));
				pb.setSize(rs.getString("size"));
				pb.setCar_num(rs.getInt("car_num"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setCountry(rs.getString("country"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setImg(rs.getString("img"));
				pb.setImg2(rs.getString("img2"));
				pb.setImg3(rs.getString("img3"));
				pb.setImg4(rs.getString("img4"));
				pb.setImg5(rs.getString("img5"));
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
		}
		return pb;
	}

	public List getProdcutList(int a) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		String name = "";
		List productList3 = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select name from thing where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}
			sql = "select color from thing where name=? group by color";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setColor(rs.getString("color"));
				productList3.add(pb);
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
		}
		return productList3;
	}

	public List getProdcutList2() {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		List productList = new ArrayList();
		try {
			// 1,2 디비연결 메서드호출
			// 3 sql 객체 생성
			// 4 rs실행저장
			// rs while 데이터 이씅면
			// 자바빈 객체 생성 BoardBean bb
			// bb 멤버변수 <= rs열데이터 가져와서 저장
			// bb 게시판 글 하나 => 저장
			con = getConnection();
			sql = "select distinct name from thing";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();
				pb.setName(rs.getString("name"));
				productList.add(pb);
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
		}
		return productList;
	}

	public int getProductCount(int a) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int count = 0;
		try {
			con = getConnection();
			sql = "select count(*) from thing where car_num= ?  ";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, a);
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

	public int getProductSerchCount(String serch_data) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		int serch_count = 0;
		try {
			con = getConnection();
			sql = "select count(distinct(name)) from thing where subject like ? || name like ? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + serch_data + "%");
			pstmt.setString(2, "%" + serch_data + "%");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				serch_count = rs.getInt(1);
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

		return serch_count;
	}

	public int ProductAddChk(String name, String color, String size) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			con = getConnection();

			sql = "select color, size from thing where name=? and color=? and size=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, color);
			pstmt.setString(3, size);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return 1;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0;
	}

	public int updateProduct(ProductBean pb) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		try {
			con = getConnection();

			sql = "update thing set color=?, size=?, stock=?,cost=?  where num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, pb.getColor());
			pstmt.setString(2, pb.getSize());
			pstmt.setInt(3, pb.getStock());
			pstmt.setInt(4, pb.getCost());
			pstmt.setInt(5, pb.getNum());

			pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return 0; // 글번호 없음
	}

	public void UpdateProduct(ProductBean pb, String backname) {
		// 1단계 드라이버로더
		// 2단계 디비연결
		// 3단게 sql 객체 생성
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		String car_name = "";
		try {

			con = getConnection();
			sql = "select car_name from category where car_num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pb.getCar_num());
			rs = pstmt.executeQuery();
			if (rs.next()) {
				car_name = rs.getString(1);
			}
			sql = "update thing set stock = ? where color = ?  and size = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pb.getStock());
			pstmt.setString(2, pb.getColor());
			pstmt.setString(3, pb.getSize());
			pstmt.executeUpdate();

			sql = "update thing set name =? , subject = ? , intro = ? , content = ?, car_num=?, type=? ,country = ? , area = ? , img =? , img2 =? , img3 =? , img4=? , img5=?   WHERE  num = ? ";
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			// ? 값 저장 // 첫번째 물음표1, id에 입력될값
			pstmt.setString(1, pb.getName());// 두번째 물음표2, pass에 입력될값
			pstmt.setString(2, pb.getSubject());// 세번째 물음표3, name에 입력될값
			pstmt.setString(3, pb.getIntro());
			pstmt.setString(4, pb.getContent());
			pstmt.setInt(5, pb.getCar_num());
			pstmt.setString(6, pb.getType());
			pstmt.setString(7, pb.getCountry());
			pstmt.setString(8, pb.getArea());
			pstmt.setString(9, pb.getImg());
			pstmt.setString(10, pb.getImg2());
			pstmt.setString(11, pb.getImg3());
			pstmt.setString(12, pb.getImg4());
			pstmt.setString(13, pb.getImg5());
			pstmt.setInt(14, pb.getNum());
			pstmt.executeUpdate();

			sql = "update thing set name =? , subject = ? , intro = ? , car_num=?, type=? , country = ? , area = ? , img =? , img2 =? , img3 =? , img4=? , img5=?   WHERE  name = ? ";
			pstmt = (PreparedStatement) con.prepareStatement(sql);
			// ? 값 저장 // 첫번째 물음표1, id에 입력될값
			pstmt.setString(1, pb.getName());// 두번째 물음표2, pass에 입력될값
			pstmt.setString(2, pb.getSubject());// 세번째 물음표3, name에 입력될값
			pstmt.setString(3, pb.getIntro());
			pstmt.setInt(4, pb.getCar_num());
			pstmt.setString(5, pb.getType());
			pstmt.setString(6, pb.getCountry());
			pstmt.setString(7, pb.getArea());
			pstmt.setString(8, pb.getImg());
			pstmt.setString(9, pb.getImg2());
			pstmt.setString(10, pb.getImg3());
			pstmt.setString(11, pb.getImg4());
			pstmt.setString(12, pb.getImg5());
			pstmt.setString(13, backname);
			pstmt.executeUpdate();

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

	}

	public List getProductAddList(int num) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		String name ="";
		int count = 0;
		List productaddList = new ArrayList();
		try {
			con = getConnection();
			sql = "select name from thing where num = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				name = rs.getString(1);
			}
			
			
			sql = "select * from thing where name=? order by num";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pb = new ProductBean();

				pb.setNum(rs.getInt("num"));
				pb.setName(rs.getString("name"));
				pb.setSubject(rs.getString("subject"));
				pb.setIntro(rs.getString("intro"));
				pb.setContent(rs.getString("content"));
				pb.setColor(rs.getString("color"));
				pb.setSize(rs.getString("size"));
				pb.setCar_num(rs.getInt("car_num"));
				pb.setType(rs.getString("type"));
				pb.setCost(rs.getInt("cost"));
				pb.setArea(rs.getString("area"));
				pb.setStock(rs.getInt("stock"));
				pb.setReadcount(rs.getInt("readcount"));
				pb.setImg(rs.getString("img"));
				pb.setImg2(rs.getString("img2"));
				pb.setImg3(rs.getString("img3"));
				pb.setImg4(rs.getString("img4"));
				pb.setImg5(rs.getString("img5"));

				productaddList.add(pb);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
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

		return productaddList;
	}

	public List getRecommendProduct(String type) {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		List productList = new ArrayList();
		try {
			con = getConnection();
			sql = "select num, car_num, name, subject, cost, type, stock, img from thing where type=? and stock > 0 group by subject order by rand() limit 0, 3";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, type);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pdb = new ProductBean();
				pdb.setNum(rs.getInt("num"));
				pdb.setName(rs.getString("name"));
				pdb.setCar_num(rs.getInt("car_num"));
				pdb.setSubject(rs.getString("subject"));
				pdb.setCost(rs.getInt("cost"));
				pdb.setType(rs.getString("type"));
				pdb.setStock(rs.getInt("stock"));
				pdb.setImg(rs.getString("img"));

				productList.add(pdb);
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
		}
		return productList;
	}

	public List getProductImgList() {
		Connection con = null;
		String sql = "";
		ResultSet rs = null;
		List productList = new ArrayList();
		try {
			con = getConnection();
			sql = "select num, car_num, name, subject, cost, type, readcount, stock, img from thing group by name order by rand() limit 0, 6";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				ProductBean pdb = new ProductBean();
				pdb.setNum(rs.getInt("num"));
				pdb.setName(rs.getString("name"));
				pdb.setCar_num(rs.getInt("car_num"));
				pdb.setSubject(rs.getString("subject"));
				pdb.setCost(rs.getInt("cost"));
				pdb.setType(rs.getString("type"));
				pdb.setReadcount(rs.getInt("readcount"));
				pdb.setStock(rs.getInt("stock"));
				pdb.setImg(rs.getString("img"));

				productList.add(pdb);
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
		}
		return productList;
	}

}
