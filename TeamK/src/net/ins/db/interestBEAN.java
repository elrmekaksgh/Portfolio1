package net.ins.db;

import java.sql.Timestamp;

public class interestBEAN {
	private int inter_num,ori_num, cost, car_num;
	private String id, type, subject, img, intro;
	private Timestamp date;
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getInter_num() {
		return inter_num;
	}
	public void setInter_num(int inter_num) {
		this.inter_num = inter_num;
	}
	public int getOri_num() {
		return ori_num;
	}
	public void setOri_num(int ori_num) {
		this.ori_num = ori_num;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public int getCar_num() {
		return car_num;
	}
	public void setCar_num(int car_num) {
		this.car_num = car_num;
	}
	
}
