package controller;

import java.util.ArrayList;

import model.dao.AdminDao;
import model.dto.AdminDto;

public class AdminController {
	// singleton start
	private static AdminController instance = new AdminController();
	private AdminController() {}
	public static AdminController getInstance() { return instance; }
	// singleton end
	
	/** 관리자모드 접속 화면 제어 메서드 */
	public boolean pattern(String pwd) {
		boolean result = AdminDao.getInstance().pattern(pwd);
		return result;
	}
	
	/** 1.신청조회 화면 제어 메소드 */
	public ArrayList<AdminDto> apply() {
		ArrayList<AdminDto> result = AdminDao.getInstance().apply();
		return result;
	}
	
	/** 2. 차량등록 화면 제어 메소드 */
	public void addCar() {
		
	}
	
	/** 3. 차량조회 화면 제어 메소드 */
	public void findCar() {
		
	}
	
	/** 4. 차량수정 화면 제어 메소드 */
	public void updateCar() {
		
	}
	
	/** 5. 차량삭제 화면 제어 메소드 */
	public void deleteCar() {
		
	}
	
}
