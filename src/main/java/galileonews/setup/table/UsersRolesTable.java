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
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class UsersRolesTable {

    private static final String usersRolesCreateSql
            = "create table users_roles ("
            + "user_id int not null,"
            + "role_id int not null,"
            + "users_roles_desc varchar(100),"
            + "primary key (user_id,role_id),"
            + "constraint fk_users_roles1 "
            + "  foreign key (user_id) references users (user_id),"
            + "constraint fk_users_roles2 "
            + "  foreign key (role_id) references roles (role_id),"
            + "index idx_users_roles1 (user_id),"
            + "index idx_users_roles2 (role_id)"
            + ");";

    private static final String usersRolesInsertSql
            = "insert into users_roles ("
            + "user_id,"
            + "role_id"
            + ") values ("
            + "?,"
            + "?"
            + ")";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists users_roles;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(usersRolesCreateSql);
    }

    public void insert(final int userId, final int roleId,
            final Connection conn)
            throws SQLException {
        try (PreparedStatement usersRolesStmt = conn.
                prepareStatement(usersRolesInsertSql)) {
            usersRolesStmt.setInt(1, userId);
            usersRolesStmt.setInt(2, roleId);
            usersRolesStmt.executeUpdate();
        }
    }

}
