package ru.qa.template.tests;

import org.testng.annotations.Test;
import ru.qa.template.model.Unit2Data;
import ru.qa.template.model.Units2;

import java.sql.*;

public class DbConnectionTest {
    @Test
    public void testDbConnection() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbname?" +
                            "user=username&password=");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT table2_id, text_field FROM table_2");
            Units2 units2 = new Units2();
            while (rs.next()) {
                units2.add(new Unit2Data().withId(rs.getInt("table2_id"))
                        .withTextField(rs.getNString("text_field")));
            }
            rs.close();
            st.close();
            conn.close();
            System.out.println(units2);

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
