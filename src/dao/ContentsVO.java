package dao;

public class ContentsVO {
	private int contents_num;           // ÄÁÅÙÃ÷ ¹øÈ£
	private String contents_subject;    // ÄÁÅÙÃ÷ Á¦¸ñ
	private String contents_creator;    // ÄÁÅÙÃ÷ Å©¸®¿¡ÀÌÅÍ
	private String contents_src;        // ÄÁÅÙÃ÷ µ¿¿µ»ó ÁÖ¼Ò
	private String contents_file;       // ÄÁÅÙÃ÷ ÆÄÀÏ
	private int contents_count;         // ÄÁÅÙÃ÷ Á¶È¸¼ö
	
	public int getContents_num() {
		return contents_num;
	}
	public void setContents_num(int contents_num) {
		this.contents_num = contents_num;
	}
	public String getContents_creator() {
		return contents_creator;
	}
	public void setContents_creator(String contents_creator) {
		this.contents_creator = contents_creator;
	}
	public int getContents_count() {
		return contents_count;
	}
	public void setContents_count(int contents_count) {
		this.contents_count = contents_count;
	}
	public String getContents_subject() {
		return contents_subject;
	}
	public void setContents_subject(String contents_subject) {
		this.contents_subject = contents_subject;
	}
	public String getContents_src() {
		return contents_src;
	}
	public void setContents_src(String contents_src) {
		this.contents_src = contents_src;
	}
	public String getContents_file() {
		return contents_file;
	}
	public void setContents_file(String contents_file) {
		this.contents_file = contents_file;
	}
}
