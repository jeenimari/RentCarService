package model.dto;

// LeaseDto 데이터를 담고 있는 데이터 전송 객체
public class LeaseDto extends Dto {
  // 멤버변수
  private int ano; // 식별번호
  private String aname; // 신청자명
  private String aphone; // 신청자 전화번호
  private int atype; // 신청 종류(리스 / 렌트)
  private int deposit; // 보증금
  private int prepayments; // 선납금
  private int residual_value; // 잔존가치
  private int duration; // 사용기간

  // 생성자 메소드 : LeaseDto 객체 생성시 데이터 초기화
  public LeaseDto(int ano, String aname, String aphone, int atype, int deposit, int prepayments, int residual_value,
      int duration) {
    this.ano = ano;
    this.aname = aname;
    this.aphone = aphone;
    this.atype = atype;
    this.deposit = deposit;
    this.prepayments = prepayments;
    this.residual_value = residual_value;
    this.duration = duration;
  } // constructor end

  // getter, setter 메소드 : 각 변수의 값을 반환.
  public int getAno() {
    return ano;
  }

  public void setAno(int ano) {
    this.ano = ano;
  }

  public String getAname() {
    return aname;
  }

  public void setAname(String aname) {
    this.aname = aname;
  }

  public String getAphone() {
    return aphone;
  }

  public void setAphone(String aphone) {
    this.aphone = aphone;
  }

  public int getAtype() {
    return atype;
  }

  public void setAtype(int atype) {
    this.atype = atype;
  }

  public int getDeposit() {
    return deposit;
  }

  public void setDeposit(int deposit) {
    this.deposit = deposit;
  }

  public int getPrepayments() {
    return prepayments;
  }

  public void setPrepayments(int prepayments) {
    this.prepayments = prepayments;
  }

  public int getResidual_value() {
    return residual_value;
  }

  public void setResidual_value(int residual_value) {
    this.residual_value = residual_value;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  // toString() 메소드 : 객체의 데이터를 문자열로 변환
  @Override
  public String toString() {
    return "LeaseDto [ano=" + ano + ", aname=" + aname + ", aphone=" + aphone + ", atype=" + atype + ", deposit="
        + deposit + ", prepayments=" + prepayments + ", residual_value=" + residual_value + ", duration=" + duration
        + "]";
  } // toString() end

} // class end
