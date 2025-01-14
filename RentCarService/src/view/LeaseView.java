package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.LeaseController;
import model.dto.LeaseDto;

/**
 * 리스 견적 시스템의 View 클래스
 * - 사용자 인터페이스를 담당하는 클래스
 * - 싱글톤 패턴을 사용하여 인스턴스를 관리
 * - 견적 확인 및 계약 신청 기능 제공
 */
public class LeaseView {
  // 싱글톤 패턴 구현을 위한 인스턴스
  private static LeaseView instance = new LeaseView();

  // 생성자를 private으로 선언하여 외부에서 인스턴스 생성 방지
  private LeaseView() {
  }

  // 싱글톤 인스턴스 반환 메소드
  public static LeaseView getInstance() {
    return instance;
  }

  // 사용자 입력을 받기 위한 Scanner 객체
  private Scanner scan = new Scanner(System.in);

  /**
   * 리스 견적 시스템의 메인 실행 메소드
   * 1. 견적 확인하기
   * 2. 계약 신청하기
   * 3. 종료
   * 사용자의 선택에 따라 해당 기능을 실행
   */
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

  /**
   * 차량 선택 프로세스를 진행하는 메소드
   * 진행 순서:
   * 1. 차종 선택 (국산/수입)
   * 2. 브랜드 선택
   * 3. 모델 선택
   * 4. 등급 선택
   * 5. 견적 계산 실행
   */
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
    }

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

    // 5. 견적 계산 실행
    calculateEstimate(gno);
  } // selectCarProcess end

  /**
   * 견적 계산을 수행하는 메소드
   * 
   * @param gno 선택된 차량 등급 번호
   * 
   *            계산 과정:
   *            1. 계약기간 선택 (36/48/60개월)
   *            2. 보증금/선납금 선택 및 비율 설정
   *            3. 잔존가치 비율 선택
   *            4. 최종 견적 계산 및 결과 출력
   */
  private void calculateEstimate(int gno) {
    LeaseDto grade = LeaseController.getInstance().getGradeInfo(gno);

    if (grade == null) {
      System.out.println("[오류] 선택하신 등급의 정보를 찾을 수 없습니다.");
      return;
    }

    System.out.println("\n=== 계약 조건 입력 ===");

    // 계약기간 선택 수정
    int duration = 0;
    while (true) {
      System.out.println(">>>계약기간 선택");
      System.out.println("1. 36개월");
      System.out.println("2. 48개월");
      System.out.println("3. 60개월");
      System.out.print("선택> ");
      int choice = scan.nextInt();

      switch (choice) {
        case 1:
          duration = 36;
          break;
        case 2:
          duration = 48;
          break;
        case 3:
          duration = 60;
          break;
        default:
          System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
          continue;
      }
      break; // 올바른 선택시 반복문 종료
    }
    System.out.println(duration + "개월이 선택되었습니다.");

    // 보증금/선납금 선택 구조도 같은 방식으로 수정
    System.out.println("\n>> 보증금 / 선납금 선택");
    int deposit = 0;
    int prepayment = 0;

    while (true) {
      System.out.println("1. 보증금");
      System.out.println("2. 선납금");
      System.out.print("선택> ");
      int paymentChoice = scan.nextInt();

      if (paymentChoice == 1) {
        while (true) {
          System.out.println(">> 보증금 비율 선택");
          System.out.println("1. 0%");
          System.out.println("2. 30%");
          System.out.println("3. 50%");
          System.out.print("선택> ");
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
            default:
              System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
              continue;
          }
          break;
        }
        prepayment = 0; // 선납금은 0으로 설정
        break;
      } else if (paymentChoice == 2) {
        while (true) {
          System.out.println(">> 선납금 비율 선택");
          System.out.println("1. 0%");
          System.out.println("2. 30%");
          System.out.println("3. 50%");
          System.out.print("선택> ");
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
            default:
              System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
              continue;
          }
          break;
        }
        deposit = 0; // 보증금은 0으로 설정
        break;
      } else {
        System.out.println("잘못된 선택입니다. 1 또는 2를 선택해주세요.");
      }
    }

    System.out.println("\n>> 잔존가치 비율 선택");
    System.out.println("1. 30%");
    System.out.println("2. 40%");
    System.out.println("3. 50%");
    System.out.print("선택> ");
    int residualChoice = scan.nextInt();

    int residualValue;
    switch (residualChoice) {
      case 1:
        residualValue = 30;
        break;
      case 2:
        residualValue = 40;
        break;
      case 3:
        residualValue = 50;
        break;
      default:
        residualValue = 30;
    }

    try {
      // 견적 계산 및 표시
      int basePrice = grade.getGprice();
      int depositAmount = (int) (basePrice * (deposit / 100.0));
      int prepaymentAmount = (int) (basePrice * (prepayment / 100.0));
      int residualAmount = (int) (basePrice * (residualValue / 100.0));
      int monthlyPayment = (basePrice - depositAmount - prepaymentAmount - residualAmount) / duration;

      System.out.println("\n=== 견적 결과 ===");
      System.out.println(
          "차량정보: " + grade.getCname() + " " + grade.getBname() + " " + grade.getMname() + " " + grade.getGname());
      System.out.printf("차량가격: %,d원\n", basePrice);
      System.out.printf("보증금: %,d원 (%d%%)\n", depositAmount, deposit);
      System.out.printf("선납금: %,d원 (%d%%)\n", prepaymentAmount, prepayment);
      System.out.printf("잔존가치: %,d원 (%d%%)\n", residualAmount, residualValue);
      System.out.printf("월 납입금: %,d원\n", monthlyPayment);
    } catch (Exception e) {
      System.out.println("[오류] 견적 계산 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  /**
   * 계약 신청을 처리하는 메소드
   * 
   * 입력 정보:
   * 1. 신청자 기본 정보 (이름, 연락처)
   * 2. 계약 조건 설정
   * - 보증금/선납금 선택
   * - 잔존가치 비율
   * - 계약기간
   * 3. 신청 정보 저장 및 확인
   * 
   * 유효성 검사:
   * - 전화번호 형식 검증 (000-0000-0000)
   * - 각 선택 항목에 대한 유효값 검증
   */
  private void applyContract() {
    System.out.print("이름: ");
    String name = scan.nextLine();

    String phone;
    while (true) {
      System.out.println("연락처 (형식: 000-0000-0000): ");
      phone = scan.nextLine();
      if (phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
        break;
      }
      System.out.println("[오류] 잘못된 전화번호 형식입니다. 000-0000-0000 형식으로 입력해주세요.");
    }

    System.out.print("계약유형(리스)");
    int type = 2; // 리스는 타입 2로 설정

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

    System.out.print(">>>잔존가치 비율 선택(1:30% 2:40% 3:50%): ");
    int residualChoice = scan.nextInt();
    int residualValue = 0;

    // 잔존가치 선택에 따른 처리
    switch (residualChoice) {
      case 1:
        residualValue = 30;
        break;
      case 2:
        residualValue = 40;
        break;
      case 3:
        residualValue = 50;
        break;
    }
    System.out.println("잔존가치 " + residualValue + "% 선택됨");

    System.out.print(">>>계약기간 선택(1:36개월 2:48개월 3:60개월): ");
    int durationChoice = scan.nextInt();
    int duration = 0;

    // 계약기간 선택에 따른 처리
    switch (durationChoice) {
      case 1:
        duration = 36;
        break;
      case 2:
        duration = 48;
        break;
      case 3:
        duration = 60;
        break;
    }
    System.out.println("계약기간 " + duration + "개월 선택됨");

    LeaseDto dto = new LeaseDto();
    dto.setAname(name);
    dto.setAphone(phone);
    dto.setAtype(type);
    dto.setDeposit(deposit);
    dto.setPrepayments(prepayment);
    dto.setResidual_value(residualValue); // 선택된 잔존가치 설정
    dto.setDuration(duration); // 선택된 계약기간 설정

    boolean result = LeaseController.getInstance().registerApplication(dto);

    if (result) {
      System.out.println("계약 신청이 완료되었습니다!");

      System.out.println("\n=== 신청 내용 확인 ===");
      System.out.println("신청자명:" + dto.getAname());
      System.out.println("연락처:" + dto.getAphone());
      System.out.println("계약유형:" + (dto.getAtype() == 1 ? "렌트" : "리스"));
      System.out.println("보증금:" + dto.getDeposit() + "%");
      System.out.println("선납금:" + dto.getPrepayments() + "%");
      System.out.println("잔존가치" + dto.getResidual_value() + "%");
      System.out.println("계약기간:" + dto.getDuration() + "개월");
    } else {
      System.out.println("계약 신청에 실패했습니다.");
      System.out.println("다시 시도해주세요");
    }
  } // applyContract end

} // class end