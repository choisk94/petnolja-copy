package com.petnolja.research.model.dao;

import static com.petnolja.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.petnolja.common.model.vo.PageInfo;
import com.petnolja.member.model.vo.Member;
import com.petnolja.pet.model.vo.Pet;
import com.petnolja.petsitter.model.vo.Sitter;
import com.petnolja.research.model.vo.Research;
import com.petnolja.research.model.vo.Review;

public class ResearchDao {

	private Properties prop = new Properties();

	public ResearchDao() {

		String fileName = ResearchDao.class.getResource("/sql/research/research-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 메인페이지에서 노출될 추천 시터 조회
	public ArrayList<Sitter> indexSitterList(Connection conn) {
		ArrayList<Sitter> list = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("bestRecommend");
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new Sitter(rset.getInt("MEM_NO"),
						rset.getString("MEM_NAME"),
						rset.getString("SITTER_TITLE"),
					    rset.getString("PATH"),
					    rset.getInt("AVGRATING")));
					    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;

	}
	
	// 메인페이지에서 노출될 베스트 리뷰 조회
	public ArrayList<Review> indexReviewList(Connection conn) {
		ArrayList<Review> list = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("bestReview");
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				list.add(new Review(rset.getString("MEM_NAME"),
						rset.getInt("REV_RATING"),
						rset.getString("REV_CONTENT"),
					    rset.getString("PATH")));
					    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;

	}
	
	
	
	//검색시 보여줄 시터리스트의 총 갯수
	public int searchListCount(Connection conn, String startDate, String endDate, String[] options) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("searchListCount");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, startDate); //시작날짜
			pstmt.setString(2, endDate); //종료날짜
			if(options!=null) {  //옵션이 빈값이 아니라면
			for (int i = 0; i < options.length; i++) { //옵션이 몇개인지 모르기 때문에 옵션개수에 따라 지정
				pstmt.setString(i+3, "%"+options[i]+"%");}
			for (int i = 0; i < 5-options.length; i++) {
				pstmt.setString(i+3+options.length, "%");}
			} else {  //옵션이 빈값이라면 의미없는 "%"로 채워줌
			for (int i = 0; i < 5; i++) {
				pstmt.setString(i+3, "%");}
			}
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("count");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
		
	
	}
	
	
		
		//검색시 보여줄 시터리스트
				public ArrayList<Research> searchSitter(Connection conn, Member m, String startDate, String endDate, String[] options, PageInfo pi, String array) {
					ArrayList<Research> list = new ArrayList<>();
					ResultSet rset = null;
					PreparedStatement pstmt = null;
					String sql = prop.getProperty("searchSitter");
					switch(array) {
						case "recommend": sql += "ORDER BY avgrating desc NULLS LAST, (case when osmall = 0 then 9999999999 else 0 end), osmall asc"; break;
						case "starArray": sql += "ORDER BY avgrating desc NULLS LAST, date_count desc, distance"; break;
						case "priceArray": sql += "ORDER BY (case when osmall = 0 then 9999999999 else 0 end), osmall asc"; break;
						case "distanceArray": sql += "ORDER BY distance, date_count desc, avgrating desc"; break;
						case "dateArray": sql += "ORDER BY DATE_COUNT DESC NULLS LAST, avgrating desc"; break;
					}
					sql+=") A ) where rnum between ? and ?";
					

					try {
						pstmt=conn.prepareStatement(sql);
						int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
						int endRow = startRow + pi.getBoardLimit() - 1;
						pstmt.setDouble(1, m.getMemLatitude()); //위도측정용
						pstmt.setDouble(2, m.getMemLongtitude()); //경도측정용
						pstmt.setString(3, startDate); //시작날짜
						pstmt.setString(4, endDate); //종료날짜
						if(options!=null) {  //옵션이 빈값이 아니라면
						for (int i = 0; i < options.length; i++) { //옵션이 몇개인지 모르기 때문에 옵션개수에 따라 지정
							pstmt.setString(i+5, "%"+options[i]+"%");}
						for (int i = 0; i < 5-options.length; i++) {
							pstmt.setString(i+5+options.length, "%");}
						} else {  //옵션이 빈값이라면 의미없는 "%"로 채워줌
						for (int i = 0; i < 5; i++) {
							pstmt.setString(i+5, "%");}
						}
						pstmt.setInt(10, startRow);
						pstmt.setInt(11, endRow);				
						rset = pstmt.executeQuery();
						while(rset.next()) {
							list.add(new Research(rset.getInt("MEM_NO"),
									rset.getString("MEM_NAME"),
									rset.getString("SITTER_TITLE"),
								    rset.getString("ADDITIONS"),
								    String.format("%,d",rset.getInt("OSMALL")),
								    String.format("%,d",rset.getInt("DSMALL")),
								    rset.getInt("AVGRATING"),
								    rset.getString("PATH"),
								    rset.getInt("DATE_COUNT"),
								    rset.getDouble("DISTANCE")
									));
								    }
					} catch (SQLException e) {
						e.printStackTrace();
					} finally {
						close(rset);
						close(pstmt);
					}
					
					return list;

					
				}
		
		
	
	//searchPetsitterDetail.jsp에 보여줄 화면 구성
	public Research searchSitterDetail(Connection conn, int sitterNo, int memNo) {
		
		Research sitterInfo = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("searchSitterDetail");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, memNo);
			pstmt.setInt(2, sitterNo);
			pstmt.setInt(3, sitterNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				sitterInfo =new Research(rset.getInt("MEM_NO"),
						rset.getString("MEM_NAME"),
						rset.getString("MEM_ADDRESS"),
					    rset.getString("LICENSE"),
					    rset.getString("PET_PERIOD"),
					    rset.getString("EXPERIENCE"),
					    rset.getString("SITTER_TITLE"),
					    rset.getString("SITTER_CONTENT"),
					    rset.getString("ADDITIONS"),
					    String.format("%,d",rset.getInt("OSMALL")),
					    String.format("%,d",rset.getInt("OMID")),
					    String.format("%,d",rset.getInt("OBIG")),
					    String.format("%,d",rset.getInt("DSMALL")),
					    String.format("%,d",rset.getInt("DMID")),
					    String.format("%,d",rset.getInt("DBIG")),
					    rset.getInt("AVGRATING"),
					    rset.getInt("SITTER_CHECKIN"),
					    rset.getInt("SITTER_CHECKOUT"),
					    rset.getString("ABLE_DATE"),
					    rset.getString("FILE_LIST"),
					    rset.getInt("FAVOR")
						);
					    }
	
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return sitterInfo;
		
	}
	
	//searchPetsitterDetail.jsp에서 특정 펫시터를 검색한 유저와 펫의 정보
	public ArrayList<Pet> memPetInfo(Connection conn, int sitterNo, int userNo) {
		ArrayList<Pet> list = new ArrayList<>();
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("memPetInfo");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, sitterNo);
			pstmt.setInt(2, userNo);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Pet(rset.getInt("PET_NO"),
						rset.getInt("MEM_NO"),
						rset.getString("pet_name"),
						rset.getString("pet_birth"),
					    rset.getString("pet_gender"),
					    rset.getString("pet_size"),
					    rset.getString("FAVORITE"),
					    rset.getString("pet_img")
						));
					    }
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}

		return list;
	}
	
	public int reviewListCount(Connection conn, int sitterNo) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("reviewListCount");
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, sitterNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return listCount;
		
	}
		
	
	
	//searchPetsitterDetail.jsp에서 특정 펫시터의 리뷰정보
		public ArrayList<Review> sitterReview(Connection conn, int sitterNo, String array) {
			ArrayList<Review> list = new ArrayList<>();
			ResultSet rset = null;
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("reviewList");
			if(array.equals("recent")) {
				sql+="order by rev_date desc) R )WHERE RNUM BETWEEN 1 AND 3";
			} else {
				sql+="order by REV_RATING desc) R )WHERE RNUM BETWEEN 1 AND 3";	
			}
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sitterNo);
				rset = pstmt.executeQuery();
				while(rset.next()) {
					list.add(new Review(rset.getInt("MEM_NO"),
							rset.getInt("sitter_no"),
							rset.getString("mem_name"),
							rset.getInt("REV_RATING"),
						    rset.getString("rev_content"),
						    rset.getString("REV_DATE"),
						    rset.getString("path"),
						    rset.getString("rev_co_content")
							));
						    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}
			return list;
		}
		
		//searchPetsitterDetail.jsp에서 특정 펫시터의 리뷰정보Ajax
		public ArrayList<Review> sitterReviewAjax(Connection conn, int sitterNo, int startRow, int endRow, String array) {
			ArrayList<Review> list = new ArrayList<>();
			ResultSet rset = null;
			PreparedStatement pstmt = null;
			String sql = prop.getProperty("reviewList");
			if(array.equals("recent")) {
				sql+="order by rev_date desc";
			} else {
				sql+="order by REV_RATING desc";	
			}
			sql+=") R )WHERE RNUM BETWEEN ? AND ?";
			try {
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, sitterNo);
				pstmt.setInt(2, startRow);
				pstmt.setInt(3, endRow);	
				rset = pstmt.executeQuery();
				while(rset.next()) {
					list.add(new Review(rset.getInt("MEM_NO"),
							rset.getInt("sitter_no"),
							rset.getString("mem_name"),
							rset.getInt("REV_RATING"),
						    rset.getString("rev_content"),
						    rset.getString("REV_DATE"),
						    rset.getString("path"),
						    rset.getString("rev_co_content")
							));
						    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(rset);
				close(pstmt);
			}

			return list;
		}

}
