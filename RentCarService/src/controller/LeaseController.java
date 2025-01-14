package controller;

import java.util.ArrayList;
import model.dao.LeaseDao;
import model.dto.LeaseDto;

/**
 * [클래스명] LeaseController
 * [설명] 차량 리스 서비스의 비즈니스 로직을 처리하는 컨트롤러
 */
public class LeaseController {
  // 싱글톤 패턴 적용
  private static LeaseController instance = new LeaseController();

  private LeaseController() {
  }

  public static LeaseController getInstance() {
    return instance;
  }

  // 카테고리(국산/수입) 목록 조회
  public ArrayList<LeaseDto> getCategoryList() {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getCategoryList();
    return result;
  }

  // 선택된 카테고리의 브랜드 목록 조회
  public ArrayList<LeaseDto> getBrandList(int cno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getBrandList(cno);
    return result;
  }

  // 선택된 브랜드의 모델 목록 조회
  public ArrayList<LeaseDto> getModelList(int bno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getModelList(bno);
    return result;
  }

  // 선택된 모델의 등급 목록 조회
  public ArrayList<LeaseDto> getGradeList(int mno) {
    ArrayList<LeaseDto> result = LeaseDao.getInstance().getGradeList(mno);
    return result;
  }

  // 선택된 등급의 상세 정보 조회
  public LeaseDto getGradeInfo(int gno) {
    LeaseDto result = LeaseDao.getInstance().getGradeInfo(gno);
    return result;
  }

  // 견적 계산 메소드
  public int calculateMonthlyPayment(int carPrice, int deposit, int prepayment, int duration) {
    // 보증금과 선납금을 제외한 금액을 계약기간으로 나누어 월 납입금 계산
    int totalAmount = carPrice - (carPrice * deposit / 100) - (carPrice * prepayment / 100);
    return totalAmount / duration;
  }

  // 렌트/리스 신청 등록
  public boolean registerApplication(LeaseDto dto) {
    boolean result = LeaseDao.getInstance().registerApplication(dto);
    return result;
  }

  // 입력값 검증 메소드
  public boolean validateInput(LeaseDto dto) {
    // 전화번호 형식 검증
    if (!dto.getAphone().matches("\\d{3}-\\d{4}-\\d{4}")) {
      return false;
    }

    // 계약기간 검증 (12~60개월)
    if (dto.getDuration() < 12 || dto.getDuration() > 60) {
      return false;
    }

    // 보증금, 선납금 범위 검증 (0~50%)
    if (dto.getDeposit() < 0 || dto.getDeposit() > 100 ||
        dto.getPrepayments() < 0 || dto.getPrepayments() > 100) {
      return false;
    }

    // 잔존가치 범위 검증 (0~50%)
    if (dto.getResidual_value() < 0 || dto.getResidual_value() > 50) {
      return false;
    }

    return true;
  }

} // LeaseController 클래스 종료