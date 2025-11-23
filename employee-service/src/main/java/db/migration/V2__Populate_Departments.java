package db.migration;

import java.sql.*;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import config.PropertyConfig;

public class V2__Populate_Departments extends BaseJavaMigration {

    @Override
    public void migrate(Context arg0) throws Exception {

        String departments = PropertyConfig.get("DEPARTMENTS");
        System.out.println("departments :" + departments);

        String[] departmentFields = departments.split(",");
        int count = departmentFields.length;

        Connection conn = arg0.getConnection();

        for (int i = 0; i < count; i++) {
            String parts[] = departmentFields[i].split(":");

            String sql = "INSERT INTO department (id, name) VALUES (?, ?)";
            try (PreparedStatement preparedstmt = conn.prepareStatement(sql)) {

                preparedstmt.setString(1, parts[0]);
                preparedstmt.setString(2, parts[1]);
                preparedstmt.executeUpdate();
            }
        }
    }
}
