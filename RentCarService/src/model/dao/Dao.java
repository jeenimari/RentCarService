package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dao {
	/** DB와 연동한 결과를 조작하는 인터페이스 */
	protected Connection conn;
	/** 연동할 DB서버의 URL */
	private String dburl = "jdbc:mysql://localhost:3306/rentcar";
	/** 연동할 DB서버의 계정명 */
	private String dbuser = "root";
	/** 연동할 DB서버의 비밀번호 */    
	 private String dbpwd = "1234";
	//private String dbpwd = "2965";

	protected Dao() {
		try {
			// 1. JDBC 클래스 드라이버 로드, Class.forName();
			Class.forName("com.mysql.cj.jdbc.Driver");
			// 2. 설정한 경로/계정/비밀번호로 DB서버에 연동을 시도하고 결과물(구현체)를 반환
			conn = DriverManager.getConnection(dburl, dbuser, dbpwd);
		} catch (ClassNotFoundException e) {
			System.out.println(e);
		} catch (SQLException e) {
			System.out.println(">> DB 연동 실패");
			System.out.println(e);
		}
	}

	// 데이터베이스 연결 확인
	protected void checkConnection() throws SQLException {
		if (conn == null || conn.isClosed()) { // 연결이 없거나 닫힌 경우
			conn = DriverManager.getConnection(dburl, dbuser, dbpwd); // 연결 재시도
		}
	}
}
