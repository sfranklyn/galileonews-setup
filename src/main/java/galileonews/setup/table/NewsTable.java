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

import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class NewsTable {

    private static final String newsCreateSql
            = "create table news ("
            + "news_id int not null auto_increment,"
            + "news_text text,"
            + "news_create_date datetime,"
            + "news_create_by varchar(50),"
            + "news_valid_from datetime,"
            + "news_valid_to datetime,"
            + "news_important bit(1) default b'0',"
            + "news_pcc varchar(10),"
            + "constraint primary key (news_id)"
            + ");";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists news;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(newsCreateSql);
    }

}
