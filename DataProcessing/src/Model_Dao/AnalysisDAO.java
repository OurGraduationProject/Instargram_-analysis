package Model_Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model_Dto.ContentDTO;

import java.util.ArrayList;

public class AnalysisDAO {
	
	static int board_num; //총 게시글 수
	
	JdbcConnector Connect = new JdbcConnector();
	
	public float board_select(String SearchNm) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
	
		//SQL문을 통해서 한 해시태그 게시글의 수가 나옴 Ex.다인치과 검색시 다인치과 게시글 갯수
		String sql = "SELECT COUNT(*) FROM hash_tag INNER JOIN hash_content_con ON hash_content_con.hash_tagAdr = hash_tag.hash_tagAdr WHERE hash_tag.hash_tagNm like '" + SearchNm + "%'";
		
		try {
			//jdbc연결
			String url ="jdbc:mysql://220.67.115.29:3388/dongdang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			
			
			//url 아이디 패스워드 입력
			conn = DriverManager.getConnection(url,"dongdang","h0t$ix");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		
			while(rs.next()) {
				board_num = rs.getInt("count(*)");
			}
		
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			Connect.DBclose(conn, stmt, rs);
		}
		return board_num;
	}
	
	public ArrayList<ContentDTO> content_select(String SearchNm){
		
		//SQL문을 통해 여러 개의 데이터를 리스트에 넣기위해 Arraylist사용.
		ArrayList<ContentDTO> list = new ArrayList<ContentDTO>();
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		//sql문으로 한 해시태그에 대한 : 해시태그이름, 해시태그 내용, 좋아요, 댓글  나옴
		String sql = "select hash_tag.hash_tagNm, content.content, content.good, content.comment_num from hash_tag INNER JOIN hash_content_con ON hash_content_con.hash_tagAdr = hash_tag.hash_tagAdr INNER JOIN content ON content.contentAdr = hash_content_con.contentAdr where hash_tag.hash_tagNm like '" + SearchNm + "%'";
				
		try {
			//jdbc연결
			String url ="jdbc:mysql://220.67.115.29:3388/dongdang?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false";
			
			//url 아이디 패스워드 입력
			conn = DriverManager.getConnection(url,"dongdang","h0t$ix");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				ContentDTO cont = new ContentDTO();
				cont.setGood(rs.getInt("good"));
				cont.setSub_hash_tag(rs.getString("content"));
				cont.setComment_num(rs.getInt("comment_num"));
				list.add(cont);	
			}return list;
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			Connect.DBclose(conn, stmt, rs);
		}
		return null;
	}
	
	
	//아래주석 관련없음 무시하셈 나중에 지울꺼
//	public static void main(String[] args) {
//		
//		int Totalgood = 0;
//		
//		AnalysisDAO test = new AnalysisDAO();
////		ArrayList<ContentDTO> list = test.content_select();
////		test.board_select();
//		if(list != null) {
//			System.out.println(list.get(0).getSub_hash_tag());
//			
//			for(ContentDTO i : list) {
////				Totalgood += i.getGood();
////				System.out.println(Totalgood);
//			}	
//		}else {
//			System.out.println("Null");
//		}
//	}
}

