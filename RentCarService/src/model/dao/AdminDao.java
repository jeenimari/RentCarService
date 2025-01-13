package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.AdminDto;
import model.dto.Dto;

public class AdminDao extends Dao {
	private String pwd = "1234";
	private ArrayList<Dto> tempData = new ArrayList<>();
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
	public boolean addCar(Dto dto) {
		boolean cstate = false; boolean bstate = false;
		boolean mstate = false; boolean gstate = false;
		try {
			String sql = "select * from car;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(dto.getCname().equals(rs.getString("cname"))) {
					cstate = true;
				}
			}
			sql = "select * from brand;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(dto.getBname().equals(rs.getString("bname"))) {
					bstate = true;
				}
			}
			sql = "select * from model;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(dto.getMname().equals(rs.getString("mname"))) {
					mstate = true;
				}
			}
			sql = "select * from grade;";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				if(dto.getGname().equals(rs.getString("gname")) && dto.getMno() == rs.getInt("mno")) {
					gstate = true;
				}
			}
			if(cstate == false) {
				sql = "insert into car(cname) values (?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getCname());
				int count = ps.executeUpdate();
				if(count == 1) { cstate = true; }
			}
			if(bstate == false) {
				sql = "insert into brand(bname, cno) values (?, ?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getBname()); ps.setInt(2, dto.getCno());
				int count = ps.executeUpdate();
				if(count == 1) { bstate = true; }
			}
			if(mstate == false) {
				sql = "insert into model(mname, bno) values (?, ?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getMname()); ps.setInt(2, dto.getBno());
				int count = ps.executeUpdate();
				if(count == 1) { mstate = true; }
			}
			if(gstate == false) {
				sql = "insert into grade(gname, gprice, mno) values (?, ?, ?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getGname()); ps.setInt(2, dto.getGprice()); ps.setInt(3, dto.getMno());
				int count = ps.executeUpdate();
				if(count == 1) { gstate = true; }
			}
			if(cstate && bstate && mstate && gstate) {
				return true;
			}
			
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	}
	
	/** 2-1. 테이블 출력 메소드 */
	public ArrayList<Dto> select(String tableName) {
		ArrayList<Dto> result = new ArrayList<>();
		try {
			String sql = "select * from " + tableName + ";";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Dto dto = new Dto();
				if(tableName.equals("car")) {
					dto.setCno(rs.getInt("cno")); dto.setCname(rs.getString("cname"));
				} else if(tableName.equals("brand")) {
					dto.setBno(rs.getInt("bno")); dto.setBname(rs.getString("bname")); dto.setCno(rs.getInt("cno"));
				} else if(tableName.equals("model")) {
					dto.setMno(rs.getInt("mno")); dto.setMname(rs.getString("mname")); dto.setBno(rs.getInt("bno"));
				} else if(tableName.equals("grade")) {
					dto.setGno(rs.getInt("gno")); dto.setGname(rs.getString("gname"));
					dto.setGprice(rs.getInt("gprice")); dto.setMno(rs.getInt("mno"));
				}
				result.add(dto);
				tempData.add(dto);
					
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return result;
	}
	
	/** 3. 차량조회 화면 처리 메소드 */
	public ArrayList<Dto> findCar() {
		ArrayList<Dto> result = new ArrayList<>();
		try {			
			String sql = "select car.cno, car.cname, brand.bno, brand.bname, "
					+ "model.mno, model.mname, grade.gno, grade.gname, grade.gprice "
					+ "from car "
					+ "inner join brand on car.cno = brand.cno "
					+ "inner join model on brand.bno = model.bno "
					+ "inner join grade on model.mno = grade.mno;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Dto dto = new Dto();
				dto.setCno(rs.getInt("cno")); dto.setBno(rs.getInt("bno")); dto.setMno(rs.getInt("mno")); dto.setGno(rs.getInt("gno"));
				dto.setCname(rs.getString("cname")); dto.setBname(rs.getString("bname")); dto.setMname(rs.getString("mname"));
				dto.setGname(rs.getString("gname")); dto.setGprice(rs.getInt("gprice")); result.add(dto);
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return result;
	}
	
	/** 4. 차량수정 화면 처리 메소드 */
	public void updateCar() {
		
	}
	
	/** 5. 차량삭제 화면 처리 메소드 */
	public void deleteCar() {
		
	}
	
}
