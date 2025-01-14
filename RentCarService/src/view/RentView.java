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
         System.out.println("3. 뒤로가기");
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
     ArrayList<RentDto> categories =  RentController.getInstance().getCategoryList();
        
     System.out.println("\n=== 차종 선택 ===");    
     
     
     for(RentDto car : categories) {
         System.out.println(car.getCno() + ". " + car.getCname());
     }
     
     System.out.print("차종 선택> ");
     int cno = scan.nextInt();
     
     // 2. 브랜드 선택
     ArrayList<RentDto> brands = 
         RentController.getInstance().getBrandList(cno);
         
     System.out.println("\n=== 브랜드 선택 ===");
     
     for(RentDto brand : brands) {
         System.out.println(brand.getBno() + ". " + brand.getBname());
     }//향상된 for end
     
     System.out.print("브랜드 선택> ");
     	int bno = scan.nextInt();
     
     // 3. 모델 선택
     ArrayList<RentDto> models = 
         RentController.getInstance().getModelList(bno);
         
     System.out.println("\n=== 모델 선택 ===");
     for(RentDto model : models) {
         System.out.println(model.getMno() + ". " + model.getMname());
     }
     
     System.out.print("모델 선택> ");
     int mno = scan.nextInt();
     
     // 4. 등급 선택
     ArrayList<RentDto> grades = 
         RentController.getInstance().getGradeList(mno);
         
     System.out.println("\n=== 등급 선택 ===");
     for(RentDto grade : grades) {
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
	    RentDto grade = RentController.getInstance().getGradeInfo(gno);
	         
	    System.out.println("\n=== 계약 조건 입력 ===");
	    
	    // 계약기간 선택 수정
	    int duration = 0;
	    while(true) {
	        System.out.println(">>>계약기간 선택");
	        System.out.println("1. 36개월");
	        System.out.println("2. 48개월");
	        System.out.println("3. 60개월");
	        System.out.print("선택> ");
	        int choice = scan.nextInt();
	        
	        switch(choice) {
	            case 1: duration = 36; break;
	            case 2: duration = 48; break;
	            case 3: duration = 60; break;
	            default:
	                System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
	                continue;
	        }
	        break;  // 올바른 선택시 반복문 종료
	    }
	    System.out.println(duration + "개월이 선택되었습니다.");
	     
	    // 보증금/선납금 선택 구조도 같은 방식으로 수정
	    System.out.println("\n>> 보증금 / 선납금 선택");
	    int deposit = 0;
	    int prepayment = 0;
	    
	    while(true) {
	        System.out.println("1. 보증금");
	        System.out.println("2. 선납금");
	        System.out.print("선택> ");
	        int paymentChoice = scan.nextInt();
	        
	        if(paymentChoice == 1) {
	            while(true) {
	                System.out.println(">> 보증금 비율 선택");
	                System.out.println("1. 0%");
	                System.out.println("2. 30%");
	                System.out.println("3. 50%");
	                System.out.print("선택> ");
	                int depositChoice = scan.nextInt();
	                
	                switch(depositChoice) {
	                    case 1: deposit = 0; break;
	                    case 2: deposit = 30; break;
	                    case 3: deposit = 50; break;
	                    default:
	                        System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
	                        continue;
	                }
	                break;
	            }
	            prepayment = 0;  // 선납금은 0으로 설정
	            break;
	        } 
	        else if(paymentChoice == 2) {
	            while(true) {
	                System.out.println(">> 선납금 비율 선택");
	                System.out.println("1. 0%");
	                System.out.println("2. 30%");
	                System.out.println("3. 50%");
	                System.out.print("선택> ");
	                int prepaymentChoice = scan.nextInt();
	                
	                switch(prepaymentChoice) {
	                    case 1: prepayment = 0; break;
	                    case 2: prepayment = 30; break;
	                    case 3: prepayment = 50; break;
	                    default:
	                        System.out.println("잘못된 선택입니다. 1~3 중에서 선택해주세요.");
	                        continue;
	                }
	                break;
	            }
	            deposit = 0;  // 보증금은 0으로 설정
	            break;
	        }
	        else {
	            System.out.println("잘못된 선택입니다. 1 또는 2를 선택해주세요.");
	        }
	    }
      
     
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
     
     System.out.print("계약유형(렌트)");
     int type = 1;
     //보증금/선납금 선택 구조 수정
     System.out.println("\n>>보증금 또는 선납금 선택하십시오");
     System.out.print(">>1.보증금 2.선납금 : ");
     int paymenetChoice = scan.nextInt();
     
     int deposit = 0; 
     int prepayment = 0;  
     
     if(paymenetChoice ==1) {   
    	 System.out.print(">>보증금 비율 선택(1:0% 2:30% 3:50%):");
    	 int depositChoice = scan.nextInt();
    	 switch(depositChoice) {
    	 	case 1: deposit = 0; break;
    	 	case 2: deposit = 30; break;
    	 	case 3: deposit = 50; break;
    	 }//switch end
     System.out.println("보증금" + deposit + "%선택됨(선납금 선택불가)");
     prepayment = 0;  //선납금 0으로 설정
     }else if(paymenetChoice ==2) {
    	 System.out.print(">>선납금 비율 선택(1:0% 2:30% 3:50%):");
    	 int prepaymentChoice = scan.nextInt();
    	 switch(prepaymentChoice) {
    	 	case 1: prepayment = 0; break;
    	 	case 2: prepayment = 30; break;
    	 	case 3: prepayment = 50; break;
    	 }//switch end
    	 System.out.println("선납금" + prepayment + "% 선택됨(보증금 선택 불가)");
    	 deposit = 0;   //보증금 0으로 설정
     }

     
     
     System.out.print(">>>잔존가치 비율 선택(1:30% 2:40% 3:50%): ");
     int residualChoice = scan.nextInt();
     int residualValue = 0;

     // 잔존가치 선택에 따른 처리
     switch(residualChoice) {
        case 1: residualValue = 30; break;
        case 2: residualValue = 40; break;
        case 3: residualValue = 50; break;
     }
     System.out.println("잔존가치 " + residualValue + "% 선택됨");

     System.out.print(">>>계약기간 선택(1:36개월 2:48개월 3:60개월): ");
     int durationChoice = scan.nextInt();
     int duration = 0;

     // 계약기간 선택에 따른 처리
     switch(durationChoice) {
        case 1: duration = 36; break;
        case 2: duration = 48; break;
        case 3: duration = 60; break;
     }
     System.out.println("계약기간 " + duration + "개월 선택됨");

     RentDto dto = new RentDto();
     dto.setAname(name);
     dto.setAphone(phone);
     dto.setAtype(type);
     dto.setDeposit(deposit);
     dto.setPrepayments(prepayment);
     dto.setResidualValue(residualValue);  // 선택된 잔존가치 설정
     dto.setDuration(duration);            // 선택된 계약기간 설정
    		
    
     
     boolean result = 
         RentController.getInstance().registerApplication(dto); 
         
     if(result) {
         System.out.println("계약 신청이 완료되었습니다!");
         
         
         System.out.println("\n=== 신청 내용 확인 ===");
         System.out.println("신청자명:" + dto.getAname());
         System.out.println("연락처:" + dto.getAphone());
         System.out.println("계약유형:" +(dto.getAtype()==1?"렌트" : "리스"));
         System.out.println("보증금:" + dto.getDeposit()+ "%");
         System.out.println("선납금:" + dto.getPrepayments()+"%");
         System.out.println("잔존가치" + dto.getResidualValue()+"%");
         System.out.println("계약기간:" + dto.getDuration()+"개월");
     }else {
    	 System.out.println("계약 신청에 실패했습니다.");
    	 System.out.println("다시 시도해주세요");
     }
 }
}//class end
