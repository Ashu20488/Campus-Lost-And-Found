<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.campus.lostfound.Util"%>
<%
    String type = "found".equals(request.getParameter("type")) ? "found" : "lost";
    String today = java.time.LocalDate.now().toString();
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Report <%= "lost".equals(type) ? "Lost" : "Found" %> Item</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">
</head>
<body>
<header>
    <div><h1>Campus Lost &amp; Found</h1></div>
    <div><a class="btn btn-light" href="<%= ctx %>/items">&larr; Back to list</a></div>
</header>

<div class="container">
    <div class="form-box">
        <h2>Report <%= "lost".equals(type) ? "Lost" : "Found" %> Item</h2>
        <% if (request.getParameter("error") != null) { %>
        <p class="error">Please fill in all required (*) fields.</p>
        <% } %>
        <form method="post" action="<%= ctx %>/items">
            <input type="hidden" name="action" value="add">
            <input type="hidden" name="type" value="<%= type %>">

            <label>Item Name *</label>
            <input name="name" required placeholder="e.g. Black wallet">

            <label>Category *</label>
            <select name="category" required>
                <option>ID Card</option><option>Books</option><option>Electronics</option>
                <option>Wallet / Money</option><option>Keys</option><option>Bag</option>
                <option>Clothing</option><option>Other</option>
            </select>

            <label>Description</label>
            <textarea name="description" rows="3" placeholder="Color, brand, special marks..."></textarea>

            <label>Place *</label>
            <input name="place" required placeholder="e.g. Library, Canteen, Block B">

            <label>Date *</label>
            <input type="date" name="date" required value="<%= today %>">

            <label>Your Name *</label>
            <input name="contactName" required>

            <label>Contact (Phone / Email) *</label>
            <input name="contact" required>

            <div class="row">
                <button type="submit" class="btn btn-primary" style="flex:1">Submit</button>
                <a class="btn btn-cancel" href="<%= ctx %>/items">Cancel</a>
            </div>
        </form>
    </div>
</div>
</body>
</html>