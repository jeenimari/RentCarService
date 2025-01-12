package model.dto;

public class Dto {
	// 멤버변수
	protected int cno;
	protected int bno;
	protected int mno;
	protected int gno;
	protected int gprice;
	protected String cname;
	protected String bname;
	protected String mname;
	protected String gname;
	
	// 생성자
	public Dto() {}
	
	// getter
	public int getCno() { return cno; }
	public int getBno() { return bno; }
	public int getMno() { return mno; }
	public int getGno() { return gno; }
	public int getGprice() { return gprice; }
	public String getCname() { return cname; }
	public String getBname() { return bname; }
	public String getMname() { return mname; }
	public String getGname() { return gname; }
	
	//setter
	public void setCno(int cno) { this.cno = cno; }
	public void setBno(int bno) { this.bno = bno; }
	public void setMno(int mno) { this.mno = mno; }
	public void setGno(int gno) { this.gno = gno; }
	public void setGprice(int gprice) { this.gprice = gprice; }
	public void setCname(String cname) { this.cname = cname; }
	public void setBname(String bname) { this.bname = bname; }
	public void setMname(String mname) { this.mname = mname; }
	public void setGname(String gname) { this.gname = gname; }

	@Override
	public String toString() {
		return "Dto [cno=" + cno + ", bno=" + bno + ", mno=" + mno + ", gno=" + gno + ", gprice=" + gprice + ", cname="
				+ cname + ", bname=" + bname + ", mname=" + mname + ", gname=" + gname + "]";
	}
		
}
