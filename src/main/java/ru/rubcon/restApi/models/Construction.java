package ru.rubcon.restApi.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Entity
@Table(name = "construction")
@Getter
@Setter
public class Construction extends BaseEntity{


    @ManyToOne(fetch = FetchType.LAZY)

    @JsonIgnore
    @JoinColumn(name = "owner", referencedColumnName = "id", updatable = false)
    private User owner;


    private String region;

    private String city;

    private String street;

    private String building;

    private String image;

    private String constName;


    @JsonIgnore
    @OneToMany(mappedBy = "constId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<MasterCall> calls = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "construction", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Document> docs = new HashSet<>();


}
