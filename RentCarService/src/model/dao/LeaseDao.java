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
 * 1. 차량 정보 조회 (카테고리, 브랜드, 모델, 등급)
 * 2. 리스 신청 처리 (등록, 조회, 상태 변경)
 * 3. 데이터베이스 CRUD 작업 수행
 * 
 * [데이터베이스 구조]
 * 1. car 테이블: 차량 카테고리 정보 (국산차/수입차)
 * - cno(PK): 카테고리 번호
 * - cname: 카테고리명
 * 
 * 2. brand 테이블: 브랜드 정보
 * - bno(PK): 브랜드 번호
 * - bname: 브랜드명
 * - cno(FK): 카테고리 번호
 * 
 * 3. model 테이블: 차량 모델 정보
 * - mno(PK): 모델 번호
 * - mname: 모델명
 * - bno(FK): 브랜드 번호
 * 
 * 4. grade 테이블: 차량 등급 정보
 * - gno(PK): 등급 번호
 * - gname: 등급명
 * - gprice: 차량 가격
 * - mno(FK): 모델 번호
 * 
 * 5. apply 테이블: 리스/렌트 신청 정보
 * - ano(PK): 신청번호 (auto_increment)
 * - aname: 신청자 이름
 * - aphone: 연락처 (형식: 000-0000-0000)
 * - atype: 신청종류 (1:렌트, 2:리스)
 * - deposit: 보증금 비율(%)
 * - prepayments: 선납금 비율(%)
 * - residual_value: 잔존가치 비율(%)
 * - duration: 계약기간(월)
 * 
 * [코드 작성 순서]
 * 1. 싱글톤 패턴 구현 (인스턴스 생성 및 반환)
 * 2. 차량 정보 조회 메소드 구현 (카테고리 → 브랜드 → 모델 → 등급)
 * 3. 리스 신청 처리 메소드 구현 (등록, 조회, 상태 변경)
 * 4. 예외 처리 및 리소스 관리
 */
public class LeaseDao extends Dao {

  /**
   * [싱글톤 패턴 구현]
   */
  private static LeaseDao instance = new LeaseDao();

  private LeaseDao() {
    super(); // 부모 클래스(Dao)의 생성자 호출하여 DB 연결 초기화
  }

  public static LeaseDao getInstance() {
    return instance;
  }

  /**
   * [메소드] getCategoryList
   * [설명] 차량 카테고리(국산차/수입차) 목록 조회
   * 
   * [처리 순서]
   * 1. car 테이블의 모든 카테고리 정보 조회
   * 2. ResultSet에서 데이터 추출하여 DTO 객체 생성
   * 3. ArrayList에 DTO 객체 추가하여 반환
   * 
   * [예외 처리]
   * - SQLException: DB 조회 실패 시 빈 ArrayList 반환
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
        dto.setCno(rs.getInt("cno")); // 카테고리 번호 설정
        dto.setCname(rs.getString("cname")); // 카테고리명 설정
        list.add(dto);
      }
    } catch (SQLException e) {
      System.out.println("[오류] 카테고리 조회 실패: " + e.getMessage());
    }
    return list;
  }

  /**
   * [메소드] registerApplication
   * [설명] 신규 리스 신청 정보 등록
   * 
   * [처리 순서]
   * 1. 입력받은 DTO 객체의 데이터 검증
   * 2. apply 테이블에 신청 정보 저장
   * 3. 등록 결과 반환 (성공: true, 실패: false)
   * 
   * [입력 데이터 검증]
   * - 이름: 필수 입력
   * - 연락처: 000-0000-0000 형식
   * - 계약유형: 1(렌트) 또는 2(리스)
   * - 보증금/선납금: 0~50% 범위
   * - 잔존가치: 30~50% 범위
   * - 계약기간: 36/48/60개월
   * 
   * [리소스 관리]
   * - try-with-resources 사용하여 자동 리소스 해제
   * 
   * @param dto 리스 신청 정보가 담긴 DTO 객체
   * @return boolean 등록 성공 여부
   */
  public boolean registerApplication(LeaseDto dto) {
    // SQL 쿼리 작성 (필수 필드만 포함)
    String sql = "INSERT INTO apply (aname, aphone, atype, deposit, prepayments, " +
        "residual_value, duration) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      // 파라미터 바인딩
      ps.setString(1, dto.getAname()); // 신청자 이름
      ps.setString(2, dto.getAphone()); // 연락처
      ps.setInt(3, dto.getAtype()); // 계약유형(1:렌트, 2:리스)
      ps.setInt(4, dto.getDeposit()); // 보증금 비율
      ps.setInt(5, dto.getPrepayments()); // 선납금 비율
      ps.setInt(6, dto.getResidual_value()); // 잔존가치 비율
      ps.setInt(7, dto.getDuration()); // 계약기간

      // 실행 및 결과 반환 (영향받은 행이 1개면 성공)
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
   * [설명] 특정 카테고리에 속한 브랜드 목록 조회
   * 
   * [처리 순서]
   * 1. 입력받은 카테고리 번호로 브랜드 테이블 조회
   * 2. ResultSet에서 브랜드 정보 추출하여 DTO 객체 생성
   * 3. ArrayList에 DTO 객체 추가하여 반환
   * 
   * [조인 관계]
   * - brand 테이블과 car 테이블이 cno로 연결
   * 
   * [예외 처리]
   * - SQLException: DB 조회 실패 시 빈 ArrayList 반환
   * - 잘못된 카테고리 번호: 빈 ArrayList 반환
   * 
   * @param cno 조회할 카테고리 번호
   * @return ArrayList<LeaseDto> 브랜드 목록
   */
  public ArrayList<LeaseDto> getBrandList(int cno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM brand WHERE cno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, cno); // 카테고리 번호 바인딩

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setBno(rs.getInt("bno")); // 브랜드 번호
          dto.setBname(rs.getString("bname")); // 브랜드명
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
   * [설명] 특정 브랜드의 차량 모델 목록 조회
   * 
   * [처리 순서]
   * 1. 입력받은 브랜드 번호로 모델 테이블 조회
   * 2. ResultSet에서 모델 정보 추출하여 DTO 객체 생성
   * 3. ArrayList에 DTO 객체 추가하여 반환
   * 
   * [조인 관계]
   * - model 테이블과 brand 테이블이 bno로 연결
   * 
   * [예외 처리]
   * - SQLException: DB 조회 실패 시 빈 ArrayList 반환
   * - 잘못된 브랜드 번호: 빈 ArrayList 반환
   * 
   * @param bno 조회할 브랜드 번호
   * @return ArrayList<LeaseDto> 모델 목록
   */
  public ArrayList<LeaseDto> getModelList(int bno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM model WHERE bno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, bno); // 브랜드 번호 바인딩

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setMno(rs.getInt("mno")); // 모델 번호
          dto.setMname(rs.getString("mname")); // 모델명
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
   * [설명] 특정 모델의 등급 목록 조회
   * 
   * [처리 순서]
   * 1. 입력받은 모델 번호로 등급 테이블 조회
   * 2. ResultSet에서 등급 정보 추출하여 DTO 객체 생성
   * 3. ArrayList에 DTO 객체 추가하여 반환
   * 
   * [조인 관계]
   * - grade 테이블과 model 테이블이 mno로 연결
   * 
   * [예외 처리]
   * - SQLException: DB 조회 실패 시 빈 ArrayList 반환
   * - 잘못된 모델 번호: 빈 ArrayList 반환
   * 
   * @param mno 조회할 모델 번호
   * @return ArrayList<LeaseDto> 등급 목록
   */
  public ArrayList<LeaseDto> getGradeList(int mno) {
    ArrayList<LeaseDto> list = new ArrayList<>();
    String sql = "SELECT * FROM grade WHERE mno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, mno); // 모델 번호 바인딩

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          LeaseDto dto = new LeaseDto();
          dto.setGno(rs.getInt("gno")); // 등급 번호
          dto.setGname(rs.getString("gname")); // 등급명
          dto.setGprice(rs.getInt("gprice")); // 차량 가격
          list.add(dto);
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 등급 목록 조회 실패: " + e.getMessage());
    }
    return list;
  }

  /**
   * [메소드] getGradeInfo
   * [설명] 특정 등급의 상세 정보 조회 (차량 전체 정보 포함)
   * 
   * [처리 순서]
   * 1. 입력받은 등급 번호로 전체 차량 정보 조회
   * 2. 4개 테이블 JOIN하여 차량의 모든 정보 조회
   * 3. ResultSet에서 데이터 추출하여 단일 DTO 객체 생성
   * 
   * [조인 관계]
   * - car ← brand ← model ← grade
   * - 각 테이블이 FK로 연결됨
   * 
   * [예외 처리]
   * - SQLException: DB 조회 실패 시 null 반환
   * - 잘못된 등급 번호: null 반환
   * 
   * @param gno 조회할 등급 번호
   * @return LeaseDto 차량 상세 정보 (조회 실패 시 null)
   */
  public LeaseDto getGradeInfo(int gno) {
    // 4개 테이블 JOIN 쿼리
    String sql = "SELECT c.cno, c.cname, b.bno, b.bname, m.mno, m.mname, g.gno, g.gname, g.gprice " +
        "FROM car c " +
        "INNER JOIN brand b ON c.cno = b.cno " +
        "INNER JOIN model m ON b.bno = m.bno " +
        "INNER JOIN grade g ON m.mno = g.mno " +
        "WHERE g.gno = ?";

    try (PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, gno); // 등급 번호 바인딩

      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          LeaseDto dto = new LeaseDto();
          // 카테고리 정보
          dto.setCno(rs.getInt("cno"));
          dto.setCname(rs.getString("cname"));
          // 브랜드 정보
          dto.setBno(rs.getInt("bno"));
          dto.setBname(rs.getString("bname"));
          // 모델 정보
          dto.setMno(rs.getInt("mno"));
          dto.setMname(rs.getString("mname"));
          // 등급 정보
          dto.setGno(rs.getInt("gno"));
          dto.setGname(rs.getString("gname"));
          dto.setGprice(rs.getInt("gprice"));
          return dto;
        }
      }
    } catch (SQLException e) {
      System.out.println("[오류] 등급 정보 조회 실패: " + e.getMessage());
    }
    return null;
  }
} // class end