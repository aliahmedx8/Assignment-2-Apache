<%@ page import="java.sql.*,edu.cs.DBUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Available Files</title></head>
<body>
<h2>Available Files</h2>
<table border="1" cellpadding="8">
  <tr><th>ID</th><th>File Name</th><th>Size (bytes)</th><th>Action</th></tr>
<%
try (Connection con = DBUtil.getConnection();
     Statement st = con.createStatement();
     ResultSet rs = st.executeQuery("SELECT id, filename, filesize FROM files ORDER BY id DESC")) {
  while (rs.next()) {
%>
  <tr>
    <td><%= rs.getInt("id") %></td>
    <td><%= rs.getString("filename") %></td>
    <td><%= rs.getLong("filesize") %></td>
    <td><a href="download?id=<%= rs.getInt("id") %>">Download</a></td>
  </tr>
<%
  }
} catch (Exception e) {
  out.println("<tr><td colspan='4' style='color:red;'>Error: " + e.getMessage() + "</td></tr>");
}
%>
</table>
<p><a href="upload.jsp">Back to Upload</a></p>
</body>
</html>

