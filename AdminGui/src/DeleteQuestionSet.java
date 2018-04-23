//Class for deleting the entire question set (use with caution, as all data will be lost), include a warning

import java.sql.*;

public class DeleteQuestionSet {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        String tablename = "QuizName1";

        try{
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DROP TABLE " + tablename;
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table deleted successfully");
    }
}
