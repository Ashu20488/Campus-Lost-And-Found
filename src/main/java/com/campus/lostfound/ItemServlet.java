package com.campus.lostfound;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/** Controller: list/search (GET) aur add/resolve/delete (POST). */
@WebServlet(name = "ItemServlet", urlPatterns = {"/items"})
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String q = req.getParameter("q");
        String type = req.getParameter("type");
        String category = req.getParameter("category");
        String status = req.getParameter("status");
        if (status == null) status = "open";   // default: sirf open items

        List<Item> items = ItemDAO.search(q, type, category, status);
        req.setAttribute("items", items);
        req.setAttribute("q", q == null ? "" : q);
        req.setAttribute("fType", type == null ? "all" : type);
        req.setAttribute("fCategory", category == null ? "all" : category);
        req.setAttribute("fStatus", status);
        req.setAttribute("cTotal", ItemDAO.count(null, null));
        req.setAttribute("cLost", ItemDAO.count("lost", "open"));
        req.setAttribute("cFound", ItemDAO.count("found", "open"));
        req.setAttribute("cResolved", ItemDAO.count(null, "resolved"));
        req.getRequestDispatcher("/items.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String type = "found".equals(req.getParameter("type")) ? "found" : "lost";
            Item item = new Item();
            item.setType(type);
            item.setName(trim(req.getParameter("name")));
            item.setCategory(trim(req.getParameter("category")));
            item.setDescription(trim(req.getParameter("description")));
            item.setPlace(trim(req.getParameter("place")));
            item.setDate(trim(req.getParameter("date")));
            item.setContactName(trim(req.getParameter("contactName")));
            item.setContact(trim(req.getParameter("contact")));
            if (item.getName().isEmpty() || item.getPlace().isEmpty()
                    || item.getContactName().isEmpty() || item.getContact().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/add.jsp?type=" + type + "&error=1");
                return;
            }
            ItemDAO.add(item);
        } else if ("resolve".equals(action)) {
            ItemDAO.resolve(parseId(req.getParameter("id")));
        } else if ("delete".equals(action)) {
            ItemDAO.delete(parseId(req.getParameter("id")));
        }
        resp.sendRedirect(req.getContextPath() + "/items");
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }

    private static int parseId(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return -1; }
    }
}
