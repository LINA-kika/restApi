package ru.rubcon.restApi.dto.construction;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.rubcon.restApi.models.Construction;


import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class BaseConstructionDto {

    private String region;

    private String city;

    private String street;

    private String building;

    private String constName;

    public static Construction convertFromBaseConstructionDto(BaseConstructionDto baseConstructionDto){
        Construction construction = new Construction();
        construction.setRegion(baseConstructionDto.getRegion());
        construction.setCity(baseConstructionDto.getCity());
        construction.setStreet(baseConstructionDto.getStreet());
        construction.setBuilding(baseConstructionDto.getBuilding());
        construction.setConstName(baseConstructionDto.getConstName());
        return construction;
    }
    public static BaseConstructionDto convertToBaseConstructionDto(Construction construction){
        return new BaseConstructionDto(
                construction.getRegion(),
                construction.getCity(),
                construction.getStreet(),
                construction.getBuilding(),
                construction.getConstName()
        );
    }
}
