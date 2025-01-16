package org.example.library;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Database {
    private static Connection db = null;
    static Connection getConnection() {

        // Check if we have a database
        if (db != null) return db;
        String username = "";
        String password = "";
        String host = "";
        String port = "";
        String name = "";

        // Load setting from our propertise
        Properties props = new Properties();
        try(InputStream input = Database.class.getResourceAsStream("/database.properties");) {

            props.load(input);
            username = props.getProperty("db.username");
            password = props.getProperty("db.password");
            host = props.getProperty("db.host");
            port = props.getProperty("db.port");
            name = props.getProperty("db.name");


        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        try{
            String url = "jdbc:mysql://"+ host; url  += ":" + port; url += "/" + name + "?useSSL=false";
            db = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            SQLExceptionPrint(e);
        }
        return db;
    }
    static void SQLExceptionPrint(SQLException sqle) {
        SQLExceptionPrint(sqle, false);
    }
    static void SQLExceptionPrint(SQLException sqle, Boolean printStackTrace){
        while(sqle != null){
            System.out.println("\n --SQLException-- \n");
            System.out.println("SQLState: " + sqle.getSQLState());
            System.out.println("Security " + sqle.getErrorCode());
            System.out.println("Message: " + sqle.getMessage());
            if(printStackTrace) sqle.printStackTrace();
            sqle = sqle.getNextException();

        }
    }
}



