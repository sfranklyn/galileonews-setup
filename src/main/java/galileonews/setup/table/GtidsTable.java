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
public class GtidsTable {

    private static final String gtidsCreateSql
            = "create table gtids ("
            + "gtid_id int not null auto_increment,"
            + "gtid_pcc varchar(10),"
            + "gtid_gtid varchar(10),"
            + "gtid_signon varchar(10),"
            + "gtid_timestamp timestamp,"
            + "constraint primary key (gtid_id)"
            + ");";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists gtids;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(gtidsCreateSql);
    }

}
