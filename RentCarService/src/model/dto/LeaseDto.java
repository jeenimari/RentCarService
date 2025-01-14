package model.dto;

import java.util.Date;

/**
 * LeaseDto - 렌트/리스 신청 정보 관리 클래스
 * 
 * [클래스 설명]
 * 1. 목적
 * - 렌트카 서비스의 렌트/리스 신청 정보를 관리하는 데이터 전송 객체
 * - 데이터베이스의 LEASE_APPLICATION 테이블과 매핑
 * 
 * 2. 주요 기능
 * - 렌트/리스 신청 정보의 생성 및 관리
 * - 데이터 유효성 검증 (전화번호 형식 등)
 * - 계약 상태 관리 (대기중/승인/거절)
 * - 계약 금액 정보 관리 (보증금, 선납금, 월납입금 등)
 * 
 * 3. 개발 규칙
 * - 모든 멤버 변수는 private으로 선언 (캡슐화)
 * - Getter/Setter를 통한 데이터 접근
 * - 데이터 검증이 필요한 필드는 Setter에서 유효성 검사 수행
 * 
 * 4. 코드 구성
 * A. 멤버 변수 선언
 * - 기본 정보 (신청번호, 신청자정보, 신청종류)
 * - 계약 정보 (보증금, 선납금, 잔존가치)
 * - 상태 정보 (신청일자, 처리상태)
 * 
 * B. 생성자 구현
 * - 기본 생성자: 프레임워크 요구사항
 * - 필수 정보 생성자: 신규 신청용
 * - 계약 정보 생성자: 계약 체결용
 * - 전체 필드 생성자: DB 조회 결과 매핑용
 * 
 * C. Getter/Setter 메소드
 * - 모든 필드에 대한 접근자/설정자 구현
 * - 필요한 경우 유효성 검사 로직 포함
 * 
 * D. 유틸리티 메소드
 * - toString(): 객체 상태 출력용
 * 
 * 5. 확장 고려사항
 * - 추가 유효성 검사 규칙 적용 가능
 * - 계약 상태 변경 이력 관리 기능 추가 가능
 * - 계약금액 계산 로직 추가 가능
 */
public class LeaseDto extends Dto {

  /* 데이터베이스 테이블 컬럼과 1:1 매핑되는 멤버 변수 */

  // === 기본 정보 ===
  private int ano; // PK, 자동 증가 (AUTO_INCREMENT)
  private String aname; // 신청자 이름 (VARCHAR, NOT NULL)
  private String aphone; // 연락처 (VARCHAR, NOT NULL, 형식: 000-0000-0000)
  private int atype; // 신청 종류 (TINYINT, 0:렌트, 1:리스)

  // === 계약 정보 ===
  private int deposit; // 보증금 (INT, 계약금)
  private int prepayments; // 선납금 (INT, 리스 계약시)
  private int residual_value; // 잔존가치 (INT, 리스 종료 후 예상 가치)
  private int duration; // 계약기간 (INT, 개월 단위)
  private int gno; // FK, 차량등급번호 (INT, GRADE_INFO 테이블 참조)
  private int monthlyPay; // 월 납입금 (INT)

  // === 상태 정보 ===
  private Date applyDate; // 신청일자 (DATETIME, DEFAULT: CURRENT_TIMESTAMP)
  private int status; // 처리상태 (TINYINT, 0:대기중, 1:승인, 2:거절)

  /**
   * 기본 생성자
   * - JPA, MyBatis 등 프레임워크 사용을 위한 필수 요소
   * - 리플렉션을 통한 객체 생성 지원
   */
  public LeaseDto() {
  }

  /**
   * 신규 신청용 생성자
   * - 렌트/리스 신청시 필수 정보만으로 객체 생성
   * - 상태값 자동 설정 (대기중)
   * - 신청일자 자동 설정 (현재시간)
   */
  public LeaseDto(String aname, String aphone, int atype, int gno, int duration) {
    this.aname = aname;
    this.aphone = aphone;
    this.atype = atype;
    this.gno = gno;
    this.duration = duration;
    this.status = 0;
    this.applyDate = new Date();
  }

  /**
   * 계약 체결용 생성자
   * - 기본 정보 + 계약 금액 정보 포함
   * - 실제 계약 체결 단계에서 사용
   */
  public LeaseDto(String aname, String aphone, int atype, int gno,
      int duration, int deposit, int monthlyPay) {
    this(aname, aphone, atype, gno, duration);
    this.deposit = deposit;
    this.monthlyPay = monthlyPay;
  }

  /**
   * 전체 필드 생성자
   * - DB 조회 결과를 객체로 변환할 때 사용
   * - 모든 필드값을 한번에 초기화
   */
  public LeaseDto(int ano, String aname, String aphone, int atype,
      int deposit, int prepayments, int residual_value,
      int duration, int gno, int monthlyPay,
      Date applyDate, int status) {
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

  /* Getter/Setter 메소드 */
  // 각 필드에 대한 접근자/설정자 메소드
  // 필요한 경우 유효성 검사 로직 포함

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

  /**
   * 전화번호 설정 메소드
   * - 전화번호 형식 검증 포함 (000-0000-0000)
   * - 잘못된 형식인 경우 예외 발생
   * 
   * @throws IllegalArgumentException 전화번호 형식이 맞지 않는 경우
   */
  public void setAphone(String aphone) {
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
   * 객체 상태 문자열 반환
   * - 디버깅 및 로깅 목적으로 사용
   * - 모든 필드값을 포함한 문자열 생성
   * 
   * @return 객체의 현재 상태를 나타내는 문자열
   */
  @Override
  public String toString() {
    return "LeaseDto [ano=" + ano + ", aname=" + aname + ", aphone=" + aphone +
        ", atype=" + atype + ", deposit=" + deposit + ", prepayments=" + prepayments +
        ", residual_value=" + residual_value + ", duration=" + duration + ", gno=" + gno +
        ", monthlyPay=" + monthlyPay + ", applyDate=" + applyDate + ", status=" + status + "]";
  }
}
