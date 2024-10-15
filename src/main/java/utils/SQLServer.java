package utils;

import enviroments.EnvironmentPropPicker;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class SQLServer {

    static EnvironmentPropPicker environmentPropPicker = new EnvironmentPropPicker();


    private static final String URL =  environmentPropPicker.getDatabaseCredentials().get("DB_HOST");
    private static final String USER = environmentPropPicker.getDatabaseCredentials().get("DB_USER_NAME");
    private static final String PASSWORD = environmentPropPicker.getDatabaseCredentials().get("DB_PASSWORD");

    public SQLServer(Map<String, String> databaseCredentials) {
    }
    public static void getConnection() throws SQLException {

        System.out.println("Connected to the database");

        DriverManager.getConnection(URL, USER, PASSWORD);

    }


}