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
			if(choose == 1) {}
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
		
	}
	
	
}
