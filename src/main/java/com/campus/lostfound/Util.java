package com.campus.lostfound;

/** Chhoti helper class: HTML escape (XSS se bachne ke liye). */
public class Util {
    public static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
