package ru.rubcon.restApi.dto.user;


import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.dto.construction.ClientConstructionDto;
import ru.rubcon.restApi.models.User;

import java.util.*;

@Getter
@Setter
public class GetClientUserDto extends BasicClientUserDto {
    private final List<ClientConstructionDto> constructions;


    public GetClientUserDto(String lastName, String firstName, String patronymic, String email, String phone, List<ClientConstructionDto> constructions) {
        super(lastName, firstName, patronymic, email, phone);
        this.constructions = constructions;
    }


    public static GetClientUserDto convertToClientUserDto(User user){
        List<ClientConstructionDto> constructions = new ArrayList<>(ClientConstructionDto.convertToClientConstructionDto(user.getConstructions()));
        Collections.sort(constructions, new Comparator<ClientConstructionDto>() {
            @Override
            public int compare(ClientConstructionDto o1, ClientConstructionDto o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });
        return new GetClientUserDto(
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                constructions);

    }
}
