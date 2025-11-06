package edu.cs;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileId = request.getParameter("id");
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT filename, filedata FROM files WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(fileId));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String fileName = rs.getString("filename");
                InputStream fileData = rs.getBinaryStream("filedata");
                response.setHeader("Content-Disposition", "attachment; filename="" + fileName + """);
                OutputStream out = response.getOutputStream();
                fileData.transferTo(out);
                out.close();
            } else {
                response.getWriter().println("<h3>❌ File not found!</h3>");
            }
        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}