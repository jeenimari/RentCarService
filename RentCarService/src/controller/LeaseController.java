package controller;

import java.util.ArrayList;
import model.dao.LeaseDao;
import model.dto.LeaseDto;

/**
 * [클래스명] LeaseController
 * [설명] 차량 리스 서비스의 비즈니스 로직을 처리하는 컨트롤러
 */
public class LeaseController {
  // Todo : 1. 상수 정의
  // 1-1. 보증금 옵션
  public static final int DEPOSIT_NONE = 0; // 보증금 0%
  public static final int DEPOSIT_30 = 30; // 보증금 30%
  public static final int DEPOSIT_50 = 50; // 보증금 50%

  // 1-2. 선납금 옵션
  public static final int PREPAYMENT_NONE = 0; // 선납금 0%
  public static final int PREPAYMENT_30 = 30; // 선납금 30%
  public static final int PREPAYMENT_50 = 50; // 선납금 50%

  // 1-3. 잔존가치 옵션
  public static final int RESIDUAL_NONE = 0; // 잔존가치 0%
  public static final int RESIDUAL_30 = 30; // 잔존가치 30%
  public static final int RESIDUAL_50 = 50; // 잔존가치 50%

  // 1-4. 계약기간 옵션
  public static final int DURATION_MIN = 36; // 최소 계약기간
  public static final int DURATION_MAX = 60; // 최대 계약기간
  public static final int DURATION_48 = 48; // 중간 계약기간

  // 1-5. 신청 상태 코드
  public static final int STATUS_WAITING = 0; // 대기중
  public static final int STATUS_APPROVED = 1; // 승인
  public static final int STATUS_REJECTED = 2; // 거절

  // Todo : 2. 싱글톤 패턴 구현
  private static LeaseController instance;

  private LeaseController() {
  }

  public static synchronized LeaseController getInstance() {
    if (instance == null) {
      instance = new LeaseController();
    }
    return instance;
  }

  // Todo : 3. 차량 정보 조회 관련 메소드
  /**
   * 3-1. 카테고리 목록 조회
   * 
   * @return 카테고리 목록
   */
  public ArrayList<LeaseDto> getCategoryList() {
    try {
      ArrayList<LeaseDto> list = LeaseDao.getInstance().getCategoryList();
      if (list == null || list.isEmpty()) {
        System.out.println("[알림] 등록된 차량 카테고리가 없습니다.");
      }
      return list;
    } catch (Exception e) {
      System.out.println("[오류] 카테고리 목록 조회 실패: " + e.getMessage());
      return null;
    }
  }

  /**
   * 3-2. 브랜드 목록 조회
   * 
   * @param cno 카테고리 번호
   * @return 브랜드 목록
   */
  public ArrayList<LeaseDto> getBrandList(int cno) {
    try {
      if (cno <= 0) {
        System.out.println("[오류] 유효하지 않은 카테고리 번호입니다.");
        return null;
      }
      ArrayList<LeaseDto> list = LeaseDao.getInstance().getBrandList(cno);
      if (list == null || list.isEmpty()) {
        System.out.println("[알림] 등록된 브랜드가 없습니다.");
      }
      return list;
    } catch (Exception e) {
      System.out.println("[오류] 브랜드 목록 조회 실패: " + e.getMessage());
      return null;
    }
  }

  // Todo : 4. 견적 계산 관련 메소드
  /**
   * 4-1. 리스 견적 계산
   * 
   * @param carPrice      차량가격
   * @param deposit       보증금
   * @param prepayment    선납금
   * @param residualValue 잔존가치
   * @param duration      계약기간
   * @return 월 납입금액
   */
  public int calculateMonthlyPayment(int carPrice, int deposit, int prepayment,
      int residualValue, int duration) {
    try {
      // 1) 입력값 검증
      validateInputValues(carPrice, deposit, prepayment, residualValue, duration);

      // 2) 견적 계산
      return calculateLeaseFee(carPrice, deposit, prepayment, residualValue, duration);
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
      return -1;
    }
  }

  // Todo : 5. 리스 신청 관련 메소드
  /**
   * 5-1. 리스 신청 등록
   * 
   * @param dto 신청 정보
   * @return 등록 성공 여부
   */
  public boolean registerApplication(LeaseDto dto) {
    try {
      // 1) 입력값 검증
      if (!validateLeaseApplication(dto)) {
        return false;
      }

      // 2) 신청 상태 초기값 설정
      dto.setStatus(STATUS_WAITING);

      // 3) DAO 호출
      return LeaseDao.getInstance().registerApplication(dto);
    } catch (Exception e) {
      System.out.println("[오류] 리스 신청 등록 실패: " + e.getMessage());
      return false;
    }
  }

  // Todo : 6. 검증 메소드들
  private void validateInputValues(int carPrice, int deposit, int prepayment,
      int residualValue, int duration) {
    if (carPrice <= 0) {
      throw new IllegalArgumentException("[오류] 유효하지 않은 차량 가격입니다.");
    }
    if (!validatePaymentOption(deposit, prepayment)) {
      throw new IllegalArgumentException("[오류] 유효하지 않은 보증금/선납금 옵션입니다.");
    }
    if (!validateResidualValue(residualValue)) {
      throw new IllegalArgumentException("[오류] 유효하지 않은 잔존가치 옵션입니다.");
    }
    if (!validateDuration(duration)) {
      throw new IllegalArgumentException("[오류] 유효하지 않은 계약기간입니다.");
    }
  }

  private boolean validatePaymentOption(int deposit, int prepayment) {
    if (deposit > 0 && prepayment > 0) {
      System.out.println("[오류] 보증금과 선납금은 동시에 선택할 수 없습니다.");
      return false;
    }
    boolean validDeposit = deposit == DEPOSIT_NONE || deposit == DEPOSIT_30 || deposit == DEPOSIT_50;
    boolean validPrepayment = prepayment == PREPAYMENT_NONE || prepayment == PREPAYMENT_30
        || prepayment == PREPAYMENT_50;

    if (!validDeposit && !validPrepayment) {
      System.out.println("[오류] 보증금/선납금은 0%, 30%, 50% 중에서 선택해야 합니다.");
      return false;
    }
    return true;
  }

  private boolean validateResidualValue(int residualValue) {
    boolean isValid = residualValue == RESIDUAL_NONE ||
        residualValue == RESIDUAL_30 ||
        residualValue == RESIDUAL_50;
    if (!isValid) {
      System.out.println("[오류] 잔존가치는 0%, 30%, 50% 중에서 선택해야 합니다.");
    }
    return isValid;
  }

  private boolean validateDuration(int duration) {
    boolean isValid = duration >= DURATION_MIN &&
        duration <= DURATION_MAX &&
        duration % 12 == 0;
    if (!isValid) {
      System.out.println("[오류] 계약기간은 36개월에서 60개월 사이여야 합니다.");
    }
    return isValid;
  }

  private boolean validateLeaseApplication(LeaseDto dto) {
    if (dto == null) {
      System.out.println("[오류] 신청 정보가 없습니다.");
      return false;
    }
    if (dto.getAname() == null || dto.getAname().trim().isEmpty()) {
      System.out.println("[오류] 신청자 이름은 필수입니다.");
      return false;
    }
    if (dto.getAphone() == null || dto.getAphone().trim().isEmpty()) {
      System.out.println("[오류] 연락처는 필수입니다.");
      return false;
    }
    return true;
  }

  // Todo : 7. 내부 계산 메소드
  private int calculateLeaseFee(int carPrice, int deposit, int prepayment,
      int residualValue, int duration) {
    try {
      int totalAmount = carPrice;
      totalAmount -= (carPrice * deposit / 100); // 보증금 차감
      totalAmount -= (carPrice * prepayment / 100); // 선납금 차감
      totalAmount -= (carPrice * residualValue / 100); // 잔존가치 차감
      return totalAmount / duration; // 월 납입금 계산
    } catch (ArithmeticException e) {
      throw new RuntimeException("[오류] 견적 계산 중 오류 발생: " + e.getMessage());
    }
  }

  // Todo : 8. 차량 등급 정보 조회 관련 메소드
  // ! 오류: getGradeList() 메소드가 LeaseController에 정의되어 있지 않음
  // ! 해결: LeaseDao를 통해 차량 등급 정보를 조회하도록 구현
  public ArrayList<LeaseDto> getGradeList(int gno) {
    return LeaseDao.getInstance().getGradeList(gno);
  }

  // Todo : 9. 모델 정보 조회 관련 메소드
  // ! 오류: getModelList() 메소드가 LeaseController에 정의되어 있지 않음
  // ! 해결: LeaseDao를 통해 모델 정보를 조회하도록 구현
  public ArrayList<LeaseDto> getModelList(int bno) {
    return LeaseDao.getInstance().getModelList(bno);
  }
} // LeaseController 클래스 종료