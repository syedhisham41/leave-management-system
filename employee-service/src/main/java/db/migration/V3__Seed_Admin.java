package db.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import config.PropertyConfig;

public class V3__Seed_Admin extends BaseJavaMigration {

    @Override
    public void migrate(Context context) throws Exception {
        Connection conn = context.getConnection();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM employees");
            rs.next();
            int count = rs.getInt(1);

            if (count == 0) {
                String sql = "INSERT INTO employees (emp_name, emp_joining_date, emp_department_id, emp_email) VALUES (?, date('now'), ?, ?)";
                try (PreparedStatement preparedStmt = conn.prepareStatement(sql)) {
                    preparedStmt.setString(1, PropertyConfig.get("ADMIN_NAME"));
                    preparedStmt.setString(2, PropertyConfig.get("ADMIN_DEPARTMENT"));
                    preparedStmt.setString(3, PropertyConfig.get("ADMIN_EMAIL"));
                    preparedStmt.executeUpdate();
                }
            }
        }
    }
}