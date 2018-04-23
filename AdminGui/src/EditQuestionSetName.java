//Allows for changing the name of the question set

import java.sql.*;

public class EditQuestionSetName {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        String tableNameOld = "NewQuizName2";
        String tableNameNew = "NewQuizname1";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "ALTER TABLE " + tableNameOld + "\n" + "RENAME TO " + tableNameNew;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table renamed successfully");
    }
}
