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
	public boolean addCar(String table, String property, Dto data) {
		boolean result = false;
		switch(table) {
			case "car":
				String cname = data.getCname();
				result = addData(table, property, cname);
				break;
			case "brand":
				String bname = data.getBname();
				result = addData(table, property, bname);
				break;
			case "model":
				String mname = data.getMname();
				result = addData(table, property, mname);
				break;
			case "grade":
		}
		return result;
	}
	
	public boolean addData(String table, String property, String name) {
		try {
			String sql = "insert into " + table + "(" + property + ") values (?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, name);
			int count = ps.executeUpdate();
			if(count == 1) { return true; }
		} catch(SQLException e) {
			System.out.println(e);
			System.out.println(">> 이미 존재하는 값입니다.");
		}
		return false;
	}
	
	public void add(Dto dto) {
		ArrayList<Dto> table = new ArrayList<>();
		String cname = dto.getCname(); String bname = dto.getBname();
		String mname = dto.getMname(); String gname = dto.getGname(); int gprice = dto.getGprice();
		boolean cstate = false, bstate = false, mstate = false, gstate = false, pstate = false;
		try {
			String sql = "select car.cno, car.cname, brand.bno, brand.bname, model.mno, model.mname, grade.gno, grade.gname, grade.gprice "
					+ "from car "
					+ "inner join brand on car.cno = brand.cno "
					+ "inner join model on brand.bno = model.bno "
					+ "inner join grade on model.mno = grade.mno where car.cname = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, cname);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Dto temp = new Dto();
				temp.setCno(rs.getInt("cno")); temp.setBno(rs.getInt("bno")); temp.setMno(rs.getInt("mno")); temp.setGno(rs.getInt("gno"));
				temp.setCname(rs.getString("cname")); temp.setBname(rs.getString("bname")); temp.setMname(rs.getString("mname"));
				temp.setGname(rs.getString("gname")); temp.setGprice(rs.getInt("gprice")); table.add(temp);
			}
			// 출력 시작
			for(int index = 0; index < table.size(); index++) {
				Dto temp = table.get(index);
				System.out.println(temp.toString());
			}
			// 출력 끝
			for(int index = 0; index < table.size(); index++) {
				Dto temp = table.get(index);
				if(temp.getCname().equals(cname)) {
					cstate = true;
				}
			}
			for(int index = 0; index < table.size(); index++) {
				Dto temp = table.get(index);
				if(temp.getBname().equals(bname)) {
					bstate = true;
				}
			}
			for(int index = 0; index < table.size(); index++) {
				Dto temp = table.get(index);
				if(temp.getMname().equals(mname)) {
					mstate = true;
				}
			}
			for(int index = 0; index < table.size(); index++) {
				Dto temp = table.get(index);
				if(temp.getGname().equals(gname)) {
					gstate = true;
				}
			}
			if(cstate == false) {
				sql = "insert into car(cname) values (?);";
				ps = conn.prepareStatement(sql);
				ps.setString(1, cname);
				int count = ps.executeUpdate();
				if(count == 1) { System.out.println(">> 카테고리 등록 성공"); } else { System.out.println(">> 카테고리 등록 실패"); }
			}
			if(bstate == false) {
				sql = "insert into brand(bname, cno) values (?, ?);";
			}
						
		} catch(SQLException e) {
			System.out.println(e);
		}
	}
	
	public boolean add2(Dto dto) {
		boolean cstate = false;
		boolean bstate = false;
		boolean mstate = false;
		boolean gstate = false;
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
	
	public ArrayList<Dto> select2(String tableName) {
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
	
	/** 2-1. 테이블 출력 메소드 */
	public ArrayList<Dto> select(String tableName) {
		ArrayList<Dto> result = new ArrayList<>();
		try {
			String Sql = "select * from " + tableName + ";";
			PreparedStatement ps = conn.prepareStatement(Sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Dto dto = new Dto();
				if(tableName.equals("car")) {
					dto.setCno(rs.getInt("cno"));
					dto.setCname(rs.getString("cname"));
					
				} else if(tableName.equals("brand")) {
					dto.setBno(rs.getInt("bno"));
					dto.setBname(rs.getString("bname"));
					dto.setCno(rs.getInt("cno"));
					
				} else if(tableName.equals("model")) {
					dto.setMno(rs.getInt("mno"));
					dto.setMname(rs.getString("mname"));
					dto.setBno(rs.getInt("bno"));
					
				} else if(tableName.equals("grade")) {
					dto.setGno(rs.getInt("gno"));
					dto.setGname(rs.getString("gname"));
					dto.setGprice(rs.getInt("gprice"));
					dto.setMno(rs.getInt("mno"));
					
				}
				result.add(dto);
				
			}
			return result;
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
