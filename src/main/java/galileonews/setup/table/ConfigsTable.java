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
public class ConfigsTable {

    private static final String configsCreateSql
            = "create table configs ("
            + "config_id int not null auto_increment,"
            + "config_key varchar(30) not null,"
            + "config_desc varchar(100),"
            + "config_type varchar(100),"
            + "config_value text,"
            + "constraint primary key (config_id),"
            + "constraint nk_configs unique (config_key)"
            + ");";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists configs;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(configsCreateSql);
    }

}
