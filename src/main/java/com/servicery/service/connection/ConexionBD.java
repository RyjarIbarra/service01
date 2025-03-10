package com.servicery.service.connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexionBD {

    public Connection cone(){

        Properties properties = new Properties();

        try {
            properties.load(new FileInputStream("src/main/resources/config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String host = properties.getProperty("host");
        String port = properties.getProperty("port");
        String database = properties.getProperty("database");
        String usurious = properties.getProperty("username");
        String contrast = properties.getProperty("password");

        Connection connection1 = null;

        try {

            String url = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            connection1 = DriverManager.getConnection(url, usurious, contrast);

        }catch (SQLException e){
            System.out.println("Error: " + e.getMessage());
        }

        return connection1;

    }
}
