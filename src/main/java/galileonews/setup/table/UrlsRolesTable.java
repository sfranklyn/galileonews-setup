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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class UrlsRolesTable {

    private static final String urlsRolesCreateSql
            = "create table urls_roles ("
            + "url_role varchar(250) not null,"
            + "role_id int not null,"
            + "primary key (url_role,role_id),"
            + "constraint fk_urls_roles1 "
            + "  foreign key (role_id) references roles (role_id),"
            + "index idx_urls_roles1 (url_role),"
            + "index idx_urls_roles2 (role_id)"
            + ");";

    private static final String urlsRolesInsertSql
            = "insert into urls_roles ("
            + "url_role,"
            + "role_id"
            + ") values ("
            + "?,"
            + "?"
            + ")";

    private final String urlPrefix = "/galileonews/faces/secure/";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists urls_roles;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(urlsRolesCreateSql);
    }

    public void insert(final Map paramMap,
            final Connection conn)
            throws SQLException {
        try (PreparedStatement urlsRolesStmt = conn.
                prepareStatement(urlsRolesInsertSql)) {
            urlsRolesStmt.setString(1, (String) paramMap.get("url_role"));
            urlsRolesStmt.setInt(2, (Integer) paramMap.get("role_id"));
            urlsRolesStmt.executeUpdate();
        }
    }

    public void insertUser(final int roleId, Connection conn)
            throws SQLException {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("role_id", roleId);

        paramMap.put("url_role", urlPrefix + "index.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "news.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "change_password.xhtml");
        insert(paramMap, conn);
    }

    public void insertAdm(final int roleId, Connection conn)
            throws SQLException {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("role_id", roleId);

        paramMap.put("url_role", urlPrefix + "advanced.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "index.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "change_password.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "users.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "usersCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "usersDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "usersRead.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "usersUpdate.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "roles.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "rolesCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "rolesDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "rolesRead.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "rolesUpdate.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "users_roles.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "users_rolesCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "users_rolesDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "users_rolesRead.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "urls_roles.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "urls_rolesCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "urls_rolesDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "urls_rolesRead.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "configs.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "configsCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "configsDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "configsRead.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "configsUpdate.xhtml");
        insert(paramMap, conn);

        paramMap.put("url_role", urlPrefix + "news.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "newsCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "newsDelete.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "newsRead.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "newsUpdate.xhtml");
        insert(paramMap, conn);
                
        paramMap.put("url_role", urlPrefix + "attachments.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "attachmentsCreate.xhtml");
        insert(paramMap, conn);
        paramMap.put("url_role", urlPrefix + "attachmentsDelete.xhtml");
        insert(paramMap, conn);
    }

}
