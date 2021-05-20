package com.petnolja.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import static com.petnolja.common.JDBCTemplate.*;
import com.petnolja.member.model.vo.Member;

public class MemberDao {
	
private Properties prop = new Properties();
	
	public MemberDao() {
		
		String fileName = MemberDao.class.getResource("/sql/member/member-mapper.xml").getPath();
		
		try {
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		
		Member m = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("loginMember");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				m = new Member(
				rset.getInt("mem_no"),
				rset.getString("mem_id"),
				rset.getString("mem_name"),
				rset.getString("mem_pwd"),
				rset.getString("mem_tel"),
				rset.getString("mem_email"),
				rset.getString("mem_address"),
				rset.getString("mem_detail_address"),
				rset.getDate("mem_enrolldate"),
				rset.getString("mem_status"),
				rset.getDate("MEM_DEL_DATE"),
				rset.getString("MEM_DEL_DETAIL"),
				rset.getString("MEM_ADS"),
				rset.getString("MEM_BLOCK"),
				rset.getString("MEM_REPORT"),
				rset.getString("MEM_REPORT_DETAIL")
				);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		
		
		return m;
		
	}
	
	
	public String findId(Connection conn, Member m) {
		String findId = null;
		ResultSet rset = null;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("findId");
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getMemName());
			pstmt.setString(2, m.getMemEmail());
			pstmt.setString(3, m.getMemTel());
			rset=pstmt.executeQuery();
			if(rset.next()){
				findId = rset.getString("MEM_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return findId;

	}

}