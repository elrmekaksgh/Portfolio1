package net.pack.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import net.member.db.ProductBean;

public class PackDAO {

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

	
	// 메인 페이지 패키지 3개
	public List getPackList(int start, int end) {
		List list = new ArrayList();

		try {
			conn = getConnection();
			
			
			sql = "select num, subject, intro, cost, date, file1 from pack where date > now() group by subject order by readcount desc, cost desc limit ?, ?";
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, start);
			pstm.setInt(2, end);

			rs = pstm.executeQuery();
			while (rs.next()) {
				PackBean PB = new PackBean();

				PB.setNum(rs.getInt("num"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setCost(rs.getInt("cost"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				
				list.add(PB);
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
	
	
	// 지역별로 분류
	public List getBoardList(int startRow, int pagesize, String area, String id) {
		List list = new ArrayList();

		try {
			conn = getConnection();
			
			if (id.equals("admin"))
			{
				sql = "select * from pack where area=? group by subject order by date asc limit ?, ?";
			}
			else
			{
				sql = "select * from pack where area=? and date > now() group by subject order by date asc limit ?, ?";
			}
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, area);
			pstm.setInt(2, startRow - 1);
			pstm.setInt(3, pagesize);

			rs = pstm.executeQuery();
			while (rs.next()) {
				PackBean PB = new PackBean();

				PB.setNum(rs.getInt("num"));
				PB.setSerial(rs.getInt("serial"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setContent(rs.getString("content"));
				PB.setType(rs.getString("type"));
				PB.setArea(rs.getString("area"));
				PB.setCity(rs.getString("city"));
				PB.setCity(rs.getString("sarea"));
				PB.setCost(rs.getInt("cost"));
				PB.setReadcount(rs.getInt("readcount"));
				PB.setStock(rs.getInt("stock"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				PB.setFile2(rs.getString("file2"));
				PB.setFile3(rs.getString("file3"));
				PB.setFile4(rs.getString("file4"));
				PB.setFile5(rs.getString("file5"));
				
				
				list.add(PB);
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
	
	
	
	// 검색어(도시, 날짜) 있는 게시판 글 가져오기
	public List getPackList_search(String search, String startDate, String id) {
		List list = new ArrayList();

		try {
			conn = getConnection();

			if (id.equals("admin"))
			{
				sql = "select num, subject, intro, min(cost) as cost, min(date) as date, file1 from pack where area=? group by subject order by date";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, search);
			}
			else
			{
				sql = "select num, subject, intro, min(cost) as cost, min(date) as date, file1 from pack where area=? and date > ? group by subject order by date";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, search);
				pstm.setString(2, startDate);
			}
			rs = pstm.executeQuery();

			while (rs.next()) {
				PackBean PB = new PackBean();

				PB.setNum(rs.getInt("num"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setCost(rs.getInt("cost"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				
				list.add(PB);
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
	
	
	// 해당 상품의 모든 날짜를 가져온다
	public List getPackList(String subject, String id) {
		List list = new ArrayList();

		try {
			conn = getConnection();
			
			if(id.equals("admin"))
			{
				sql = "select * from pack where subject=? order by date";
			}
			else
			{
				sql = "select * from pack where subject=? and date > now() order by date";
			}
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, subject);

			rs = pstm.executeQuery();
			
			while (rs.next()) {
				PackBean PB = new PackBean();

				PB.setNum(rs.getInt("num"));
				PB.setSerial(rs.getInt("serial"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setContent(rs.getString("content"));
				PB.setType(rs.getString("type"));
				PB.setArea(rs.getString("area"));
				PB.setCity(rs.getString("city"));
				PB.setSarea(rs.getString("sarea"));
				PB.setCost(rs.getInt("cost"));
				PB.setReadcount(rs.getInt("readcount"));
				PB.setStock(rs.getInt("stock"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				PB.setFile2(rs.getString("file2"));
				PB.setFile3(rs.getString("file3"));
				PB.setFile4(rs.getString("file4"));
				PB.setFile5(rs.getString("file5"));
				
				list.add(PB);
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
	
	
	
	
	// 해당 지역의 패키지 갯수 카운터
	public int getPackCount(String area, String id) {
		int count = 0;
		try {
			conn = getConnection();

			if (id.equals("admin"))
			{
				sql = "select count(DISTINCT subject) from pack where area=?";
			}
			else
			{
				sql = "select count(DISTINCT subject) from pack where area=? and date > now()";
			}
			

			pstm = conn.prepareStatement(sql);
			pstm.setString(1, area);
			rs = pstm.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
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

		return count;
	}
	
	
	
	
	public List getRecommendProduct(String type) {
		List list = new ArrayList();

		try {
			conn = getConnection();
			
				sql = "select * from pack where type=? and date > now() group by subject order by rand() limit 0, 3";
			
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, type);

			rs = pstm.executeQuery();
			
			while (rs.next()) {
				PackBean PB = new PackBean();

				PB.setNum(rs.getInt("num"));
				PB.setSerial(rs.getInt("serial"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setContent(rs.getString("content"));
				PB.setType(rs.getString("type"));
				PB.setArea(rs.getString("area"));
				PB.setCity(rs.getString("city"));
				PB.setSarea(rs.getString("sarea"));
				PB.setCost(rs.getInt("cost"));
				PB.setReadcount(rs.getInt("readcount"));
				PB.setStock(rs.getInt("stock"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				PB.setFile2(rs.getString("file2"));
				PB.setFile3(rs.getString("file3"));
				PB.setFile4(rs.getString("file4"));
				PB.setFile5(rs.getString("file5"));
				
				list.add(PB);
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
	
	
	
	// 검색어(도시, 날짜) 있는 게시판 갯수 카운터
	public int getPackCount(String search, String startDate, String id) {
		int count = 0;
		try {
			conn = getConnection();

			if (id.equals("admin"))
			{
				sql = "select count(DISTINCT subject) from pack where area = ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, search);
			}
			else
			{
				sql = "select count(DISTINCT subject) from pack where area = ? and date > ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, search);
				pstm.setString(2, startDate);
			}

			rs = pstm.executeQuery();
			if (rs.next()) {
				count = rs.getInt(1);
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

		return count;
	}
	
	
	public PackBean getPack(int num) {
		PackBean PB = new PackBean();
		try {
			conn = getConnection();
			sql = "select * from pack where num=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();

			if (rs.next()) {
				PB.setNum(rs.getInt("num"));
				PB.setSerial(rs.getInt("serial"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setContent(rs.getString("content"));
				PB.setType(rs.getString("type"));
				PB.setArea(rs.getString("area"));
				PB.setCity(rs.getString("city"));
				PB.setSarea(rs.getString("sarea"));
				PB.setCost(rs.getInt("cost"));
				PB.setReadcount(rs.getInt("readcount"));
				PB.setStock(rs.getInt("stock"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				PB.setFile2(rs.getString("file2"));
				PB.setFile3(rs.getString("file3"));
				PB.setFile4(rs.getString("file4"));
				PB.setFile5(rs.getString("file5"));
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

		return PB;
	}
	
	public void insertPack(PackBean pb) {
		try {

			conn = getConnection();

			sql = "select max(num) from pack";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			int serial = Integer.parseInt(String.valueOf(pb.getSerial()) + String.valueOf(num));
			System.out.println("WriteInsert content >> " + pb.getContent());

			sql = "insert into pack values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			pstm.setInt(2, serial);
			pstm.setString(3, pb.getSubject());
			pstm.setString(4, pb.getIntro());
			pstm.setString(5, pb.getContent());
			pstm.setString(6, pb.getType());
			pstm.setString(7, pb.getArea());
			pstm.setString(8, pb.getCity());
			pstm.setString(9, pb.getSarea());
			pstm.setInt(10, pb.getCost());
			pstm.setInt(11, 0);
			pstm.setInt(12, pb.getStock());
			pstm.setString(13, pb.getDate());
			pstm.setString(14, pb.getFile1());
			pstm.setString(15, pb.getFile2());
			pstm.setString(16, pb.getFile3());
			pstm.setString(17, pb.getFile4());
			pstm.setString(18, pb.getFile5());

			pstm.executeUpdate();

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
	}
	
	public void updateReadcount(int num) {
		try {
			conn = getConnection();
			sql = "update pack set readcount = readcount + 1 where num=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			pstm.executeUpdate();

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
		}
	}
	
	
	
	public int updatePackcontent(PackBean pb, String ori_subject) {
		try {
			conn = getConnection();

			sql = "select min(num) from pack where subject= ?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, ori_subject);
			rs = pstm.executeQuery();
			
			if (rs.next())
			{
				sql = "update pack set subject=?, intro=?, content=?, type=?, area=?, city=?, sarea=?, file1=?, file2=?, file3=?, file4=?, file5=? "
						+ "where subject=? and num = ?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, pb.getSubject());
				pstm.setString(2, pb.getIntro());
				pstm.setString(3, pb.getContent());
				pstm.setString(4, pb.getType());
				pstm.setString(5, pb.getArea());
				pstm.setString(6, pb.getCity());
				pstm.setString(7, pb.getSarea());
				pstm.setString(8, pb.getFile1());
				pstm.setString(9, pb.getFile2());
				pstm.setString(10, pb.getFile3());
				pstm.setString(11, pb.getFile4());
				pstm.setString(12, pb.getFile5());
				pstm.setString(13, ori_subject);
				pstm.setInt(14, rs.getInt(1));
				pstm.executeUpdate();
				
				sql = "update pack set subject=?, intro=?, type=?, area=?, city=?, sarea=?, file1=?, file2=?, file3=?, file4=?, file5=?  where subject=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, pb.getSubject());
				pstm.setString(2, pb.getIntro());
				pstm.setString(3, pb.getType());
				pstm.setString(4, pb.getArea());
				pstm.setString(5, pb.getCity());
				pstm.setString(6, pb.getSarea());
				pstm.setString(7, pb.getFile1());
				pstm.setString(8, pb.getFile2());
				pstm.setString(9, pb.getFile3());
				pstm.setString(10, pb.getFile4());
				pstm.setString(11, pb.getFile5());
				pstm.setString(12, ori_subject);
				
				pstm.executeUpdate();
				return 1; // 수정 성공
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
		}
		return 0; // 글번호 없음
	}
	
	

	public int deletePack(int num) {
		try {
			conn = getConnection();

			sql = "select subject from pack where num=?";
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();
			
			if (rs.next())
			{
				sql = "delete from pack where subject=?";
				pstm = conn.prepareStatement(sql);
				pstm.setString(1, rs.getString("subject"));
				pstm.executeUpdate();
				return 1;
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		}
		return -1;
	}
	
	
	
	public int PackDateAddChk(String subject, String date) {
		try {
			conn = getConnection();

			sql = "select subject, date from pack where subject=? and date=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, subject);
			pstm.setString(2, date);
			rs = pstm.executeQuery();
			
			if (rs.next())
			{
				return 1;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
		}
		return 0;
	}
	
	
	public int updatePackDate(PackBean pb) {
		try 
		{
			conn = getConnection();
			
			sql = "update pack set date=?, cost=?, stock=?  where num=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, pb.getDate());
			pstm.setInt(2, pb.getCost());
			pstm.setInt(3, pb.getStock());
			pstm.setInt(4, pb.getNum());
					
			pstm.executeUpdate();

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
		}
		return 0; // 글번호 없음
	}
	
	// original Num 값으로 패키지 내용 가져올때 호출
	public PackBean getPack_original(int num) {
		PackBean PB = new PackBean();
		try {
			conn = getConnection();
			sql = "select * from pack where subject=(select subject from pack where num=?) group by subject";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();

			if (rs.next()) {
				PB.setNum(rs.getInt("num"));
				PB.setSerial(rs.getInt("serial"));
				PB.setSubject(rs.getString("subject"));
				PB.setIntro(rs.getString("intro"));
				PB.setContent(rs.getString("content"));
				PB.setType(rs.getString("type"));
				PB.setArea(rs.getString("area"));
				PB.setCity(rs.getString("city"));
				PB.setSarea(rs.getString("sarea"));
				PB.setCost(rs.getInt("cost"));
				PB.setReadcount(rs.getInt("readcount"));
				PB.setStock(rs.getInt("stock"));
				PB.setDate(rs.getString("date"));
				PB.setFile1(rs.getString("file1"));
				PB.setFile2(rs.getString("file2"));
				PB.setFile3(rs.getString("file3"));
				PB.setFile4(rs.getString("file4"));
				PB.setFile5(rs.getString("file5"));
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
		return PB;
	}
}
