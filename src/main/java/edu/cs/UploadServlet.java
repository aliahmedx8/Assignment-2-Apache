package edu.cs;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
@MultipartConfig(maxFileSize = 500 * 1024 * 1024)
public class UploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Part filePart = request.getPart("file");
        String fileName = filePart.getSubmittedFileName();
        long fileSize = filePart.getSize();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (fileSize == 0) {
            out.println("<h3 style='color:red;'>❌ Upload failed: File is empty!</h3>");
            return;
        }
        if (fileSize > 500L * 1024 * 1024) {
            out.println("<h3 style='color:red;'>❌ Upload failed: File exceeds 500MB limit!</h3>");
            return;
        }
        try (Connection conn = DBUtil.getConnection();
             InputStream inputStream = filePart.getInputStream();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO files(filename, filedata, filesize) VALUES (?, ?, ?)")) {
            ps.setString(1, fileName);
            ps.setBlob(2, inputStream);
            ps.setLong(3, fileSize);
            ps.executeUpdate();
            out.println("<h3 style='color:green;'>✅ File uploaded successfully: " + fileName + "</h3>");
            out.println("<a href='download.jsp'>Go to Download Page</a>");
        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}