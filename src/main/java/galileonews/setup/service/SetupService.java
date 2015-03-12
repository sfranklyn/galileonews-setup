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
package galileonews.setup.service;

import galileonews.setup.table.UsersTable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import galileonews.setup.table.ConfigsTable;
import galileonews.setup.table.GtidsTable;
import galileonews.setup.table.NewsTable;
import galileonews.setup.table.RolesTable;
import galileonews.setup.table.UrlsRolesTable;
import galileonews.setup.table.UsersRolesTable;
import org.joda.time.DateTime;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class SetupService {

    private static final Logger log = Logger.getLogger(SetupService.class.getName());
    private static final String driver = "com.mysql.jdbc.Driver";
    private static MessageDigest messageDigest = null;

    static {
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
            Class.forName(driver);
        } catch (NoSuchAlgorithmException | ClassNotFoundException ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public static String hashPassword(final String password) {
        byte[] hash = null;
        try {
            messageDigest.reset();
            hash = messageDigest.digest(password.getBytes());
        } catch (Exception ex) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return getHexString(hash);
    }

    public static String getHexString(byte[] b) {
        String result = "";
        for (int i = 0; i < b.length; i++) {
            result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
        }
        return result;
    }

    public void dropDb(String server, String port, String user, String password)
            throws ClassNotFoundException, SQLException {
        String jdbcUrl = "jdbc:mysql://" + server + ":" + port + "/mysql";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("drop user 'galileonews'@'localhost';");
            stmt.executeUpdate("drop database galileonews;");
        }
    }

    public void createDb(String server, String port, String user, String password)
            throws ClassNotFoundException, SQLException {
        String jdbcUrl = "jdbc:mysql://" + server + ":" + port + "/mysql";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("create database if not exists galileonews;");
            stmt.executeUpdate("create user 'galileonews'@'localhost' "
                    + "identified by 'galileonews';");
            stmt.executeUpdate("grant all on galileonews.* to "
                    + "'galileonews'@'localhost';");
        }
    }

    public void execute(String server, String port, String user, String password)
            throws ClassNotFoundException, SQLException {

        createDb(server, port, user, password);

        String jdbcUrl = "jdbc:mysql://" + server + ":" + port + "/galileonews";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, "galileonews", "galileonews");
                Statement stmt = conn.createStatement()) {

            UsersTable usersTable = new UsersTable();
            RolesTable rolesTable = new RolesTable();
            UsersRolesTable usersRolesTable = new UsersRolesTable();
            UrlsRolesTable urlsRolesTable = new UrlsRolesTable();
            ConfigsTable configsTable = new ConfigsTable();
            NewsTable newsTable = new NewsTable();
            GtidsTable gtidsTable = new GtidsTable();

            urlsRolesTable.drop(stmt);
            usersRolesTable.drop(stmt);
            usersTable.drop(stmt);
            rolesTable.drop(stmt);
            configsTable.drop(stmt);
            newsTable.drop(stmt);
            gtidsTable.drop(stmt);

            usersTable.create(stmt);
            rolesTable.create(stmt);
            usersRolesTable.create(stmt);
            urlsRolesTable.create(stmt);
            configsTable.create(stmt);
            newsTable.create(stmt);
            gtidsTable.create(stmt);

            String roleName = "ADM";
            String roleDesc = "Administrator";
            String roleMenu = "adm.xhtml";
            final int adminRoleId = rolesTable.insert(
                    roleName, roleDesc, roleMenu, conn);

            roleName = "USR";
            roleDesc = "User";
            roleMenu = "user.xhtml";
            final int userRoleId = rolesTable.insert(
                    roleName, roleDesc, roleMenu, conn);

            Map paramMap = new HashMap<>();
            String adminName1 = "adm1";
            paramMap.put("user_name", adminName1);
            String userPassword = hashPassword("adm1");
            paramMap.put("user_password", userPassword);
            paramMap.put("user_full_name", "Administrator One");
            paramMap.put("user_desc", "Created By Setup");
            paramMap.put("user_email", "-");
            final int adminUserId1 = usersTable.insert(paramMap, conn);
            usersRolesTable.insert(adminUserId1, adminRoleId, conn);

            paramMap = new HashMap<>();
            String userName1 = "user1";
            userPassword = hashPassword("user1");
            paramMap.put("user_name", userName1);
            paramMap.put("user_password", userPassword);
            paramMap.put("user_full_name", "User One");
            paramMap.put("user_desc", "Created By Setup");
            final int userId1 = usersTable.insert(paramMap, conn);

            paramMap = new HashMap<>();
            String userName2 = "user2";
            userPassword = hashPassword("user2");
            paramMap.put("user_name", userName2);
            paramMap.put("user_password", userPassword);
            paramMap.put("user_full_name", "User Two");
            paramMap.put("user_desc", "Created By Setup");
            final int userId2 = usersTable.insert(paramMap, conn);

            usersRolesTable.insert(userId1, userRoleId, conn);
            usersRolesTable.insert(userId2, userRoleId, conn);

            urlsRolesTable.insertAdm(adminRoleId, conn);
            urlsRolesTable.insertUser(userRoleId, conn);

            DateTime today = new DateTime();                    
            paramMap = new HashMap<>();
            StringBuilder newsText= new StringBuilder();
            newsText.append("Berita 1\n\n");
            newsText.append("Baris 1\n");
            newsText.append("Baris 2\n");
            newsText.append("Baris 3\n");
            paramMap.put("news_text", newsText.toString());
            paramMap.put("news_valid_from", today.getMillis());
            paramMap.put("news_valid_to", today.plusDays(1).getMillis());
            paramMap.put("news_important", false);
            paramMap.put("news_pcc", "*");
            newsTable.insert(paramMap, conn);
            paramMap = new HashMap<>();

            newsText= new StringBuilder();
            newsText.append("Berita 2\n\n");
            newsText.append("Baris 1\n");
            newsText.append("Baris 2\n\n");
            newsText.append("Baris 3\n");
            paramMap.put("news_text", newsText.toString());
            paramMap.put("news_valid_from", today.getMillis());
            paramMap.put("news_valid_to", today.plusDays(1).getMillis());
            paramMap.put("news_important", false);
            paramMap.put("news_pcc", "*");
            newsTable.insert(paramMap, conn);

            newsText= new StringBuilder();
            newsText.append("Berita 3\n\n");
            newsText.append("Baris 1\n");
            newsText.append("Baris 2\n");
            newsText.append("Baris 3\n");
            newsText.append("Baris 4\n");
            paramMap.put("news_text", newsText.toString());
            paramMap.put("news_valid_from", today.getMillis());
            paramMap.put("news_valid_to", today.plusDays(1).getMillis());
            paramMap.put("news_important", true);
            paramMap.put("news_pcc", "756O");
            newsTable.insert(paramMap, conn);

            newsText= new StringBuilder();
            newsText.append("Berita 4\n\n");
            newsText.append("Baris 1\n");
            newsText.append("Baris 2\n");
            newsText.append("Baris 3\n");
            newsText.append("Baris 4\n");
            paramMap.put("news_text", newsText.toString());
            paramMap.put("news_valid_from", today.plusDays(1).getMillis());
            paramMap.put("news_valid_to", today.plusDays(2).getMillis());
            paramMap.put("news_important", true);
            paramMap.put("news_pcc", "756O");
            newsTable.insert(paramMap, conn);
        }
    }

}
