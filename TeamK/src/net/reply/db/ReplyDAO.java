package net.reply.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ReplyDAO {
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
	
	
	public int getCommentCount(int num) {
		int count = 0;
		try {
			conn = getConnection();
			sql = "select count(*) from reply where reply_type = 3 and group_del=(select num from pack where subject=(select subject from pack where num=?) group by subject)";
			
//			sql = "select * from reply where reply_type = 3 and group_del=(select num from pack where subject=(select subject from pack where num=?) group by subject) order by re_ref desc, re_seq limit ?, ?";
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
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
	
	
	public List getCommentList(int start, int count, int num) {
		List list = new ArrayList();

		try {
			conn = getConnection();
//			sql = "select * from reply where reply_type = 3 and group_del=? order by re_ref desc, re_seq limit ?, ?";
			sql = "select * from reply where reply_type = 3 and group_del=(select num from pack where subject=(select subject from pack where num=?) group by subject) order by re_ref desc, re_seq limit ?, ?";
			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			pstm.setInt(2, start - 1);
			pstm.setInt(3, count);

			rs = pstm.executeQuery();
			while (rs.next()) {
				ReplyBean RB = new ReplyBean();
				RB.setNum(rs.getInt("num"));
				RB.setId(rs.getString("id"));
				RB.setDate(rs.getString("date"));
				RB.setContent(rs.getString("content"));
				RB.setRe_ref(rs.getInt("re_ref"));
				RB.setRe_lev(rs.getInt("re_lev"));
				RB.setRe_seq(rs.getInt("re_seq"));
				RB.setGroup_del(rs.getInt("group_del"));
				RB.setReply_type(rs.getInt("reply_type"));
				RB.setH_or_s(rs.getInt("h_or_s"));
				

				list.add(RB);
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
	
	
	public void insertReply(ReplyBean rb) {
		try {

			conn = getConnection();

			sql = "select max(num) from reply";
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}

			sql = "insert into reply values(?,?,now(),?,?,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			pstm.setString(2, rb.getId());
			pstm.setString(3, rb.getContent());
			pstm.setInt(4, num); // 답변글 그룹 == 일반글 번호
			pstm.setInt(5, 0); // 답변글 들여쓰기 - 일반글 들여쓰기없음
			pstm.setInt(6, 0); // 답변글 순서 - 일반글 순서 맨위
			pstm.setInt(7, rb.getGroup_del());
			pstm.setInt(8, 3);
			pstm.setInt(9, rb.getH_or_s());
			
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
	
	public void re_insertReply(ReplyBean rb) {
		try {

			int re_lev = 0;
			int re_seq = 0;
			conn = getConnection();
			
			sql = "select max(num) from reply";  // 글번호
			pstm = conn.prepareStatement(sql);
			rs = pstm.executeQuery();

			if (rs.next()) {
				num = rs.getInt(1) + 1;
			}
			
			sql = "update reply set re_seq = re_seq+1 where re_ref=? and re_seq > ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, rb.getRe_ref());
			pstm.setInt(2, rb.getRe_seq());
			pstm.executeUpdate();
			
			sql = "insert into reply values(?,?,now(),?,?,?,?,?,?,?)";			
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			pstm.setString(2, rb.getId());
			pstm.setString(3, rb.getContent());
			pstm.setInt(4, rb.getRe_ref()); // 답변글 그룹 == 일반글 번호
			pstm.setInt(5, rb.getRe_lev() + 1); // 답변글 들여쓰기 - 일반글 들여쓰기없음
			pstm.setInt(6, rb.getRe_seq() + 1); // 답변글 순서 - 일반글 순서 맨위
			pstm.setInt(7, rb.getGroup_del());
			pstm.setInt(8, 3);
			pstm.setInt(9, rb.getH_or_s());
			
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
	
	
	public int deleteReply(int num, String id)
	{
		 // 1 성공 -1 실패 0 아이디없음
		try {
			conn = getConnection();
			if (id.equals("admin"))
			{
				sql = "delete from reply where num=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, num);
				pstm.executeUpdate();
			}
			else
			{
				sql = "select id from reply where num=?";
				pstm = conn.prepareStatement(sql);
				pstm.setInt(1, num);
				rs = pstm.executeQuery();
			}
			
			if (rs.next())
			{
				if (id.equals(rs.getString(1)))
				{
					sql = "delete from reply where num=?";
					pstm = conn.prepareStatement(sql);
					pstm.setInt(1, num);
					pstm.executeUpdate();
					return 1;
				}
				else
					return -1;
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
		
		return 0;
	}
	
	
	public ReplyBean getComment(int num) {
		ReplyBean bb = new ReplyBean();
		try {
			conn = getConnection();
			sql = "select * from reply where num=?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, num);
			rs = pstm.executeQuery();

			if (rs.next()) {
				bb.setNum(rs.getInt(1));
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

		return bb;
	}
	
	
	public void updateReply(String content, int num, int h_or_s) {
		try {
			conn = getConnection();
			sql = "update reply set content=?, h_or_s=? where num=?";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, content);
			pstm.setInt(2, h_or_s);
			pstm.setInt(3, num);
			
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
}
