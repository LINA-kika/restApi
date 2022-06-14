package ru.rubcon.restApi.dto.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.models.DocType;
import ru.rubcon.restApi.models.Document;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetClientDocDto {
    private Long id;
    private Date uploadDate;

    private String docPath;
    private DocType type;
    private String docName;
    private Long constId;

    public static GetClientDocDto convertToClientDocDto(Document document){
        return new GetClientDocDto(
                document.getId(),
                document.getUploadDate(),
                document.getDocPath(),
                document.getType(),
                document.getDocName(),
                document.getConstruction().getId()
        );
    }
}
