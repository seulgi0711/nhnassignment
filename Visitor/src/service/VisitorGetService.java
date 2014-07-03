package service;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

import model.Message;
import dao.VisitorDao;

public class VisitorGetService {
//	public static void main(String args[]) {
//		VisitorGetService tmp2 = new VisitorGetService();
//		List<Message> tmp = null;
//
//		tmp = tmp2.getMessageList();
//	}

	public List<Message> getMessageList() {
		Connection conn = null;
		List<Message> messageList = null;
		VisitorDao visitorDao = new VisitorDao();
		int messageCount = -1;

		conn = DbUtil.getConnection();

		messageCount = visitorDao.selectCount(conn);

		if (messageCount > 0) {
			messageList = visitorDao.selectList(conn);
		} else {
			messageList = Collections.emptyList();
		}

		DbUtil.close(conn);

		return messageList;
	}
	
	public int getCount(){
		Connection conn = null;
		int count = -1;
		VisitorDao visitorDao = new VisitorDao();
		
		count = visitorDao.selectCount(conn);
		
		DbUtil.close(conn);
		
		return count;
	}
}
