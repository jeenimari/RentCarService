package model.dao;

public class AdminDao extends Dao {
	private String pwd = "1234";
	
	// singleton start
	private static AdminDao instance = new AdminDao();
	private AdminDao() {}
	public static AdminDao getInstance() { return instance; }
	// singleton end
}
