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
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class UsersTable {

    private static final String usersCreateSql
            = "create table users ("
            + "user_id int not null auto_increment,"
            + "user_name varchar(50) not null,"
            + "user_password varchar(128),"
            + "user_full_name varchar(200),"
            + "user_email varchar(200),"
            + "user_phone varchar(50),"
            + "user_last_pwd_change datetime,"
            + "constraint primary key (user_id),"
            + "constraint nk_users unique (user_name)"
            + ");";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists users;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(usersCreateSql);
    }

    public int insert(final Map paramMap,
            final Connection conn)
            throws SQLException {
        int userId = 0;
        String usersSql = "insert into users ("
                + "user_name," //1
                + "user_password," //2
                + "user_full_name," //3
                + "user_email," //4
                + "user_last_pwd_change";   //5
        usersSql = usersSql.concat(") values ("
                + "?," //1
                + "?," //2
                + "?," //3
                + "?," //4
                + "?"); //5
        usersSql = usersSql.concat(")");
        Date now = new Date();
        try (PreparedStatement usersStmt
                = conn.prepareStatement(usersSql,
                        Statement.RETURN_GENERATED_KEYS)) {
            usersStmt.setString(1, (String) paramMap.get("user_name"));
            usersStmt.setString(2, (String) paramMap.get("user_password"));
            usersStmt.setString(3, (String) paramMap.get("user_full_name"));
            usersStmt.setString(4, (String) paramMap.get("user_email"));
            usersStmt.setTimestamp(5, new Timestamp(now.getTime()));
            usersStmt.executeUpdate();
            ResultSet resultSet = usersStmt.getGeneratedKeys();
            if (resultSet.next()) {
                userId = resultSet.getInt(1);
            }
        }
        return userId;
    }

}
