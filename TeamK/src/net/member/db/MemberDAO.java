package net.member.db;

import java.sql.*;
import java.util.*;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.tq.util.*;



public class MemberDAO {

	common com = new common();

	public void insertMember(MemberBean m) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";

		try {

			con = com.connect();

			sql = "insert into member(id,pass,name,postcode,address1,"
					+ "address2,mobile,email) values(?,?,?,?,?,?,?,?)";

			pstmt = con.prepareStatement(sql);

			pstmt.setString(1, m.getId());
			pstmt.setString(2, m.getPass());
			pstmt.setString(3, m.getName());
			pstmt.setString(4, m.getPostcode());
			pstmt.setString(5, m.getAddress1());
			pstmt.setString(6, m.getAddress2());
			pstmt.setString(7, m.getMobile());
			pstmt.setString(8, m.getEmail());

			pstmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			com.close(con, pstmt);
		}

	}// insertMamber

	public int idCheck(String id, String pass) {

		int check = -1;

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			con = com.connect();

			sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
		
//				String txtCipher = "";
//				txtCipher = Base64.encode(pass.getBytes());
//				pass = txtCipher;
				String hashPass=rs.getString("pass");
				//BCrypt.checkpw(pass, hashPass);
				if (BCrypt.checkpw(pass, hashPass)) {
					return check = 1;
				} else {
					return check = 0;
				}
			} else {
				return check = -1;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 객체닫기
			com.close(con, pstmt, rs);
		}

		return check;

	}//idCheck

	public int joinIdCheck(String id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		int check = 0;
		try {
			// 1,2 디비연결 메서드호출
			con = com.connect();
			// 3 id에 해당하는 데이터 있는지 조회
			sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			// 4 rs= 실행
			rs = pstmt.executeQuery();
			// 5 rs데이터 있으면 check=1 아이디 중복
			// 없으면 check=0 아이디중복 아님
			if (rs.next()) {
				check = 1;
			} else {
				check = 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			com.close(con, pstmt, rs);
		}
		return check;
	}//joinIdCheck

	public MemberBean getMember(String id) {

		MemberBean mb = new MemberBean();

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			// 1단계 드라이버로더
			con = com.connect();
			// 3단계 sql 객체 생성 id에 해당하는 pass 가져오기
			sql = "select * from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);

			// 4단계 rs = 실행결과저장
			rs = pstmt.executeQuery();

			if (rs.next()) {
				mb = new MemberBean();

				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setPostcode(rs.getString("postcode"));
				mb.setAddress1(rs.getString("address1"));
				mb.setAddress2(rs.getString("address2"));
				mb.setMobile(rs.getString("mobile"));
				mb.setEmail(rs.getString("email"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			com.close(con, pstmt, rs);

		}
		return mb;
	}//getMember

	public int updateMember(MemberBean mb) {

		int check = -1;

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			con = com.connect();

			sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getId());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				String hashPass=rs.getString("pass");
				//BCrypt.checkpw(mb.getPass(), hashPass);
				if (BCrypt.checkpw(mb.getPass(), hashPass)) {
					sql = "update  member set name=?,address1=?,address2=?," + "mobile=?,email=?,postcode=? where id=?";
					pstmt = con.prepareStatement(sql);

					pstmt.setString(1, mb.getName());
					pstmt.setString(2, mb.getAddress1());
					pstmt.setString(3, mb.getAddress2());
					pstmt.setString(4, mb.getMobile());
					pstmt.setString(5, mb.getEmail());
					pstmt.setString(6, mb.getPostcode());
					pstmt.setString(7, mb.getId());

					pstmt.executeUpdate();
					return check = 1;
				} else {
					check = 0;
				}
			} else {// 아이디없음
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 객체닫기
			com.close(con, pstmt, rs);
		}
		return check;

	}//updateMember

	public void passchange(String id, String newpass) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
			try {
			con = com.connect();
			sql = "update member set pass =? where id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, newpass);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			com.close(con, pstmt, rs);
		}
		
	}//passchange

	public void emailchange(String id, String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		try {
			con = com.connect();
			sql = "update member set email =? where id=? ";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, email);
			pstmt.setString(2, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			com.close(con, pstmt, rs);
		}
	}//emailchange

	public int deleteMember(MemberBean mb) {

		int check = -1;

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			// 1단계 드라이버로더
			con = com.connect();

			sql = "select pass from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, mb.getId());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				if (mb.getPass().equals(rs.getString("pass"))) {

					sql = "delete  from member where id=? and pass=?";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, mb.getId());
					pstmt.setString(2, mb.getPass());

					pstmt.executeUpdate();// 실행

					return check = 1;
				} else {
					check = 0;
				}
			} else {// 아이디없음
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 객체닫기
			com.close(con, pstmt, rs);
		}
		return check;

	}//deleteMember

	public List getMemberList() {

		List memberList = new ArrayList();

		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;

		try {
			con = com.connect();
			sql = "select* from member";
			pstmt = con.prepareStatement(sql);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberBean mb = new MemberBean();
				mb.setId(rs.getString("id"));
				mb.setPass(rs.getString("pass"));
				mb.setName(rs.getString("name"));
				mb.setAddress1(rs.getString("address1"));
				mb.setAddress2(rs.getString("address2"));
				mb.setMobile(rs.getString("mobile"));
				mb.setEmail(rs.getString("email"));
				mb.setPostcode(rs.getString("postcode"));

				memberList.add(mb);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			com.close(con, pstmt, rs);

		}
		return memberList;

	}// getMemberList

	public String findid(String name, String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		String id = "";

		try {
			// 1단계 드라이버로더
			con = com.connect();

			sql = "select id from member where name=? and email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				id = rs.getString("id");
				System.out.println(id);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 객체닫기
			com.close(con, pstmt, rs);
		}
		return id;
	}//findid

	public String findpass(String id, String name, String email) {
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql = "";
		ResultSet rs = null;
		String pass = "";
		String pass2 = "";
		try {
			// 1단계 드라이버로더
			con = com.connect();

			sql = "select pass from member where id=? and name=? and email=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			rs = pstmt.executeQuery();
			if (rs.next()) {

				pass = rs.getString("pass");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 객체닫기
			com.close(con, pstmt, rs);
		}
		return pass;
	}//findpass
}// MemberDAO
