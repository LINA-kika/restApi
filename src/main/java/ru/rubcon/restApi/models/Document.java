package ru.rubcon.restApi.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;


@Data
@Table(name = "document")
@Entity
public class Document extends BaseEntity{


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_id", referencedColumnName = "id", updatable = false)
    private Construction construction;

    @CreationTimestamp
    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "doc_path")
    private String docPath;

    @Column(name = "doc_name")
    private String docName;

    @Enumerated(EnumType.STRING)
    private DocType type;

}
