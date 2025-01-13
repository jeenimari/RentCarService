package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.dto.RentDto;

public class RentDao extends Dao {
	//싱글톤
	private static RentDao instance = new RentDao();
	private RentDao() {super();}
	public static RentDao getInstance() {return instance;}
	
	//카테고리 조회
	public ArrayList<RentDto>getCagegoryList(){
		ArrayList<RentDto> list = new ArrayList<>();
		try {
				String sql = "select * from car";
				PreparedStatement ps = conn.prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				RentDto dto = new RentDto();
				dto.setCno(rs.getInt("cno"));
				dto.setCname(rs.getString("cname"));
				list.add(dto);
				}
				}catch (SQLException e) {
				System.out.println("카테고리 조회 오류: " + e);
				}
				return list;
	}// c end
	
	//브랜드조회
	
	public ArrayList<RentDto>getBrandList(int cno){
		ArrayList<RentDto> list = new ArrayList<>();
		try {
			String sql = "SELECT * FROM brand WHERE cno = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, cno);
			ResultSet rs = ps.executeQuery(); 
			
			while(rs.next()) {
				RentDto dto = new RentDto();
				dto.setBno(rs.getInt("bno"));
				dto.setBname(rs.getString("bname"));
				dto.setCno(rs.getInt("cno"));
				list.add(dto);
			}//while end
		}catch (SQLException e) {
			System.out.println("브랜드 조회 오류" + e);
			// TODO: handle exception
		}
		return list;
		
	}// c end
	
	//모델 조회
	public ArrayList<RentDto>getModelList(int bno){
		ArrayList<RentDto>list = new ArrayList<>();
		try {
			String sql =  "SELECT * FROM model WHERE bno = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, bno);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				RentDto dto = new RentDto();
				dto.setMno(rs.getInt("mno"));
				dto.setMname(rs.getString("mname"));
				dto.setBno(rs.getInt("bno"));
				list.add(dto);
			}//while end
		}catch (SQLException e) {
			System.out.println("모델 조회 오류:"+e);
			// TODO: handle exception
		}
		return list;
		
	}// c end
	 // 등급 조회
	   public ArrayList<RentDto> getGradeList(int mno) {
	       ArrayList<RentDto> list = new ArrayList<>();
	       try {
	           String sql = "SELECT * FROM grade WHERE mno = ?";
	           PreparedStatement ps = conn.prepareStatement(sql);
	           ps.setInt(1, mno);
	           ResultSet rs = ps.executeQuery();
	           
	           while(rs.next()) {
	               RentDto dto = new RentDto();
	               dto.setGno(rs.getInt("gno"));
	               dto.setGname(rs.getString("gname"));
	               dto.setGprice(rs.getInt("gprice"));
	               dto.setMno(rs.getInt("mno"));
	               list.add(dto);
	           }
	       } catch(SQLException e) {
	           System.out.println("등급 조회 오류: " + e);
	       }
	       return list;
	   }

	   // 렌트/리스 신청
	   public boolean registerApplication(RentDto dto) {
	       try {
	           String sql = "INSERT INTO apply (aname, aphone, atype, deposit, prepayments, residual_value, duration) VALUES (?, ?, ?, ?, ?, ?, ?)";
	           PreparedStatement ps = conn.prepareStatement(sql);
	           ps.setString(1, dto.getAname());
	           ps.setString(2, dto.getAphone());
	           ps.setInt(3, dto.getAtype());
	           ps.setInt(4, dto.getDeposit());
	           ps.setInt(5, dto.getPrepayments());
	           ps.setInt(6, dto.getResidualValue());
	           ps.setInt(7, dto.getDuration());
	           
	           return ps.executeUpdate() == 1;
	       } catch(SQLException e) {
	           System.out.println("신청 등록 오류: " + e);
	       }
	       return false;
	   }

	   // 특정 등급 정보 조회
	   public RentDto getGradeInfo(int gno) {
	       try {
	           String sql = "SELECT * FROM grade WHERE gno = ?";
	           PreparedStatement ps = conn.prepareStatement(sql);
	           ps.setInt(1, gno);
	           ResultSet rs = ps.executeQuery();
	           
	           if(rs.next()) {
	               RentDto dto = new RentDto();
	               dto.setGno(rs.getInt("gno"));
	               dto.setGname(rs.getString("gname"));
	               dto.setGprice(rs.getInt("gprice"));
	               dto.setMno(rs.getInt("mno"));
	               return dto;
	           }
	       } catch(SQLException e) {
	           System.out.println("등급 정보 조회 오류: " + e);
	       }
	       return null;
	   }
}// c end 
