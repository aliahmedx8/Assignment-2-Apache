package edu.cs;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        if (idStr == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id");
            return;
        }

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT filename,filedata FROM files WHERE id=?")) {
            ps.setInt(1, Integer.parseInt(idStr));
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                    return;
                }
                String filename = rs.getString("filename");
                try (var is = rs.getBinaryStream("filedata");
                     OutputStream os = resp.getOutputStream()) {
                    resp.setContentType("application/octet-stream");
                    resp.setHeader("Content-Disposition",
                        "attachment; filename=\"" + filename.replace("\"", "") + "\"");
                    byte[] buf = new byte[8192];
                    int len;
                    while ((len = is.read(buf)) != -1) os.write(buf, 0, len);
                    os.flush();
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}

