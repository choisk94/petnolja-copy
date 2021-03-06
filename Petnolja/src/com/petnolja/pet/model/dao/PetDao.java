package com.petnolja.pet.model.dao;

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
import com.petnolja.memreserve.model.vo.ReserveContent;
import com.petnolja.pet.model.vo.Pet;
import com.petnolja.pet.model.vo.PetLogList;

public class PetDao {
   
   private Properties prop = new Properties();
   
   public PetDao() {
      
      String fileName = PetDao.class.getResource("/sql/pet/pet-mapper.xml").getPath();
      
      try {
         prop.loadFromXML(new FileInputStream(fileName));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   
   /** 박정빈
    *  반려견 등록
    */
   public int insertPet(Connection conn, Pet p) {
      // insert문
      int result = 0;
      PreparedStatement pstmt = null;
      String sql = prop.getProperty("insertPet");
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, p.getMemNo());
         pstmt.setString(2, p.getPetName());
         pstmt.setString(3, p.getPetGender());
         pstmt.setString(4, p.getPetBreed());
         pstmt.setString(5, p.getPetBirth());
         pstmt.setDouble(6, p.getPetWeight());
         pstmt.setString(7, p.getNeutered());
         pstmt.setString(8, p.getPetSize());
         pstmt.setString(9, p.getChip());
         pstmt.setString(10, p.getVaccine());
         pstmt.setString(11, p.getCaution());
         pstmt.setString(12, p.getNote());
         pstmt.setString(13, p.getHospi());
         pstmt.setString(14, p.getHospiTel());
         pstmt.setString(15, p.getPetImg());
         
         result = pstmt.executeUpdate();
      
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(pstmt);
      }
      
      return result;
   }
   
   /** 박정빈
    *  펫정보 조회
    */
   public Pet selectPet(Connection conn, int petNo){
      // select문 => ResultSet (여러행)
      Pet p = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String sql = prop.getProperty("selectPet");
      
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, petNo);
         
         rset = pstmt.executeQuery();
         
      if(rset.next()) {
         p = new Pet(rset.getInt("pet_No"),
                  rset.getString("pet_Name"),
                  rset.getString("pet_Birth"),
                  rset.getDouble("pet_Weight"),
                  rset.getString("pet_Gender"),
                  rset.getString("pet_Breed"),
                  rset.getString("pet_Img"),
                  rset.getString("vaccine"),
                  rset.getString("caution"),
                  rset.getString("note"),
                  rset.getString("hospi"),
                  rset.getString("hospi_Tel"),
                   rset.getString("neutered"),
                  rset.getString("chip"));
      }
         
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(rset);
         close(pstmt);
      }
      
      return p;
   }
   
   /** 최서경
    * 특정 회원의 펫 목록 조회
    */
   public ArrayList<Pet> selectPetList(Connection conn, int memNo){
      ArrayList<Pet> petList = new ArrayList<>();
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String sql = prop.getProperty("selectPetList");
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, memNo);
         
         rset = pstmt.executeQuery();
         
         while(rset.next()) {
            petList.add(new Pet(rset.getInt("pet_no"),
                           rset.getString("pet_name")));
            
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(rset);
         close(pstmt);
      }
      return petList;
         
      }

   /** 박정빈
    *  알겠습니다.
    */
   public ArrayList<Pet> petList(Connection conn, int userNo) {
      ArrayList<Pet> list = new ArrayList<>();
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String sql = prop.getProperty("petList");
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, userNo);
         rset = pstmt.executeQuery();
         while (rset.next()) {
            list.add(new Pet(
               rset.getInt("pet_no"),
               rset.getString("pet_name"),         
               rset.getString("pet_birth"),
               rset.getDouble("pet_weight"),
               rset.getString("pet_gender"),
               rset.getString("pet_size"),
               rset.getString("pet_breed"),
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
   
   /** 최서경
    * 회원의 펫 목록 -> 클릭하여 펫 상세정보 조회
    */
   public Pet selectAdminPetDetail(Connection conn, int petNo) {
      
      Pet p = null;
      PreparedStatement pstmt = null;
      ResultSet rset = null;
      String sql = prop.getProperty("selectAdminPetDetail");
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, petNo);
         rset = pstmt.executeQuery();
         
         if(rset.next()) {
            p = new Pet(rset.getString("pet_birth"),
                     rset.getInt("pet_weight"),
                     rset.getString("pet_gender"),
                     rset.getString("pet_size"),
                     rset.getString("pet_breed"),
                     rset.getString("pet_img"),
                     rset.getString("vaccine"),
                     rset.getString("caution"),
                     rset.getString("note"),
                     rset.getString("hospi"),
                     rset.getString("hospi_tel"),
                     rset.getString("neutered"),
                     rset.getString("chip"));
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(rset);
         close(pstmt);
      }
      
      return p;
   }

   /** 최서경
    * 회원의 펫 목록 -> 펫정보 삭제
    */   
   public int adminDeletePet(Connection conn, String[] petList) {
      int result = 0;
      PreparedStatement pstmt = null;
      String sql = prop.getProperty("adminDeletePet");
      
      sql += "WHERE PET_NO IN(" + String.join(",", petList) + ")";
      
      try {
         pstmt = conn.prepareStatement(sql);
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(pstmt);
      }
      return result;
   }
   /** 박정빈
    * 반려견 목록 > 반려견 수정
    */
   public int updatePet(Connection conn, Pet p) {
         // update문
         int result = 0;
         PreparedStatement pstmt = null;
         String sql = prop.getProperty("updatePet");
         try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, p.getPetName());
            pstmt.setString(2, p.getPetGender());
            pstmt.setString(3, p.getPetBreed());
            pstmt.setString(4, p.getPetBirth());
            pstmt.setDouble(5, p.getPetWeight());
            pstmt.setString(6, p.getNeutered());
            pstmt.setString(7, p.getChip());
            pstmt.setString(8, p.getPetImg());
            pstmt.setString(9, p.getPetSize());
            pstmt.setString(10, p.getVaccine());
            pstmt.setString(11, p.getCaution());
            pstmt.setString(12, p.getNote());
            pstmt.setString(13, p.getHospi());
            pstmt.setString(14, p.getHospiTel());
            pstmt.setInt(15, p.getPetNo());
            
            result = pstmt.executeUpdate();
            
         } catch (SQLException e) {
            e.printStackTrace();
         } finally {
            close(pstmt);
         }
         
         return result;
      }
   /** 박정빈
    *  반려견 목록 삭제 기능
    */
   public int deletePet(Connection conn, int petNo, String petImg) {
      // update문
      int result = 0;
      PreparedStatement pstmt = null;
      String sql = prop.getProperty("deletePet");
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, petNo);
         pstmt.setString(2, petImg);
         
         result = pstmt.executeUpdate();
         
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         close(pstmt);
      }
      return result;
   }
   /** 박정빈
    *  회원이 펫시터 예약리스트를 조회할때 목록 갯수 뽑기
    */
   public int dailyLogCount(Connection conn, int memNo, String startDate, String endDate) {
	   // select문
	   int listCount = 0;
	   PreparedStatement pstmt = null;
	   ResultSet rset = null;
	   String sql = prop.getProperty("dailyLogCount");
	   try {
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, memNo);
		pstmt.setDate(2, java.sql.Date.valueOf(startDate));
        pstmt.setDate(3, java.sql.Date.valueOf(endDate));
		
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
   
   /** 박정빈
    * 펫 일지 보기 관련 
    */
   public ArrayList<PetLogList> dailyLog(Connection conn, int memNo, String startDate, String endDate, PageInfo pi){
      // select문
      ArrayList<PetLogList> list = new ArrayList<>();
      ResultSet rset = null;
      PreparedStatement pstmt = null;
      String sql = prop.getProperty("dailyLog");
      int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1;
	  int endRow = startRow + pi.getBoardLimit() - 1;
      
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, memNo);
         pstmt.setDate(2, java.sql.Date.valueOf(startDate));
         pstmt.setDate(3, java.sql.Date.valueOf(endDate));
         pstmt.setInt(4, startRow);
		 pstmt.setInt(5, endRow);
         
         rset = pstmt.executeQuery();
         while(rset.next()) {
            list.add(new PetLogList(rset.getInt("MEM_NO"),
                              rset.getInt("SITTER_NO"),
                              rset.getString("SITTERNAME"),
                              rset.getInt("LOG_NO"),
                              rset.getLong("RES_NO"),
                              rset.getDate("LOG_TIME"),
                              rset.getString("LOG_CONTENT"),
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
   
}   

	
