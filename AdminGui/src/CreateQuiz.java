//Creation of a new question set

import java.sql.*;

public class CreateQuiz {
    public static void main(String args[]) {
        Connection c = null;
        Statement stmt = null;
        String tablename ="QuizName1";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE " + tablename +
                    "(QUESTION_ID   INTEGER     PRIMARY KEY     AUTOINCREMENT   NOT NULL," +
                    "Q_IMAGE    TEXT, " +
                    "QUESTION   TEXT    NOT NULL, " +
                    "A_IMAGE_1    TEXT, " +
                    "ANSWER_1   TEXT    NOT NULL, " +
                    "A_IMAGE_2    TEXT, " +
                    "ANSWER_2   TEXT    NOT NULL, " +
                    "A_IMAGE_3    TEXT, " +
                    "ANSWER_3   TEXT, " +
                    "A_IMAGE_4    TEXT, " +
                    "ANSWER_4   TEXT, " +
                    "CORRECT_ANSWER    INTEGER )";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Table created successfully");
    }
}
