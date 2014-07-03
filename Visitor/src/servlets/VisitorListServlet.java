package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Message;
import service.VisitorGetService;
import service.VisitorWriteService;

@SuppressWarnings("serial")
public class VisitorListServlet extends HttpServlet {
	private static final int WRITE = 1;
	private static final int CORRECT = 2;
	private static final int COUNT = 3;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int actionType = 0;

		actionType = Integer.parseInt(request.getParameter("action"));

		if (actionType == WRITE) {
			VisitorWriteService visitor = new VisitorWriteService();
			Message msg = new Message();

			msg.setId(request.getParameter("id"));
			msg.setEmail(request.getParameter("email"));
			msg.setPassword(request.getParameter("password"));
			msg.setMessage(request.getParameter("message"));
			msg.setCurDate(request.getParameter("curDate"));
			msg.setCorDate(request.getParameter("curDate"));

			visitor.write(msg);
		} else if (actionType == CORRECT) {

		} else if (actionType == COUNT) {
			int count;
			VisitorGetService visitor = new VisitorGetService();
			
			count = visitor.getCount();
		} else {
			System.out.println("작동 타입 에러입니다.");
		}
	}
}
