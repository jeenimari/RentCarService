package view;

import java.util.Scanner;



public class RentView {
	private static RentView instance = new RentView();
	private RentView() {}
	public static RentView getInstance() {return instance;}
	
	private Scanner scan = new Scanner(System.in);
	
	public void run() {
		while(true) {
			System.out.println("\n====렌트카===== ");
			//1.차종타입 선택1123
			System.out.println("1.국산차 2.수입차"); int choose = scan.nextInt();
			if(choose == 1) { RentView.getInstance().국산브랜드조회함수; }
			else if(choose == 2) {RentView.getInstance().수입브랜드조회함수;}
			else {System.out.println("잘못누르셨습니다.");}
			
			
		}
	}//run 함수 end
	
	//국산차브랜드 조회함수
	
	//수입브랜드 조회함수
	
}//class end
