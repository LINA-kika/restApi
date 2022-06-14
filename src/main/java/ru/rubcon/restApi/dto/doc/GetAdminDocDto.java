package ru.rubcon.restApi.dto.doc;

import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.*;


import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class GetAdminDocDto {
    private Long id;
    private Date uploadDate;

    private String docPath;
    private DocType type;
    private String docName;
    private Date lastUpdateDate;

    public GetAdminDocDto(Long id, Date uploadDate, String docPath, DocType type, String docName, Date lastUpdateDate) {
        this.id = id;
        this.uploadDate = new Timestamp(System.currentTimeMillis());
        this.docPath = docPath;
        this.type = type;
        this.docName = docName;
        this.lastUpdateDate = lastUpdateDate;
    }

    public static GetAdminDocDto convertToDocDto(Document doc){
        return new GetAdminDocDto(
                doc.getId(),
                doc.getUploadDate(),
                doc.getDocPath(),
                doc.getType(),
                doc.getDocName(),
                doc.getUpdated()
        );
    }
}
