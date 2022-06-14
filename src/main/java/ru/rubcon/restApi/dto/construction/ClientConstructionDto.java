package ru.rubcon.restApi.dto.construction;

import lombok.Data;
import ru.rubcon.restApi.dto.call.CallTableDto;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.Document;
import ru.rubcon.restApi.models.MasterCall;

import java.io.Serializable;
import java.util.*;

@Data
public class ClientConstructionDto {
    private final Long id;
    private final String image;
    private final String constName;

    public static Set<ClientConstructionDto> convertToClientConstructionDto(Set<Construction> constructions) {
        Set<ClientConstructionDto> set = new HashSet<>();
        for (Construction construction : constructions) {
            set.add(new ClientConstructionDto(construction.getId(), construction.getImage(), construction.getConstName()
                    ));
        }
        return set;
    }
}