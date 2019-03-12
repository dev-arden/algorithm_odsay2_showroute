package algorithm_odsay2_showroute;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class db {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://graduate.cbiz6ipldrs0.ap-northeast-2.rds.amazonaws.com:3306/graduate?useSSL=false";
	static final String USERNAME = "soo"; // DB ID
	static final String PASSWORD = "11111111"; // DB Password

	private Connection conn = null;
	private Statement stmt = null;
	private ResultSet rs = null;
	
	public db() {
	      System.out.print("DatabaseName Connection 연결 : ");
	      try {
	         Class.forName(JDBC_DRIVER);
	         conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	         if (conn != null) {
	            System.out.println("OK");
	         } else {
	            System.out.println("Failed");
	         }

	      } catch (ClassNotFoundException e) {
	         System.out.println("Class Not Found Exection");
	         e.printStackTrace();
	      } catch (SQLException e) {
	         System.out.println("SQL Exception");
	         e.printStackTrace();
	      }
	   }
	
	public int userCount() {
		int usercount = 0;// userinfo 행 수를 담을 변수
		String query = "SELECT COUNT(*) as cnt FROM userinfocopy";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				usercount = rs.getInt(1);
			}
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		
		return usercount;
	}
	
	
	public int departure(int i) {
		int depcode = 0;
		String query = "select code from subwaynogada, userinfocopy where userinfocopy.id = " + i + " and subwaynogada.name = userinfocopy.name";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				depcode = rs.getInt(1);
			}
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		
		return depcode;
	}
	
	public int destination(int i) {
		int descode = 0;
		String query = "SELECT code FROM rankSave WHERE user = "+ i +" and rank = 1";
		//SELECT code FROM rankSave WHERE user = 1 and rank = 1
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				descode = rs.getInt(1);
			}
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		
		return descode;
	}
	
	public int prefer(int i) {
		int routecode = 0;
		String query = "SELECT userRoute FROM userinfocopy WHERE id = "+ i;
		//SELECT code FROM rankSave WHERE user = 1 and rank = 1
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				routecode = rs.getInt(1);
			}
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
		
		return routecode+1;
	}
	
	public void deleteuserinfocopy() {
		String query = "TRUNCATE userinfocopy";
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			stmt.close();
		}
		catch (SQLException e) {
			System.out.println("SQL Exception : " + e.getMessage());
		}
	}
}
