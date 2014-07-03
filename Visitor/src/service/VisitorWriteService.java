package service;

import java.sql.Connection;

import model.Message;
import dao.VisitorDao;

public class VisitorWriteService {
//	public static void main(String args[]){
//		VisitorWriteService tmp2 = new VisitorWriteService();
//		Message msg = new Message();
//
//		msg.setId("5");
//		msg.setEmail("TEST@TEST.COM");
//		msg.setPassword("testpass");
//		msg.setMessage("testing hahaha");
//		msg.setCurDate(null);
//		msg.setCorDate(null);
//
//		tmp2.write(msg);
//	}
	
	public void write(Message msg){
		Connection conn = null;
		
		conn = DbUtil.getConnection();
		VisitorDao visitorDao = new VisitorDao();
		
		visitorDao.insert(conn, msg);
		
		DbUtil.close(conn);
	}
}
