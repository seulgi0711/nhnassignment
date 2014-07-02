package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import spms.service.DbUtil;

public class VisitorListServlet extends GenericServlet {
	public void service(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		String sql = "select * from visitor order by id desc";

		try {
			Class.forName("cubrid.jdbc.driver.CUBRIDDriver");
			conn = DbUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			out.println("<html><head><title>visitor</title></head>");
			out.println("<body><h1>Visitor</h1>");
			while (rs.next()) {
				out.println(rs.getInt("id") + "," + rs.getString("email") + ","
						+ rs.getString("password") + ","
						+ rs.getString("message") + "<br>");
			}
			out.println("</body></html>");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtil.close(rs);
			DbUtil.close(stmt);
			DbUtil.close(conn);
		}
	}
}
