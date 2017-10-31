package net.board.db;

import java.sql.Date;

public class BoardReplyBean {
	private int num; //리플번호
	private String id; //작성자 id
	private Date date; //날짜
	private String content; //리플내용
	private int re_ref; //리플번호
	private int re_lev;
	private int re_seq;
	private int group_del; //board num = reply group_del (board의 글 num과 같은 group_del리플이 전부 출력)
	
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRe_ref() {
		return re_ref;
	}
	public void setRe_ref(int re_ref) {
		this.re_ref = re_ref;
	}
	public int getRe_lev() {
		return re_lev;
	}
	public void setRe_lev(int re_lev) {
		this.re_lev = re_lev;
	}
	public int getRe_seq() {
		return re_seq;
	}
	public void setRe_seq(int re_seq) {
		this.re_seq = re_seq;
	}
	public int getGroup_del() {
		return group_del;
	}
	public void setGroup_del(int group_del) {
		this.group_del = group_del;
	}
}
