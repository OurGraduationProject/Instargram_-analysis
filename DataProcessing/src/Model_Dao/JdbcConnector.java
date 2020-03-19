package Model_Dao;

import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model_Dto.DTOtest;;


public class JdbcConnector {

	static String Keyword;
	Connection conn =null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public JdbcConnector(){
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			String url ="jdbc:mysql://220.67.115.29:3388/dongdang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			
			Connection conn = DriverManager.getConnection(url,"dongdang","h0t$ix");
			System.out.println("연결성공");
			
		}catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("클래스 적재 실패!!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("연결 실패!!");
        }
	}
	
	public void DBclose(Connection conn, Statement stmt, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			}catch(SQLException e){}
		}
		if(stmt != null) {
			try {
				stmt.close();
			}catch(SQLException e){}
		}
		if(conn != null) {
			try {
				conn.close();
			}catch(SQLException e){}
		}
	}
}	
