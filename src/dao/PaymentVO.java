package dao;

import java.util.Date;

public class PaymentVO {
	private String memId;
	private int paymentNum;
	private String[] product_id;
	//private String product_id2;
	private String[] product_name;
	private int[] product_price;
	private int[] quantity;
	private int[] money;   
	private String phone;
	private String email;
	private String address;
	private String payment;
	private int sumMoney;
	private int fee;
	private int allSum;
	private String paymentDate;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String[] getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String[] product_id) {
		this.product_id = product_id;
	}
	public int[] getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int[] product_price) {
		this.product_price = product_price;
	}
	public int[] getQuantity() {
		return quantity;
	}
	public void setQuantity(int[] quantity) {
		this.quantity = quantity;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public int getSumMoney() {
		return sumMoney;
	}
	public void setSumMoney(int sumMoney) {
		this.sumMoney = sumMoney;
	}
	public int getFee() {
		return fee;
	}
	public void setFee(int fee) {
		this.fee = fee;
	}
	public int getAllSum() {
		return allSum;
	}
	public void setAllSum(int allSum) {
		this.allSum = allSum;
	}
	public String[] getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String[] product_name) {
		this.product_name = product_name;
	}
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public int[] getMoney() {
		return money;
	}
	public void setMoney(int[] money) {
		this.money = money;
	}
	public int getPaymentNum() {
		return paymentNum;
	}
	public void setPaymentNum(int paymentNum) {
		this.paymentNum = paymentNum;
	}
	

}
