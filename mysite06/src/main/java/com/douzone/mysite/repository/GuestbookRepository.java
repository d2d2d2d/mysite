package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> getList() {
		return sqlSession.selectList("guestbook.getList");
	}

	public int insert(GuestbookVo vo) {
		return sqlSession.insert("guestbook.insert", vo);
	}

	public int delete(long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		return delete(vo);
	}

	public int delete( GuestbookVo vo ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
					" delete" + 
							"   from guestbook" + 
							"  where no=?" +
							"    and password=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, vo.getNo() );
			pstmt.setString( 2, vo.getPassword() );

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new GuestbookRepositoryException(e.getMessage());
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;		
	}	

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName( "org.mariadb.jdbc.Driver" );
			String url="jdbc:mysql://192.168.1.116:3307/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch( ClassNotFoundException e ) {
			throw new GuestbookRepositoryException( "드러이버 로딩 실패:" + e );
		} 

		return conn;
	}


}
