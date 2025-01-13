package view;

import java.util.Scanner;

public class MainView {
	// singleton start
	private static MainView instance = new MainView();
	private MainView() {}
	public static MainView getInstance() { return instance; }
	// singleton end
	
	Scanner scan = new Scanner(System.in);
	
	/** 메인뷰 페이지 */
	public void run() {
		System.out.println("======== 메인화면 ========");
		while(true) {			
			System.out.print(">> 1.렌트 2.리스 3.관리모드 4.종료 : ");
			int choose = scan.nextInt();
			if(choose == 1) {RentView.getInstance().run();}
			else if(choose == 2) {}
			else if(choose == 3) { AdminView.getInstance().pattern(); }
			else if(choose == 4) {System.out.println(">> 종료"); break; }
		}
	}
}
