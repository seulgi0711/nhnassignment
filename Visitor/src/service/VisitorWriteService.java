package service;

import java.sql.Connection;

import model.Message;
import dao.VisitorDao;

public class VisitorWriteService {	
//	public static void main(String args[]){
//		Message msg = new Message();
//		Connection conn = null;
//		VisitorDao visitorDao = new VisitorDao();
//		
//		
//		conn = DbUtil.getConnection();
//		
//		msg.setId("15");
//		msg.setEmail("correct");
//		msg.setPassword("1898170");
//		msg.setMessage("cor~");
//		
//		visitorDao.update(conn, msg);
//	}
	public void write(Message msg){
		Connection conn = null;
		
		conn = DbUtil.getConnection();
		VisitorDao visitorDao = new VisitorDao();
		
		visitorDao.insert(conn, msg);
		
		DbUtil.close(conn);
	}
	
	public void update(Message msg){
		Connection conn = null;
		
		conn = DbUtil.getConnection();
		VisitorDao visitorDao = new VisitorDao();
		
		System.out.println("업데이트 결과 : " + visitorDao.update(conn, msg));
		
		DbUtil.close(conn);
	}
}
