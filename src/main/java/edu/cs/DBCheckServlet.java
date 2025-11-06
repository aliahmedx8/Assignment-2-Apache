package edu.cs;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/plain;charset=UTF-8");
        try (PrintWriter out = resp.getWriter();
             Connection con = DBUtil.getConnection();
             Statement st = con.createStatement()) {
            out.println("DB connection OK");
            ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM files");
            if (rs.next()) out.println("files.count=" + rs.getLong(1));
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace(resp.getWriter());
        }
    }
}
