package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.LeaseController;
import model.dto.LeaseDto;

/**
 * [클래스명] LeaseView
 * [설명] 리스 서비스의 사용자 인터페이스를 담당하는 뷰 클래스
 * 
 * [주요 기능]
 * 1. 메인 메뉴 표시 및 처리
 * 2. 차량 선택 프로세스 진행
 * 3. 견적 계산 및 표시
 * 4. 계약 신청 처리
 */
public class LeaseView {
  /* 1. 상수 정의 */
  // 1-1. 메뉴 옵션
  private static final int MENU_ESTIMATE = 1; // 견적 확인
  private static final int MENU_CONTRACT = 2; // 계약 신청
  private static final int MENU_EXIT = 3; // 종료

  // 1-2. 보증금/선납금 옵션
  @SuppressWarnings("unused")
  private static final int PAYMENT_DEPOSIT = 1; // 보증금 선택
  @SuppressWarnings("unused")
  private static final int PAYMENT_PREPAYMENT = 2; // 선납금 선택

  // 1-3. 계약 타입
  @SuppressWarnings("unused")
  private static final int CONTRACT_TYPE_LEASE = 2; // 리스 계약

  /* 2. 싱글톤 패턴 구현 */
  private static LeaseView instance = new LeaseView();
  private Scanner scan;

  private LeaseView() {
    scan = new Scanner(System.in);
  }

  public static LeaseView getInstance() {
    return instance;
  }

  /**
   * [메소드] run
   * 메인 메뉴를 표시하고 사용자 선택을 처리하는 메인 실행 메소드
   */
  public void run() {
    while (true) {
      try {
        // 1. 메인 메뉴 표시
        System.out.println("\n=== 리스 견적 시스템 ===");
        System.out.println("1. 견적 확인하기");
        System.out.println("2. 계약 신청하기");
        System.out.println("3. 종료");
        System.out.print("선택> ");

        // 2. 사용자 입력 처리
        int choice = scan.nextInt();
        scan.nextLine(); // 버퍼 처리

        // 3. 메뉴 처리
        switch (choice) {
          case MENU_ESTIMATE:
            selectCarProcess();
            break;
          case MENU_CONTRACT:
            applyContract();
            break;
          case MENU_EXIT:
            System.out.println("프로그램을 종료합니다.");
            return;
          default:
            System.out.println("[오류] 잘못된 메뉴 선택입니다.");
        }
      } catch (Exception e) {
        System.out.println("[오류] 잘못된 입력입니다.");
        scan.nextLine(); // 버퍼 클리어
      }
    }
  }

  /**
   * [메소드] selectCarProcess
   * 차량 선택 프로세스를 진행하는 메소드
   * - 카테고리 → 브랜드 → 모델 → 등급 순으로 선택
   */
  private void selectCarProcess() {
    try {
      // 1. 차종 선택
      ArrayList<LeaseDto> categories = LeaseController.getInstance().getCategoryList();
      if (categories == null || categories.isEmpty()) {
        System.out.println("[오류] 차종 정보를 불러올 수 없습니다.");
        return;
      }

      System.out.println("\n=== 차종 선택 ===");
      for (LeaseDto car : categories) {
        System.out.println(car.getCno() + ". " + car.getCname());
      }

      System.out.print("차종 선택> ");
      int cno = scan.nextInt();

      // 2. 브랜드 선택
      ArrayList<LeaseDto> brands = LeaseController.getInstance().getBrandList(cno);
      if (brands == null || brands.isEmpty()) {
        System.out.println("[오류] 브랜드 정보를 불러올 수 없습니다.");
        return;
      }

      System.out.println("\n=== 브랜드 선택 ===");
      for (LeaseDto brand : brands) {
        System.out.println(brand.getBno() + ". " + brand.getBname());
      }

      System.out.print("브랜드 선택> ");
      int bno = scan.nextInt();

      // 3. 모델 선택
      // ! 오류: LeaseController에 getModelList() 메소드가 정의되어 있지 않음
      // !해결: LeaseController에 getModelList() 메소드를 추가하고 LeaseDao를 통해 모델 정보를 조회하도록 구현
      ArrayList<LeaseDto> models = LeaseController.getInstance().getModelList(bno);
      if (models == null || models.isEmpty()) {
        System.out.println("[오류] 모델 정보를 불러올 수 없습니다.");
        return;
      }

      System.out.println("\n=== 모델 선택 ===");
      for (LeaseDto model : models) {
        System.out.println(model.getMno() + ". " + model.getMname());
      }

      System.out.print("모델 선택> ");
      int mno = scan.nextInt();

      // 4. 등급 선택
      ArrayList<LeaseDto> grades = LeaseController.getInstance().getGradeList(mno);
      if (grades == null || grades.isEmpty()) {
        System.out.println("[오류] 등급 정보를 불러올 수 없습니다.");
        return;
      }

      System.out.println("\n=== 등급 선택 ===");
      for (LeaseDto grade : grades) {
        System.out.printf("%d. %s (%,d원)\n",
            grade.getGno(),
            grade.getGname(),
            grade.getGprice());
      }

      System.out.print("등급 선택> ");
      int gno = scan.nextInt();

      // 5. 견적 계산
      calculateEstimate(gno);

    } catch (Exception e) {
      System.out.println("[오류] 차량 선택 중 오류가 발생했습니다: " + e.getMessage());
      scan.nextLine(); // 버퍼 클리어
    }
  }

  /**
   * [메소드] calculateEstimate
   * 선택된 차량의 견적을 계산하고 표시하는 메소드
   * 
   * @param gno 선택된 차량 등급 번호
   */
  private void calculateEstimate(int gno) {
    try {
      // ! 오류 1: getGradeInfo() 메소드가 LeaseController에 정의되어 있지 않음
      // ! 해결: LeaseController의 getGradeList()를 사용하여 등급 정보를 가져옴
      ArrayList<LeaseDto> grades = LeaseController.getInstance().getGradeList(gno);
      if (grades == null || grades.isEmpty()) {
        System.out.println("[오류] 차량 정보를 불러올 수 없습니다.");
        return;
      }
      LeaseDto grade = grades.get(0); // 선택한 등급의 정보

      // 계약 조건 입력 받기
      System.out.println("\n=== 계약 조건 입력 ===");
      System.out.print("계약기간(개월): ");
      int duration = scan.nextInt();

      // 보증금/선납금 선택
      System.out.println("\n>> 보증금 / 선납금 선택");
      System.out.print(">> 1.보증금 2.선납금 : ");
      int paymentChoice = scan.nextInt();

      int deposit = 0;
      int prepayment = 0;

      // ! 오류 2: processDepositChoice()와 processPrepaymentChoice() 메소드가 정의되어 있지 않음
      // ! 해결: 보증금과 선납금 처리 로직을 직접 구현
      if (paymentChoice == 1) { // PAYMENT_DEPOSIT
        System.out.print("보증금 비율 선택 (0%, 30%, 50%): ");
        deposit = scan.nextInt();
      } else if (paymentChoice == 2) { // PAYMENT_PREPAYMENT
        System.out.print("선납금 비율 선택 (0%, 30%, 50%): ");
        prepayment = scan.nextInt();
      }

      // 잔존가치 입력
      System.out.print("잔존가치(%): ");
      int residualValue = scan.nextInt();

      // 견적 계산 및 표시
      displayEstimate(grade.getGprice(), deposit, prepayment, residualValue, duration);

    } catch (Exception e) {
      System.out.println("[오류] 견적 계산 중 오류가 발생했습니다: " + e.getMessage());
      scan.nextLine();
    }
  }

  /**
   * [메소드] displayEstimate
   * 계산된 견적 결과를 표시하는 메소드
   */
  private void displayEstimate(int basePrice, int deposit, int prepayment,
      int residualValue, int duration) {
    int depositAmount = (int) (basePrice * (deposit / 100.0));
    int prepaymentAmount = (int) (basePrice * (prepayment / 100.0));
    int residualAmount = (int) (basePrice * (residualValue / 100.0));
    int monthlyPayment = (basePrice - depositAmount - prepaymentAmount - residualAmount) / duration;

    System.out.println("\n=== 견적 결과 ===");
    System.out.printf("차량가격: %,d원\n", basePrice);
    System.out.printf("보증금: %,d원\n", depositAmount);
    System.out.printf("선납금: %,d원\n", prepaymentAmount);
    System.out.printf("잔존가치: %,d원\n", residualAmount);
    System.out.printf("월 납입금: %,d원\n", monthlyPayment);
  }

  // 계약 신청
  private void applyContract() {
    System.out.print("이름: ");
    String name = scan.nextLine();

    System.out.print("연락처: ");
    String phone = scan.nextLine();

    System.out.print("계약유형(리스=2): ");
    int type = scan.nextInt();

    System.out.println("\n>> 보증금 / 선납금 선택");
    System.out.print(">> 1.보증금 2.선납금 : ");
    int paymentChoice = scan.nextInt();

    int deposit = 0;
    int prepayment = 0;

    if (paymentChoice == 1) {
      System.out.print(">> 보증금 비율 선택 (1:0% 2:30% 3:50%): ");
      int depositChoice = scan.nextInt();
      switch (depositChoice) {
        case 1:
          deposit = 0;
          break;
        case 2:
          deposit = 30;
          break;
        case 3:
          deposit = 50;
          break;
      }
      System.out.println("보증금 " + deposit + "% 선택됨 (선납금 선택 불가)");
      prepayment = 0;
    } else if (paymentChoice == 2) {
      System.out.print(">> 선납금 비율 선택 (1:0% 2:30% 3:50%): ");
      int prepaymentChoice = scan.nextInt();
      switch (prepaymentChoice) {
        case 1:
          prepayment = 0;
          break;
        case 2:
          prepayment = 30;
          break;
        case 3:
          prepayment = 50;
          break;
      }
      System.out.println("선납금 " + prepayment + "% 선택됨 (보증금 선택 불가)");
      deposit = 0;
    }

    System.out.print("잔존가치(%): ");
    int residualValue = scan.nextInt();

    System.out.print("계약기간(개월): ");
    int duration = scan.nextInt();

    if (!validateInput(duration, 36, 60)) {
      System.out.println("계약기간은 36~60개월 사이여야 합니다.");
      return;
    }

    LeaseDto dto = new LeaseDto();
    dto.setAname(name);
    dto.setAphone(phone);
    dto.setAtype(type);
    dto.setDeposit(deposit);
    dto.setPrepayments(prepayment);
    dto.setResidual_value(residualValue);
    dto.setDuration(duration);

    boolean result = LeaseController.getInstance().registerApplication(dto);

    if (result) {
      System.out.println("리스 신청이 완료되었습니다!");
    } else {
      System.out.println("리스 신청에 실패했습니다.");
    }
  }

  // 입력값 검증 로직 추가 필요
  private boolean validateInput(int value, int min, int max) {
    return value >= min && value <= max;
  }

} // class end