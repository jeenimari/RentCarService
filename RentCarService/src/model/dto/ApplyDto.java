package model.dto;

public class ApplyDto extends Dto {
	private int ano;
	private String aname;
	private String aphone;
	private int atype;
	private int deposit;
	private int prepayments;
	private int residual_value;
	private int duration;
	
	public ApplyDto() {}
	public ApplyDto(int ano, String aname, String aphone, 
			int atype, int deposit, int prepayments, int residual_value, int duration) {
		this.ano = ano; this.aname = aname; this.aphone = aphone;
		this.atype = atype; this.deposit = deposit; this.prepayments = prepayments;
		this.residual_value = residual_value; this.duration = duration;
	}
	
	//getter
	public int getAno() { return this.ano; }
	public String getAname() { return this.aname; }
	public String getAphone() { return this.aphone; }
	public int getAtype() { return this.atype; }
	public int getDeposit() { return this.deposit; }
	public int getPrepayments() { return this.prepayments; }
	public int getResidualValue() { return this.residual_value; }
	public int getDuration() { return this.duration; }	
	
	//setter
	public void setAno(int ano) { this.ano = ano; }
	public void setAname(String aname) { this.aname = aname; }
	public void setAphone(String aphone) { this.aphone = aphone; }
	public void setAtype(int atype) { this.atype = atype; }
	public void setDeposit(int deposit) { this.deposit = deposit; }
	public void setPrepayments(int prepayments) { this.prepayments = prepayments; }
	public void setResidualValue(int residual_value) { this.residual_value = residual_value; }
	public void setDuration(int duration) { this.duration = duration; }
	
	@Override
	public String toString() {
		return "AdminDto [ano=" + ano + ", aname=" + aname + ", aphone=" + aphone + ", atype=" + atype + ", deposit="
				+ deposit + ", prepayments=" + prepayments + ", residual_value=" + residual_value + ", duration="
				+ duration + "]";
	}
	
	
}
