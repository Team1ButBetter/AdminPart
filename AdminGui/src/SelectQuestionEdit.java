//Class for outputing the exact question within the table that needs to be edited

import java.sql.*;

public class SelectQuestionEdit {

    public static void main( String args[]) {
        Connection c = null;
        Statement stmt = null;
        Integer qID = 3;
        String tableName = "QuizName1";

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Q_IMAGE, QUESTION, A_IMAGE_1, ANSWER_1, A_IMAGE_2, ANSWER_2, A_IMAGE_3, ANSWER_3, A_IMAGE_4, ANSWER_4, CORRECT_ANSWER FROM " + tableName + " WHERE QUESTION_ID =" + qID);
            while (rs.next() ) {
                String QI = rs.getString("Q_IMAGE");
                String Q = rs.getString("QUESTION");
                String AI1 = rs.getString("A_IMAGE_1");
                String A1 = rs.getString("ANSWER_1");
                String AI2 = rs.getString("A_IMAGE_2");
                String A2 = rs.getString("ANSWER_2");
                String AI3 = rs.getString("A_IMAGE_3");
                String A3 = rs.getString("ANSWER_3");
                String AI4 = rs.getString("A_IMAGE_4");
                String A4 = rs.getString("ANSWER_4");
                Integer CA = rs.getInt("CORRECT_ANSWER");

                System.out.println(QI);
                System.out.println(Q);
                System.out.println(AI1);
                System.out.println(A1);
                System.out.println(AI2);
                System.out.println(A2);
                System.out.println(AI3);
                System.out.println(A3);
                System.out.println(AI4);
                System.out.println(A4);
                System.out.println(CA);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Question Selected Successfully");
    }
}
