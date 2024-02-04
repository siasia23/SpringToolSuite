package com.oracle.oBootBoard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;

import com.oracle.oBootBoard.dto.BDto;

// DAO class (DML Logic 구현)
// DB랑 연동해서 데이터 처리 로직 수행함!

// @Configuration에 @Bean 달아둬서 여기에서는 @Repository 안 달아도 됨
public class JdbcDao implements BDao {

	// JDBC : Java DataBase Connectivity (API)
	// JDBC 사용 : DB 연결에 가장 기본적임. DataSource 사용해야 함
	
	// DataSource : DriverManager 대신 사용되는거
		// A factory for connections to the physical data source that this DataSource object represents.
		// An alternative to the DriverManager facility, a DataSource object is the preferred means of getting a connection.
		// .yml에서 부여한 datasource 정보가 모두 들어감!
	
	// 한 번 설정하고 다시는 바꾸지 않으려고 final로 선언 (DB 연동에 필요한 정보는 한 번만 넣어주면 되니까!)
	private final DataSource dataSource;
	
	public JdbcDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		// DataSourceUtils : Helper class that provides static methods for obtaining JDBC Connections from a javax.sql.DataSource.
		return DataSourceUtils.getConnection(dataSource);
	}
	
	// list
	// 게시판 목록
	@Override
	public ArrayList<BDto> boardList() {

		ArrayList<BDto> bList = new ArrayList<BDto>();
		Connection conn = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		
		System.out.println("JdbcDao boardList() start...");
		
		// 댓글 순서 위해서 order by
		String sql = "select * from mvc_board order by bGroup desc, bStep asc";
		System.out.println("JdbcDao boardList() Query : " + sql);
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			// pk가 아니면 while문
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
				
				// constructor 방식으로 data 저장
				BDto dto = new BDto(bId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
				bList.add(dto);	// List 형태로 data 누적시킴
				
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

	// write_view
	// 새 게시글 작성하기
	@Override
	public void write(String bName, String bTitle, String bContent) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		// 1. insert into mvc_board
		String sql = "insert into mvc_board values(mvc_board_seq.nextval, ?, ?, ?, sysdate, 0, mvc_board_seq.currval, 0, 0)";
		
			// nextval, currval은 oracle 내장 함수
		// currval : nextval 실행된 후의(값이 이미 변경된 후의) 현재 값
			// 그래서 nextval이 무조건!! 선행되어야 함.
			// nextval 없는 currval은 존재하지 않음!!.
		
		// 2. PreparedStatement 방식으로
		// 3. mvc_board_seq
		// 4. bId , bGroup 같게 (그래야 댓글 그룹이 같아짐)
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
			System.out.println("write dataSource : " + e.getMessage());
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

		upHit(bId);
		
		BDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select bId, bHit, bName, bTitle, bContent from mvc_board where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao contentView Query : " + sql);
			
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
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


	// 게시글 클릭하면 조회수 증가
	private void upHit(int bId) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update mvc_board set bHit = bHit+1 where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao upHit Query : " + sql);
			
			pstmt.setInt(1, bId);
			
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

	// content_view에서
	// 작성된 게시글 수정하기
	@Override
	public void modify(int bId, String bName, String bTitle, String bContent) {

		Connection conn = null;
		PreparedStatement pstmt = null;

		String sql = "update mvc_board set bName=?, bTitle=?, bContent=? where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao modify Query : " + sql);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bId);
			
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

	
	// reply_view
	// 댓글 입력 창
	@Override
	public BDto reply_view(int bId) {

		BDto dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "select * from mvc_board where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao reply_view Query : " + sql);
			
			pstmt.setInt(1, bId);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				int sbId = rs.getInt("bId");
				int bHit = rs.getInt("bHit");
				int bGroup = rs.getInt("bGroup");
				int bStep = rs.getInt("bStep");
				int bIndent = rs.getInt("bIndent");
				String bName = rs.getString("bName");
				String bTitle = rs.getString("bTitle");
				String bContent = rs.getString("bContent");
				Timestamp bDate = rs.getTimestamp("bDate");
				
				dto = new BDto(sbId, bName, bTitle, bContent, bDate, bHit, bGroup, bStep, bIndent);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			try {
				if (rs != null) rs.close();
				if (pstmt != null) pstmt.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		return dto;
		
	}

	
	// reply_view
	// 댓글 쓰기
	@Override
	public void reply(int bId, String bName, String bTitle, String bContent, int bGroup, int bStep, int bIndent) {

//	    [1] bId SEQUENCE = bGroup 
//	    [2] bName, bTitle, bContent -> request Value
		
//	    [3] 홍해 기적
		// 대댓글
		replyShape(bGroup, bStep);
		
//	    [4] bStep / bIndent   + 1
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into mvc_board values(MVC_BOARD_SEQ.nextval, ?, ?, ?, sysdate, 0, ?, ?, ?)";
//		String sql = "insert into mvc_board (bId, bName, bTitle, bContent, bGroup, bStep, bIndent "
//						+ "values (MVC_BOARD_SEQ.nextval, ?, ?, ?, ?, ?, ?)";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao reply Query : " + sql);
			
			pstmt.setString(1, bName);
			pstmt.setString(2, bTitle);
			pstmt.setString(3, bContent);
			pstmt.setInt(4, bGroup);
			pstmt.setInt(5, bStep + 1);
			pstmt.setInt(6, bIndent + 1);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("reply dataSource : " + e.getMessage());
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

	// reply_view
	// 대댓글 로직 : bStep + 1 시키기
	private void replyShape(int bGroup, int bStep) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update mvc_board set bStep = bStep + 1 where bGroup =? and bStep >?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao replyShape Query : " + sql);
			
			pstmt.setInt(1, bGroup);
			pstmt.setInt(2, bStep);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("replyShape dataSource : " + e.getMessage());
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

	// 글 삭제
	@Override
	public void delete(int bId) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "delete from mvc_board where bId=?";
		
		try {
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("JdbcDao delete Query : " + sql);
			
			pstmt.setInt(1, bId);
			
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

}
