package com.back.domain.wiseSaying.dao;

import com.back.domain.wiseSaying.entity.WiseSaying;
import com.back.domain.wiseSaying.util.DatabaseConnection;
import com.back.jdbc.core.JdbcTemplate;

//import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

// To do
// JdbcTemplate 구현필요
// DataSource 사용해 DB 연결

//JdbcTemplate jdbc 사용할 때 발생하는 반복 문제를 해결해줌
public class WiseSayingDao {
    //    private final JdbcTemplate jdbcTemplate;

    public WiseSayingDao(){
        makeSampleWiseSayingsIfNone();
    }

    private void makeSampleWiseSayingsIfNone(){
        List<WiseSaying> wiseSayings = findAll();
        if(wiseSayings.isEmpty()){
            for(int i=1; i<=10; i++){
                int lastId = getLastId() + 1;
                WiseSaying wiseSaying = new WiseSaying(lastId, "명언 " + i, "작자미상 " + i);
                insertWiseSaying(wiseSaying);
            }
        }
    }

    public boolean insertWiseSaying(WiseSaying wiseSaying){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "INSERT INTO wise_sayings (id, content, author) VALUES (?, ?, ?)";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, wiseSaying.getId());
            pstmt.setString(2, wiseSaying.getContent());
            pstmt.setString(3, wiseSaying.getAuthor());
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

    public boolean updateWiseSaying(WiseSaying wiseSaying){
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean flag = false;
        try {
            String sql = "UPDATE wise_sayings SET content = ?, author = ? WHERE id = ?";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, wiseSaying.getContent());
            pstmt.setString(2, wiseSaying.getAuthor());
            pstmt.setInt(3, wiseSaying.getId());
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

    public int getLastId(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int lastId = 0;
        try {
            String sql = "SELECT MAX(id) FROM wise_sayings";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return lastId;
    }

    public WiseSaying findById(int id){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        WiseSaying wiseSaying = null;
        try {
            String sql = "SELECT id, content, author FROM wise_sayings WHERE id = ?";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                wiseSaying = new WiseSaying(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("author")
                );
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return wiseSaying;
    }
    public List<WiseSaying> findAllByContent(String content){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WiseSaying> wiseSayings = new ArrayList<>();
        try {
            String sql = "SELECT id, content, author FROM wise_sayings WHERE content LIKE ? ORDER BY id DESC";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + content + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WiseSaying wiseSaying = new WiseSaying(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("author")
                );
                wiseSayings.add(wiseSaying);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return wiseSayings;
    }
    public List<WiseSaying> findAllByAuthor(String author){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WiseSaying> wiseSayings = new ArrayList<>();
        try {
            String sql = "SELECT id, content, author FROM wise_sayings WHERE author LIKE ? ORDER BY id DESC";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + author + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WiseSaying wiseSaying = new WiseSaying(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("author")
                );
                wiseSayings.add(wiseSaying);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return wiseSayings;
    }

    public List<WiseSaying> findAll(){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WiseSaying> wiseSayings = new ArrayList<>();
        try {
            String sql = "SELECT id, content, author FROM wise_sayings ORDER BY id DESC";
            conn = new DatabaseConnection().getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WiseSaying wiseSaying = new WiseSaying(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("author")
                );
                wiseSayings.add(wiseSaying);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                if(rs != null) rs.close();
                if(pstmt != null) pstmt.close();
                if(conn != null) conn.close();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return wiseSayings;
    }
}
