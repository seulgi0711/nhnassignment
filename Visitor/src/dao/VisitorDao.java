package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Message;
import service.DbUtil;

public class VisitorDao {
	Connection conn;
	
	public void setConnection(Connection conn){
		this.conn = conn;
	}
	
	public int insert(Connection conn, Message msg){
		PreparedStatement pstmt = null;
		String sql = "insert into visitor values (?, ?, ?, ?, ?, ?)";
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getId());
			pstmt.setString(2, msg.getEmail());
			pstmt.setString(3, msg.getPassword());
			pstmt.setString(4, msg.getMessage());
			pstmt.setString(5, msg.getCurDate());
			pstmt.setString(6, msg.getCorDate());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("메시지 등록 실패 : " + e.getMessage());
		} finally {
			DbUtil.close(pstmt);
		}
		
		return result;
	}
	
	public Message select(Connection conn, int messageId){
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select * from visitor where id = " + messageId;
		
		Message msg = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				msg = makeMessageFromResultSet(rs);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
		}
		
		return msg;
	}
	
	public List<Message> selectList(Connection conn) {
		Statement stmt = null;
		ResultSet rs = null;
		
		ArrayList<Message> messageList = null;
		
		String sql = "select * from visitor order by id desc";
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			messageList = new ArrayList<Message>();
			
			while (rs.next()) {
				Message newMsg = makeMessageFromResultSet(rs);
				messageList.add(newMsg);
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
		}
		
		return messageList;
	}
	
	public int selectCount(Connection conn){
		Statement stmt = null;
		ResultSet rs = null;
		
		String sql = "select count(*) from visitor";
		
		int count = -1;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(stmt);
			DbUtil.close(rs);
		}
		
		return count;
	}

	public int update(Connection conn, Message msg){
		PreparedStatement pstmt = null;
		String sql = "update visitor set email=?, message=?, corDate=? where id=?";
		int result = -1;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg.getEmail());
			pstmt.setString(2, msg.getMessage());
			pstmt.setString(3, msg.getCorDate());
			pstmt.setString(4, msg.getId());
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("업데이트 실패" + e.getMessage());
		} finally {
			DbUtil.close(pstmt);
		}
		
		return result;
	}
	
	protected Message makeMessageFromResultSet(ResultSet rs){
		Message msg = new Message();

		try {
			msg.setId(rs.getString("id"));
			msg.setEmail(rs.getString("email"));
			msg.setPassword(rs.getString("password"));
			msg.setMessage(rs.getString("message"));
			msg.setCurDate(rs.getString("curDate"));
			msg.setCorDate(rs.getString("corDate"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return msg;
	}
}
