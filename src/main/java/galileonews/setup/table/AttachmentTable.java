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
public class AttachmentTable {

    private static final String attachmentCreateSql
            = "create table attachment ("
            + "attachment_id int not null auto_increment,"
            + "news_id int not null,"
            + "attachment_file_name varchar(250),"
            + "attachment_file_type varchar(30),"
            + "attachment_content mediumblob,"
            + "constraint primary key (attachment_id),"
            + "constraint fk_attachment1 "
            + "  foreign key (news_id) references news (news_id),"
            + "index idx_attachment1 (news_id)"
            + ");";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists attachment;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(attachmentCreateSql);
    }

}
