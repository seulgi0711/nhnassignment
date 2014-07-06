package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Message;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import service.VisitorGetService;
import service.VisitorWriteService;

@SuppressWarnings("serial")
public class VisitorListServlet extends HttpServlet {
	private static final int GET_ALL_DB = 0;
	private static final int WRITE = 1;
	private static final int CORRECT = 2;
	private static final int COUNT = 3;
	private static final int GET_DB = 4;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");

		String jsonData = request.getParameter("json");
		// Object obj = JSONValue.parse(jsonData);
		JSONObject jobj = JSONObject.fromObject(jsonData);
		// response.getWriter().print(jobj);

		int actionType = Integer.parseInt(String.valueOf(jobj.get("action")));

		if ((actionType == WRITE || actionType == CORRECT) &&
			isValidEmailAddress(String.valueOf(jobj.get("email"))) == false) {
				System.out.println("이메일 꺼져");
				ArrayList<Message> list = new ArrayList<Message>();
				Message msg = new Message();
				msg.setId("email");

				list.add(msg);

				JSONArray jlist = JSONArray.fromObject(list);
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("db", jlist);

				JSONObject dbJson = JSONObject.fromObject(map);

				response.getWriter().print(dbJson);
		} else if (actionType == GET_ALL_DB) {
			VisitorGetService visitor = new VisitorGetService();
			List<Message> list = null;

			list = visitor.getMessageList();
			JSONArray jlist = JSONArray.fromObject(list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dblist", jlist);

			JSONObject dbJson = JSONObject.fromObject(map);

			response.getWriter().print(dbJson);
		}else if (actionType == WRITE) {
			VisitorWriteService writeVisitor = new VisitorWriteService();
			VisitorGetService getVisitor = new VisitorGetService();
			Message msg = new Message();

			msg.setId(String.valueOf(jobj.get("id")));
			msg.setEmail(String.valueOf(jobj.get("email")));
			msg.setPassword(String.valueOf(jobj.get("password")));
			msg.setMessage(String.valueOf(jobj.get("message")));
			msg.setCurDate(String.valueOf(jobj.get("curDate")));
			msg.setCorDate(String.valueOf(jobj.get("corDate")));

			writeVisitor.write(msg);

			msg = getVisitor.getMessage(Integer.parseInt(msg.getId()));

			ArrayList<Message> list = new ArrayList<Message>();
			list.add(msg);

			JSONArray jlist = JSONArray.fromObject(list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("db", jlist);

			JSONObject dbJson = JSONObject.fromObject(map);

			response.getWriter().print(dbJson);

		} else if (actionType == CORRECT) {
			VisitorGetService getVisitor = new VisitorGetService();
			ArrayList<Message> list = new ArrayList<Message>();
			Message msg = new Message();

			msg = getVisitor.getMessage(Integer.parseInt(String.valueOf(jobj
					.get("id"))));

			if (msg.getPassword().equals(String.valueOf(jobj.get("password"))) == false) {
				System.out.println("비밀번호가 다르다");
				msg.setId("fail");
			} else {
				VisitorWriteService writeVisitor = new VisitorWriteService();

				msg.setEmail(String.valueOf(jobj.get("email")));
				msg.setMessage(String.valueOf(jobj.get("message")));
				msg.setCorDate(String.valueOf(jobj.get("corDate")));

				writeVisitor.update(msg);

				msg.setId("success");
			}

			list.add(msg);

			JSONArray jlist = JSONArray.fromObject(list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("db", jlist);

			JSONObject dbJson = JSONObject.fromObject(map);

			response.getWriter().print(dbJson);

		} else if (actionType == COUNT) {
			int count;
			VisitorGetService visitor = new VisitorGetService();
			count = visitor.getCount();

			response.getWriter().print(Integer.toString(count));
		} else if (actionType == GET_DB) {
			VisitorGetService getVisitor = new VisitorGetService();
			ArrayList<Message> list = new ArrayList<Message>();
			Message msg = new Message();

			msg = getVisitor.getMessage(Integer.parseInt(String.valueOf(jobj
					.get("id"))));
			list.add(msg);

			JSONArray jlist = JSONArray.fromObject(list);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("db", jlist);

			JSONObject dbJson = JSONObject.fromObject(map);

			response.getWriter().print(dbJson);
		} else {
			System.out.println("작동 타입 에러입니다.");
		}
	}

	public boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
}
