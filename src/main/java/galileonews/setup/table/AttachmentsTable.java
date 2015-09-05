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

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class AttachmentsTable {

    private static final String attachmentsCreateSql
            = "create table attachments ("
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

    private static final String attachmentsInsertSql
            = "insert into attachments ("
            + "news_id," //1
            + "attachment_file_name," //2               
            + "attachment_file_type," //3
            + "attachment_content" //4
            + ") values ("
            + "?," //1
            + "?," //2
            + "?," //3
            + "?" //4
            + ")";

    public void drop(Statement stmt) throws SQLException {
        stmt.executeUpdate("drop table if exists attachments;");
    }

    public void create(Statement stmt) throws SQLException {
        stmt.executeUpdate(attachmentsCreateSql);
    }

    public int insert(final Map paramMap,
            final Connection conn)
            throws SQLException, IOException {
        int attachmentId = 0;
        try (PreparedStatement attachmentsStmt
                = conn.prepareStatement(attachmentsInsertSql,
                        Statement.RETURN_GENERATED_KEYS)) {
            
            attachmentsStmt.setInt(1, (Integer) paramMap.get("news_id")); 
            String attachmentFileName = (String) paramMap.get("attachment_file_name"); 
            attachmentsStmt.setString(2, attachmentFileName);
            
            String attachmentExtension = FilenameUtils.getExtension(attachmentFileName).toLowerCase();
            switch(attachmentExtension) {
                case "txt":
                    attachmentsStmt.setString(3, "text/plain");
                    break;
                case "htm":
                case "html":
                    attachmentsStmt.setString(3, "text/html");
                    break;
                case "pdf":
                    attachmentsStmt.setString(3, "application/download");
                    break;
                case "jpg":
                case "jpeg":
                    attachmentsStmt.setString(3, "image/jpeg");
                    break;
                case "png":
                    attachmentsStmt.setString(3, "image/png");
                    break;
                default:
                    attachmentsStmt.setString(3, "application/octet-stream");
            }
            
            Path file = FileSystems.getDefault().getPath("src/main/resources", attachmentFileName);
            byte[] byteArray = Files.readAllBytes(file);
            attachmentsStmt.setBytes(4, byteArray);
            attachmentsStmt.executeUpdate();
            ResultSet resultSet = attachmentsStmt.getGeneratedKeys();
            if (resultSet.next()) {
                attachmentId = resultSet.getInt(1);
            }
        }

        return attachmentId;
    }
}
