package net.mod.db;

import java.sql.Timestamp;

public class ModTradeInfoBEAN {
	
	
	private String address1, address2, postcode, name, mobile;
	private String trade_type, id, pack_count, memo, color, size,payer ;
	private String img, subject, intro,trade_num, status_text, trans_num;
	private String o_memo;
	private int stock_check,status, thing_count, ori_num,cost, po_receive_check,num, ti_num,total_cost,to_null_check;
	private Timestamp trade_date,date;
	private int stock;
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getStock_check() {
		return stock_check;
	}
	public void setStock_check(int stock_check) {
		this.stock_check = stock_check;
	}
	public String getO_memo() {
		return o_memo;
	}
	public void setO_memo(String o_memo) {
		this.o_memo = o_memo;
	}
	public int getTo_null_check() {
		return to_null_check;
	}
	public void setTo_null_check(int to_null_check) {
		this.to_null_check = to_null_check;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getTrans_num() {
		return trans_num;
	}
	public void setTrans_num(String trans_num) {
		this.trans_num = trans_num;
	}
	public Timestamp getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(Timestamp trade_date) {
		this.trade_date = trade_date;
	}
	public int getTotal_cost() {
		return total_cost;
	}
	public void setTotal_cost(int total_cost) {
		this.total_cost = total_cost;
	}
	public int getTi_num() {
		return ti_num;
	}
	public void setTi_num(int ti_num) {
		this.ti_num = ti_num;
	}
	public String getStatus_text() {
		return status_text;
	}
	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getTrade_num() {
		return trade_num;
	}
	public void setTrade_num(String trade_num) {
		this.trade_num = trade_num;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public int getNum(){
		return num;
	}
	public void setNum(int num){
		this.num = num;
	}
	public int getPo_receive_check() {
		return po_receive_check;
	}
	public void setPo_receive_check(int po_receive_check) {
		this.po_receive_check = po_receive_check;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPack_count() {
		return pack_count;
	}
	public void setPack_count(String pack_count) {
		this.pack_count = pack_count;
	}
	public int getThing_count() {
		return thing_count;
	}
	public void setThing_count(int thing_count) {
		this.thing_count = thing_count;
	}
	public int getOri_num() {
		return ori_num;
	}
	public void setOri_num(int ori_num) {
		this.ori_num = ori_num;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
}
