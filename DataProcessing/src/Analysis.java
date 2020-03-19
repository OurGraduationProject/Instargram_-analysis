
import java.util.ArrayList;

import Model_Dao.AnalysisDAO;
import Model_Dto.ContentDTO;

public class Analysis {
	
	static String SearchNm = "석수동";
	static AnalysisDAO DAO = new AnalysisDAO();
	
	static float totalgood = 0; // 메인해시태그 의 전체 좋아요 갯수
	static float totalweight = 0;// 메인해시태그 의 가중치조건의 합
	static float totalcomment = 0; // 메인해시태그의 총 댓글 수
	static float totalsub_hash = 0;	// 메인해시태그의 총 서브해시태그 갯수
	
	//AnalysisDAO에 있는 content_select메서드의 데이터들을 리스트에 넣어 가져옴
	ArrayList<ContentDTO> list = DAO.content_select(SearchNm);
	
	public void Totalgood(float totalgood, float totalweight) {
		
//		DAO.content_select(SearchNm);
//		ArrayList<ContentDTO> list = DAO.content_select(SearchNm);
		
		if(list != null) {
			
			for(ContentDTO i : list) {
				totalgood += i.getGood();	//한 게시글의 전체 좋아요 합
			}
			for(ContentDTO i : list) {
//				한 게시글의 좋아요 비중이  20% ,40%, 60%, 100% 대로 가중치가 부여됨 (수정이 필요하다 생각..좋아요가 전부다 1로 나와버림)  
				if(i.getGood() / totalgood <= 0.2) {
					totalweight += 1;
				}
				else if(i.getGood() / totalgood <= 0.4) {
					totalweight += 1.25;
				}
				else if(i.getGood() / totalgood <= 0.6) {
					totalweight += 1.75;
				}
				else if(i.getGood() / totalgood <= 1) {
					totalweight += 2;
				}
				this.totalgood = totalgood;
				this.totalweight = totalweight;
			}	
		}else {
			System.out.println("리스트가 비었습니다.");
		}
	}
	
	
	public void Totalcomment(float totalcomment) {
		
//		DAO.content_select(SearchNm);
//		ArrayList<ContentDTO> list = DAO.content_select(SearchNm);
		
		if(list != null) {
			
			for(ContentDTO i : list) {
				totalcomment += i.getComment_num();	//한 게시글의 전체 좋아요 합
			}
			this.totalcomment = totalcomment;
		}else {
			System.out.println("리스트가 비었습니다.");
		}
	}
	
	
	public void Totalsub_hash(float totalsub_hash) {
		
		String temporary;	// 임시 저장소
		String[] shop;	// split#을 나눈 배열 저장소
		
		if(list != null) {
			// 서브 해시태그 content를 #으로 나눠서 길이만큼 더함
			for(ContentDTO i : list) {
				temporary = i.getSub_hash_tag();
				shop = temporary.split("#");
				totalsub_hash += shop.length;
			}
		}else {
			System.out.println("리스트가 비었습니다.");
		}
		this.totalsub_hash = totalsub_hash;
	}
	
	public static void main(String[] args) {
		
		float boardnum;
		float totalscore;
		
		Analysis analysis = new Analysis();
		
		boardnum = DAO.board_select(SearchNm);
		
		analysis.Totalgood(totalgood, totalweight);
		analysis.Totalcomment(totalcomment);
		analysis.Totalsub_hash(totalsub_hash);
		
		
		// 서브해시태그에 대한 수 는 로직이 너무 복잡해 질 거같아 의논 후 코딩할 계획
		totalscore = (float) (totalgood * 0.35 + totalcomment * 0.45 + boardnum * 0.15 + totalsub_hash * 0.5);
		
		System.out.println(SearchNm + "에 대한 조회 결과 총 " + boardnum + " 개의 게시글 \n" 
				+ totalgood + " 개의 좋아요 \n" + totalcomment + " 개의 댓글 수 \n" + totalsub_hash + " 개의 서브 해시 태그 수가 조회 되었습니다.");
		
		System.out.println(SearchNm + "에 대한 종합점수는 " + totalscore + "입니다.");
		}
}
