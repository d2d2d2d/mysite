package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;


public class BoardRepository {
	public List<BoardVo> getList(){
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, dept, u.name" + 
					" from board b, user u" + 
					" where b.user_no = u.no" + 
					" order by g_no desc, o_no asc";
			pstmt = conn.prepareStatement(sql);

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


	public int insert(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert" + 
					" into board(title, contents, hit, reg_date, g_no, o_no, dept, user_no)" + 
					" values(?, ?, ?, now(),"
					+ " (select ifnull(max(b.g_no), 0)+1 from board b),"
					+ " 1, 0, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, 0);
			pstmt.setLong(4, vo.getUserNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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


	public int update(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board" + 
					" set title=?, contents=?, reg_date=now()" + 
					" where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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


	public int delete( Long no ) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = 
					"delete" + 
							" from board" + 
							" where no=?";
			pstmt = conn.prepareStatement( sql );

			pstmt.setLong( 1, no );

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error :" + e);
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


	public BoardVo findView(Long no) {
		BoardVo vo = new BoardVo();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select title, contents, user_no" + 
					" from board" + 
					" where no = ?";
			pstmt = conn.prepareStatement(sql);	// prepareStatement에서 해당 sql을 미리 컴파일한다.
			pstmt.setLong( 1, no );
			rs = pstmt.executeQuery();	// 쿼리를 실행하고 결과를 ResultSet 객체에 담는다.

			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long userNo = rs.getLong(3);

				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserNo(userNo);
				vo.setNo(no);
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
		return vo;
	}	


	public List<BoardVo> paging(int from, int rowSize) {
		List<BoardVo> list = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select b.no, title, contents, hit, date_format(reg_date, '%Y-%m-%d %h:%i:%s'), g_no, o_no, dept, u.name, u.no" + 
					" from board b, user u" + 
					" where  b.user_no = u.no" + 
					" order by g_no desc, o_no asc"+
					" limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong( 1, from );
			pstmt.setLong( 2, rowSize );
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


	public int getTotalBoard() {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "Select count(*)"
					+ " from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while(rs.next()) {
				count = rs.getInt(1);
			}

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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

	public int replyUpdate(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board" + 
					" set o_no = o_no+1" + 
					" where g_no = ? "+
					" 		and o_no > ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getgNo());
			pstmt.setLong(2, vo.getoNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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


	public int replyInsert(BoardVo vo) {
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "insert" + 
					" into board(title, contents, hit, reg_date, g_no, o_no, dept, user_no)" + 
					" values(?, ?, ?, now(), ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, 0);
			pstmt.setLong(4, vo.getgNo());
			pstmt.setLong(5, vo.getoNo()+1);	//@@@전 board에서 +1씩
			pstmt.setLong(6, vo.getDept()+1);
			pstmt.setLong(7, vo.getUserNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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
		int count = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "update board" + 
					" set hit = hit+1" + 
					" where no = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getNo());

			count = pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			// 자원 정리
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
