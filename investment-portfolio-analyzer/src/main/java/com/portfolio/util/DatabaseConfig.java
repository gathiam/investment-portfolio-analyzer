package com.portfolio.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class provides centralized access to database configuration settings.
 * It loads settings from a properties file and provides default values if the file is missing.
 * @author Gaoussou Thiam
 * @date 02/17/2026
 */
public class DatabaseConfig {


    /** The name of the configuration file. */
    private static final String CONFIG_FILE = "database.properties";

    /** Properties object to store configuration settings. */
    private static final Properties properties = new Properties();

    /**
     * Static initializer block that loads configuration settings when the class is loaded.
     * Provides default values if the configuration file cannot be found.
     */
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.out.println("Sorry, unable to find " + CONFIG_FILE);
                // Default values if properties file is missing
                properties.setProperty("db.url", "jdbc:mysql://localhost:3306/investment_portfolio");
                properties.setProperty("db.user", "root");
                properties.setProperty("db.password", "");
            } else {
                properties.load(input);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets the database URL.
     *
     * @return The JDBC URL for the database connection.
     */
    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    /**
     * Gets the database username.
     *
     * @return The username for the database connection.
     */
    public static String getUser() {
        return properties.getProperty("db.user");
    }

    /**
     * Gets the database password.
     *
     * @return The password for the database connection.
     */
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}