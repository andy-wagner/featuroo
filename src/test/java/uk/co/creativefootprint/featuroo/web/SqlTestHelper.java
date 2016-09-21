package uk.co.creativefootprint.featuroo.web;

import uk.co.creativefootprint.featuroo.repository.BaseRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlTestHelper {

    private static String DB_DRIVER="org.h2.Driver";
    private static String DB_CONNECTION="jdbc:h2:~/featuroo-test";
    private static String DB_USER="";
    private static String DB_PASSWORD="";

    public static void resetDb(){
        BaseRepository baseRepository = new BaseRepository(DB_DRIVER,DB_CONNECTION,DB_USER,DB_PASSWORD);

        deleteDb();
        try {
            baseRepository.createDb("db.sql");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDb() {
        Connection dbConnection;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            PreparedStatement s = dbConnection.prepareStatement("DROP ALL OBJECTS");
            s.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
