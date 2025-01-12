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

	public ArrayList<RentDto>
	
	
}// c end 
