package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.dto.LeaseDto;

/**
 * [클래스명] LeaseDao
 * [설명] 리스 관련 데이터베이스 작업을 처리하는 DAO(Data Access Object) 클래스
 * 
 * [주요 기능]
 * 1. 차량 정보 조회 (카테고리, 브랜드 목록)
 * 2. 리스 신청 처리 (등록, 조회, 상태 변경)
 * 3. 데이터베이스 CRUD 작업 수행
 * 
 * [테이블 구조]
 * 1. apply 테이블
 * - ano: 신청번호 (PK, auto_increment)
 * - aname: 신청자 이름
 * - aphone: 연락처 (형식: 000-0000-0000)
 * - atype: 신청종류 (0:렌트, 1:리스)
 * - deposit: 보증금
 * - prepayments: 선납금
 * - residual_value: 잔존가치
 * - duration: 계약기간(월)
 * - gno: 차량등급번호 (FK)
 * - monthly_pay: 월납입금
 * - apply_date: 신청일자
 * - status: 신청상태 (0:대기중, 1:승인, 2:거절)
 * 
 * 2. car 테이블: 차량 카테고리 정보
 * 3. brand 테이블: 브랜드 정보
 */
public class LeaseDao extends Dao {

  /**
   * [필드] 싱글톤 인스턴스
   * - 데이터베이스 연결의 효율적 관리를 위한 싱글톤 패턴 구현
   */
  private static LeaseDao instance = new LeaseDao();

  /**
   * [생성자] private 생성자
   * - 싱글톤 패턴 구현을 위해 private으로 선언
   * - 부모 클래스(Dao)의 생성자를 호출하여 DB 연결 초기화
   */
  private LeaseDao() {
    super(); // DB 연결 초기화
  }

  /**
   * [메소드] getInstance
   * - 싱글톤 인스턴스 반환 메소드
   * 
   * @return LeaseDao 싱글톤 인스턴스
   */
  public static LeaseDao getInstance() {
    return instance;
  }

  /**
   * [메소드] getCategoryList
   * - 차량 카테고리 목록 조회
   * 
   * [처리내용]
   * 1. car 테이블의 모든 카테고리 정보 조회
   * 2. 카테고리 번호(cno)와 이름(cname) 매핑
   * 3. LeaseDto 객체로 변환하여 목록 반환
   * 
   * @return ArrayList<LeaseDto> 카테고리 목록
   */
  public ArrayList<LeaseDto> getCategoryList() {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM car";

    try (PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery()) {

      while (rs.next()) {
        LeaseDto dto = new LeaseDto();
        dto.setCno(rs.getInt("cno"));
        dto.setCname(rs.getString("cname"));
        list.add(dto);
      }
    } catch (SQLException e) {
      System.out.println("[오류] 카테고리 조회 실패: " + e.getMessage());
    }
    return list;
  }

  /**
   * [메소드] registerApplication
   * - 신규 리스 신청 정보 등록
   * 
   * [처리내용]
   * 1. 신청자 정보 및 계약 조건 저장
   * 2. 신청 상태는 기본값 0(대기중)으로 설정
   * 3. 신청일자는 현재 시간(NOW())으로 자동 설정
   * 
   * [입력 데이터]
   * - aname: 신청자 이름 (필수)
   * - aphone: 연락처 (필수, 형식 검증)
   * - atype: 신청종류 (0:렌트, 1:리스)
   * - deposit: 보증금
   * - prepayments: 선납금
   * - residual_value: 잔존가치
   * - duration: 계약기간
   * - gno: 차량등급번호
   * - monthly_pay: 월납입금
   * 
   * @param dto 리스 신청 정보가 담긴 DTO
   * @return boolean 등록 성공 여부
   */
  public boolean registerApplication(LeaseDto dto) {
    String sql = "INSERT INTO apply (aname, aphone, atype, deposit, prepayments, " +
        "residual_value, duration, gno, monthly_pay, apply_date, status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), 0)";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setString(1, dto.getAname());
      ps.setString(2, dto.getAphone());
      ps.setInt(3, dto.getAtype());
      ps.setInt(4, dto.getDeposit());
      ps.setInt(5, dto.getPrepayments());
      ps.setInt(6, dto.getResidual_value());
      ps.setInt(7, dto.getDuration());
      ps.setInt(8, dto.getGno());
      ps.setInt(9, dto.getMonthlyPay());

      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      System.out.println("[오류] 리스 신청 등록 실패: " + e.getMessage());
      return false;
    }
  }

  /**
   * [메소드] getLeaseStatus
   * - 리스 신청 상태 조회
   * 
   * [처리내용]
   * 1. 신청번호(ano)로 신청 정보 조회
   * 2. 조회 결과를 LeaseDto 객체로 변환
   * 3. 신청 정보가 없는 경우 null 반환
   * 
   * [조회 정보]
   * - 신청자 정보 (이름, 연락처)
   * - 계약 조건 (보증금, 선납금, 잔존가치 등)
   * - 신청 상태 (0:대기중, 1:승인, 2:거절)
   * 
   * @param ano 조회할 신청 번호
   * @return LeaseDto 신청 정보 (없으면 null)
   */
  public LeaseDto getLeaseStatus(int ano) {
    String sql = "SELECT * FROM apply WHERE ano = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, ano);

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          return new LeaseDto(
              rs.getInt("ano"),
              rs.getString("aname"),
              rs.getString("aphone"),
              rs.getInt("atype"),
              rs.getInt("deposit"),
              rs.getInt("prepayments"),
              rs.getInt("residual_value"),
              rs.getInt("duration"),
              rs.getInt("gno"),
              rs.getInt("monthly_pay"),
              rs.getTimestamp("apply_date"),
              rs.getInt("status"));
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 리스 상태 조회 실패: " + e.getMessage());
    }
    return null;
  }

  /**
   * [메소드] updateLeaseStatus
   * - 리스 신청 상태 변경
   * 
   * [처리내용]
   * 1. 신청번호(ano)로 해당 신청건 확인
   * 2. 상태값 업데이트 (0:대기중 → 1:승인 또는 2:거절)
   * 3. 변경 결과 반환
   * 
   * [상태코드]
   * - 0: 대기중 (초기상태)
   * - 1: 승인 (계약진행)
   * - 2: 거절 (계약거절)
   * 
   * @param ano    변경할 신청 번호
   * @param status 변경할 상태 값
   * @return boolean 상태 변경 성공 여부
   */
  public boolean updateLeaseStatus(int ano, int status) {
    String sql = "UPDATE apply SET status = ? WHERE ano = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, status);
      ps.setInt(2, ano);

      return ps.executeUpdate() == 1;
    } catch (SQLException e) {
      System.out.println("[오류] 상태 변경 실패: " + e.getMessage());
      return false;
    }
  }

  /**
   * [메소드] getBrandList
   * - 특정 카테고리의 브랜드 목록 조회
   * 
   * [처리내용]
   * 1. 카테고리 번호(cno)로 해당 카테고리의 브랜드 조회
   * 2. 브랜드 정보(bno, bname) 매핑
   * 3. LeaseDto 객체로 변환하여 목록 반환
   * 
   * [조회 조건]
   * - cno: 카테고리 번호 (car 테이블의 PK)
   * 
   * [조회 항목]
   * - bno: 브랜드 번호
   * - bname: 브랜드 이름
   * 
   * @param cno 조회할 카테고리 번호
   * @return ArrayList<LeaseDto> 브랜드 목록
   */
  public ArrayList<LeaseDto> getBrandList(int cno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM brand WHERE cno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, cno);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setBno(rs.getInt("bno"));
          dto.setBname(rs.getString("bname"));
          list.add(dto);
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 브랜드 목록 조회 실패: " + e.getMessage());
    }
    return list;
  }

  /**
   * [메소드] getModelList
   * - 특정 브랜드의 차량 모델 목록 조회
   * 
   * [처리내용]
   * 1. 브랜드 번호(bno)로 해당 브랜드의 모델 조회
   * 2. 모델 정보(mno, mname) 매핑
   * 3. LeaseDto 객체로 변환하여 목록 반환
   * 
   * [조회 조건]
   * - bno: 브랜드 번호 (brand 테이블의 PK)
   * 
   * [조회 항목]
   * - mno: 모델 번호
   * - mname: 모델 이름
   * 
   * @param bno 조회할 브랜드 번호
   * @return ArrayList<LeaseDto> 모델 목록
   */
  public ArrayList<LeaseDto> getModelList(int bno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM model WHERE bno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, bno);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setMno(rs.getInt("mno"));
          dto.setMname(rs.getString("mname"));
          list.add(dto);
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 모델 목록 조회 실패: " + e.getMessage());
    }
    return list;
  }

  /**
   * [메소드] getGradeList
   * - 특정 모델의 등급 목록 조회
   * 
   * [처리내용]
   * 1. 모델 번호(mno)로 해당 모델의 등급 정보 조회
   * 2. 등급 정보(gno, gname, price) 매핑
   * 3. LeaseDto 객체로 변환하여 목록 반환
   * 
   * [조회 조건]
   * - mno: 모델 번호 (model 테이블의 PK)
   * 
   * [조회 항목]
   * - gno: 등급 번호
   * - gname: 등급 이름
   * - price: 차량 가격
   * 
   * @param mno 조회할 모델 번호
   * @return ArrayList<LeaseDto> 등급 목록
   */
  public ArrayList<LeaseDto> getGradeList(int mno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM grade WHERE mno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, mno);

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setGno(rs.getInt("gno"));
          dto.setGname(rs.getString("gname"));
          dto.setGprice(rs.getInt("gprice"));
          list.add(dto);
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 등급 목록 조회 실패: " + e.getMessage());
    }
    return list;
  }
} // class end