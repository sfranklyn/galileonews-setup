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
public class NewsTable {

    private static final String newsCreateSql
            = "create table news ("
            + "news_id int not null auto_increment,"
            + "news_text text,"
            + "news_create_date timestamp,"
            + "news_create_by varchar(50),"
            + "news_valid_from datetime,"
            + "news_valid_to datetime,"
            + "news_important bit(1) default b'0',"
            + "news_pcc varchar(10),"
            + "constraint primary key (news_id)"
            + ");";

    private static final String newsInsertSql
            = "insert into news ("
            + "news_text," //1
            + "news_create_date," //2
            + "news_create_by," //3
            + "news_valid_from,"//4
            + "news_valid_to," //5
            + "news_important," //6
            + "news_pcc" //7
            + ") values ("
            + "?," //1
            + "?," //2
            + "?," //3
            + "?," //4
            + "?," //5
            + "?," //6
            + "?" //7
            + ")";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists news;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(newsCreateSql);
    }

    public int insert(final Map paramMap,
            final Connection conn)
            throws SQLException {
        int newsId = 0;
        Date now = new Date();
        try (PreparedStatement newsStmt
                = conn.prepareStatement(newsInsertSql,
                        Statement.RETURN_GENERATED_KEYS)) {
            newsStmt.setString(1, (String) paramMap.get("news_text"));              //news_text
            newsStmt.setTimestamp(2, new Timestamp(now.getTime()));                 //news_create_date
            newsStmt.setString(3, "setup");                                         //news_create_by
            newsStmt.setDate(4, new java.sql.Date((long) paramMap.get("news_valid_from")));   //news_valid_from
            newsStmt.setDate(5, new java.sql.Date((long) paramMap.get("news_valid_to")));     //news_valid_to
            newsStmt.setBoolean(6, (boolean) paramMap.get("news_important"));       //news_important
            newsStmt.setString(7, (String) paramMap.get("news_pcc"));               //news_pcc
            newsStmt.executeUpdate();
            ResultSet resultSet = newsStmt.getGeneratedKeys();
            if (resultSet.next()) {
                newsId = resultSet.getInt(1);
            }
        }
        return newsId;

    }

}
