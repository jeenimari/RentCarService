package model.dto;

public class RentDto extends Dto {
	
	// 추가 필드 (신청 관련)
	   private int ano;
	   private String aname;
	   private String aphone;
	   private int atype;
	   private int deposit;
	   private int prepayments;
	   private int residualValue;
	   private int duration;

	   // 기본 생성자
	   public RentDto() {
	       super(); // 부모 클래스의 생성자 호출
	   }

	   // 전체 필드를 포함한 생성자
	   public RentDto(int cno, int bno, int mno, int gno, int gprice,
	                 String cname, String bname, String mname, String gname,
	                 int ano, String aname, String aphone, int atype,
	                 int deposit, int prepayments, int residualValue, int duration) {
	       // 부모 클래스의 필드 초기화
	       super();
	       this.cno = cno;
	       this.bno = bno;
	       this.mno = mno;
	       this.gno = gno;
	       this.gprice = gprice;
	       this.cname = cname;
	       this.bname = bname;
	       this.mname = mname;
	       this.gname = gname;
	       
	       // 자식 클래스의 필드 초기화
	       this.ano = ano;
	       this.aname = aname;
	       this.aphone = aphone;
	       this.atype = atype;
	       this.deposit = deposit;
	       this.prepayments = prepayments;
	       this.residualValue = residualValue;
	       this.duration = duration;
	   }

	   // 신청 정보만을 위한 생성자
	   public RentDto(String aname, String aphone, int atype,
	                 int deposit, int prepayments, int residualValue, int duration) {
	       super();
	       this.aname = aname;
	       this.aphone = aphone;
	       this.atype = atype;
	       this.deposit = deposit;
	       this.prepayments = prepayments;
	       this.residualValue = residualValue;
	       this.duration = duration;
	   }

	   // 신청 관련 필드의 getter/setter
	   public int getAno() { return ano; }
	   public void setAno(int ano) { this.ano = ano; }
	   
	   public String getAname() { return aname; }
	   public void setAname(String aname) { this.aname = aname; }
	   
	   public String getAphone() { return aphone; }
	   public void setAphone(String aphone) { this.aphone = aphone; }
	   
	   public int getAtype() { return atype; }
	   public void setAtype(int atype) { this.atype = atype; }
	   
	   public int getDeposit() { return deposit; }
	   public void setDeposit(int deposit) { this.deposit = deposit; }
	   
	   public int getPrepayments() { return prepayments; }
	   public void setPrepayments(int prepayments) { this.prepayments = prepayments; }
	   
	   public int getResidualValue() { return residualValue; }
	   public void setResidualValue(int residualValue) { this.residualValue = residualValue; }
	   
	   public int getDuration() { return duration; }
	   public void setDuration(int duration) { this.duration = duration; }
}
