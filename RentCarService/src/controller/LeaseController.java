package controller;

import java.util.ArrayList;
import model.dao.LeaseDao;
import model.dto.LeaseDto;

/**
 * LeaseController 클래스
 * 
 * [클래스 설명]
 * 차량 리스 서비스의 전반적인 비즈니스 로직을 처리하는 컨트롤러 클래스
 * MVC 패턴에서 Controller 역할을 담당하며, View와 Model(DAO) 사이의 중재자 역할 수행
 * 
 * [주요 기능]
 * 1. 차량 정보 조회 (카테고리, 브랜드, 모델, 등급)
 * 2. 리스 견적 계산
 * 3. 리스 신청 처리
 * 4. 입력값 유효성 검증
 * 
 * [디자인 패턴]
 * - Singleton 패턴 적용: 시스템 전반에서 단일 인스턴스만 사용하도록 보장
 */
public class LeaseController {
  // 싱글톤 패턴 구현을 위한 private static 인스턴스
  private static LeaseController instance = new LeaseController();

  // private 생성자로 외부에서의 인스턴스 생성 방지
  private LeaseController() {
  }

  /**
   * 싱글톤 인스턴스 반환 메소드
   * 
   * @return LeaseController 단일 인스턴스
   */
  public static LeaseController getInstance() {
    return instance;
  }

  /**
   * 차량 카테고리(국산/수입) 목록 조회
   * 
   * @return ArrayList<LeaseDto> 카테고리 목록
   */
  public ArrayList<LeaseDto> getCategoryList() {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getCategoryList();
    return result;
  }

  /**
   * 선택된 카테고리에 해당하는 브랜드 목록 조회
   * 
   * @param cno 카테고리 번호
   * @return ArrayList<LeaseDto> 브랜드 목록
   */
  public ArrayList<LeaseDto> getBrandList(int cno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getBrandList(cno);
    return result;
  }

  /**
   * 선택된 브랜드의 차량 모델 목록 조회
   * 
   * @param bno 브랜드 번호
   * @return ArrayList<LeaseDto> 모델 목록
   */
  public ArrayList<LeaseDto> getModelList(int bno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getModelList(bno);
    return result;
  }

  /**
   * 선택된 모델의 등급 목록 조회
   * 
   * @param mno 모델 번호
   * @return ArrayList<LeaseDto> 등급 목록
   */
  public ArrayList<LeaseDto> getGradeList(int mno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getGradeList(mno);
    return result;
  }

  /**
   * 선택된 등급의 상세 정보 조회
   * 
   * @param gno 등급 번호
   * @return LeaseDto 등급 상세 정보
   */
  public LeaseDto getGradeInfo(int gno) {
    LeaseDto result = LeaseDao.getInstance().getGradeInfo(gno);
    return result;
  }

  /**
   * 월 납입금 견적 계산
   * [계산 로직]
   * 1. 차량가격에서 보증금 비율만큼 차감
   * 2. 선납금 비율만큼 추가 차감
   * 3. 남은 금액을 계약기간으로 나누어 월 납입금 산출
   * 
   * @param carPrice   차량 가격
   * @param deposit    보증금 비율 (%)
   * @param prepayment 선납금 비율 (%)
   * @param duration   계약기간 (개월)
   * @return int 월 납입금
   */
  public int calculateMonthlyPayment(int carPrice, int deposit, int prepayment, int duration) {
    int totalAmount = carPrice - (carPrice * deposit / 100) - (carPrice * prepayment / 100);
    return totalAmount / duration;
  }

  /**
   * 리스 신청 정보 등록
   * 
   * @param dto 리스 신청 정보가 담긴 DTO
   * @return boolean 등록 성공 여부
   */
  public boolean registerApplication(LeaseDto dto) {
    boolean result = LeaseDao.getInstance().registerApplication(dto);
    return result;
  }

  /**
   * 리스 신청 입력값 유효성 검증
   * [검증 항목]
   * 1. 전화번호 형식 (000-0000-0000)
   * 2. 계약기간 범위 (12~60개월)
   * 3. 보증금, 선납금 범위 (0~100%)
   * 4. 잔존가치 범위 (0~50%)
   * 
   * @param dto 검증할 리스 신청 정보
   * @return boolean 유효성 검증 통과 여부
   */
  public boolean validateInput(LeaseDto dto) {
    // 전화번호 형식 검증 (정규식: 000-0000-0000)
    if (!dto.getAphone().matches("\\d{3}-\\d{4}-\\d{4}")) {
      return false;
    }

    // 계약기간 범위 검증
    if (dto.getDuration() < 12 || dto.getDuration() > 60) {
      return false;
    }

    // 보증금, 선납금 범위 검증
    if (dto.getDeposit() < 0 || dto.getDeposit() > 100 ||
        dto.getPrepayments() < 0 || dto.getPrepayments() > 100) {
      return false;
    }

    // 잔존가치 범위 검증
    if (dto.getResidual_value() < 0 || dto.getResidual_value() > 50) {
      return false;
    }

    return true;
  }
}