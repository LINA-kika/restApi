package ru.rubcon.restApi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;


@Entity
@Data
@Table(name = "mastercall")
public class MasterCall extends BaseEntity {


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "construction_id", referencedColumnName = "id", updatable = false)
    private Construction constId;

    private String theme;

    private String reason;

    @Column(name = "report_path")
    private String reportPath;

    @Column(name = "departure_call_date")
    private Date departureCallDate;

    @Column(name = "master_arriving_date")
    @LastModifiedDate
    private Date masterArrivingDate;

    @Enumerated(EnumType.ORDINAL)
    private CallStatus callStatus;


}
