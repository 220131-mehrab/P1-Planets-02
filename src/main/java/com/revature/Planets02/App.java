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

class Planet{
    private int planetID;
    private String name;
    public Planet(int planetID, String name) {
        this.planetID = planetID;
        this.name = name;
    }
    public Planet() {
    }
    public int getplanetID() {
        return planetID;
    }
    public void setPlanetID(int planetID) {
        this.planetID = planetID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Planet [name=" + name + ", planetID=" + planetID + "]";
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
                try{
                    ResultSet rs = connection.prepareStatement("select * from planet").executeQuery();
                    while(rs.next()){
                        planets.add(new Planet(rs.getInt("PlanetID"), rs.getString("Name")));
                    }
                } catch (SQLException e){
                    System.err.println("Fail to retrieve from db: " + e.getSQLState());
                }

                //Get a JSON Mapper
                ObjectMapper mapper = new ObjectMapper();
                String results = mapper.writeValueAsString(planets);
                resp.setContentType("applicaton/json");
                resp.getWriter().println(results);
            }
            Planet planet = new Planet();
            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
                ObjectMapper mapper = new ObjectMapper();
                String newPlanet = mapper.readValue(req.getInputStream(), String.class);
                System.out.println(newPlanet);
                try{
                    PreparedStatement stmt = connection.prepareStatement("insert into 'planet' values(?, ?)");
                    stmt.setInt(1, planet.getplanetID());
                    stmt.setString(2, planet.getName());
                    stmt.executeUpdate();
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

        server.addServlet("", "planetServlet", planetServlet).addMapping("/planets");
        try{
            server.start();
        } catch(LifecycleException e){
            System.err.println("Failed to start server: " + e.getMessage());
        }
    }
}
