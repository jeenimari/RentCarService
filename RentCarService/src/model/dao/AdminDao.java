package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.ApplyDto;
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
	public ArrayList<ApplyDto> apply() {
		ArrayList<ApplyDto> result = new ArrayList<>();
		try {
			String sql = "select * from apply";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ApplyDto ad = new ApplyDto(
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
	public boolean updateCar(Dto dto) {
		String sql = "select * from " + dto.getTname() + ";";
		PreparedStatement ps;
		ResultSet rs;
		try {
			if(dto.getTname().equals("brand")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getBname().equals(rs.getString("bname"))) {
						sql = "update brand set bname = ? where bname = ?;";
						ps = conn.prepareStatement(sql);
						ps.setString(1, dto.getName());
						ps.setString(2, dto.getBname());
						int count = ps.executeUpdate();
						if(count == 1) { return true; }
					}
				}
			}
			if(dto.getTname().equals("model")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getMname().equals(rs.getString("mname"))) {
						sql = "update model set mname = ? where mname = ?;";
						ps = conn.prepareStatement(sql);
						ps.setString(1, dto.getName());
						ps.setString(2, dto.getMname());
						int count = ps.executeUpdate();
						if(count == 1) { return true; }
					}
				}
			}
			if(dto.getTname().equals("grade")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getGname().equals(rs.getString("gname")) && dto.getGprice() == rs.getInt("gprice") && dto.getMno() == rs.getInt("mno")) {
						sql = "update grade set gname = ?, gprice = ?, mno = ? where gname = ? and gprice = ? and mno = ?;";
						ps = conn.prepareStatement(sql);
						ps.setString(1, dto.getName());
						ps.setInt(2, dto.getNewPrice());
						ps.setInt(3, dto.getNewNo());
						ps.setString(4, dto.getGname());
						ps.setInt(5, dto.getGprice());
						ps.setInt(6, dto.getMno());
						int count = ps.executeUpdate();
						if(count == 1) { return true; }
					}
				}
			}
		} catch(SQLException e) {
			System.out.println(e);
		}
		return false;
	}
	
	/** 5. 차량삭제 화면 처리 메소드 */
	public String deleteCar(Dto dto) {
		String sql = "select * from " + dto.getTname() + ";";
		PreparedStatement ps;
		ResultSet rs;
		try {			
			if(dto.getTname().equals("brand")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getName().equals(rs.getString("bname"))) {
						sql = "delete from brand where bname = '" + dto.getName() + "';";
						ps = conn.prepareStatement(sql);
						int count = ps.executeUpdate();
						if(count == 1) { return "브랜드 : " + dto.getName() + " 삭제 성공"; }
					}
				}
			}
			if(dto.getTname().equals("model")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getName().equals(rs.getString("mname"))) {
						sql = "delete from model where mname = '" + dto.getName() + "';";
						ps = conn.prepareStatement(sql);
						int count = ps.executeUpdate();
						if(count == 1) { return "모델 : " + dto.getName() + " 삭제 성공"; }
					}
				}
			}
			if(dto.getTname().equals("grade")) {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					if(dto.getName().equals(rs.getString("gname")) &&
							dto.getGprice() == rs.getInt("gprice") && dto.getMno() == rs.getInt("mno")) {
						sql = String.format("delete from grade where gname = '%s' and gprice = '%d' and mno = '%d';",
								dto.getName(), dto.getGprice(), dto.getMno());
						ps = conn.prepareStatement(sql);
						int count = ps.executeUpdate();
						if(count == 1) { return "등급 : " + dto.getName() + " 삭제 성공"; }
					}
				}
			}
			
		} catch(SQLException e) {
			System.out.println(e);
		}
		return null;
	}
	
}
