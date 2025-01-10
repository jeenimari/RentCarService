package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.AdminDto;

public class AdminDao extends Dao {
	private String pwd = "1234";
	
	// singleton start
	private static AdminDao instance = new AdminDao();
	private AdminDao() {}
	public static AdminDao getInstance() { return instance; }
	// singleton end
	
	/** 관리자모드 접속 화면 처리 메소드 */
	public boolean pattern(String pwd) {
		if(this.pwd.equals(pwd)) { return true; }
		return false;
	}
	
	/** 1.신청조회 화면 메소드 */
	public ArrayList<AdminDto> apply() {
		ArrayList<AdminDto> result = new ArrayList<>();
		return result;
	}
	
}
