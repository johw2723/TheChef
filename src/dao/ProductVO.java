package dao;

public class ProductVO {
	private String product_id;       // ��ǰ id
	private String product_name;     // ��ǰ �̸�
	private int product_price;    // ��ǰ ����
	private String product_detail;   // ��ǰ ������
	private String product_keyword;  // ��ǰ Ű����
	private String product_url;      // ��ǰ �̹��� ���� �ּ�
	
	public String getProduct_id() {
		return product_id;
	}
	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public int getProduct_price() {
		return product_price;
	}
	public void setProduct_price(int product_price) {
		this.product_price = product_price;
	}
	public String getProduct_detail() {
		return product_detail;
	}
	public void setProduct_detail(String product_detail) {
		this.product_detail = product_detail;
	}
	public String getProduct_keyword() {
		return product_keyword;
	}
	public void setProduct_keyword(String product_keyword) {
		this.product_keyword = product_keyword;
	}
	public String getProduct_url() {
		return product_url;
	}
	public void setProduct_url(String product_url) {
		this.product_url = product_url;
	}
}
