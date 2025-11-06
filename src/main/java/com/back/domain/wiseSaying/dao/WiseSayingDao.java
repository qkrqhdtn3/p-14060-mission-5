package com.back.domain.wiseSaying.dao;

import com.back.domain.wiseSaying.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WiseSayingDao {

    public boolean insertWiseSaying(int id, String content, String author){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "INSERT INTO wise_sayings (id, content, author) VALUES (?, ?, ?)";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, content);
            pstmt.setString(3, author);
            int rowsAffected = pstmt.executeUpdate();
            flag = rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return flag;
    }

    public boolean deleteWiseSaying(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "DELETE FROM wise_sayings WHERE id = ?";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            flag = rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return flag;
    }

    public boolean updateWiseSaying(int id, String content, String author){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "UPDATE wise_sayings SET content = ?, author = ? WHERE id = ?";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, content);
            pstmt.setString(2, author);
            pstmt.setInt(3, id);
            int rowsAffected = pstmt.executeUpdate();
            flag = rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return flag;
    }
}
