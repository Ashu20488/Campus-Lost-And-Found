package com.campus.lostfound;

/** Model class: ek lost ya found item. */
public class Item {
    private int id;
    private String type;        // "lost" ya "found"
    private String name;
    private String category;
    private String description;
    private String place;
    private String date;
    private String contactName;
    private String contact;
    private String status;      // "open" ya "resolved"

    public Item() { }

    public Item(int id, String type, String name, String category, String description,
                String place, String date, String contactName, String contact, String status) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.category = category;
        this.description = description;
        this.place = place;
        this.date = date;
        this.contactName = contactName;
        this.contact = contact;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPlace() { return place; }
    public void setPlace(String place) { this.place = place; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
