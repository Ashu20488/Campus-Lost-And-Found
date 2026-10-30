<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List, com.campus.lostfound.Item, com.campus.lostfound.Util"%>
<%
    List<Item> items = (List<Item>) request.getAttribute("items");
    String q = (String) request.getAttribute("q");
    String fType = (String) request.getAttribute("fType");
    String fCategory = (String) request.getAttribute("fCategory");
    String fStatus = (String) request.getAttribute("fStatus");
    String[] cats = {"ID Card", "Books", "Electronics", "Wallet / Money", "Keys", "Bag", "Clothing", "Other"};
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Campus Lost &amp; Found</title>
    <link rel="stylesheet" href="<%= ctx %>/style.css">
</head>
<body>
<header>
    <div>
        <h1>Campus Lost &amp; Found</h1>
        <small>Report lost items and help return found items to their owners</small>
    </div>
    <div>
        <a class="btn btn-light" href="<%= ctx %>/add.jsp?type=lost">+ Report Lost</a>
        <a class="btn btn-light" href="<%= ctx %>/add.jsp?type=found">+ Report Found</a>
    </div>
</header>

<div class="container">
    <div class="stats">
        <div class="stat"><b><%= request.getAttribute("cTotal") %></b>Total</div>
        <div class="stat"><b class="red"><%= request.getAttribute("cLost") %></b>Lost</div>
        <div class="stat"><b class="green"><%= request.getAttribute("cFound") %></b>Found</div>
        <div class="stat"><b><%= request.getAttribute("cResolved") %></b>Resolved</div>
    </div>

    <form class="toolbar" method="get" action="<%= ctx %>/items">
        <input type="text" name="q" value="<%= Util.esc(q) %>" placeholder="Search by item, place or description...">
        <select name="type">
            <option value="all">All Types</option>
            <option value="lost" <%= "lost".equals(fType) ? "selected" : "" %>>Lost</option>
            <option value="found" <%= "found".equals(fType) ? "selected" : "" %>>Found</option>
        </select>
        <select name="category">
            <option value="all">All Categories</option>
            <% for (String c : cats) { %>
            <option <%= c.equals(fCategory) ? "selected" : "" %>><%= c %></option>
            <% } %>
        </select>
        <select name="status">
            <option value="open" <%= "open".equals(fStatus) ? "selected" : "" %>>Open only</option>
            <option value="resolved" <%= "resolved".equals(fStatus) ? "selected" : "" %>>Resolved only</option>
            <option value="all" <%= "all".equals(fStatus) ? "selected" : "" %>>All status</option>
        </select>
        <button class="btn btn-primary" type="submit">Search</button>
    </form>

    <% if (items == null || items.isEmpty()) { %>
        <div class="empty">No items found.</div>
    <% } else { %>
    <div class="grid">
        <% for (Item i : items) {
               boolean resolved = "resolved".equals(i.getStatus()); %>
        <div class="card">
            <div class="noimg"><%= "lost".equals(i.getType()) ? "&#128269;" : "&#128230;" %></div>
            <div class="body">
                <span class="tag <%= resolved ? "resolved" : i.getType() %>"><%= resolved ? "RESOLVED" : i.getType().toUpperCase() %></span>
                <h3><%= Util.esc(i.getName()) %></h3>
                <p><b>Category:</b> <%= Util.esc(i.getCategory()) %></p>
                <p><b>Place:</b> <%= Util.esc(i.getPlace()) %></p>
                <p><b>Date:</b> <%= Util.esc(i.getDate()) %></p>
                <% if (!i.getDescription().isEmpty()) { %><p><%= Util.esc(i.getDescription()) %></p><% } %>
                <p><b>Contact:</b> <%= Util.esc(i.getContactName()) %> - <%= Util.esc(i.getContact()) %></p>
            </div>
            <div class="actions">
                <% if (!resolved) { %>
                <form method="post" action="<%= ctx %>/items">
                    <input type="hidden" name="action" value="resolve">
                    <input type="hidden" name="id" value="<%= i.getId() %>">
                    <button class="btn-sm green-bg" type="submit"><%= "lost".equals(i.getType()) ? "Mark as Recovered" : "Mark as Returned" %></button>
                </form>
                <% } %>
                <form method="post" action="<%= ctx %>/items" onsubmit="return confirm('Are you sure you want to delete this entry?');">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="id" value="<%= i.getId() %>">
                    <button class="btn-sm red-bg" type="submit">Delete</button>
                </form>
            </div>
        </div>
        <% } %>
    </div>
    <% } %>
</div>

<footer>Campus Lost &amp; Found | Java Servlet, JSP and MySQL</footer>
</body>
</html>