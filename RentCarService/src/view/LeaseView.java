package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.LeaseController;
import model.dto.LeaseDto;

public class LeaseView {
  private static LeaseView instance = new LeaseView();

  private LeaseView() {
  }

  public static LeaseView getInstance() {
    return instance;
  }

  private Scanner scan = new Scanner(System.in);

  // 메인 실행
  public void run() {
    while (true) {
      System.out.println("\n=== 리스 견적 시스템 ===");
      System.out.println("1. 견적 확인하기");
      System.out.println("2. 계약 신청하기");
      System.out.println("3. 종료");
      System.out.print("선택> ");

      int choice = scan.nextInt();
      scan.nextLine(); // 버퍼 처리

      switch (choice) {
        case 1:
          selectCarProcess();
          break;
        case 2:
          applyContract();
          break;
        case 3:
          return;
      }
    }
  }

  // 차량 선택 프로세스
  private void selectCarProcess() {
    // 1. 차종 선택 (국산/수입)
    ArrayList<LeaseDto> categories = LeaseController.getInstance().getCategoryList();

    System.out.println("\n=== 차종 선택 ===");

    for (LeaseDto car : categories) {
      System.out.println(car.getCno() + ". " + car.getCname());
    }

    System.out.print("차종 선택> ");
    int cno = scan.nextInt();

    // 2. 브랜드 선택
    ArrayList<LeaseDto> brands = LeaseController.getInstance().getBrandList(cno);

    System.out.println("\n=== 브랜드 선택 ===");

    for (LeaseDto brand : brands) {
      System.out.println(brand.getBno() + ". " + brand.getBname());
    } // 향상된 for end

    System.out.print("브랜드 선택> ");
    int bno = scan.nextInt();

    // 3. 모델 선택
    ArrayList<LeaseDto> models = LeaseController.getInstance().getModelList(bno);

    System.out.println("\n=== 모델 선택 ===");
    for (LeaseDto model : models) {
      System.out.println(model.getMno() + ". " + model.getMname());
    }

    System.out.print("모델 선택> ");
    int mno = scan.nextInt();

    // 4. 등급 선택
    ArrayList<LeaseDto> grades = LeaseController.getInstance().getGradeList(mno);

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
  }

  // 견적 계산
  private void calculateEstimate(int gno) {
    LeaseDto grade = LeaseController.getInstance().getGradeInfo(gno);

    System.out.println("\n=== 계약 조건 입력 ===");
    System.out.print("계약기간(개월): ");
    int duration = scan.nextInt();

    // 보증금/선납금 선택 구조 수정
    System.out.println("\n>> 보증금 / 선납금 선택");
    System.out.print(">> 1.보증금 2.선납금 : ");
    int paymentChoice = scan.nextInt();

    int deposit = 0;
    int prepayment = 0;

    // 보증금 선택 시
    if (paymentChoice == 1) {
      System.out.print(">> 보증금 비율 선택 (1:0% 2:30% 3:50%) : ");
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
      prepayment = 0; // 선납금은 0으로 설정

    }
    // 선납금 선택 시
    else if (paymentChoice == 2) {
      System.out.print(">> 선납금 비율 선택 (1:0% 2:30% 3:50%) : ");
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
      deposit = 0; // 보증금은 0으로 설정
    }

    // 견적 계산 및 표시
    int basePrice = grade.getGprice();
    int depositAmount = (int) (basePrice * (deposit / 100.0));
    int prepaymentAmount = (int) (basePrice * (prepayment / 100.0));
    int monthlyPayment = (basePrice - depositAmount - prepaymentAmount) / duration;

    System.out.println("\n=== 견적 결과 ===");
    System.out.printf("차량가격: %,d원\n", basePrice);
    System.out.printf("보증금: %,d원\n", depositAmount);
    System.out.printf("선납금: %,d원\n", prepaymentAmount);
    System.out.printf("월 납입금: %,d원\n", monthlyPayment);
  }

  // 계약 신청
  private void applyContract() {
    System.out.print("이름: ");
    String name = scan.nextLine();

    System.out.print("연락처: ");
    String phone = scan.nextLine();

    System.out.print("계약유형(렌트): ");
    int type = scan.nextInt();
    // 보증금/선납금 선택 구조 수정
    System.out.println("\n>>보증금 또는 선납금 선택하십시오");
    System.out.print(">>1.보증금 2.선납금 : ");
    int paymenetChoice = scan.nextInt();

    int deposit = 0;
    int prepayment = 0;

    if (paymenetChoice == 1) {
      System.out.print(">>보증금 비율 선택(1:0% 2:30% 3:50%):");
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
      }// switch end
      System.out.println("보증금" + deposit + "%선택됨(선납금 선택불가)");
      prepayment = 0; // 선납금 0으로 설정
    } else if (paymenetChoice == 2) {
      System.out.print(">>선납금 비율 선택(1:0% 2:30% 3:50%):");
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
      System.out.println("선납금" + prepayment + "% 선택됨(보증금 선택 불가)");
      deposit = 0; // 보증금 0으로 설정
    }

    System.out.print("잔존가치(%): ");
    int residualValue = scan.nextInt();

    System.out.print("계약기간(개월): ");
    int duration = scan.nextInt();

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
      System.out.println("계약 신청이 완료되었습니다!");
    } else {
      System.out.println("계약 신청에 실패했습니다.");
    }
  } // applyContract end

} // class end