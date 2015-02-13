/*
 * Copyright 2015 Samuel Franklyn <sfranklyn@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package galileonews.setup.table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class RolesTable {

    private static final String rolesCreateSql
            = "create table roles ("
            + "role_id int not null auto_increment,"
            + "role_name varchar(50) not null,"
            + "role_desc varchar(100),"
            + "role_menu varchar(50),"
            + "constraint primary key (role_id),"
            + "constraint nk_roles unique (role_name)"
            + ");";

    private static final String rolesInsertSql
            = "insert into roles ("
            + "role_name,"
            + "role_desc,"
            + "role_menu"
            + ") values ("
            + "?,"
            + "?,"
            + "?"
            + ")";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists roles;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(rolesCreateSql);
    }

    public int insert(
            final String roleName,
            final String roleDesc,
            final String roleMenu,
            final Connection conn)
            throws SQLException {
        int roleId = 0;
        try (PreparedStatement rolesStmt = conn.
                prepareStatement(rolesInsertSql, Statement.RETURN_GENERATED_KEYS)) {

            rolesStmt.setString(1, roleName);
            rolesStmt.setString(2, roleDesc);
            rolesStmt.setString(3, roleMenu);
            rolesStmt.executeUpdate();
            ResultSet resultSet = rolesStmt.getGeneratedKeys();
            if (resultSet.next()) {
                roleId = resultSet.getInt(1);
            }
        }
        return roleId;
    }

}
