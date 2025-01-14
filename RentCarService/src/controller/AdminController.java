package controller;

import java.util.ArrayList;

import model.dao.AdminDao;
import model.dto.ApplyDto;
import model.dto.Dto;

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
	public ArrayList<ApplyDto> apply() {
		ArrayList<ApplyDto> result = AdminDao.getInstance().apply();
		return result;
	}
	
	/** 2. 차량등록 화면 제어 메소드 */	
	public boolean addCar(Dto dto) {
		boolean result = AdminDao.getInstance().addCar(dto);
		return result;
	}
	
	/** 2-1. 테이블 출력 메소드 */
	public ArrayList<Dto> select(String tableName) {
		ArrayList<Dto> result = AdminDao.getInstance().select(tableName);
		return result;
	}	
	
	/** 3. 차량조회 화면 제어 메소드 */
	public ArrayList<Dto> findCar() {
		ArrayList<Dto> result = AdminDao.getInstance().findCar();
		return result;
				
	}
	
	/** 4. 차량수정 화면 제어 메소드 */
	public boolean updateCar(Dto dto) {
		boolean result = AdminDao.getInstance().updateCar(dto);
		return result;
	}
	
	/** 5. 차량삭제 화면 제어 메소드 */
	public String deleteCar(Dto dto) {
		String result = AdminDao.getInstance().deleteCar(dto);
		return result;
	}
	
}
