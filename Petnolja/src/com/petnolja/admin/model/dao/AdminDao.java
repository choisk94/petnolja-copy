package com.petnolja.admin.model.dao;

import static com.petnolja.common.JDBCTemplate.close;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import com.petnolja.admin.model.vo.Admin;
import com.petnolja.admin.model.vo.Deal;
import com.petnolja.common.model.vo.PageInfo;

public class AdminDao {
	
	private Properties prop = new Properties();
	
	public AdminDao() {
		
		String fileName = AdminDao.class.getResource("/sql/admin/admin-mapper.xml").getPath();

		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/** 최서경
	 * 관리자 로그인
	 */
	public Admin adminLogin(Connection conn, String adminId, String adminPwd) {
	
		Admin loginAdmin = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("adminLogin");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, adminId);
			pstmt.setString(2, adminPwd);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginAdmin = new Admin(rset.getInt("admin_no"),
									   rset.getString("admin_id"),
									   rset.getString("admin_pwd"),
									   rset.getString("admin_email"),
									   rset.getString("admin_phone"),
									   rset.getString("admin_status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return loginAdmin;
	}
	
	/** 최서경
	 * 확정된 거래내역 총 개수
	 */
	public int selectDealListCount(Connection conn) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectDealListCount");
		
		try {
			pstmt = conn.prepareStatement(sql);
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
	
	
	/** 최서경
	 * 거래목록 조회
	 */
	public ArrayList<Deal> selectDealList(Connection conn, PageInfo pi){
		
		int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() + 1;
		int endRow = startRow + pi.getBoardLimit() -1;
		
		ArrayList<Deal> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectDealList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Deal(rset.getInt("res_no")
						 		, rset.getString("mid") 	//고객 아이디
						 		, rset.getString("mem_id")	//시터 아이디
						 		, rset.getString("res_checkin")
						 		, rset.getDate("res_date")
						 		, rset.getString("res_status")
						 		, rset.getInt("pay_amount")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	/** 최서경
	 * 거래번호를 이용하여 검색
	 */
	public ArrayList<Deal> selectKeyDealList(Connection conn, PageInfo pi, String keyword){
		int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() + 1;
		int endRow = startRow + pi.getBoardLimit() -1;
		
		ArrayList<Deal> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectDealList");
		
		sql += "AND UPPER(RES_NO) =" + keyword;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Deal(rset.getInt("res_no")
						 		, rset.getString("mid") 	//고객 아이디
						 		, rset.getString("mem_id")	//시터 아이디
						 		, rset.getString("res_checkin")
						 		, rset.getDate("res_date")
						 		, rset.getString("res_status")
						 		, rset.getInt("pay_amount")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	
	/** 최서경
	 * 거래날짜를 이용하여 검색
	 */
	public ArrayList<Deal> selectKeyDateDealList(Connection conn, PageInfo pi, String date){
		int startRow = (pi.getCurrentPage() -1) * pi.getBoardLimit() + 1;
		int endRow = startRow + pi.getBoardLimit() -1;
		
		ArrayList<Deal> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectKeyDateDealList");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, date);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			
			rset = pstmt.executeQuery();
			while(rset.next()) {
				list.add(new Deal(rset.getInt("res_no")
						 		, rset.getString("mid") 	//고객 아이디
						 		, rset.getString("mem_id")	//시터 아이디
						 		, rset.getString("res_checkin")
						 		, rset.getDate("res_date")
						 		, rset.getString("res_status")
						 		, rset.getInt("pay_amount")));
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
