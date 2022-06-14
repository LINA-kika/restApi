package ru.rubcon.restApi.dto.doc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rubcon.restApi.models.DocType;
import ru.rubcon.restApi.models.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsertAdminDocDto {
    private String docName;
    private DocType type;
    //private Long constId;

    public static Document convertFromInsertDocDto(InsertAdminDocDto insertDocDto) {
        Document doc = new Document();
        doc.setType(insertDocDto.getType());
        doc.setDocName(insertDocDto.getDocName());
        //doc.set
        return doc;
    }
}
