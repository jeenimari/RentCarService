package model.dto;

import java.util.Date;

// Todo : 1. LeaseDto 클래스
//  - 렌트/리스 신청 정보를 담는 데이터 전송 객체(DTO)
//  - Dto 클래스를 상속받아 기본 기능 확장

public class LeaseDto extends Dto {

  /* 2. 멤버 변수 선언부 */
  private int ano; // 1) 렌트/리스 신청 식별 번호 (auto increment)
  private String aname; // 2) 신청자 이름
  private String aphone; // 3) 신청자 연락처 (형식: 000-0000-0000)
  private int atype; // 4) 신청 종류 (0:렌트, 1:리스)
  private int deposit; // 5) 보증금 (계약시 납부하는 금액)
  private int prepayments; // 6) 선납금 (계약시 선납하는 금액)
  private int residual_value; // 7) 잔존가치 (리스 종료 후 차량의 예상 가치)
  private int duration; // 8) 계약 기간 (개월 단위)
  private int gno; // 9) 선택한 차량 등급 번호
  private int monthlyPay; // 10) 월 납입금
  private Date applyDate; // 11) 신청 일자 (자동 생성)
  private int status; // 12) 신청 상태 (0:대기중, 1:승인, 2:거절)

  // Todo : 3. 생성자 정의부
  // 3-1. 기본 생성자
  // - JPA나 프레임워크 사용을 위한 필수 생성자
  // - 객체 생성 후 setter로 값 설정 가능

  public LeaseDto() {
  }

  /**
   * 3-2. 렌트/리스 신청 시 필요한 기본 정보 생성자
   * 
   * @param aname    신청자 이름
   * @param aphone   연락처
   * @param atype    렌트/리스 구분
   * @param gno      차량 등급
   * @param duration 계약 기간
   */

  public LeaseDto(String aname, String aphone, int atype, int gno, int duration) {
    this.aname = aname; // 신청자 이름 설정
    this.aphone = aphone; // 연락처 설정
    this.atype = atype; // 렌트/리스 구분 설정
    this.gno = gno; // 차량 등급 설정
    this.duration = duration; // 계약 기간 설정
    this.status = 0; // 신청 상태 초기값: 대기중
    this.applyDate = new Date(); // 현재 시간으로 신청일자 자동 설정
  }

  /**
   * 3-3. 계약 금액 정보가 포함된 생성자
   * - 기본 정보에 금액 관련 정보 추가
   * - 실제 계약 체결 시 사용
   */
  public LeaseDto(String aname, String aphone, int atype, int gno,
      int duration, int deposit, int monthlyPay) {
    this(aname, aphone, atype, gno, duration); // 기본 정보 생성자 호출
    this.deposit = deposit; // 보증금 설정
    this.monthlyPay = monthlyPay; // 월 납입금 설정
  }

  /**
   * 3-4. 전체 필드 생성자
   * - 데이터베이스 조회 결과를 객체로 변환할 때 사용
   * - 모든 필드 값을 한 번에 설정
   */
  public LeaseDto(int ano, String aname, String aphone, int atype,
      int deposit, int prepayments, int residual_value,
      int duration, int gno, int monthlyPay,
      Date applyDate, int status) {
    // 모든 필드 초기화
    this.ano = ano;
    this.aname = aname;
    this.aphone = aphone;
    this.atype = atype;
    this.deposit = deposit;
    this.prepayments = prepayments;
    this.residual_value = residual_value;
    this.duration = duration;
    this.gno = gno;
    this.monthlyPay = monthlyPay;
    this.applyDate = applyDate;
    this.status = status;
  }

  // Todo : 4. Getter/Setter 메소드
  // 각 멤버 변수에 대한 접근자/설정자 메소드
  // 캡슐화 원칙에 따라 private 멤버 변수 접근을 위해 필요

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
    // 전화번호 형식 검증 추가
    if (!aphone.matches("\\d{3}-\\d{4}-\\d{4}")) {
      throw new IllegalArgumentException("잘못된 전화번호 형식입니다.");
    }
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

  public int getGno() {
    return gno;
  }

  public void setGno(int gno) {
    this.gno = gno;
  }

  public int getMonthlyPay() {
    return monthlyPay;
  }

  public void setMonthlyPay(int monthlyPay) {
    this.monthlyPay = monthlyPay;
  }

  public Date getApplyDate() {
    return applyDate;
  }

  public void setApplyDate(Date applyDate) {
    this.applyDate = applyDate;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  /**
   * Todo : 5. toString 메소드 오버라이드
   * - 객체의 현재 상태를 문자열로 반환
   * - 디버깅 및 로깅 용도로 사용
   */
  @Override
  public String toString() {
    return "LeaseDto [ano=" + ano + ", aname=" + aname + ", aphone=" + aphone +
        ", atype=" + atype + ", deposit=" + deposit + ", prepayments=" + prepayments +
        ", residual_value=" + residual_value + ", duration=" + duration + ", gno=" + gno +
        ", monthlyPay=" + monthlyPay + ", applyDate=" + applyDate + ", status=" + status + "]";
  }

} // class end
