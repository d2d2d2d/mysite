package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<BoardVo> getList(){
		return sqlSession.selectList("board.getList");
	}

	public int insert(BoardVo vo) {		
		return sqlSession.insert("board.insert", vo);
	}


	public int update(BoardVo vo) {
		return sqlSession.update("board.update", vo);
	}


	public int delete( Long no ) {
		return sqlSession.delete("board.delete", no);
	}


	public BoardVo findView(Long no) {
		return sqlSession.selectOne("board.findView", no);
	}	


	public List<BoardVo> paging(int from, int rowSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("from", from);
		map.put("rowSize", rowSize);
		return sqlSession.selectList("board.paging", map);
	}


	public int getTotalBoard() {
		return sqlSession.selectOne("board.getTotalBoard");
	}

	public int replyUpdate(BoardVo vo) {
		return sqlSession.insert("board.replyUpdate", vo);
	}


	public int replyInsert(BoardVo vo) {
		return sqlSession.insert("board.replyInsert", vo);
	}


	public BoardVo findNo(Long no) {
		BoardVo vo = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select g_no, o_no, dept" + 
					" from board" + 
					" where no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong( 1, no );
			rs = pstmt.executeQuery();

			if(rs.next()) {
				int gNo = rs.getInt(1);
				int oNo = rs.getInt(2);
				int dept = rs.getInt(3);

				vo.setNo(no);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDept(dept);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return vo;
	}


	public int hitUpdate(BoardVo vo) {
		return sqlSession.update("board.hitUpdate", vo);
	}
	
	
	public List<BoardVo> search(String kwd, int from, int rowSize) {
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), "
					+ "g_no, o_no, dept, u.name, u.no" + 
					" from board b, user u" + 
					" where b.user_no = u.no" + 
					" and contents LIKE ?" +
					" order by g_no desc, o_no asc" +
					" limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString( 1, '%'+kwd+'%' );
			pstmt.setLong( 2, from );
			pstmt.setLong( 3, rowSize );
			rs = pstmt.executeQuery();

			//5. 결과 가져오기
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int gNo = rs.getInt(6);
				int oNo = rs.getInt(7);
				int dept = rs.getInt(8);
				String name = rs.getString(9);
				Long userNo = rs.getLong(10);

				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDept(dept);
				vo.setName(name);
				vo.setUserNo(userNo);

				list.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			//6. 자원정리
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
		}
		return list;
		
	}


	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			//1. 드라이버 로딩
			Class.forName( "com.mysql.jdbc.Driver" );

			//2. 연결하기
			String url="jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			System.out.println( "드러이버 로딩 실패:" + e );
		} 

		return conn;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getCount(String kwd) {
		// TODO Auto-generated method stub
		return 0;
	}

}
