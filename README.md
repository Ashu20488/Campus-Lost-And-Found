# Campus Lost and Found Management System

A web-based mini project to report, search and claim lost or found items on campus.

## Technologies Used
- Java (Servlet + JSP)
- Hibernate 6
- MySQL 8
- Apache Tomcat 10
- Maven
- NetBeans IDE

## Features
- Report a lost or found item
- Search items by name, category or location
- Claim an item (status changes to "Claimed")
- Delete an item

## Project Structure
- `com.campus.entity`: Item (Hibernate entity)
- `com.campus.dao`: ItemDAO (database operations)
- `com.campus.servlet`: ItemServlet (controller)
- `com.campus.util`: HibernateUtil (session factory)
- `index.jsp`: user interface

## How to Run
1. Create a MySQL database named `campus_lost_found`
2. Open `src/main/resources/hibernate.cfg.xml` and set your MySQL username and password
3. Open the project in NetBeans and run it on Apache Tomcat 10 or above
4. Open `http://localhost:8080/CampusLostAndFound/items` in the browser

## Author
Ashu
