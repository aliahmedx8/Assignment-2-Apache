<%@ page import="java.sql.*" %>
<%@ page import="edu.cs.DBUtil" %>
<html><head><title>Download Files</title></head>
<body><h2>Available Files</h2>
<table border="1">
<tr><th>ID</th><th>File Name</th><th>Size (bytes)</th><th>Action</th></tr>
<%
try (Connection conn = DBUtil.getConnection();
     PreparedStatement ps = conn.prepareStatement("SELECT * FROM files");
     ResultSet rs = ps.executeQuery()) {
    while (rs.next()) {
        out.println("<tr><td>" + rs.getInt("id") + "</td><td>" +
                    rs.getString("filename") + "</td><td>" +
                    rs.getLong("filesize") + "</td><td><a href='download?id=" +
                    rs.getInt("id") + "'>Download</a></td></tr>");
    }
} catch (Exception e) { e.printStackTrace(out); }
%>
</table></body></html>