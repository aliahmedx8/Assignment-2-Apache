package edu.cs;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,
    maxFileSize = 500L * 1024 * 1024,
    maxRequestSize = 510L * 1024 * 1024
)
public class UploadServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part file = req.getPart("file");
        if (file == null || file.getSize() <= 0) {
            req.setAttribute("msg", "Upload failed: empty file is not allowed.");
            req.getRequestDispatcher("/upload.jsp").forward(req, resp);
            return;
        }

        if (file.getSize() > 500L * 1024 * 1024) {
            req.setAttribute("msg", "Upload failed: file exceeds 500MB limit.");
            req.getRequestDispatcher("/upload.jsp").forward(req, resp);
            return;
        }

        String filename = file.getSubmittedFileName();
        try (InputStream in = file.getInputStream();
             Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO files(filename,filedata,filesize) VALUES(?,?,?)")) {
            ps.setString(1, filename);
            ps.setBlob(2, in);
            ps.setLong(3, file.getSize());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/download.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/upload.jsp").forward(req, resp);
    }
}

