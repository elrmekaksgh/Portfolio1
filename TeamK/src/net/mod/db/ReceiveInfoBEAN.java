package net.mod.db;

public class ReceiveInfoBEAN {
	private int ra_num, basic_setting, trade_type, status;
	private String id, name,mobile, postcode, address1,address2;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(int trade_type) {
		this.trade_type = trade_type;
	}
	public int getRa_num() {
		return ra_num;
	}
	public void setRa_num(int ra_num) {
		this.ra_num = ra_num;
	}
	public int getBasic_setting() {
		return basic_setting;
	}
	public void setBasic_setting(int basic_setting) {
		this.basic_setting = basic_setting;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
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
}
