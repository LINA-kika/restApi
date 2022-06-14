package ru.rubcon.restApi.dto.construction;
import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.dto.call.AdminCallDto;
import ru.rubcon.restApi.dto.doc.GetAdminDocDto;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.models.MasterCall;


import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class AdminConstructionDto extends BaseConstructionDto {

    private Set<AdminCallDto> calls = new HashSet<>();

    private Set<GetAdminDocDto> docs = new HashSet<>();

    private String image;

    public AdminConstructionDto(String region, String city, String street, String building, String image, Set<AdminCallDto> calls, Set<GetAdminDocDto> docs, String constName) {
        super(region, city, street, building, constName);
        this.calls = calls;
        this.docs = docs;
        this.image = image;
    }

    public static AdminConstructionDto convertToAdminConstructionDto(Construction construction) {
        Set<AdminCallDto> calls = new HashSet<>();
        for (MasterCall c : construction.getCalls()) {
            AdminCallDto.convertToAdminCallDto(c);
        }
        Set<GetAdminDocDto> docs = new HashSet<>();
        for (Document c : construction.getDocs()) {
            GetAdminDocDto.convertToDocDto(c);
        }
        return new AdminConstructionDto(
                construction.getRegion(),
                construction.getCity(),
                construction.getStreet(),
                construction.getBuilding(),
                construction.getImage(),
                calls,
                docs,
                construction.getConstName()

        );
    }


}
