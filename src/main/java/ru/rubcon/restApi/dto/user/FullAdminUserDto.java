package ru.rubcon.restApi.dto.user;

import lombok.Getter;
import lombok.Setter;
import ru.rubcon.restApi.dto.construction.AdminConstructionDto;
import ru.rubcon.restApi.models.Construction;
import ru.rubcon.restApi.models.User;

import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

public class FullAdminUserDto extends BasicClientUserDto {
    private String username;
    private Set<AdminConstructionDto> constructions;


    public FullAdminUserDto(String lastName, String firstName, String patronymic, String email, @Pattern(regexp = "(^$|[0-9]{11})") String phone, String username, Set<AdminConstructionDto> constructionDtos) {
        super(lastName, firstName, patronymic, email, phone);
        this.username = username;
        this.constructions = constructionDtos;
    }

    public static FullAdminUserDto convertToFullAdminUserDto(User user){
        Set<AdminConstructionDto> constructionDtos = new HashSet<>();
        for(Construction c: user.getConstructions()){
            constructionDtos.add(AdminConstructionDto.convertToAdminConstructionDto(c));
        }
        return new FullAdminUserDto(
                user.getLastName(),
                user.getFirstName(),
                user.getPatronymic(),
                user.getEmail(),
                user.getPhone(),
                user.getUsername(),
                constructionDtos
        );
    }
}
