package com.alejandro.facturacion.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@Component
public class DatabaseCreator implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "root";
        String dbName = "facturacion";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS " + dbName);
            System.out.println("Base de datos verificada/creada: " + dbName);
        } catch (Exception e) {
            System.err.println("Error creando la base de datos: " + e.getMessage());
        }
    }
} 