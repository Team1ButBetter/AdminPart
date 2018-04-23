//returns a list of every question ID in a given table
import java.sql.*;
import java.util.ArrayList;

public class QuestionIDList {
    public static void main(String args[]){
        Connection c = null;
        Statement stmt = null;
        String tableName = "Quizname1";
        ArrayList<String> IDArray = new ArrayList<String>(10);

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT QUESTION_ID FROM " + tableName);
            while (rs.next() ) {
                IDArray.add(rs.getString("QUESTION_ID"));
            }
            System.out.println(IDArray);
            rs.close();
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table list created successfully");
    }
}
