package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;

public class JdbcDao implements BDao {

	// JDBC 사용
	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
	}
	
	// 전체 게시글 화면에 뿌리기
	@Override
	public ArrayList<BDto> boardList() {

		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		System.out.println("JdbcDao boardList() start...");
		
		// 과제
		
		String sql = "select * from mvc_board order by bGroup desc, bStep asc";
		System.out.println("JdbcDao query : " + sql);
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int bId = rs.getInt("bId");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
				bList.add(dto);
				
			}
			
		} catch (SQLException e) {
			System.out.println("list dataSource : " + e.getMessage());
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return bList;
		
	}

	// 새 게시글 작성하기
	@Override
	public void write(String bName, String bTitle, String bContent) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// HW1
		
		// 1. insert into mvc_board
		String sql = "insert into mvc_board values(mvc_board_seq.nextval, ?, ?, ?, sysdate, 0, mvc_board_seq.nextval, 0, 0)";
		
		// 2. PreparedStatement 방식으로
		// 3. mvc_board_seq
		// 4. bId , bGroup 같게
		// 5. bStep, bIndent, bDate --> 0, 0 , sysdate
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao write Query : " + sql);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

	// content_view
	// 게시글 클릭하면 해당 내용 뿌리기
	@Override
	public BDto contentView(int bId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BDto dto = null;
		
		String sql = "select bId, bHit, bName, bTitle, bContent from mvc_board where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao contentView Query : " + sql);
			
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int bHit = rs.getInt("bHit");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				
				dto = new BDto();
				
				dto.setbId(bId);
				dto.setbHit(bHit);
				dto.setbName(bName);
				dto.setbTitle(bTitle);
				dto.setbContent(bContent);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return dto;
	}

}
