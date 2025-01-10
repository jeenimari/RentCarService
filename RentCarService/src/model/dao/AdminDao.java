package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
		try {
			String sql = "select * from apply";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				AdminDto ad = new AdminDto(
						rs.getInt("ano"), rs.getString("aname"), rs.getString("aphone"),
						rs.getInt("atype"), rs.getInt("deposit"), rs.getInt("prepayments"),
						rs.getInt("residual_value"), rs.getInt("duration"));
				result.add(ad);
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		return result;
	}
	
	/** 2. 차량등록 화면 처리 메소드 */
	public void addCar() {
		
	}
	
	/** 3. 차량조회 화면 처리 메소드 */
	public void findCar() {
		
	}
	
	/** 4. 차량수정 화면 처리 메소드 */
	public void updateCar() {
		
	}
	
	/** 5. 차량삭제 화면 처리 메소드 */
	public void deleteCar() {
		
	}
	
}
