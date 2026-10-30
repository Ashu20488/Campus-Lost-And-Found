package com.campus.lostfound;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {

    public static void add(Item i) {
        String sql = "INSERT INTO items (type,name,category,description,place,item_date,contact_name,contact,status) "
                   + "VALUES (?,?,?,?,?,?,?,?,'open')";
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, i.getType());
            ps.setString(2, i.getName());
            ps.setString(3, i.getCategory());
            ps.setString(4, i.getDescription());
            ps.setString(5, i.getPlace());
            ps.setString(6, i.getDate());
            ps.setString(7, i.getContactName());
            ps.setString(8, i.getContact());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Item add nahi hua: " + e.getMessage(), e);
        }
    }

    public static List<Item> search(String q, String type, String category, String status) {
        StringBuilder sql = new StringBuilder("SELECT * FROM items WHERE 1=1");
        List<String> params = new ArrayList<>();
        if (!isAll(type))     { sql.append(" AND type=?");     params.add(type); }
        if (!isAll(category)) { sql.append(" AND category=?"); params.add(category); }
        if (!isAll(status))   { sql.append(" AND status=?");   params.add(status); }
        if (q != null && !q.trim().isEmpty()) {
            sql.append(" AND (name LIKE ? OR description LIKE ? OR place LIKE ? OR category LIKE ?)");
            String like = "%" + q.trim() + "%";
            for (int k = 0; k < 4; k++) params.add(like);
        }
        sql.append(" ORDER BY id DESC");

        List<Item> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            for (int k = 0; k < params.size(); k++) ps.setString(k + 1, params.get(k));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Item(
                            rs.getInt("id"), rs.getString("type"), rs.getString("name"),
                            rs.getString("category"), nz(rs.getString("description")), rs.getString("place"),
                            rs.getString("item_date"), rs.getString("contact_name"),
                            rs.getString("contact"), rs.getString("status")));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Items load nahi hue: " + e.getMessage(), e);
        }
        return list;
    }

    public static void resolve(int id) {
        run("UPDATE items SET status='resolved' WHERE id=?", id);
    }

    public static void delete(int id) {
        run("DELETE FROM items WHERE id=?", id);
    }

    public static int count(String type, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM items WHERE 1=1");
        if (type != null)   sql.append(" AND type=?");
        if (status != null) sql.append(" AND status=?");
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql.toString())) {
            int k = 1;
            if (type != null)   ps.setString(k++, type);
            if (status != null) ps.setString(k++, status);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Count nahi mila: " + e.getMessage(), e);
        }
    }

    private static void run(String sql, int id) {
        try (Connection c = DBConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static boolean isAll(String s) { return s == null || s.isEmpty() || s.equals("all"); }
    private static String nz(String s) { return s == null ? "" : s; }
}