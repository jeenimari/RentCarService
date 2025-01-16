package view;

import java.util.Scanner;

public class MainView {
	// singleton start
	private static MainView instance = new MainView();

	private MainView() {
	}

	public static MainView getInstance() {
		return instance;
	}
	// singleton end

	Scanner scan = new Scanner(System.in);

	// 구분선 출력 메서드
	private void printDivider() {
		System.out.println("\n============================================");
	}

	/** 메인뷰 페이지 */
	public void run() {
		while (true) {
			printDivider();
			System.out.print("           🚘 자동차 렌트 / 리스 서비스 🚘           ");
			printDivider();
			System.out.println("1. 렌트 서비스");
			System.out.println("2. 리스 서비스");
			System.out.println("3. 관리자 모드");
			System.out.println("4. 종료");
			printDivider();
			System.out.print("선택> ");

			int choose = scan.nextInt();
			if (choose == 1) {
				RentView.getInstance().run();
			} else if (choose == 2) {
				LeaseView.getInstance().run();
			} else if (choose == 3) {
				AdminView.getInstance().pattern();
			} else if (choose == 4) {
				printDivider();
				System.out.println("     ✨ 이용해 주셔서 감사합니다 ✨     ");
				printDivider();
				break;
			}
		}
	}
}
