package net.board.db;

import java.sql.Date;

public class BoardBean {
	  private int num; //글번호 (글쓸때마다 board 테이블 전체에서 1씩증가)
	  private String id; //아이디
	  private String subject; //제목
	  private String content; //내용
	  private int readcount; //조회수
	  private Date date; //날짜
	  private String file1;
	  private String file2;
	  private String file3;
	  private String file4;
	  private String file5;
	  private int type; //type 1일때는 리뷰게시판, 2일때는 Q&A 게시판 , 3일때는 공지사항
	  private int re_ref; //게시판에 표시되는 글번호(글쓸때마다 type에 따라서 각각 1씩 증가)
	  private String email; // Q&A게시판에 글남길때 답변받을 메일주소
	  private String type_select; // 글 분류 
	  
	  
	
	public String getType_select() {
		return type_select;
	}
	public void setType_select(String type_select) {
		this.type_select = type_select;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getReadcount() {
		return readcount;
	}
	public void setReadcount(int readcount) {
		this.readcount = readcount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getFile1() {
		return file1;
	}
	public void setFile1(String file1) {
		this.file1 = file1;
	}
	public String getFile2() {
		return file2;
	}
	public void setFile2(String file2) {
		this.file2 = file2;
	}
	public String getFile3() {
		return file3;
	}
	public void setFile3(String file3) {
		this.file3 = file3;
	}
	public String getFile4() {
		return file4;
	}
	public void setFile4(String file4) {
		this.file4 = file4;
	}
	public String getFile5() {
		return file5;
	}
	public void setFile5(String file5) {
		this.file5 = file5;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
