package view;

import java.util.ArrayList;
import java.util.Scanner;

import controller.AdminController;
import model.dto.AdminDto;

public class AdminView {
	// singleton start
	private static AdminView instance = new AdminView();
	private AdminView() {}
	public static AdminView getInstance() { return instance; }
	// singleton end
	
	Scanner scan = new Scanner(System.in);
	
	/** 관리자모드 접속 화면 */
	public void pattern() {
		String pwd = scan.next();
		boolean result = AdminController.getInstance().pattern(pwd);
		if(result) { run(); }
		else { }
	}
	
	/** 관리자모드 메인 화면 */
	public void run() {
		System.out.println("======== 관리자모드 ========");
		while(true) {
			System.out.print("1.신청조회 2.차량등록 3.차량조회 4.차량수정 5.차량삭제 6.뒤로가기 : ");
			int choose = scan.nextInt();
			if(choose == 1) { apply(); }
			else if(choose == 2) {}
			else if(choose == 3) {}
			else if(choose == 4) {}
			else if(choose == 5) {}
			else if(choose == 6) {
				System.out.println(">> 뒤로가기");
				System.out.println("======== 메인화면 ========");
				break; 
			}
		}
	}
	
	/** 1.신청조회 화면 메소드 */
	public void apply() {
		ArrayList<AdminDto> result = AdminController.getInstance().apply();
		System.out.println("======== 신청조회 ========");
		System.out.println(">> 순번\t이름\t전화번호\t\t종류\t보증금\t선납금\t잔존가치\t계약기간");
		for(int index = 0; index < result.size(); index++) {
			AdminDto ad = result.get(index);
			System.out.printf(">> %d\t%s\t%s\t%d\t%d\t%d\t%d\t%d\n",
					ad.getAno(), ad.getAname(), ad.getAphone(), ad.getAtype(),
					ad.getDeposit(), ad.getPrepayments(), ad.getResidualValue(), ad.getDuration());
		}
		System.out.println("======== 신청조회 ========");
	}
	
	/** 2. 차량등록 화면 메소드 */
	public void addCar() {
		
	}
	
	/** 3. 차량조회 화면 메소드 */
	public void findCar() {
		
	}
	
	/** 4. 차량수정 화면 메소드 */
	public void updateCar() {
		
	}
	
	/** 5. 차량삭제 화면 메소드 */
	public void deleteCar() {
		
	}
	
}
