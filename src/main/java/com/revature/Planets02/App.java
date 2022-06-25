package com.revature.Planets02;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.h2.util.json.JsonConstructorUtils;

class Planet{
    private int planetID;
    private String name;
    private String mass;
    private int moon;
    private long distance;
    public Planet(int planetID, String name, String mass, int moon, long distance) {
        this.planetID = planetID;
        this.name = name;
        this.mass = mass;
        this.moon = moon;
        this.distance = distance;
    }
    public Planet() {
    }
    public int getplanetID() {
        return planetID;
    }
    public void setplanetID(int planetID) {
        this.planetID = planetID;
    }
    public String getname() {
        return name;
    }
    public void setname(String name) {
        this.name = name;
    }
    public String getmass() {
        return mass;
    }
    public void setmass(String mass) {
        this.mass = mass;
    }
    public int getmoon() {
        return moon;
    }
    public void setmoon(int moon) {
        this.moon = moon;
    }
    public long getdistance() {
        return distance;
    }
    public void setdistance(long distance) {
        this.distance = distance;
    }
    @Override
    public String toString() {
        return "Planet [name=" + name + ", planetID=" + planetID + "mass" + mass + "moon" + moon + "distance" +distance + "]";
    }

}

public class App {
    public static void main(String[] args) throws SQLException {
        // Connect to DB
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;INIT=runscript from 'classpath:schema.sql'", "sa", "");

        // connection.DriverManager.getConnection("jdbc:h2:mem", "Planets", "");
        // connection.createStatement().execute(sql"CREATE TABLE PLANETS(id int primary key, Name varchar);

        HttpServlet planetServlet = new HttpServlet() {
            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
                List<Planet> planets = new ArrayList<>();
                try {
                    ResultSet rs = connection.prepareStatement("select * from planets").executeQuery();
                    while (rs.next()) {
                        planets.add(new Planet(rs.getInt("PlanetId"), rs.getString("NameofPlanet"), rs.getString("Mass"), rs.getInt("Moons"), rs.getLong("Distance")));
                    }
                } catch (SQLException e) {
                    System.err.println("Failed to retrieve from db: " + e.getSQLState());;
                }
                // Get a JSON Mapper
                ObjectMapper mapper = new ObjectMapper();
                String results = mapper.writeValueAsString(planets);
                resp.setContentType("application/json");
                resp.getWriter().println(results);
            }
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
                ObjectMapper mapper = new ObjectMapper();
                Planet newPlanet = mapper.readValue(req.getInputStream(), Planet.class);
                System.out.println(newPlanet);
                try{
                    PreparedStatement stmt = connection.prepareStatement("insert into planets values(?, ?, ?, ?, ?)");
                    stmt.setInt(1, newPlanet.getplanetID());
                    stmt.setString(2, newPlanet.getname());
                    stmt.setString(3, newPlanet.getmass());
                    stmt.setInt(4, newPlanet.getmoon());
                    stmt.setLong(5, newPlanet.getdistance());


                } catch(SQLException e) {
                    System.err.println("Failed to insert: " + e.getMessage());
                }
            }

        };

        // Run server
        Tomcat server = new Tomcat();
        server.getConnector();
        server.addContext("", null);
        server.addServlet("", "defaultServlet", new Server()).addMapping("/*");
        server.addServlet("", "planetServlet", planetServlet).addMapping("/allplanets");
        //server.addServlet("", "dwarfplanetServlet", planetServlet).addMapping("/planets");
        try{
            server.start();
        } catch(LifecycleException e){
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}
