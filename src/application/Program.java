package application;

import db.DB;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Program {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement st = null;
        SimpleDateFormat sdf = new SimpleDateFormat("DD/MM/YYYY");

        try {
            conn = DB.getConnection();
            st = conn.prepareStatement(
                    "INSERT INTO seller " +
                            "(Name, Email, BirthDate, BaseSalary, DepartmentId)" +
                            "VALUES " +
                            "(?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            st.setString(1, "Mark Down");
            st.setString(2, "mark.down@email.com");
            st.setDate(3, new Date(sdf.parse("17/07/1980").getTime()));
            st.setDouble(4,3090.00);
            st.setInt(5, 4);

            int affectedRows = st.executeUpdate();

            if (affectedRows > 0) {
                ResultSet rs = st.getGeneratedKeys();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    System.out.println("Done! Row id = : " + id);
                }
            } else {
                System.out.println("No rows affected!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            DB.closeStatement(st);
            DB.closeConnection();
        }
    }
}
