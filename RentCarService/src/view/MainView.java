package view;

public class MainView {
	// singleton start
	private static MainView instance = new MainView();
	private MainView() {}
	public static MainView getInstance() { return instance; }
	// singleton end
	
	/** 메인뷰 페이지 */
	public void run() {
		System.out.println("실행!");
	}
}
