package dao;

import java.util.*;

public class TheChefVO {
	private String memId;
	private String pw;
	private String email;
	private String phone;
	private String address;
	private String payment;
	private Date memberDate;
	
	public TheChefVO(String memId) {
		this.memId = memId;	
	}
	
	// join
	public TheChefVO(String memId, String pw, String email, String phone, String address, String payment) {
		this.memId = memId;
		this.pw = pw;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.payment = payment;
	}
		
	
	// List
	public TheChefVO(String memId, String pw, String email, String phone, String address, String payment, Date memberDate) {
		this.memId = memId;
		this.pw = pw;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.payment = payment;
		this.memberDate = memberDate;
	}
	
	// login
	public TheChefVO(String memId, String pw) {
		this.memId = memId;
		this.pw = pw;
	}

	public TheChefVO() {
	}

	public String getMemId() {
		return memId;
	}

	public void setMemId(String memId) {
		this.memId = memId;
	}
	
	public String getPw() {
		return pw;
	}
	
	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public Date getMemberDate() {
		return memberDate;
	}

	public void setMemberDate(Date memberDate) {
		this.memberDate = memberDate;
	}
}
