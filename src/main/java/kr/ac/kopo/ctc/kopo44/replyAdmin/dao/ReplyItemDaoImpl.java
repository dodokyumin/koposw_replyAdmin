package kr.ac.kopo.ctc.kopo44.replyAdmin.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.kopo.ctc.kopo44.replyAdmin.domain.ReplyItem;

public class ReplyItemDaoImpl implements ReplyItemDao {

	public ReplyItemDaoImpl() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("jdbc 드라이버 로드 실패");
		}
	}

	@Override
	public int createOne(ReplyItem ReplyItem) {
		String sql = "INSERT INTO " + TABLE_NAME
				+ "(title, date, content, rootid, relevel, recnt, viewcnt) VALUES (?, ?, ?, ?, ?, ?, ?)";
		int result;

		int lastId = getLastId() + 1;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setString(1, ReplyItem.getTitle());
			pstmt.setString(2, ReplyItem.getDate());
			pstmt.setString(3, ReplyItem.getContent());
			pstmt.setInt(7, ReplyItem.getViewcnt());

			pstmt.setInt(6, ReplyItem.getRecnt());
			pstmt.setInt(5, ReplyItem.getRelevel());
			// 유동적 조절
			pstmt.setInt(4, lastId);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// e만 넣으면 hashcode가 나오므로 getMessage()를 붙여준다.
			throw new IllegalStateException("insert 실패 " + e.getMessage());
		}
		return result;
	}

	@Override
	public ReplyItem readOne(int inputId) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

		ReplyItem replyItem = new ReplyItem();
		// 조회수 1증가
		plusViewcnt(inputId);

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, inputId);
			try (ResultSet rs = pstmt.executeQuery();) {

				rs.next();
				int id = rs.getInt("id");
				String title = rs.getString("title");
				String date = rs.getString("date");
				String content = rs.getString("content");
				int rootid = rs.getInt("rootid");
				int relevel = rs.getInt("relevel");
				int recnt = rs.getInt("recnt");
				int newViewcnt = rs.getInt("viewcnt");

				replyItem.setId(id);
				replyItem.setTitle(title);
				replyItem.setDate(date);
				replyItem.setContent(content);
				replyItem.setRootid(rootid);
				replyItem.setRelevel(relevel);
				replyItem.setRecnt(recnt);
				replyItem.setViewcnt(newViewcnt);
			}

		} catch (SQLException e) {
			// e만 넣으면 hashcode가 나오므로 getMessage()를 붙여준다.
			throw new IllegalStateException("insert 실패 " + e.getMessage());
		}
		return replyItem;
	}

	public void plusViewcnt(int inputId) {
		String sql = "UPDATE " + TABLE_NAME + " SET viewcnt = viewcnt + 1 WHERE id = ?";

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setInt(1, inputId);

			pstmt.executeUpdate();

		} catch (SQLException e) {

			throw new IllegalStateException("조회수 1증가 실패" + e.getMessage());
		}
		return;
	}

	@Override
	public List<ReplyItem> readAll(int startIndex, int countPerPage) {
		List<ReplyItem> results = new ArrayList<>();
		String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ROOTID + " DESC, " + COLUMN_RECNT
				+ " LIMIT ?, ?";
		// TRY RESOURCE CATCH문
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, startIndex - 1);
			pstmt.setInt(2, countPerPage);
			try (ResultSet rs = pstmt.executeQuery();) {

				while (rs.next()) {
					int id = rs.getInt("id");
					String title = rs.getString("title");
					String date = rs.getString("date");
					String content = rs.getString("content");
					int rootid = rs.getInt("rootid");
					int relevel = rs.getInt("relevel");
					int recnt = rs.getInt("recnt");
					int viewcnt = rs.getInt("viewcnt");

					ReplyItem replyItem = new ReplyItem();

					replyItem.setId(id);
					replyItem.setTitle(title);
					replyItem.setDate(date);
					replyItem.setContent(content);
					replyItem.setRootid(rootid);
					replyItem.setRelevel(relevel);
					replyItem.setRecnt(recnt);
					replyItem.setViewcnt(viewcnt);

					results.add(replyItem);
				}
			}
		} catch (SQLException e) {
			throw new IllegalStateException("db 연결 실패" + e.getMessage());
		}
		return results;
	}

	@Override
	public ReplyItem updateOne(ReplyItem replyItem) {
		String sql = "UPDATE " + TABLE_NAME + " SET title=?, content=?, WHERE id = ?";

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setString(1, replyItem.getTitle());
			pstmt.setString(2, replyItem.getDate());
			pstmt.setString(3, replyItem.getContent());
			pstmt.setInt(4, replyItem.getId());

			pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return replyItem;
	}

	@Override
	public int deleteOne(int id) {
		String sql = "delete from " + TABLE_NAME + " where id=?";
		int result = 0;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, id);
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new IllegalStateException("db 연결 실패" + e.getMessage());
		}

		return result;
	}

	@Override
	public int RowCount() {
		int rowcount = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery("SELECT COUNT(*) FROM " + TABLE_NAME);) {

			while (rset.next()) {
				rowcount = rset.getInt(1);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("count 실패 " + e.getMessage());
		}
		return rowcount;
	}

	@Override
	public int deleteAll() {
		String sql = "delete from " + TABLE_NAME + " where id > 0";
		int result = 0;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new IllegalStateException("db 연결 실패" + e.getMessage());
		}

		return result;
	}

	@Override
	public int getViewcnt(int inputId) {
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";
		int viewcnt = 0;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			pstmt.setInt(1, inputId);
			try (ResultSet rs = pstmt.executeQuery();) {

				rs.next();
				viewcnt = rs.getInt("viewcnt");
			}

		} catch (SQLException e) {
			// e만 넣으면 hashcode가 나오므로 getMessage()를 붙여준다.
			throw new IllegalStateException("조회수 읽어오기 실패 " + e.getMessage());
		}
		return viewcnt;
	}

	@Override
	public int getLastId() {
		int lastId = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery("SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME);) {

			while (rset.next()) {
				lastId = rset.getInt(1);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("count 실패 " + e.getMessage());
		}
		return lastId;
	}

	@Override
	public int createReplyOne(ReplyItem replyItem) {
		String sql = "INSERT INTO " + TABLE_NAME
				+ "(title, date, content, rootid, relevel, recnt, viewcnt) VALUES (?, ?, ?, ?, ?, ?, ?)";
		int result;

		// 댓글 제목에 -> 붙이기
		String retitle = addReplyMark(replyItem.getRelevel()) + replyItem.getTitle();

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {

			pstmt.setString(1, retitle);
			pstmt.setString(2, replyItem.getDate());
			pstmt.setString(3, replyItem.getContent());
			pstmt.setInt(4, replyItem.getRootid());
			pstmt.setInt(5, replyItem.getRelevel());
			pstmt.setInt(7, replyItem.getViewcnt());

			// 유동적 조절
			pstmt.setInt(6, replyItem.getRecnt());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// e만 넣으면 hashcode가 나오므로 getMessage()를 붙여준다.
			throw new IllegalStateException("insert 실패 " + e.getMessage());
		}
		return result;
	}

	@Override
	public ArrayList<Integer[]> findRecnt(int rootid, int MomRecnt) {
		ArrayList<Integer[]> rootidPack = new ArrayList<Integer[]>();
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt.executeQuery("SELECT relevel, recnt FROM " + TABLE_NAME + " WHERE rootid = "
						+ rootid + " AND " + COLUMN_RECNT + " >= " + MomRecnt + " ORDER BY recnt");) {

			while (rset.next()) {
				int relevel = rset.getInt(1);
				int recnt = rset.getInt(2);
				rootidPack.add(new Integer[] { relevel, recnt });
			}
		} catch (SQLException e) {
			throw new IllegalStateException("count 실패 " + e.getMessage());
		}

		return rootidPack;
	}

	// 서비스 단으로 가야함. 수정 필요
	private String addReplyMark(int relevel) {
		String addMark = "";
		for (int i = 0; i < relevel; i++) {
			addMark = addMark + "👉";
		}
		return addMark;
	}

	private int getLastRecnt(int rootid) {
		int lastRecnt = 0;
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				Statement stmt = conn.createStatement();
				ResultSet rset = stmt
						.executeQuery("SELECT MAX(recnt) FROM " + TABLE_NAME + " WHERE rootid = " + rootid);) {

			while (rset.next()) {
				lastRecnt = rset.getInt(1);
			}
		} catch (SQLException e) {
			throw new IllegalStateException("count 실패 " + e.getMessage());
		}
		return lastRecnt;
	}

	@Override						//			2			2
	public void pushBackRecnt(int rootid, int relevel, int recnt) {
		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate("UPDATE " + TABLE_NAME + " set " + COLUMN_RECNT + " = " + COLUMN_RECNT + " + 1 where "
					+ COLUMN_ROOTID + " = " + rootid + " AND " + COLUMN_RECNT + " >= " + recnt + " ORDER BY "
					+ COLUMN_RECNT + " DESC");
//			stmt.executeUpdate("UPDATE " + TABLE_NAME + " set " + COLUMN_RECNT + " = " + COLUMN_RECNT + " + 1 where "
//					+ COLUMN_RECNT + " >= " + (recnt) + " ORDER BY " + COLUMN_ID + " DESC");
		} catch (SQLException e) {
			throw new IllegalStateException("id뒤로 밀기 실패 " + e.getMessage());
		}
	}

	@Override
	public int deleteLowerLevels(int rootid, int start, int end) {
		String sql = "delete from " + TABLE_NAME + " where rootid = " + rootid + " AND " + COLUMN_RECNT + ">" + start
				+ " AND " + COLUMN_RECNT + "<" + end;
		int result = 0;

		try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:33063/koposw44", "root", "koposw44");
				PreparedStatement pstmt = conn.prepareStatement(sql);) {
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			throw new IllegalStateException("db 연결 실패" + e.getMessage());
		}

		return result;
	}

}
