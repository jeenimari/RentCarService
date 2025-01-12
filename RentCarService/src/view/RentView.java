package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.RentController;
import model.dto.RentDto;




public class RentView { // class start
	private static RentView instance = new RentView();
	private RentView() {}
	public static RentView getInstance() { return instance; }
 
	private Scanner scan = new Scanner(System.in);
 
	// 메인 실행
	public void run() {
    while(true) {
         System.out.println("\n=== 렌트카 견적 시스템 ===");
         System.out.println("1. 견적 확인하기");
         System.out.println("2. 계약 신청하기");
         System.out.println("3. 종료");
         System.out.print("선택> ");
         
         int choice = scan.nextInt();
         scan.nextLine(); // 버퍼 처리
         
         switch(choice) {
             case 1: selectCarProcess(); break;
             case 2: applyContract(); break;
             case 3: return;
         }
     }
 }
 
	// 차량 선택 프로세스
	private void selectCarProcess() {
     // 1. 차종 선택 (국산/수입)
     ArrayList<RentDto> categories =  RentController.getInstance().getCarCategories();
        
     System.out.println("\n=== 차종 선택 ===");    
     
     
     for(RentDto car : categories) {
         System.out.println(car.getCno() + ". " + car.getCname());
     }
     
     System.out.print("차종 선택> ");
     int cno = scan.nextInt();
     
     // 2. 브랜드 선택
     ArrayList<RentDto> brands = 
         RentController.getInstance().getBrandsByCategory(cno);
         
     System.out.println("\n=== 브랜드 선택 ===");
     
     for(RentDto brand : brands) {
         System.out.println(brand.getBno() + ". " + brand.getBname());
     }//향상된 for end
     
     System.out.print("브랜드 선택> ");
     	int bno = scan.nextInt();
     
     // 3. 모델 선택
     ArrayList<RentDto> models = 
         RentController.getInstance().getModelsByBrand(bno);
         
     System.out.println("\n=== 모델 선택 ===");
     for(ModelDTO model : models) {
         System.out.println(model.getMno() + ". " + model.getMname());
     }
     
     System.out.print("모델 선택> ");
     int mno = scan.nextInt();
     
     // 4. 등급 선택
     ArrayList<GradeDTO> grades = 
         RentCarController.getInstance().getGradesByModel(mno);
         
     System.out.println("\n=== 등급 선택 ===");
     for(GradeDTO grade : grades) {
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
     GradeDTO grade = 
         RentCarController.getInstance().getGradeInfo(gno);
         
     System.out.println("\n=== 계약 조건 입력 ===");
     System.out.print("계약기간(개월): ");
     int duration = scan.nextInt();
     
     System.out.print("보증금(%): ");
     int deposit = scan.nextInt();
     
     System.out.print("선납금(%): ");
     int prepayment = scan.nextInt();
     
     // 견적 계산 및 표시
     int basePrice = grade.getGprice();
     int depositAmount = (int)(basePrice * (deposit/100.0));
     int prepaymentAmount = (int)(basePrice * (prepayment/100.0));
     int monthlyPayment = 
         (basePrice - depositAmount - prepaymentAmount) / duration;
         
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
     
     System.out.print("계약유형(1:렌트, 2:리스): ");
     int type = scan.nextInt();
     
     System.out.print("보증금(%): ");
     int deposit = scan.nextInt();
     
     System.out.print("선납금(%): ");
     int prepayment = scan.nextInt();
     
     System.out.print("잔존가치(%): ");
     int residualValue = scan.nextInt();
     
     System.out.print("계약기간(개월): ");
     int duration = scan.nextInt();
     
     RentDto dto = new RentDto(
         0, name, phone, type, deposit, 
         prepayment, residualValue, duration
     );
     
     boolean result = 
         RentCarController.getInstance().applyContract(dto);
         
     if(result) {
         System.out.println("계약 신청이 완료되었습니다!");
     } else {
         System.out.println("계약 신청에 실패했습니다.");
     }
 }
}//class end
