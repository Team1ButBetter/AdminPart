//Class for inserting a new question (row) into the question set

import java.sql.*;

public class InsertQuestion {

    InsertQuestion(String[] args) {
        Connection c = null;
        Statement stmt = null;
        String tablename = args[1];
        String imageq =  args[2];
        String q =  args[3];
        String image1 =  args[4];
        String a1 =  args[5];
        String image2 =  args[6];
        String a2 =  args[7];
        String image3 =  args[8];
        String a3 = args[9];
        String image4 =  args[10];
        String a4 =  args[11];


        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:DataBase.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO " + tablename +
                    "(Q_IMAGE,QUESTION,A_IMAGE_1,ANSWER_1,A_IMAGE_2,ANSWER_2,A_IMAGE_3,ANSWER_3,A_IMAGE_4,ANSWER_4,CORRECT_ANSWER) " +
                    "VALUES (" + imageq + ", " + q + ", " + image1 + ", " + a1 + ", " + image2 + ", " + a2 + ", " +
                    image3 + ", " + a3 + ", " + image4 + ", " + a4 + ", " + 1 + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record created successfully");
    }

    public static void CreateQuestion(String[] args) {
        Connection c = null;
        Statement stmt = null;
        String tablename = "QuizName1";
        String imageq = "1";
        String q = "1";
        String image1 = "1";
        String a1 = "1";
        String image2 = "1";
        String a2 = "1";
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
            String sql = "INSERT INTO " + tablename +
                    "(Q_IMAGE,QUESTION,A_IMAGE_1,ANSWER_1,A_IMAGE_2,ANSWER_2,A_IMAGE_3,ANSWER_3,A_IMAGE_4,ANSWER_4,CORRECT_ANSWER) " +
                    "VALUES (" + imageq + ", " + q + ", " + image1 + ", " + a1 + ", " + image2 + ", " + a2 + ", " +
                    image3 + ", " + a3 + ", " + image4 + ", " + a4 + ", " + 1 + ");";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Record created successfully");
    }
}
