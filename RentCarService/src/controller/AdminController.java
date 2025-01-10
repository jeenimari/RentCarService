package controller;

public class AdminController {
	// singleton start
	private static AdminController instance = new AdminController();
	private AdminController() {}
	public static AdminController getInstance() { return instance; }
	// singleton end
}
