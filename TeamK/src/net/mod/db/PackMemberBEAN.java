package net.mod.db;

public class PackMemberBEAN {
	private String hangul_name, first_name, last_name, mobile, pm_id;
	private int pm_num, po_num, leader_check,birth_day, adult_or_child;
	public int getAdult_or_child() {
		return adult_or_child;
	}
	public void setAdult_or_child(int adult_or_child) {
		this.adult_or_child = adult_or_child;
	}
	public String getPm_id() {
		return pm_id;
	}
	public void setPm_id(String pm_id) {
		this.pm_id = pm_id;
	}
	public int getPm_num() {
		return pm_num;
	}
	public void setPm_num(int pm_num) {
		this.pm_num = pm_num;
	}
	public int getPo_num() {
		return po_num;
	}
	public void setPo_num(int po_num) {
		this.po_num = po_num;
	}
	public int getLeader_check() {
		return leader_check;
	}
	public void setLeader_check(int leader_check) {
		this.leader_check = leader_check;
	}
	public String getHangul_name() {
		return hangul_name;
	}
	public void setHangul_name(String hangul_name) {
		this.hangul_name = hangul_name;
	}
	public int getBirth_day() {
		return birth_day;
	}
	public void setBirth_day(int birth_day) {
		this.birth_day = birth_day;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
