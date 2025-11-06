<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Upload</title></head>
<body>
<h2>Upload a file</h2>
<% String msg = (String)request.getAttribute("msg");
   if (msg != null) { %>
   <p style="color:red;"><%= msg %></p>
<% } %>
<form method="post" action="upload" enctype="multipart/form-data">
  <input type="file" name="file" required />
  <button type="submit">Upload</button>
</form>
<p><a href="download.jsp">See uploaded files</a></p>
</body>
</html>

