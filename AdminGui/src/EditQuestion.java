//Class for editing the existing records in the database

import java.sql.*;

public class EditQuestion {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        String tablename = "QuizName1";
        Integer ID = 3;
        String imageq = "1";
        String q = "1";
        String image1 = "1";
        String a1 = "27";
        String image2 = "1";
        String a2 = "23";
        String image3 = "1";
        String a3 = "1";
        String image4 = "1";
        String a4 = "1";


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "UPDATE " + tablename + "\n" +
                    "SET Q_IMAGE = " + imageq + ", " + "QUESTION = " + q + ", " + "A_IMAGE_1 = " + image1 + ", " + "ANSWER_1 = " + a1 + ", " + "A_IMAGE_2 = " + image2 + ", " + "ANSWER_2 = " + a2 + ", " + "A_IMAGE_3 = " + image3 + ", " + "ANSWER_3 = " + a3 + ", " + "A_IMAGE_4 = " + image4 + ", " + "ANSWER_4 = " + a4 + " " + "\n" +
                    "WHERE QUESTION_ID = " + ID;
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record updated successfully");
    }
}
