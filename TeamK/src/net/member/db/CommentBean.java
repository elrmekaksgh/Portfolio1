package net.member.db;

import java.sql.Timestamp;

public class CommentBean {
	private int num;
	private String id;
	private String date;
	private String content;
	private int re_ref;
	private int re_lev;
	private int re_seq;
	private int group_del;
	private int h_or_s;
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
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
	public int getH_or_s() {
		return h_or_s;
	}
	public void setH_or_s(int h_or_s) {
		this.h_or_s = h_or_s;
	}

	
	
}
