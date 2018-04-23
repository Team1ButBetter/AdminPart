// Query for a list of tables in the database

import java.sql.*;
import java.util.ArrayList;

public class ViewQuestionSetList {
    public static void main( String args[]) {
        Connection c = null;
        ArrayList<String> TableArray = new ArrayList<String>(10);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.db");
            System.out.println("Opened database successfully");

            DatabaseMetaData m = c.getMetaData();
            ResultSet tables = m.getTables(null,null, "%", null);
            while (tables.next()) {
                TableArray.add(tables.getString(3));
            }
            System.out.println(TableArray);
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table list created successfully");
    }
}
